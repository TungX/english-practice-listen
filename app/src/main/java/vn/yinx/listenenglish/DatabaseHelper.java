package vn.yinx.listenenglish;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vn.yinx.listenenglish.entity.Config;
import vn.yinx.listenenglish.entity.FileMusic;
import vn.yinx.listenenglish.entity.FolderMusic;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.entity.PlaylistHasFileMusic;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "SQLite";
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "yin-listen-practices";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabase.onCreate ...");
        Config config = new Config();
        db.execSQL(config.toCreateScript());

        FileMusic file = new FileMusic();
        db.execSQL(file.toCreateScript());

        FolderMusic folder = new FolderMusic();
        db.execSQL(folder.toCreateScript());

        Playlist playlist = new Playlist();
        db.execSQL(playlist.toCreateScript());

        PlaylistHasFileMusic playlistHasFileMusic = new PlaylistHasFileMusic();
        db.execSQL(playlistHasFileMusic.toCreateScript());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabase.onUpgrade ... ");
        try {
            Config config = new Config();
            db.execSQL("DROP TABLE IF EXISTS " + config.getTableName());
            Log.i(TAG, "Drop configs complete");
        }catch (Exception e){

        }
        try {
            FolderMusic config = new FolderMusic();
            db.execSQL("DROP TABLE IF EXISTS " + config.getTableName());
            Log.i(TAG, "Drop configs complete");
        }catch (Exception e){

        }
        try {
            FileMusic config = new FileMusic();
            db.execSQL("DROP TABLE IF EXISTS " + config.getTableName());
            Log.i(TAG, "Drop configs complete");
        }catch (Exception e){

        }
        try {
            Playlist config = new Playlist();
            db.execSQL("DROP TABLE IF EXISTS " + config.getTableName());
            Log.i(TAG, "Drop configs complete");
        }catch (Exception e){

        }
        try {
            PlaylistHasFileMusic config = new PlaylistHasFileMusic();
            db.execSQL("DROP TABLE IF EXISTS " + config.getTableName());
            Log.i(TAG, "Drop configs complete");
        }catch (Exception e){

        }
        //Create tables again
        onCreate(db);
    }

    private static DatabaseHelper database;
    public static void init(Context context){
        DatabaseHelper.database = new DatabaseHelper(context);
    }
    public static SQLiteDatabase getReadDatabase(){
        return DatabaseHelper.database.getReadableDatabase();
    }

    public static SQLiteDatabase getWriteDatabase(){
        return DatabaseHelper.database.getWritableDatabase();
    }
}
