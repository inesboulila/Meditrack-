package tn.meditrackplus.meditrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MediTrackFinal.db";
    private static final int DATABASE_VERSION = 1;

    // Table Medications
    private static final String TABLE_MEDS = "medications";
    private static final String COL_ID = "id";
    private static final String COL_MED_NAME = "name";
    private static final String COL_MED_TIME = "time";

    // Table Vitals
    private static final String TABLE_VITALS = "vitals";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Meds Table
        db.execSQL("CREATE TABLE " + TABLE_MEDS + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MED_NAME + " TEXT, " + COL_MED_TIME + " TEXT)");

        // Create Vitals Table
        db.execSQL("CREATE TABLE " + TABLE_VITALS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VITALS);
        onCreate(db);
    }

    // --- MEDICATION OPERATIONS (Create, Read, Update, Delete) ---

    public boolean addMedication(String name, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MED_NAME, name);
        cv.put(COL_MED_TIME, time);
        return db.insert(TABLE_MEDS, null, cv) != -1;
    }

    public ArrayList<Medication> getAllMedications() {
        ArrayList<Medication> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String time = cursor.getString(2);
                list.add(new Medication(id, name, time));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean updateMedication(int id, String name, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MED_NAME, name);
        cv.put(COL_MED_TIME, time);
        return db.update(TABLE_MEDS, cv, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteMedication(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MEDS, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    // --- VITALS OPERATIONS ---

    public boolean addVital(String data, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("data", data);
        cv.put("date", date);
        return db.insert(TABLE_VITALS, null, cv) != -1;
    }

    public ArrayList<String> getAllVitals() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VITALS + " ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1) + "|" + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}