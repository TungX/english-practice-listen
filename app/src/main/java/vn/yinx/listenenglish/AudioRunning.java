package vn.yinx.listenenglish;

import android.media.MediaPlayer;

public class AudioRunning extends Thread {
    private MediaPlayer mp;
    private MainActivity mainActivity;

    public AudioRunning(MainActivity mainActivity, MediaPlayer mp) {
        this.mainActivity = mainActivity;
        this.mp = mp;
    }

    @Override
    public void run() {
        while (this.mp.getCurrentPosition() < this.mp.getDuration()) {
            this.mainActivity.updateSeek(this.mp.getCurrentPosition());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
