package foctupus.sheeper.com.foctupus.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by schae on 11.12.2015.
 */

public class FoctupusDatabase {

    private DBHelper helper;
    private SQLiteDatabase db_writable;
    private SQLiteDatabase db_readable;

    private static String TABLE_NAME = "foctupus";
    private static String COLUMN_BEST = "best";
    private static String COLUMN_SOUND = "sound";

    private static FoctupusDatabase instance;

    private FoctupusDatabase(Context context)
    {
        helper = new DBHelper(context);
        db_writable = helper.getWritableDatabase();
        db_readable = helper.getReadableDatabase();


    }


    public static FoctupusDatabase getInstance()
    {
        return instance;
    }

    public static FoctupusDatabase getInstance(Context context)
    {
        if(instance == null)
            instance  = new FoctupusDatabase(context);

        return instance;
    }

    public void setBest(int best)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BEST, best);
        db_writable.update(TABLE_NAME, contentValues, "_id=1", null);
    }

    public int getBest()
    {
        Cursor c = db_readable.rawQuery("SELECT `" + COLUMN_BEST + "` FROM `" + TABLE_NAME + "` WHERE _id=?", new String[] { "1" });
        if(c.getCount() > 0)
        {
            c.moveToFirst();
            return c.getInt(0);
        }

        return 0;
    }

    public void setSoundEnabled(boolean enabled)
    {
        int value = enabled ? 1 : 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOUND, value);
        db_writable.update(TABLE_NAME, contentValues, "_id=1", null);
    }

    public boolean isSoundEnabled()
    {
        Cursor c = db_readable.rawQuery("SELECT `" + COLUMN_SOUND + "` FROM `" + TABLE_NAME + "` WHERE _id=?", new String[] { "1" });
        if(c.getCount() > 0)
        {
            c.moveToFirst();
            return c.getInt(0) == 1;
        }

        return false;
    }


    private static class DBHelper extends SQLiteOpenHelper
    {

        private static final String DATABASE_NAME = "foctupusdatabase";
        private static final int DATABASE_VERSION =2;


        private String CREATE_HIGHSCORE = "CREATE TABLE `" + TABLE_NAME + "` (`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                " `" + COLUMN_BEST + "` INTEGER, " +
                " `" + COLUMN_SOUND + "` INTEGER);";

        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            super.onDowngrade(db, oldVersion, newVersion);


            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HIGHSCORE);
            db.execSQL("INSERT INTO `" + TABLE_NAME + "` (`" + COLUMN_BEST + "`, `" + COLUMN_SOUND + "`) VALUES (0, 1);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
        }
    }
}
