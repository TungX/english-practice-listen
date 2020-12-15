package vn.yinx.listenenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.fragment.FragmentPlay;
import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.entity.Sentence;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sentence> sentences;

    public LyricAdapter(Context context, ArrayList<Sentence> sentences) {
        this.context = context;
        this.sentences = sentences;
        if (this.sentences == null) {
            this.sentences = new ArrayList<>();
        } else {
            this.sentences.get(0).setActive(true);
        }
    }

    public void updateStatus(int current, int toPlay) {
        this.sentences.get(current).setActive(false);
        this.sentences.get(toPlay).setActive(true);
        notifyItemChanged(current);
        notifyItemChanged(toPlay);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.sentence_lyric, parent, false);
        return new LyricAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sentence sentence = this.sentences.get(position);
        holder.content.setText(sentence.getContent());
        if (sentence.isActive()) {
            holder.content.setTextColor(Color.parseColor("#FF8C00"));
            holder.repeat.setVisibility(View.VISIBLE);
        } else {
            holder.content.setTextColor(Color.BLACK);
            holder.repeat.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.sentences.size();
    }

//    public View getView(final int position, View convertView, ViewGroup parent) {
//        View viewSentence = View.inflate(parent.getContext(), R.layout.sentence_lyric, null);
//
//        Sentence sentence = getItem(position);
//
//        TextView content = viewSentence.findViewById(R.id.sentence_content);
//        content.setText(sentence.getContent());
//        if(sentence.isActive()){
//            content.setTextColor(Color.parseColor("#FF8C00"));
//        }else {
//            content.setTextColor(Color.BLACK);
//        }
//        content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("OnClick", "Click at sentence " + position);
//                if(Stores.getCurrentSentence() != position){
//                    sentences.get(Stores.getCurrentSentence()).setActive(false);
//                    Stores.fragmentPlay.updateSeek(sentences.get(position).getStart(), position);
//                }
//                Stores.getMp().seekTo(sentences.get(position).getStart());
//                Stores.setCurrentSentence(position);
//                sentences.get(Stores.getCurrentSentence()).setActive(true);
//                content.setTextColor(Color.parseColor("#FF8C00"));
//                if(!Stores.getMp().isPlaying()){
//                    Stores.fragmentPlay.switchPlayIcon();
//                }
//            }
//        });
//        Button btnReload = viewSentence.findViewById(R.id.lyric_sentence_reload);
//        if (sentences.get(position).isRepeating()) {
//            btnReload.setBackgroundResource(R.drawable.cancel);
//        }else{
//            btnReload.setBackgroundResource(R.drawable.repeat);
//        }
//        btnReload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("OnClick", "Reload at sentence " + position);
//                if (sentences.get(position).isRepeating()) {
//                    btnReload.setBackgroundResource(R.drawable.cancel);
//                    sentences.get(position).setRepeating(false);
//                } else {
//                    btnReload.setBackgroundResource(R.drawable.repeat);
//                    sentences.get(position).setRepeating(true);
//                }
//
//            }
//        });
//        return viewSentence;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView content;
        private Button repeat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            content = itemView.findViewById(R.id.sentence_content);
            repeat = itemView.findViewById(R.id.lyric_sentence_repeat);
            repeat.setOnClickListener(this);
            content.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == R.id.sentence_content) {
                if (Stores.getCurrentSentence() != position) {
                    Stores.fragmentPlay.updateSeek(sentences.get(position).getStart(), Stores.getCurrentSentence(), position);
                }
                Stores.getMp().seekTo(sentences.get(position).getStart());
                Stores.setCurrentSentence(position);
                sentences.get(Stores.getCurrentSentence()).setActive(true);
                content.setTextColor(Color.parseColor("#FF8C00"));
                if (!Stores.getMp().isPlaying()) {
                    Stores.fragmentPlay.switchPlayIcon();
                }
                return;
            }
            if (v.getId() == R.id.lyric_sentence_repeat) {
                if (!sentences.get(position).isActive())
                    return;
                if (sentences.get(position).isRepeating()) {
                    repeat.setBackgroundResource(R.drawable.repeat);
                    sentences.get(position).setRepeating(false);
                } else {
                    repeat.setBackgroundResource(R.drawable.cancel);
                    sentences.get(position).setRepeating(true);
                }
            }
        }
    }


}
