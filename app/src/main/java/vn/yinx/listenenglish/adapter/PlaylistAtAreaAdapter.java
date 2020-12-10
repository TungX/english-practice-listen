package vn.yinx.listenenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.dialog.CreatePlaylistDialog;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;

public class PlaylistAtAreaAdapter extends RecyclerView.Adapter<PlaylistAtAreaAdapter.ViewHolder> {

    private ArrayList<Playlist> playlists;
    private Context context;
    private FragmentPlaylist fragmentPlaylist;

    public PlaylistAtAreaAdapter(Context context, FragmentPlaylist fragmentPlaylist, ArrayList<Playlist> playlists) {
        this.playlists = playlists;
        this.context = context;
        this.fragmentPlaylist = fragmentPlaylist;
    }

    public void addPlaylist(Playlist playlist){
        this.playlists.add(playlist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_playlist_area, parent, false);
        return new PlaylistAtAreaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAtAreaAdapter.ViewHolder holder, int position) {
        Playlist playlist = this.playlists.get(position);
        if(playlist.getId() == -1){
            holder.content.setText(R.string.create_playlist);
            holder.avatar.setText("+");
        }else{
            holder.content.setText(playlist.getName());
            holder.avatar.setText(playlist.getName().charAt(0) + "");
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
            if(position == 0){
                fragmentPlaylist.showDialogCreatePlaylist();
            }else{
                fragmentPlaylist.addToPlaylist(playlists.get(position));
            }

        }
    }


}
