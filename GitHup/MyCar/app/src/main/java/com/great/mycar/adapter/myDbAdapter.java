package com.great.mycar.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String phone, String mail,String password)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, password);
        contentValues.put(myDbHelper.phone,phone );
        contentValues.put(myDbHelper.Email,mail);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String Sl="a b c";
        String[] columns = {myDbHelper.UID,myDbHelper.Email,myDbHelper.MyPASSWORD,myDbHelper.NAME,myDbHelper.phone};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.Email));
            String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));

            Sl=(cid+ " " + name + " " + password+" "+cursor.getString(cursor.getColumnIndex(myDbHelper.NAME))+" "+cursor.getString(cursor.getColumnIndex(myDbHelper.phone)));
        }
        return Sl;
    }
    public String[] getData_inf()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String Sl[]=new String[10];
        String[] columns = {myDbHelper.UID,myDbHelper.Email,myDbHelper.MyPASSWORD,myDbHelper.NAME
                ,myDbHelper.phone};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String mail =cursor.getString(cursor.getColumnIndex(myDbHelper.Email));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  phone =cursor.getString(cursor.getColumnIndex(myDbHelper.phone));
            Sl= new String[]{name, mail, phone };
        }
        return Sl;
    }
    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final String TABLE_NAME_2 = "myTable_2";   // Table Name 2
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String Email = "Email";    //Column III
        private static final String phone = "Phone";    //Column IV
        private static final String MyPASSWORD= "Password";    // Column VI
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+
                MyPASSWORD+" VARCHAR(225) , "+ Email+" VARCHAR(225) , "+ phone+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private static final String DROP_TABLE_2 ="DROP TABLE IF EXISTS "+TABLE_NAME_2;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);

            } catch (Exception e) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
            }
        }
    }
}
