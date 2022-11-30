package edu.gatech.seclass.jobcompare6300.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "JobOfferComparison.db";

    public static final String TABLE_JOB_DETAILS = "JOB_DETAILS";
    public static final String TABLE_LOCATIONS = "LOCATIONS";
    public static final String TABLE_COMPARISON_SETTINGS = "COMPARISON_SETTINGS";

    // Job details table columns
    public static final String JD_ID = "id";
    public static final String JD_TITLE = "title";
    public static final String JD_COMPANY = "company";
    public static final String JD_LOCATION_ID = "locationId";
    public static final String JD_SALARY = "annualSalary";
    public static final String JD_BONUS = "annualBonus";
    public static final String JD_RELOC_STIPEND = "relocStipend";
    public static final String JD_RETIREMENT = "retirementBenefitsPct";
    public static final String JD_STOCK = "restrictedStockUnit";
    public static final String JD_CURRENT = "isCurrentJob";

    // Location table columns
    public static final String L_ID = "id";
    public static final String L_CITY = "city";
    public static final String L_STATE = "state";
    public static final String L_COST_OF_LIVING = "costOfLiving";

    // Comparison Settings table columns
    public static final String CS_ID = "id";
    public static final String CS_SALARY = "yearlySalaryWeight";
    public static final String CS_BONUS = "yearlyBonusWeight";
    public static final String CS_RETIREMENT = "retirementBenefitsWeight";
    public static final String CS_RELOCATION = "relocationStipendWeight";
    public static final String CS_STOCK = "restrictedStockAwardWeight";

    public static final String CREATE_TABLE_JOB_DETAILS =
            "CREATE TABLE " + TABLE_JOB_DETAILS
            + " (" + JD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JD_TITLE + " TEXT N0T NULL, "
            + JD_COMPANY + " TEXT NOT NULL, "
            + JD_LOCATION_ID + " INTEGER NOT NULL, "
            + JD_SALARY + " REAL DEFAULT 0, "
            + JD_BONUS + " REAL DEFAULT 0, "
            + JD_RELOC_STIPEND + " REAL DEFAULT 0, "
            + JD_RETIREMENT + " INTEGER DEFAULT 0, "
            + JD_STOCK + " INTEGER DEFAULT 0, "
            + JD_CURRENT + " INTEGER DEFAULT 0)";

    public static final String CREATE_TABLE_LOCATION =
            "CREATE TABLE " + TABLE_LOCATIONS
            + "(" + L_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + L_CITY + " TEXT NOT NULL, "
            + L_STATE + " TEXT NOT NULL, "
            + L_COST_OF_LIVING + " INTEGER DEFAULT 0)";

    public static final String CREATE_TABLE_COMPARISON_SETTINGS =
            "CREATE TABLE " + TABLE_COMPARISON_SETTINGS
            + "(" + CS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CS_SALARY + " INTEGER DEFAULT 7, "
            + CS_BONUS + " INTEGER DEFAULT 7, "
            + CS_RETIREMENT + " INTEGER DEFAULT 7, "
            + CS_RELOCATION + " INTEGER DEFAULT 7, "
            + CS_STOCK + " INTEGER DEFAULT 7)";

    public static final String INIT_COMPARISON_SETTINGS =
            "INSERT INTO " + TABLE_COMPARISON_SETTINGS + " VALUES(1, 7, 7, 7, 7, 7)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_JOB_DETAILS);
        db.execSQL(CREATE_TABLE_COMPARISON_SETTINGS);
        db.execSQL(INIT_COMPARISON_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPARISON_SETTINGS);

        onCreate(db);
    }
}
