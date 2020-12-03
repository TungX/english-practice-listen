package vn.yinx.listenenglish.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

import vn.yinx.listenenglish.DatabaseHelper;
import vn.yinx.listenenglish.util.TextUtils;

public class EntityBase<T> {
    private static String TAG = "SQLite";
    protected String tableName;
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toCreateScript() {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append("(id INTEGER PRIMARY KEY,");
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (!f.getName().startsWith("_")) {
                continue;
            }
            if (f.getName().equals("id")) {
                continue;
            }
            String fieldName = TextUtils.toField(f.getName());
            if (fieldName.equals("id")) {
                continue;
            }
            switch (f.getType().getName().toLowerCase()) {
                case "java.lang.boolean":
                case "boolean":
                    sb.append(fieldName).append(" INTEGER DEFAULT 0, ");
                    break;
                case "java.lang.long":
                case "long":
                    sb.append(fieldName).append(" INTEGER, ");
                    break;
                default:
                    sb.append(fieldName).append(" TEXT, ");
            }

        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        Log.d("DataBase", sb.toString());
        return sb.toString();
    }

    public long create() throws Exception {
        SQLiteDatabase db = DatabaseHelper.getWriteDatabase();
        ContentValues values = new ContentValues();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            String fieldName = TextUtils.toField(f.getName());
            if (!f.getName().startsWith("_")) {
                continue;
            }
            if (fieldName.equals("id")) {
                continue;
            }
            f.setAccessible(true);
            if (f.get(this) == null) {
//                Log.d("Syntask", "attribute name null: "+f.getName());
                continue;
            }
            Log.d("SaveToDB", f.getName());
            Log.d("SaveToDB", f.getType().getName().toLowerCase());
            switch (f.getType().getName().toLowerCase()) {
                case "java.lang.boolean":
                case "boolean":
                    values.put(fieldName, f.getBoolean(this) ? 1 : 0);
                    break;
                case "java.lang.long":
                case "long":
                    values.put(fieldName, f.getLong(this));
                    break;
                default:
                    values.put(fieldName, f.get(this).toString());
            }
        }

        long id = db.insert(this.tableName, null, values);
        Log.d("SaveToDB", "Create " + this.getClass().getName() + " complete");
        Log.d("SaveToDB", "Create " + values);
        //Closing database connection
        db.close();
        return id;
    }

    public int update() throws Exception {
        SQLiteDatabase db = DatabaseHelper.getWriteDatabase();
        ContentValues values = new ContentValues();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            String fieldName = TextUtils.toField(f.getName());
            f.setAccessible(true);
            if (!f.getName().startsWith("_")) {
                continue;
            }
            if (fieldName.equals("id")) {
                continue;
            }
            if (f.get(this) == null) {
                continue;
            }
            switch (f.getType().getName().toLowerCase()) {
                case "java.lang.boolean":
                    values.put(fieldName, f.getBoolean(this) ? 1 : 0);
                    break;
                case "java.lang.long":
                case "long":
                    values.put(fieldName, f.getLong(this));
                    break;
                default:
                    values.put(fieldName, f.get(this).toString());
            }

        }

        int r = db.update(this.tableName, values, "id = ?",
                new String[]{String.valueOf(this.id)});
        Log.d("Syntask", "Update user complete");
        return r;
    }

    public EntityBase cursor2Object(Cursor cursor) throws Exception {
        EntityBase entityBase = this.getClass().newInstance();
        entityBase.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex("id"))));
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (!f.getName().startsWith("_"))
                continue;
            String fieldName = TextUtils.toField(f.getName());
            f.setAccessible(true);
            switch (f.getType().getName().toLowerCase()) {
                case "java.lang.long":
                case "long":
                    f.set(entityBase, Long.parseLong(cursor.getString(cursor.getColumnIndex(fieldName))));
                    break;
                case "java.lang.boolean":
                case "boolean":
                    f.set(entityBase, cursor.getString(cursor.getColumnIndex(fieldName)).equals("1"));
                    break;
                case "java.lang.string":
                    f.set(entityBase, cursor.getString(cursor.getColumnIndex(fieldName)));
                    break;
                default:
                    break;
            }
        }
        return entityBase;
    }

    public ArrayList<? extends EntityBase> getAll(String selectQuery) throws Exception {
        ArrayList<EntityBase> entities = new ArrayList<>();
        getAll(selectQuery, entities);
        return entities;
    }

    public void getAll(String selectQuery, ArrayList<EntityBase> entities) throws Exception {
        SQLiteDatabase db = DatabaseHelper.getReadDatabase();
        long start = System.currentTimeMillis();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("SQLline", "rawQuery of " + this.getTableName() + ": " + (System.currentTimeMillis() - start));
        if (cursor.moveToFirst()) {
            do {
                entities.add(cursor2Object(cursor));
            } while (cursor.moveToNext());
        }
        Log.d("SQLline", "load all of " + this.getTableName() + ": " + (System.currentTimeMillis() - start));
    }

    public ArrayList<? extends EntityBase> getAll() throws Exception {
        String selectQuery = "SELECT * FROM " + this.tableName;
        return getAll(selectQuery);
    }

    public void deleteAll() throws Exception {
        String script = "DELETE FROM " + this.tableName;
        //Execute Script.
        SQLiteDatabase db = DatabaseHelper.getWriteDatabase();
        db.execSQL(script);
    }

    public String getTableName() {
        return tableName;
    }
}
