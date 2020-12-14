package vn.yinx.listenenglish;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentHome;
import vn.yinx.listenenglish.fragment.FragmentPlay;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;
import vn.yinx.listenenglish.util.Stores;

public class MainActivity extends AppCompatActivity {
    private boolean hasPermission = false;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(FragmentHome.newInstance());
        Stores.currentNavigation = R.id.navigation_home;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET, Manifest.permission.WAKE_LOCK,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 0);
        }

        DatabaseHelper.init(this.getBaseContext());
        Stores.mainActivity = this;
        Playlist playlist = new Playlist();
        try {
            Stores.playlists = playlist.getAll();
        } catch (Exception e) {
            Stores.playlists = new ArrayList<>();
            e.printStackTrace();
        }

        FolderMusic folderMusic = new FolderMusic();
        try {
            Stores.folderMusics = folderMusic.getAll();
            Log.d("MainActivity", "Folder music size: " + Stores.folderMusics.size());

        } catch (Exception e) {
            Stores.folderMusics = new ArrayList<>();
            e.printStackTrace();
        }
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == Stores.currentNavigation) {
                        return true;
                    }
                    Stores.currentNavigation = item.getItemId();
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(FragmentHome.newInstance());
                            return true;
                        case R.id.navigation_playlist:
                            openFragment(FragmentPlaylist.newInstance(null));
                            return true;
                        case R.id.music:
                            FragmentPlay.setListPlaying(-1);
                            openFragment(FragmentPlay.newInstance());
//                                openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 0) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasPermission = true;
            }
        }
    }
}