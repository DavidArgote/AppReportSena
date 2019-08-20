package com.davidargote.appreportsena.model;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class ManagerHelper {

    SQLiteDatabase db;
    ControlHelper controlHelper;
    Context context;

    public ManagerHelper(Context context) {
        this.controlHelper = new ControlHelper(context);
    }


    public void openDbWr() {
        db = controlHelper.getWritableDatabase();
    }

    public void openDbRd() {
        db = controlHelper.getReadableDatabase();
    }

    public void closeDb() {
        if (db != null) {
            db.close();
        }
    }

    public long insertDatReport(Report report) {

        openDbWr();

        ContentValues values = new ContentValues();

        values.put(Constants.NAME_COL_2_REPORTS, report.getTitleReport());
        values.put(Constants.NAME_COL_3_REPORTS, report.getImageReport());
        values.put(Constants.NAME_COL_4_REPORTS, report.getUrlAudio());
        values.put(Constants.NAME_COL_5_REPORTS, report.getLatReport());
        values.put(Constants.NAME_COL_6_REPORTS, report.getLongReport());

        long mInsert = db.insert(Constants.NAME_TABLE_1, null, values);

        closeDb();

        return mInsert;
    }


    @TargetApi(Build.VERSION_CODES.P)
    public List<Report> listReports() {

        openDbRd();

        ArrayList<Report> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.NAME_TABLE_1, null);

        CursorWindow cw = new CursorWindow("test", 9000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cursor;
        ac.setWindow(cw);

        try {
            if (ac.moveToFirst()){

                do {

                    Report report = new Report();

                    report.setId(ac.getInt(0));
                    report.setTitleReport(ac.getString(1));
                    report.setImageReport(ac.getBlob(2));
                    report.setUrlAudio(ac.getString(3));
                    report.setLatReport(ac.getDouble(4));
                    report.setLongReport(ac.getDouble(5));

                    list.add(report);

                }while (ac.moveToNext());

            }
        } catch (SQLiteBlobTooBigException e) {
            e.printStackTrace();
        }

        closeDb();

        return list;

    }

}
