package vn.yinx.listenenglish;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;

public class AudioRunning extends Thread {
    private MainActivity mainActivity;
    private ArrayList<Sentence> sentences;
    private LyricAdapter lyricAdapter;

    public AudioRunning(MainActivity mainActivity,  ArrayList<Sentence> sentences) {
        this.mainActivity = mainActivity;
        this.sentences = sentences;
    }

    @Override
    public void run() {
        while (Stores.getMp().getCurrentPosition() < Stores.getMp().getDuration()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!Stores.getMp().isPlaying()) {
                continue;
            }
            int positionChange = -1;
            int currentTime = Stores.getMp().getCurrentPosition();
            for (int i = Stores.getCurrentSentence(); i < sentences.size(); i++) {
                Sentence sentence = sentences.get(i);
                if (sentence.getEnd() < currentTime) {
                    continue;
                }
                if (i == Stores.getCurrentSentence()) {
                    break;
                }
//                Log.d("AudioRunning", "current time: " + currentTime + "; end time: " + sentence.getEnd() + " end < current: " + (sentence.getEnd() < currentTime) + "; current sentence: " + currentSentence + "; i = " + i);
                sentences.get(Stores.getCurrentSentence()).setActive(false);
                Stores.setCurrentSentence(i);
                sentence.setActive(true);
                positionChange = i;
                break;
            }
            this.mainActivity.updateSeek(Stores.getMp().getCurrentPosition(), positionChange);
        }

    }
}
