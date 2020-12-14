package vn.yinx.listenenglish.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;

import vn.yinx.listenenglish.DatabaseHelper;
import vn.yinx.listenenglish.util.TextUtils;

public class FileMusic extends EntityBase {
    private String _name;
    private String _audioPath;
    private String _lyric;
    private long _folderId;
    private long _status = 1;
    private String _checksum;
    private boolean isChecked = false;
    private ArrayList<Sentence> sentences;

    {
        this.tableName = "files";
    }

    public long getStatus() {
        return _status;
    }

    public void setStatus(long _status) {
        this._status = _status;
    }

    public String getChecksum() {
        return _checksum;
    }

    public void setChecksum(String _checksum) {
        this._checksum = _checksum;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getAudioPath() {
        return _audioPath;
    }

    public void setAudioPath(String _audioPath) {
        this._audioPath = _audioPath;
    }

    public void setLyric(String _lyric) {
        this._lyric = _lyric;
    }

    public ArrayList<Sentence> getSentences() throws Exception {
        if (sentences == null && _lyric != null) {
            sentences = new ArrayList<>();
            JSONArray jSentences = new JSONArray(_lyric);
            for (int i = 0; i < jSentences.length(); i++) {
                Sentence sentence = new Sentence(jSentences.getJSONObject(i));
                sentence.setId(i);
                sentences.add(sentence);
            }
        }
        return sentences;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public long getFolderId() {
        return _folderId;
    }

    public void setFolderId(long _folderId) {
        this._folderId = _folderId;
    }

    public long findByPath() throws Exception {
        String selectQuery = "SELECT id FROM " + this.tableName + " WHERE audio_path = '" + this._audioPath + "'";
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
