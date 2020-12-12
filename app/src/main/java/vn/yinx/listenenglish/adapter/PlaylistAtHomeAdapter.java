package vn.yinx.listenenglish.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.entity.PlaylistHasFileMusic;
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
        if(this.playlists == null)
            return 0;
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
            Playlist playlist = playlists.get(position);
            PlaylistHasFileMusic playlistHasFileMusic = new PlaylistHasFileMusic();
            ListMusic listMusic = new ListMusic("playlist");
            listMusic.setName(playlist.getName());
            try {
                FileMusic fileMusic = new FileMusic();
                String query = "SELECT A.* FROM fileTable AS A INNER JOIN playlistHasFileTable AS B ON A.id = B.file_id WHERE B.playlist_id = "+playlist.getId();
                query = query.replace("fileTable", fileMusic.getTableName()).replace("playlistHasFileTable", playlistHasFileMusic.getTableName());
                Log.d("PlayListAtHome", query);
                ArrayList<FileMusic> files = fileMusic.getAll(query);
                playlist.setFiles(files);
                listMusic.setFiles(playlist.getFiles());
                Stores.mainActivity.openFragment(FragmentPlaylist.newInstance(listMusic));
                Stores.currentNavigation = R.id.navigation_playlist;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
