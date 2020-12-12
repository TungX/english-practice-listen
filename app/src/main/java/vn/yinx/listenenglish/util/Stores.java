package vn.yinx.listenenglish.util;

import android.media.MediaPlayer;

import java.util.ArrayList;

import vn.yinx.listenenglish.MainActivity;
import vn.yinx.listenenglish.entity.Config;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentPlay;

public class Stores {
    private static MediaPlayer mp;
    public static MainActivity mainActivity;
    private static int currentSentence;
    public static FragmentPlay fragmentPlay;
    public static Config config;
    public static ArrayList<Playlist> playlists;
    public static ArrayList<FolderMusic> folderMusics;
    public static int currentNavigation;
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
