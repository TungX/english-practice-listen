package vn.yinx.listenenglish.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.adapter.FolderAdapter;
import vn.yinx.listenenglish.adapter.PlaylistAtHomeAdapter;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.ListMusic;

public class FragmentPlaylist extends BaseFragment implements View.OnClickListener{
    private LinearLayout folderArea;
    private static ListMusic listPlaying;
    private static ListMusic listMusic;
    public static FragmentPlaylist newInstance(ListMusic listMusic) {
        if(listMusic != null){
            FragmentPlaylist.listMusic = listMusic;
        }
        FragmentPlaylist fragment = new FragmentPlaylist();
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