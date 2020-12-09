package vn.yinx.listenenglish.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.adapter.FileAdapter;
import vn.yinx.listenenglish.adapter.FolderAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtAreaAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtHomeAdapter;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.entity.Playlist;

public class FragmentPlaylist extends BaseFragment implements View.OnClickListener {
    private TextView listName, addToPlaylist, cancelBtn;
    private Button playMusic;
    private RecyclerView files;
    private static ListMusic listPlaying;
    private ListMusic listMusic;
    private FileAdapter fileAdapter;
    private LinearLayout playlistArea;
    private FrameLayout wrapper;
    private RecyclerView playlists;
    private PlaylistAtAreaAdapter playlistAtAreaAdapter;

    public FragmentPlaylist(ListMusic listMusic) {
//        if (listMusic == null) {
//            this.listMusic = FragmentPlaylist.listPlaying;
//        } else {
//            this.listMusic = listMusic;
//        }
        this.listMusic = listMusic;

    }

    public static FragmentPlaylist newInstance(ListMusic listMusic) {
        FragmentPlaylist fragment = new FragmentPlaylist(listMusic);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void init() {
        listName = findViewById(R.id.list_name);
        wrapper = findViewById(R.id.wrapper);
        listName.setText(listMusic.getName());
        addToPlaylist = findViewById(R.id.add_to_playlist);
        files = findViewById(R.id.files);

        LinearLayoutManager layoutManagerFolder = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        files.setLayoutManager(layoutManagerFolder);
        fileAdapter = new FileAdapter(mContext, listMusic.getFiles());
        files.setAdapter(fileAdapter);

        playMusic = findViewById(R.id.play_music);
        playMusic.setOnClickListener(this);

        addToPlaylist = findViewById(R.id.add_to_playlist);
        addToPlaylist.setOnClickListener(this);

        cancelBtn = findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this);

        playlistArea = findViewById(R.id.playlist_area);

        LinearLayoutManager layoutManagerPlaylist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        playlists = findViewById(R.id.playlists);
        playlists.setLayoutManager(layoutManagerPlaylist);
        ArrayList<Playlist> items = new ArrayList<>();
        items.add(new Playlist());
        items.addAll(Stores.playlists);
        playlistAtAreaAdapter = new PlaylistAtAreaAdapter(mContext, this, items);
        playlists.setAdapter(playlistAtAreaAdapter);
    }

    public void hidePlayListArea(){
        playlistArea.setVisibility(View.GONE);
        wrapper.setBackgroundColor(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music:
                break;
            case R.id.add_to_playlist:
                int numberSelected = 0;
                for (FileMusic fileMusic : listMusic.getFiles()) {
                    if (fileMusic.isChecked())
                        numberSelected++;
                }
                if (numberSelected > 0){
                    wrapper.setBackgroundColor(Color.parseColor("#A6A6A6"));
                    playlistArea.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.cancel_button:
                playlistArea.setVisibility(View.GONE);
                wrapper.setBackgroundColor(0);
                break;
        }
    }
}