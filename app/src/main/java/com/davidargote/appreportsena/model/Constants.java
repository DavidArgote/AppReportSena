package com.davidargote.appreportsena.model;

public class Constants {

    public static String NAME_DB = "REPORTS.db";
    public static int VERSION_DB = 1;

    public static String NAME_TABLE_1 = "REPORTS";
    public static String NAME_COL_1_REPORTS = "ID";
    public static String NAME_COL_2_REPORTS = "TITLE";
    public static String NAME_COL_3_REPORTS = "IMAGE";
    public static String NAME_COL_4_REPORTS = "URLAUIDO";
    public static String NAME_COL_5_REPORTS = "LAT";
    public static String NAME_COL_6_REPORTS = "LONG";

    public static String CREATE_TABLE_1 = "CREATE TABLE " + NAME_TABLE_1 + "(" + NAME_COL_1_REPORTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            NAME_COL_2_REPORTS + " TEXT," + NAME_COL_3_REPORTS + " BLOB," + NAME_COL_4_REPORTS + " TEXT," +
                                            NAME_COL_5_REPORTS + " REAL, " + NAME_COL_6_REPORTS + " REAL)";

    public static String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + NAME_TABLE_1;



}
