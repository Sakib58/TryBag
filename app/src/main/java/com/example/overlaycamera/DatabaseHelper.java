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
        String createTable2 = "CREATE TABLE BAG_DATA (ID TEXT,SIDE1 TEXT,SIDE2 TEXT, SIDE3 TEXT, NAME TEXT, COLOR TEXT,SITE TEXT,IS_CHILD_LIKE INTEGER, IS_ADULT_LIKE INTEGER, PRIMARY KEY (ID))";
        db.execSQL(createTable2);

    }

    public boolean addBagData(String id, String side1, String side2, String side3, String name, String color,String site, boolean isChildLike, boolean isAdultLike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("SIDE1",side1);
        contentValues.put("SIDE2",side2);
        contentValues.put("SIDE3",side3);
        contentValues.put("NAME",name);
        contentValues.put("COLOR",color);
        contentValues.put("SITE",site);
        if (isChildLike) contentValues.put("IS_CHILD_LIKE",1);
        else contentValues.put("IS_CHILD_LIKE",0);
        if (isAdultLike)contentValues.put("IS_ADULT_LIKE",1);
        else contentValues.put("IS_ADULT_LIKE",0);

        long result = db.insert("BAG_DATA",null,contentValues);
        if (result == -1){
            return false;
        }else return true;
    }

    public Cursor getBagData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM BAG_DATA;",null);
        return c;
    }

    public Cursor getChildLikedBagData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM BAG_DATA WHERE IS_CHILD_LIKE=1;",null);
        return c;
    }

    public Cursor getAdultLikedBagData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM BAG_DATA WHERE IS_ADULT_LIKE=1;",null);
        return c;
    }

    public void updateChildLikeBagData(String id, int value)
    {
        Helper.log("DatabaseHelper-77:"+value);
        SQLiteDatabase mDb= this.getWritableDatabase();
        //mDb.execSQL("UPDATE BAG_DATA SET 'IS_CHILD_LIKE'="+value+" WHERE 'ID'="+"'"+id+"'");
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_CHILD_LIKE",value);
        mDb.update("BAG_DATA",contentValues,"ID='"+id+"'",null);
    }

    public void deletePerson(int section){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, SECTION + "=?", new String[]{String.valueOf(section)});
    }

    public void updateAdultLikeBagData(String id, int value)
    {
        Helper.log("DatabaseHelper-87:"+value);
        SQLiteDatabase mDb= this.getWritableDatabase();
        //mDb.execSQL("UPDATE BAG_DATA SET 'IS_CHILD_LIKE'="+value+" WHERE 'ID'="+"'"+id+"'");
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_ADULT_LIKE",value);
        mDb.update("BAG_DATA",contentValues,"ID='"+id+"'",null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS BAG_DATA");

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
        db.execSQL("delete from BAG_DATA");
    }
}
