package vn.yinx.listenenglish.task;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import vn.yinx.listenenglish.Stores;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.PlaylistHasFileMusic;
import vn.yinx.listenenglish.entity.Sentence;
import vn.yinx.listenenglish.fragment.FragmentHome;
import vn.yinx.listenenglish.util.TextUtils;

public class ScanTask extends Thread {
    private FragmentHome fragmentHome;

    public ScanTask(FragmentHome fragmentHome) {
        this.fragmentHome = fragmentHome;
    }

    public void run() {
        Stores.folderMusics.clear();
        this.fragmentHome.showFolderArea();
        File sdCard = Environment.getExternalStorageDirectory();
        HashMap<String, FolderMusic> folders = new HashMap<>();
        ArrayList<File> files = new ArrayList<>();
        try {
            FileMusic fileMusicDB = new FileMusic();
            FolderMusic folderMusicDb = new FolderMusic();
            fileMusicDB.updateStatus(0);
            folderMusicDb.updateStatus(0);
            Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());
            loadAudioFiles(folders, files, sdCard);
            sdCard = new File("/storage");
            Log.d("MainActivityOnCreate", "sdCard: " + sdCard.getAbsolutePath());
            loadAudioFiles(folders, files, sdCard);
            for (File fi : files) {
                String folderPath = fi.getParentFile().getAbsolutePath();
                FileMusic fileMusic = new FileMusic();
                fileMusic.setName(fi.getName());
                fileMusic.setFolderId(folders.get(folderPath).getId());
                fileMusic.setAudioPath(fi.getAbsolutePath());
                File lyric = new File(fi.getAbsolutePath().replace("mp3", "TextGrid"));
                if (lyric.isFile()) {
                    ArrayList<Sentence> sentences = TextUtils.readSentences(lyric);
                    fileMusic.setSentences(sentences);
                    JSONArray jSentence = new JSONArray();
                    for (Sentence sentence : sentences) {
                        jSentence.put(sentence.toObject());
                    }
                    fileMusic.setLyric(jSentence.toString());
                }

//                String hex = TextUtils.checksum(fi);
                long id = fileMusicDB.findByPath();
                if (id == -1) {
                    id = fileMusic.create();
                    fileMusic.setId(id);
                } else {
                    fileMusic.setId(id);
                    fileMusic.update();
                }
                folders.get(folderPath).getFiles().add(fileMusic);
            }

            if (files.isEmpty()) {
                Thread.sleep(1000);
            }
            ArrayList<FileMusic> fileMusicDeletes = fileMusicDB.getAll("SELECT * FROM " + fileMusicDB.getTableName() + " WHERE status = 0");
            fileMusicDB.delete("status = 0");
            PlaylistHasFileMusic playlistHasFileMusicDb = new PlaylistHasFileMusic();
            for (FileMusic fi : fileMusicDeletes) {
                playlistHasFileMusicDb.delete("file_id = " + fi.getId());
            }
            folderMusicDb.delete("status = 0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fragmentHome.resetFolderAdapter();
        this.fragmentHome.finish();
    }

    private void loadAudioFiles(HashMap<String, FolderMusic> folders, ArrayList<File> files, File fi) throws Exception {
        if (fi.isFile() && fi.getName().toLowerCase().endsWith("mp3") && !fi.getName().trim().startsWith(".")) {
            Log.d("MainActivityOnCreate", fi.getAbsolutePath());
            files.add(fi);
            String folderPath = fi.getParentFile().getAbsolutePath();
            if (!folders.containsKey(fi.getParentFile().getAbsolutePath())) {
                FolderMusic folderMusic = new FolderMusic();
                folderMusic.setName(fi.getParentFile().getName());
                folderMusic.setFiles(new ArrayList<>());
                folderMusic.setPath(folderPath);
                folders.put(folderPath, folderMusic);

                long id = folderMusic.findByPath();
                if (id == -1) {
                    id = folderMusic.create();
                    folderMusic.setId(id);
                } else {
                    folderMusic.setId(id);
                    folderMusic.update();
                }

                Stores.folderMusics.add(folderMusic);
                this.fragmentHome.resetFolderAdapter();
            }
            return;
        }
        if (!fi.isDirectory()) {
            return;
        }
        File[] arrFiles = fi.listFiles();
        if (arrFiles == null) {
            return;
        }
        for (File f : arrFiles) {
            loadAudioFiles(folders, files, f);
        }
    }
}
