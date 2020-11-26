package vn.yinx.listenenglish;

import android.media.MediaPlayer;

public class Stores {
    private static MediaPlayer mp;
    private static int currentSentence;
    public static FragmentPlay fragmentPlay;
    public static synchronized void setMp(MediaPlayer mp){
        Stores.mp = mp;
    }

    public static synchronized MediaPlayer getMp(){
        return Stores.mp;
    }

    public static synchronized int getCurrentSentence() {
        return currentSentence;
    }

    public static synchronized void setCurrentSentence(int currentSentence) {
        Stores.currentSentence = currentSentence;
    }
}
