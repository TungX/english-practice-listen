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

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentHome;
import vn.yinx.listenenglish.fragment.FragmentPlay;

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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET, Manifest.permission.WAKE_LOCK,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 0);
        }

        DatabaseHelper.init(this.getBaseContext());

        Playlist playlist = new Playlist();
        try {
            Stores.playlists = playlist.getAll();
            for (int i = 0; i < 10; i++) {
                Playlist p1 = new Playlist();
                p1.setName("Playlist " + (i + 1));
                Stores.playlists.add(p1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FolderMusic folderMusic = new FolderMusic();
        try {
            Stores.folderMusics = folderMusic.getAll();
            for (int i = 0; i < 10; i++) {
                FolderMusic fm = new FolderMusic();
                fm.setName("Music " + (i + 1));
                Stores.folderMusics.add(fm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(FragmentHome.newInstance());
                        case R.id.navigation_playlist:
                            openFragment(FragmentPlay.newInstance());
                            return true;
                        case R.id.music:
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
                // permission was granted, yay! Do
                hasPermission = true;
            }  // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
    }
}