package vn.yinx.listenenglish.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;

public class PlaylistAtHomeAdapter extends RecyclerView.Adapter<PlaylistAtHomeAdapter.ViewHolder> {

    private ArrayList<Playlist> playlists;
    private Context context;

    public PlaylistAtHomeAdapter(Context context, ArrayList<Playlist> folderMusics) {
        this.playlists = folderMusics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_playlist, parent, false);
        return new PlaylistAtHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAtHomeAdapter.ViewHolder holder, int position) {
        Playlist playlist = this.playlists.get(position);
        if(playlist.getName() != null){
            holder.content.setText(playlist.getName());
            holder.avatar.setText(""+playlist.getName().charAt(0));
        }
    }

    @Override
    public long getItemId(int position) {
        return this.playlists.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return this.playlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView content, avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            content = itemView.findViewById(R.id.playlist_name);
            avatar = itemView.findViewById(R.id.item_avatar);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Playlist folder = playlists.get(position);
            ListMusic listMusic = new ListMusic();
            listMusic.setName(folder.getName());
            try {
                if (folder.getFiles() == null) {
                    FileMusic fileMusic = new FileMusic();
                    ArrayList<FileMusic> files = fileMusic.getAll("SELECT * FROM " + fileMusic.getTableName() + " WHERE folder_id = " + folder.getId());
                    folder.setFiles(files);
                }
                listMusic.setFiles(folder.getFiles());
                for (int i = 0; i < 10; i++) {
                    FileMusic fm = new FileMusic();
                    fm.setName("File " + (i + 1));
                    listMusic.getFiles().add(fm);
                }
                Stores.mainActivity.openFragment(FragmentPlaylist.newInstance(listMusic));
                Stores.currentNavigation = R.id.navigation_playlist;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
