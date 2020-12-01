package vn.yinx.listenenglish.entity;

import java.util.ArrayList;

public class FolderMusic extends EntityBase {
    private String _name;
    private String _path;
    private ArrayList<FileMusic> files;
    {
        this.tableName = "folders";
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }

    public ArrayList<FileMusic> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileMusic> files) {
        this.files = files;
    }
}
