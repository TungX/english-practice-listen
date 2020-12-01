package vn.yinx.listenenglish.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

import vn.yinx.listenenglish.AudioRunning;
import vn.yinx.listenenglish.adapter.LyricAdapter;
import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.entity.Sentence;

public class FragmentPlay extends BaseFragment implements View.OnClickListener{
    MediaPlayer mp;
    private SeekBar seekbar;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    private LyricAdapter lyricAdapter;
    private ListView lyrics;
    private Button btnPlay;
    public static FragmentPlay newInstance() {
        FragmentPlay fragment = new FragmentPlay();
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
        return inflater.inflate(R.layout.fragment_play, container, false);
    }
    @Override
    public void init() {
        Stores.fragmentPlay = this;
        seekbar = findViewById(R.id.playerSeekBar);
        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        lyrics = findViewById(R.id.lyrics);
        seekbar.setClickable(false);


        ArrayList<Sentence> sentences = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Sentence sentence = new Sentence();
            sentence.setStart(i * 2000);
            sentence.setEnd(sentence.getStart() + 1990);
            sentence.setContent("Sentence at " + i);
            sentences.add(sentence);
        }
        lyricAdapter = new LyricAdapter(sentences);
        lyrics.setAdapter(lyricAdapter);


//        mp = new MediaPlayer();
        mp = MediaPlayer.create(mContext, R.raw.kiss_the_rain);
        Stores.setMp(mp);
        Stores.setCurrentSentence(0);
        new AudioRunning(this, sentences).start();
        finalTime = mp.getDuration();
        startTime = mp.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        seekbar.setProgress((int) startTime);
//        File sdCard = Environment.getStorageDirectory();
//        Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());
//        ArrayList<File> files = new ArrayList<>();
//        loadAudioFiles(files, sdCard);
        try {
//            mp.setDataSource(files.get(0).getAbsolutePath());
//            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        File dir = new File(sdCard, "/f-faces/images/" + format.format(date));

    }

    long startTouch = 0;

    public void updateSeek(int time, int position) {
        Log.d("updateSeek", "Time: " + time);
        seekbar.setProgress(time);
        if (position > -1) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    lyricAdapter.notifyDataSetChanged();
                    if (System.currentTimeMillis() - startTouch > 3000) {
                        lyrics.smoothScrollToPositionFromTop(position, 500);
                    }
                }
            });
        }


    }

    public void loadAudioFiles(ArrayList<File> files, File file) {
        if (files.size() > 0) {
            return;
        }
        if (file.isFile()) {
            if (file.getName().toLowerCase().endsWith("mp3")) {
                files.add(file);
            }
            return;
        }

        if (!file.isDirectory()) {
            return;
        }
        File[] tfs = file.listFiles();
        if (tfs == null) {
            return;
        }
        for (File fi : tfs) {
            loadAudioFiles(files, fi);
        }
    }

    public void onClickPrev() {

    }

    public void onClickNext() {

    }

    public void switchPlayIcon(){
        if (Stores.getMp().isPlaying()) {
            Stores.getMp().pause();
            btnPlay.setBackgroundResource(R.drawable.play);
        } else {
            Stores.getMp().start();
            btnPlay.setBackgroundResource(R.drawable.pause);
        }
    }

    public void onClickPlayPause() {
        switchPlayIcon();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                onClickPlayPause();
                break;
        }
    }
}