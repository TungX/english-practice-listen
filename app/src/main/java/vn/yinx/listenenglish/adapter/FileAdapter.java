package vn.yinx.listenenglish.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.entity.FileMusic;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<FileMusic> files;

    public FileAdapter(Context context, ArrayList<FileMusic> files) {
        this.context = context;
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_file, parent, false);
        return new FileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileMusic fileMusic = this.files.get(position);
        holder.content.setText(fileMusic.getName());
        holder.selected.setChecked(fileMusic.isChecked());
        Log.d("onBindViewHolder", "Checked: "+fileMusic.isChecked());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView content;
        private CheckBox selected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            content = itemView.findViewById(R.id.file_name);
            selected = itemView.findViewById(R.id.selected);
            selected.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(v.getId() == R.id.selected){
                files.get(position).setChecked(selected.isChecked());
            }
        }
    }
}
