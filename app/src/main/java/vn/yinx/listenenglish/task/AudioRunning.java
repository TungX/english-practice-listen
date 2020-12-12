package vn.yinx.listenenglish.task;

import java.util.ArrayList;

import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.adapter.LyricAdapter;
import vn.yinx.listenenglish.entity.Sentence;
import vn.yinx.listenenglish.fragment.FragmentPlay;

public class AudioRunning extends Thread {
    private FragmentPlay fragmentPlay;
    private ArrayList<Sentence> sentences;
    private LyricAdapter lyricAdapter;

    public AudioRunning(FragmentPlay fragmentPlay, ArrayList<Sentence> sentences) {
        this.fragmentPlay = fragmentPlay;
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
            this.fragmentPlay.updateSeek(Stores.getMp().getCurrentPosition(), positionChange);
        }

    }
}
