package vn.yinx.listenenglish;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;

public class AudioRunning extends Thread {
    private MediaPlayer mp;
    private MainActivity mainActivity;
    private ArrayList<Sentence> sentences;
    private int currentSentence = 0;
    private LyricAdapter lyricAdapter;

    public AudioRunning(MainActivity mainActivity, MediaPlayer mp, ArrayList<Sentence> sentences) {
        this.mainActivity = mainActivity;
        this.mp = mp;
        this.sentences = sentences;
    }

    @Override
    public void run() {
        while (this.mp.getCurrentPosition() < this.mp.getDuration()) {
            int positionChange = -1;
            int currentTime = this.mp.getCurrentPosition();
            for (int i = currentSentence; i < sentences.size(); i++) {
                Sentence sentence = sentences.get(i);
                if (sentence.getEnd() < currentTime) {
                    continue;
                }
                if (i == currentSentence) {
                    break;
                }
//                Log.d("AudioRunning", "current time: " + currentTime + "; end time: " + sentence.getEnd() + " end < current: " + (sentence.getEnd() < currentTime) + "; current sentence: " + currentSentence + "; i = " + i);
                sentences.get(currentSentence).setActive(false);
                currentSentence = i;
                sentence.setActive(true);
                positionChange = i;
                break;
            }
            this.mainActivity.updateSeek(this.mp.getCurrentPosition(), positionChange);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
