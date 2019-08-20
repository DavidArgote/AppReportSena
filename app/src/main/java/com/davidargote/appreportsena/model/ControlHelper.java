package com.davidargote.appreportsena.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlHelper extends SQLiteOpenHelper {


    public ControlHelper(Context context) {
        super(context, Constants.NAME_DB, null, Constants.VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Constants.CREATE_TABLE_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(Constants.DROP_TABLE_1);
        onCreate(sqLiteDatabase);

    }
}
