package vn.yinx.listenenglish.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.adapter.FolderAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtHomeAdapter;
import vn.yinx.listenenglish.entity.FolderMusic;

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private LinearLayout folderArea;
    private FolderAdapter folderAdapter;
    private PlaylistAtHomeAdapter playlistAtHomeAdapter;
    private RecyclerView folders, playlists;
    private TextView scan;

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void init() {
        folders = findViewById(R.id.folders);
        folderArea = findViewById(R.id.folder_area);
        LinearLayoutManager layoutManagerFolder = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        folders.setLayoutManager(layoutManagerFolder);
        folderAdapter = new FolderAdapter(mContext, Stores.folderMusics);
        folders.setAdapter(folderAdapter);
        findViewById(R.id.btn_showmore).setOnClickListener(this);

        playlists = findViewById(R.id.playlists);
        LinearLayoutManager layoutManagerPlaylist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        playlists.setLayoutManager(layoutManagerPlaylist);
        playlistAtHomeAdapter = new PlaylistAtHomeAdapter(mContext, Stores.playlists);
        playlists.setAdapter(playlistAtHomeAdapter);

        scan = findViewById(R.id.scan_audio);
        scan.setOnClickListener(this);
//        File sdCard = Environment.getStorageDirectory();
//        Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());
//        ArrayList<File> files = new ArrayList<>();
//        loadAudioFiles(files, sdCard);
        try {
//            mp.setDataSource(files.get(0).getAbsolutePath());
//            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        File dir = new File(sdCard, "/f-faces/images/" + format.format(date));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_showmore:
                if (folderArea.getVisibility() == View.GONE) {
                    folderArea.setVisibility(View.VISIBLE);
                } else {
                    folderArea.setVisibility(View.GONE);
                }
                break;
            case R.id.scan_audio:
                File sdCard = Environment.getExternalStorageDirectory();
                ArrayList<File> files = new ArrayList<>();
                Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());

                loadAudioFiles(files, sdCard);
                try{
                    sdCard = Environment.getStorageDirectory();
                    Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());
                    loadAudioFiles(files, sdCard);
                }catch (Exception e){

                }
                HashMap<String, FolderMusic> folers = new HashMap<>();
                for(File f: files){
                    if(folers.containsKey(f.getParentFile().getAbsolutePath())){
//                        folers.get()
                    }
//                    File lyric = new File(f.getAbsolutePath().replace("mp3", "TextGrid"));
//                    if(!lyric.isFile()){
//                        continue;
//                    }
                    Log.d("MainActivityOnCreate", f.getAbsolutePath());
                }
                break;
        }
    }

    private void loadAudioFiles(ArrayList<File> files, File fi) {
        if(fi.isFile() && fi.getName().toLowerCase().endsWith("mp3")){
            Log.d("MainActivityOnCreate", fi.getAbsolutePath());
            files.add(fi);
            return;
        }
        if(!fi.isDirectory()){
            return;
        }
        File[] arrFiles = fi.listFiles();
        for(File f: arrFiles){
            loadAudioFiles(files, f);
        }
    }
}