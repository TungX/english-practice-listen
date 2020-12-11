package vn.yinx.listenenglish.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.yinx.listenenglish.DatabaseHelper;

public class FolderMusic extends EntityBase {
    private String _name;
    private String _path;
    private long _status = 1;
    private ArrayList<FileMusic> files;

    {
        this.tableName = "folders";
    }

    public long getStatus() {
        return _status;
    }

    public void setStatus(long status) {
        this._status = status;
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

    public long findByPath() throws Exception {
        String selectQuery = "SELECT id FROM " + this.tableName + " WHERE path = '" + this._path + "'";
        SQLiteDatabase db = DatabaseHelper.getReadDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            return cursor.getColumnIndex("id");
        return -1;
    }

    public void updateStatus(int status) {
        SQLiteDatabase db = DatabaseHelper.getWriteDatabase();
        ContentValues values = new ContentValues();
        values.put("status", status);
        db.update(this.tableName, values, "1",
                new String[]{});
    }
}
