package com.dam.ehedeihega.masacorporal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseBMI extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bmi.db";
    private static final String TABLE_PEOPLE = "people";
    private static final String ID = "pk_id";
    private static final String NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String AGE = "age";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String SEX = "sex";

    private static DataBaseBMI dataBaseBMI;


    private DataBaseBMI(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized static DataBaseBMI getDataBaseBMI(Context context) {
        if (dataBaseBMI == null) {
            dataBaseBMI = new DataBaseBMI(context);
        }
        return dataBaseBMI;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PEOPLE +  " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT NOT NULL, "
                + LAST_NAME + " TEXT NOT NULL, "
                + AGE  + " INTEGER NOT NULL, "
                + HEIGHT + " REAL NOT NULL, "
                + WEIGHT + " REAL NOT NULL, "
                + SEX + " TEXT NOT NULL "
                + ")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            String query = "DROP TABLE IF EXISTS " + TABLE_PEOPLE;
            db.execSQL(query);
            onCreate(db);
        }

    }

    public long insertPerson(SQLiteDatabase db, Person person) {
        String sex = null;

        if (person instanceof Man)
            sex = "Man";
        else
            sex = "Woman";

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, person.getFistName());
        contentValues.put(LAST_NAME, person.getLastName());
        contentValues.put(AGE, person.getAge());
        contentValues.put(HEIGHT, person.getHeight());
        contentValues.put(WEIGHT, person.getWeight());
        contentValues.put(SEX, sex);

        return db.insert(TABLE_PEOPLE, null, contentValues);
    }

    public ArrayList<Person> getPeople(SQLiteDatabase db, String name) {
        ArrayList<Person> people = new ArrayList<>();
        Person person;
        String sex;
        String [] columns = new String[]{NAME, LAST_NAME, AGE, HEIGHT, WEIGHT, SEX};
        String where = NAME + " LIKE ? ";
        String [] whereArgs = {"%" + name + "%"};
        String orderby = LAST_NAME + " ASC";
        Cursor c = null;
        c = db.query(TABLE_PEOPLE, columns, where, whereArgs, null, null, LAST_NAME);

        if (c != null && c.moveToFirst()) {
            do {
                sex = c.getString(5);

                if (sex.equals("Man")) {
                    person = new Man(
                            c.getString(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getDouble(3),
                            c.getDouble(4),
                            c.getString(5)
                    );
                } else {
                    person = new Woman(
                            c.getString(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getDouble(3),
                            c.getDouble(4),
                            c.getString(5)
                    );
                }
                people.add(person);

            } while (c.moveToNext());
        }
        return people;
    }
}
