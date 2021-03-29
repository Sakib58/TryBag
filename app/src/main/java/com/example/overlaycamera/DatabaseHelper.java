package com.example.overlaycamera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "image_data";
    private static final String SECTION = "section";
    private static final String SIDE = "side";
    private static final String IMAGE_BITMAP = "image_bitmap";
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String LEFT_MARGIN = "left_margin";
    private static final String TOP_MARGIN = "top_margin";

    public DatabaseHelper(@Nullable Context context) {
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_NAME+" ("+SECTION+" INTEGER,"+SIDE+" INTEGER,"+IMAGE_BITMAP+" TEXT,"
                +HEIGHT+" INTEGER,"+WIDTH+" INTEGER,"+LEFT_MARGIN+" INTEGER,"+TOP_MARGIN+" INTEGER,"+" PRIMARY KEY ("+SECTION+", "+SIDE+"))";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }

    public boolean addData(int section,int side,String bitmap,int height,int width,int leftMargin,int topMargin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECTION,section);
        contentValues.put(SIDE,side);
        contentValues.put(IMAGE_BITMAP,bitmap);
        contentValues.put(HEIGHT,height);
        contentValues.put(WIDTH,width);
        contentValues.put(LEFT_MARGIN,leftMargin);
        contentValues.put(TOP_MARGIN,topMargin);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+";",null);
        return c;
    }

    public void reInitialize(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
    }
}
