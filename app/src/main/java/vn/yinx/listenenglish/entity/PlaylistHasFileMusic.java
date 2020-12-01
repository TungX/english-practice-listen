package vn.yinx.listenenglish.entity;

public class PlaylistHasFileMusic extends EntityBase {
    private long _playlistId;
    private long _fileId;
    {
        this.tableName = "playlist_has_files";
    }
}
