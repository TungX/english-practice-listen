package vn.yinx.listenenglish.entity;

import android.util.Log;

import java.lang.reflect.Field;

import vn.yinx.listenenglish.util.TextUtils;

public class PlaylistHasFileMusic extends EntityBase {
    private long _playlistId;
    private long _fileId;
    {
        this.tableName = "playlist_has_files";
    }

    public String toCreateScript() {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append("(id INTEGER PRIMARY KEY, file_id INTEGER, playlist_id INTEGER, UNIQUE (file_id, playlist_id)");
        Log.d("DataBase", sb.toString());
        return sb.toString();
    }

    public long getPlaylistId() {
        return _playlistId;
    }

    public void setPlaylistId(long _playlistId) {
        this._playlistId = _playlistId;
    }

    public long getFileId() {
        return _fileId;
    }

    public void setFileId(long _fileId) {
        this._fileId = _fileId;
    }
}
