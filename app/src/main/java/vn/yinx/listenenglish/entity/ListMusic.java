package vn.yinx.listenenglish.entity;

import java.util.ArrayList;

public class ListMusic {
    private String name;
    private ArrayList<FileMusic> files;
    private String type;

    public ListMusic(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FileMusic> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileMusic> files) {
        this.files = files;
    }
}
