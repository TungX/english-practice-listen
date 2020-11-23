package vn.yinx.listenenglish;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LyricAdapter extends BaseAdapter {

    private ArrayList<Sentence> sentences;

    public LyricAdapter(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    @Override
    public int getCount() {
        return this.sentences.size();
    }

    @Override
    public Sentence getItem(int position) {
        return this.sentences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.sentences.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewSentence;
        if (convertView == null) {
            viewSentence = View.inflate(parent.getContext(), R.layout.sentence_lyric, null);
        } else viewSentence = convertView;

        Sentence sentence = getItem(position);

        TextView content = viewSentence.findViewById(R.id.sentence_content);
        content.setText(sentence.getContent());
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick", "Click at sentence " + position);
            }
        });
        Button btnReload = viewSentence.findViewById(R.id.lyric_sentence_reload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick", "Reload at sentence " + position);
                if (sentences.get(position).isRepeating()) {
                    btnReload.setBackgroundResource(R.drawable.repeat);
                    sentences.get(position).setRepeating(false);
                } else {
                    btnReload.setBackgroundResource(R.drawable.cancel);
                    sentences.get(position).setRepeating(true);
                }

            }
        });
        return viewSentence;
    }


}
