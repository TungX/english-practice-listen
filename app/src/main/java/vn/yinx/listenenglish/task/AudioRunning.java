package vn.yinx.listenenglish.task;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;

import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.adapter.LyricAdapter;
import vn.yinx.listenenglish.entity.Sentence;
import vn.yinx.listenenglish.fragment.FragmentPlay;

public class AudioRunning extends Thread {
    private FragmentPlay fragmentPlay;
    private ArrayList<Sentence> sentences;
    private MediaPlayer mp;
    private LyricAdapter lyricAdapter;
    private boolean isStopped = false;

    public AudioRunning(FragmentPlay fragmentPlay, MediaPlayer mp, ArrayList<Sentence> sentences) {
        this.fragmentPlay = fragmentPlay;
        this.sentences = sentences;
        this.mp = mp;
    }

    public void setStopped() {
        this.isStopped = true;
        Log.d("AutoRunning", "setStopped");
    }

    @Override
    public void run() {
        while (this.mp.getCurrentPosition() < this.mp.getDuration()) {
            if (this.isStopped)
                return;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!this.mp.isPlaying()) {
                continue;
            }
            int positionChange = -1;
            int currentTime = this.mp.getCurrentPosition();
            if (sentences != null)
                for (int i = Stores.getCurrentSentence(); i < sentences.size(); i++) {
                    Sentence sentence = sentences.get(i);
                    if (sentence.getEnd() < currentTime) {
                        continue;
                    }
                    if (i == Stores.getCurrentSentence()) {
                        break;
                    }
                    sentences.get(Stores.getCurrentSentence()).setActive(false);
                    Stores.setCurrentSentence(i);
                    sentence.setActive(true);
                    positionChange = i;
                    break;
                }
            if (this.isStopped)
                return;
            Log.d("AutoRunning", "Is stopped: " + this.isStopped);
            this.fragmentPlay.updateSeek(this.mp.getCurrentPosition(), positionChange);
        }

    }
}
