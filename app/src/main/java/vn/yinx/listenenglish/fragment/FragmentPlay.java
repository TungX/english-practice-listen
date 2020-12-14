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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import vn.yinx.listenenglish.entity.ListMusic;
import vn.yinx.listenenglish.task.AudioRunning;
import vn.yinx.listenenglish.adapter.LyricAdapter;
import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.entity.Sentence;

public class FragmentPlay extends BaseFragment implements View.OnClickListener {
    MediaPlayer mp;
    private SeekBar seekbar;
    private double startTime = 0;
    private double finalTime = 0;
    private LyricAdapter lyricAdapter;
    private ListView lyrics;
    private Button btnPlay;
    private static FragmentPlay fragmentPlay;
    public static ListMusic listPlaying;
    private static int musicPlayingIndex = 0;
    private static int requestPlayIndex = 0;
    private TextView fileName, currentTime, durationTime;

    public static FragmentPlay newInstance() {
        if (fragmentPlay != null)
            return fragmentPlay;
        fragmentPlay = new FragmentPlay();
        Bundle args = new Bundle();
        fragmentPlay.setArguments(args);
        return fragmentPlay;
    }

    public static synchronized void setListPlaying(ListMusic listMusic, int index) {
        listPlaying = listMusic;
        if (index < 0 || index >= listMusic.getFiles().size()) {
            index = 0;
        }
        requestPlayIndex = index;
    }

    public static synchronized void setListPlaying(int index) {
        requestPlayIndex = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("FragmentPlay", "onCreateView");
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void init() {
        Log.d("FragmentPlay", "Init FragmentPlay");
        Log.d("FragmentPlay", "musicPlayingIndex = " + musicPlayingIndex);
        Stores.fragmentPlay = this;
        fileName = findViewById(R.id.file_name);
        seekbar = findViewById(R.id.playerSeekBar);
        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_prev).setOnClickListener(this);
        lyrics = findViewById(R.id.lyrics);
        seekbar.setClickable(false);
        currentTime = findViewById(R.id.current_time);
        durationTime = findViewById(R.id.duration_time);
        ArrayList<Sentence> sentences = new ArrayList<>();
        lyricAdapter = new LyricAdapter();
        lyrics.setAdapter(lyricAdapter);
        playMusic();
    }

    public void playMusic() {
        if (requestPlayIndex > -1) {
            musicPlayingIndex = requestPlayIndex;
            if (Stores.audioRunningTask != null) {
                Stores.audioRunningTask.setStopped();
            }
            if (Stores.getMp() != null) {
                Stores.getMp().stop();
            }
            mp = new MediaPlayer();
            try {
                mp.setDataSource(listPlaying.getFiles().get(musicPlayingIndex).getAudioPath());
                mp.prepare();
                Stores.setMp(mp);
                startTime = mp.getCurrentPosition();
                currentTime.setText(String.format("%2d:%2d", ((long) finalTime / 60000),
                        TimeUnit.MILLISECONDS.toSeconds((long) (finalTime % 60000))));
                seekbar.setProgress(0);
                currentTime.setText(" 0: 0");
                Stores.setCurrentSentence(0);
                mp.start();
                Stores.audioRunningTask = new AudioRunning(this, mp, listPlaying.getFiles().get(musicPlayingIndex).getSentences());
                Stores.audioRunningTask.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finalTime = Stores.getMp().getDuration();
        seekbar.setMax((int) finalTime);
        durationTime.setText(String.format("%2d:%2d", ((long) finalTime / 60000),
                TimeUnit.MILLISECONDS.toSeconds((long) (finalTime % 60000))));
        if (listPlaying != null && listPlaying.getFiles() != null && musicPlayingIndex > -1 && musicPlayingIndex < listPlaying.getFiles().size())
            fileName.setText(listPlaying.getFiles().get(musicPlayingIndex).getName());
    }

    long startTouch = 0;

    public void updateSeek(int time, int position) {
        Log.d("updateSeek", "Time: " + time);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                seekbar.setProgress(time);
                currentTime.setText(String.format("%2d:%2d", ((long) time / 60000),
                        TimeUnit.MILLISECONDS.toSeconds((long) (time % 60000))));
//                    lyricAdapter.notifyDataSetChanged();

//                if (System.currentTimeMillis() - startTouch > 3000) {
//                    lyrics.smoothScrollToPositionFromTop(position, 500);
//                }
            }
        });
        if (position > -1) {

        }


    }

    public void prevMusic() {
        if (requestPlayIndex == -1) {
            requestPlayIndex = musicPlayingIndex;
        }
        if (requestPlayIndex - 1 < 0) {
            requestPlayIndex = listPlaying.getFiles().size();
        }
        requestPlayIndex--;
        playMusic();
    }

    public void nextMusic() {
        if (requestPlayIndex == -1) {
            requestPlayIndex = musicPlayingIndex;
        }
        if (requestPlayIndex + 1 >= listPlaying.getFiles().size()) {
            requestPlayIndex = 0;
        }
        requestPlayIndex++;
        playMusic();
    }

    public void switchPlayIcon() {
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
        switch (v.getId()) {
            case R.id.btn_play:
                onClickPlayPause();
                break;
            case R.id.btn_next:
                nextMusic();
                break;
            case R.id.btn_prev:
                prevMusic();
                break;
        }
    }
}