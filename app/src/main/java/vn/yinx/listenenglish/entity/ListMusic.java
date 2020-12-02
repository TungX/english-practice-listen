package vn.yinx.listenenglish.entity;

import java.util.ArrayList;

public class ListMusic {
    private String name;
    private ArrayList<FileMusic> files;

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
