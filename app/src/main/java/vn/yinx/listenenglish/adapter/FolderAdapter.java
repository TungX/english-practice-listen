package vn.yinx.listenenglish.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.entity.Sentence;
import vn.yinx.listenenglish.fragment.FragmentPlay;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;

public class FolderAdapter extends BaseAdapter {

    private ArrayList<FolderMusic> folderMusics;

    public FolderAdapter(ArrayList<FolderMusic> folderMusics) {
        this.folderMusics = folderMusics;
    }

    @Override
    public int getCount() {
        return this.folderMusics.size();
    }

    @Override
    public FolderMusic getItem(int position) {
        return this.folderMusics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.folderMusics.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewSentence = View.inflate(parent.getContext(), R.layout.item_folder, null);

        FolderMusic folder = getItem(position);

        TextView content = viewSentence.findViewById(R.id.folder_name);
        content.setText(folder.getName());

        viewSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolderMusic folder = getItem(position);
                ListMusic listMusic = new ListMusic();
                listMusic.setName(folder.getName());
                try {
                    if (folder.getFiles() == null) {
                        FileMusic fileMusic = new FileMusic();
                        ArrayList<FileMusic> files = fileMusic.getAll("SELECT * FROM " + fileMusic.getTableName() + " WHERE _folderId = " + folder.getId());
                        folder.setFiles(files);
                    }
                    listMusic.setFiles(folder.getFiles());
                    for (int i = 0; i < 10; i++) {
                        FileMusic fm = new FileMusic();
                        fm.setName("File " + (i + 1));
                        listMusic.getFiles().add(fm);
                    }
                    Stores.mainActivity.openFragment(FragmentPlaylist.newInstance(listMusic));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return viewSentence;
    }


}
