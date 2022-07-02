package com.example.me.sqliteperson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="person_list";
    private static final String TABLE_NAME ="person";
    private static final String ID ="id";
    private static final String NAME ="name";
    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME,null, 1); //tạo csdl
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +" integer primary key AUTOINCREMENT, "+
                NAME +" TEXT)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly",
                Toast.LENGTH_SHORT).show();
    }

    //Add new a person
    public void addPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, person.getName());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void addPerson(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void addPerson(long id,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        db.insert(TABLE_NAME, null, values);
    }
        //Select a person by ID
    public Person getPersonById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID, NAME },
                ID + "=?",new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(id,cursor.getString(1));
        cursor.close();
        db.close();
        return person;
    }
    //Select a person by Name
    public Person getPersonByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID, NAME },
                NAME + "=?",new String[] { String.valueOf(name) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new
                Person(Long.parseLong(cursor.getString(0).toString()),name);
        cursor.close();
        db.close();
        return person;
    }
    // Update name of person
    public int Update(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,person.getName());
        return db.update(TABLE_NAME,values,ID +"=?",
                new String[] { String.valueOf(person.getId())});
    }

    // Getting All person
    public List<Person> getAllPeople() {
        List<Person> listPerson = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));
                listPerson.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPerson;
    }
    // Delete a person by ID
    public void deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(person.getId()) });
        db.close();
    }
    public void  deletePerson(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME + " = ?",
                new String[] { String.valueOf(name) });
        db.close();
    }
    // Get Count person in Table Person
    public int getPersonCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
