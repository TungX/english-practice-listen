package vn.yinx.listenenglish.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.adapter.FolderAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtHomeAdapter;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.Sentence;
import vn.yinx.listenenglish.task.ScanTask;
import vn.yinx.listenenglish.util.TextUtils;

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private LinearLayout folderArea;
    private FolderAdapter folderAdapter;
    private PlaylistAtHomeAdapter playlistAtHomeAdapter;
    private RecyclerView folders, playlists;
    private TextView scan;
    private boolean scanning = false;
    private static FragmentHome fragment;
    public static FragmentHome newInstance() {
        if(FragmentHome.fragment !=null)
            return FragmentHome.fragment;
        FragmentHome.fragment = new FragmentHome();
        Bundle args = new Bundle();
        FragmentHome.fragment.setArguments(args);
        return FragmentHome.fragment;
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

    public synchronized boolean isScanning(){
        return scanning;
    }

    public synchronized void finish(){
        scanning = false;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                scan.setText(R.string.scan);

            }
        });

    }

    public void showFolderArea(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                folderArea.setVisibility(View.VISIBLE);
            }
        });
    }

    public void resetFolderAdapter(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                folderAdapter.notifyDataSetChanged();
            }
        });
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
                Log.d("Onclick", "request scan");
                if (isScanning()) {
                    break;
                }
                scanning = true;
                scan.setText(R.string.scanning);
                new ScanTask(this).start();
                break;
        }
    }


}