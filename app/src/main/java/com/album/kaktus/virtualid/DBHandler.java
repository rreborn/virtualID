package com.album.kaktus.virtualid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.album.kaktus.virtualid.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "VirutalID";
    // Table name
    private static final String TABLE_PROFILE = "Profile";
    private static final String TABLE_NUMBER = "NUMBER";

    // Profile Columns names
    private static final String KEY_ID_PROFILE = "idProfile";
    private static final String KEY_NAME = "name";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PICTURE = "picture";

    // Number Columns names
    private static final String KEY_PHONE_NUMBER = "phoneNumber";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_ID_PROFILE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " STRING ,"
                + KEY_ALAMAT + " TEXT ,"
                + KEY_EMAIL + " STRING ,"
                + KEY_PICTURE + " STRING "
                + ")";

        String CREATE_NUMBER_TABLE = "CREATE TABLE " + TABLE_NUMBER + "("
                + KEY_ID_PROFILE + " INTEGER ,"
                + KEY_PHONE_NUMBER + " STRING "
                + ")";

        db.execSQL(CREATE_PROFILE_TABLE);
        db.execSQL(CREATE_NUMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertProfile(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_ALAMAT, "");
        values.put(KEY_EMAIL, "");
        values.put(KEY_PICTURE, "");

        long newID = db.insert(TABLE_PROFILE, null, values);
        return (int)newID;
    }

    public List<Contact> selectContact(String id){
        List<Contact> listProfile = new ArrayList<>();
        String selectQuery;
        String number = "";
        if (id.compareTo("-") == 0) {
            selectQuery = "SELECT * FROM " + KEY_ID_PROFILE + " WHERE 1";
        } else {
            selectQuery = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + KEY_ID_PROFILE + " = '" + String.valueOf(id) + "'";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
        } catch (IllegalStateException e) {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
        }

        if (cursor.moveToFirst()) {
            do {
                int idProfile = cursor.getInt(0);
                String nama = cursor.getString(1);
                String alamat = cursor.getString(2);
                String email = cursor.getString(3);
                String pic = cursor.getString(4);

                Contact item = new Contact();
                item.setID(idProfile);
                item.setName(nama);
                item.setPicURI(pic);

                number = selectNumber(idProfile);
                item.setNumber(number);
                listProfile.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listProfile;
    }

    public void insertNumber(int idProfile, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_PROFILE, idProfile);
        values.put(KEY_PHONE_NUMBER, number);

        db.insert(TABLE_NUMBER, null, values);
    }

    public String selectNumber(int id){
        String selectQuery = "SELECT * FROM " + TABLE_NUMBER + " WHERE " + KEY_ID_PROFILE + " = '" + id + "'";
        String number = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
        } catch (IllegalStateException e) {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
        }

        if (cursor.moveToFirst()) {
            do {
                String num = cursor.getString(1);
                if(number.compareTo("")!= 0){
                    number = number + "\n";
                }
                number = number + num;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return number;
    }
//
//    public void updateProduk(Product item) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_IMG_PRODUCT, item.getImgProduct());
//        values.put(KEY_NAME_PRODUCT, item.getNameProduct());
//        values.put(KEY_PRICE_PRODUCT, item.getPriceProduct());
//        values.put(KEY_LOW_STOCK_PRODUCT, item.getLowStokProduct());
//        db.update(TABLE_PRODUCT, values, KEY_ID_PRODUCT + "='" + item.getIdProduct() + "'", null);
//    }
}