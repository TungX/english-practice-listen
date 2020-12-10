package vn.yinx.listenenglish.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.adapter.FileAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtAreaAdapter;
import vn.yinx.listenenglish.dialog.CreatePlaylistDialog;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.entity.PlaylistHasFileMusic;

public class FragmentPlaylist extends BaseFragment implements View.OnClickListener {
    private TextView listName, cancelBtn;
    private ImageView menuIcon;
    private Button playMusic;
    private RecyclerView files;
    private static ListMusic listPlaying;
    private ListMusic listMusic;
    private FileAdapter fileAdapter;
    private LinearLayout playlistArea;
    private FrameLayout wrapper;
    private RecyclerView playlists;
    private PlaylistAtAreaAdapter playlistAtAreaAdapter;
    private PopupMenu popup;

    public FragmentPlaylist(ListMusic listMusic) {
        if (listMusic == null) {
            this.listMusic = FragmentPlaylist.listPlaying;
        } else {
            this.listMusic = listMusic;
        }

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

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_to_playlist:
                wrapper.setBackgroundColor(Color.parseColor("#A6A6A6"));
                playlistArea.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_remove_playlist:
                ArrayList<FileMusic> fileMusics;
                PlaylistHasFileMusic playlistHasFileMusic = new PlaylistHasFileMusic();
                switch (listMusic.getType()) {
                    case "folder":
                        fileMusics = new ArrayList<>();
                        for (FileMusic fileMusic : listMusic.getFiles()) {
                            if (fileMusic.isChecked()){
                                try {
                                    fileMusic.delete();
                                    playlistHasFileMusic.delete("file_id = "+fileMusic.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                fileMusics.add(fileMusic);
                            }
                        }
                        break;
                    case "playlist":
                        fileMusics = new ArrayList<>();
                        for (FileMusic fileMusic : listMusic.getFiles()) {
                            if (fileMusic.isChecked()){
                                try {
                                    playlistHasFileMusic.delete("file_id = "+fileMusic.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                fileMusics.add(fileMusic);
                            }
                        }
                        if(fileMusics != null){
                            listMusic.setFiles(fileMusics);
                            fileAdapter.updateFiles(listMusic.getFiles());
                        }
                        break;
                }
                break;
        }
        return true;
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
        menuIcon = findViewById(R.id.menu_add_to_playlist);
        files = findViewById(R.id.files);

        LinearLayoutManager layoutManagerFolder = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        files.setLayoutManager(layoutManagerFolder);
        fileAdapter = new FileAdapter(mContext, listMusic.getFiles());
        files.setAdapter(fileAdapter);

        playMusic = findViewById(R.id.play_music);
        playMusic.setOnClickListener(this);

        menuIcon = findViewById(R.id.menu);
        menuIcon.setOnClickListener(this);

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

    public void addToPlaylist(Playlist playlist) {
        try {
            for (FileMusic fileMusic : listMusic.getFiles()) {
                if (!fileMusic.isChecked()) {
                    continue;
                }
                PlaylistHasFileMusic playlistHasFileMusic = new PlaylistHasFileMusic();
                playlistHasFileMusic.setPlaylistId(playlist.getId());
                playlistHasFileMusic.setFileId(fileMusic.getId());
                playlistHasFileMusic.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        playlistArea.setVisibility(View.GONE);
        wrapper.setBackgroundColor(0);
    }

    public void showDialogCreatePlaylist() {
        CreatePlaylistDialog dialogEnterAccount = new CreatePlaylistDialog(this);
        dialogEnterAccount.show(getParentFragmentManager(), CreatePlaylistDialog.class.getSimpleName());
    }

    public void addPlaylist(Playlist playlist) {
        playlistAtAreaAdapter.addPlaylist(playlist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music:
                break;
            case R.id.menu:
                popup = new PopupMenu(getContext(), this.menuIcon);
                popup.inflate(R.menu.action_playlist_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                popup.show();
//                int numberSelected = 0;
//                for (FileMusic fileMusic : listMusic.getFiles()) {
//                    if (fileMusic.isChecked())
//                        numberSelected++;
//                }
//                if (numberSelected > 0) {
//                    wrapper.setBackgroundColor(Color.parseColor("#A6A6A6"));
//                    playlistArea.setVisibility(View.VISIBLE);
//                }

                break;
            case R.id.cancel_button:
                playlistArea.setVisibility(View.GONE);
                wrapper.setBackgroundColor(0);
                break;
        }
    }
}