package vn.yinx.listenenglish;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    private boolean hasPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET, Manifest.permission.WAKE_LOCK,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 0);
        } else {
            hasPermission = true;
        }

        while (!hasPermission) {
            try {
                Thread.sleep(1000);
                Log.d("MainActivityOnCreate", "check permission");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("MainActivityOnCreate", "has permission");
//        mp = new MediaPlayer();
        mp = MediaPlayer.create(this.getBaseContext(), R.raw.kiss_the_rain);
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


//        Log.d("MainActivityOnCreate", "loadAudioFiles: " + file.getName());
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

    public void onClickPrev(View view) {

    }

    public void onClickNext(View view) {

    }

    public void onClickPlayPause(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 0) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do
                hasPermission = true;
            }  // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
    }
}