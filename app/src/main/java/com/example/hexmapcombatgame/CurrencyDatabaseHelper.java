package com.example.hexmapcombatgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game_currency.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_CURRENCY = "currency";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AMOUNT = "amount";

    // Create table SQL statement
    private static final String CREATE_TABLE_CURRENCY = "CREATE TABLE " + TABLE_CURRENCY + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_AMOUNT + " INTEGER)";

    public CurrencyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CURRENCY);
        seedInitialCurrencyData(db);
        System.out.println("new database has been created");
    }


    private void seedInitialCurrencyData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, 100); // Set the initial currency amount

        db.insert(TABLE_CURRENCY, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);
    }

    // CRUD operations

    public long insertCurrency(int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        return db.insert(TABLE_CURRENCY, null, values);
    }

    @SuppressLint("Range")
    public int getCurrencyAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_AMOUNT};
        Cursor cursor = db.query(TABLE_CURRENCY, projection, null, null, null, null, null);
        int amount = 0;
        if (cursor.moveToFirst()) {
            amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
        }
        cursor.close();
        return amount;
    }

    public int updateCurrencyAmount(int newAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, newAmount);
        return db.update(TABLE_CURRENCY, values, null, null);
    }

    // Add more methods for CRUD operations as needed
}


