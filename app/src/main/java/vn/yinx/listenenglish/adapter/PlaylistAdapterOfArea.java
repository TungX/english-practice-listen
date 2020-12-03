package vn.yinx.listenenglish.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.entity.Playlist;

public class PlaylistAdapterOfArea extends BaseAdapter {

    private ArrayList<Playlist> playlists;

    public PlaylistAdapterOfArea(ArrayList<Playlist> folderMusics) {
        this.playlists = folderMusics;
    }

    @Override
    public int getCount() {
        return this.playlists.size();
    }

    @Override
    public Playlist getItem(int position) {
        return this.playlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.playlists.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewSentence;

        if(position == 0){
            viewSentence = View.inflate(parent.getContext(), R.layout.item_playlist, null);
        }else{
            viewSentence = View.inflate(parent.getContext(), R.layout.item_playlist, null);
        }

        Playlist folder = getItem(position);

        TextView content = viewSentence.findViewById(R.id.playlist_name);
        content.setText(folder.getName());

        viewSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return viewSentence;
    }


}
