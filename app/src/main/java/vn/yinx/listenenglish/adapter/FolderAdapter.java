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
import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private ArrayList<FolderMusic> folderMusics;
    private Context context;

    public FolderAdapter(Context context, ArrayList<FolderMusic> folderMusics) {
        this.folderMusics = folderMusics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FolderMusic folder = this.folderMusics.get(position);
        holder.content.setText(folder.getName());
        holder.avatar.setText(folder.getName().charAt(0) + "");
    }

    @Override
    public long getItemId(int position) {
        return this.folderMusics.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (this.folderMusics == null)
            return 0;
        return folderMusics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView content, avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            content = itemView.findViewById(R.id.folder_name);
            avatar = itemView.findViewById(R.id.item_avatar);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            FolderMusic folder = folderMusics.get(position);
            ListMusic listMusic = new ListMusic("folder");
            listMusic.setName(folder.getName());
            try {
                if (folder.getFiles() == null) {
                    FileMusic fileMusic = new FileMusic();
                    ArrayList<FileMusic> files = fileMusic.getAll("SELECT * FROM " + fileMusic.getTableName() + " WHERE folder_id = " + folder.getId());
                    folder.setFiles(files);
                } else {
                    for (FileMusic f : folder.getFiles()) {
                        f.setChecked(false);
                    }
                }
                listMusic.setFiles(folder.getFiles());
                Stores.mainActivity.openFragment(FragmentPlaylist.newInstance(listMusic));
                Stores.currentNavigation = R.id.navigation_playlist;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
