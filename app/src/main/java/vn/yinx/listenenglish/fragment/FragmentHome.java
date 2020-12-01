package vn.yinx.listenenglish.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.adapter.FolderAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAdapter;

public class FragmentHome extends BaseFragment implements View.OnClickListener{
    private LinearLayout folderArea;
    private FolderAdapter folderAdapter;
    private PlaylistAdapter playlistAdapter;
    private ListView folders, playlists;
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
        folderAdapter = new FolderAdapter(Stores.folderMusics);
        folders.setAdapter(folderAdapter);
        findViewById(R.id.btn_showmore).setOnClickListener(this);

        playlists = findViewById(R.id.playlists);
        playlistAdapter = new PlaylistAdapter(Stores.playlists);
        playlists.setAdapter(playlistAdapter);
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
        switch (v.getId()){
            case R.id.btn_showmore:
                if(folderArea.getVisibility() == View.GONE){
                    folderArea.setVisibility(View.VISIBLE);
                }else{
                    folderArea.setVisibility(View.GONE);
                }
        }
    }
}