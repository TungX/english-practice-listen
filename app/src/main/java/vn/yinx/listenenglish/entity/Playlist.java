package vn.yinx.listenenglish.entity;

import java.util.ArrayList;

public class Playlist extends EntityBase {
    {
        this.tableName = "playlists";
        this.id = -1;
    }
    private String _name;
    private ArrayList<FileMusic> files;

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public ArrayList<FileMusic> getFiles() {
        if(files == null){
            files = new ArrayList<>();
        }
        return files;
    }

    public void setFiles(ArrayList<FileMusic> files) {
        this.files = files;
    }
}
