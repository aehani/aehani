package edu.gatech.seclass.jobcompare6300.model;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Test;


public class DatabaseHelperTest {

    private final Context appContxt = getInstrumentation().getTargetContext();

    @Test
    public void test_DB_Created(){
        DatabaseHelper dbhelper = new DatabaseHelper(appContxt);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        assertTrue(db.isOpen());
        // Now close the DB and check if it's closed
        db.close();
        assertFalse(db.isOpen());
    }

    @Test
    public void test_DataInsertJobDetailsTable(){
        DatabaseHelper dbhelper = new DatabaseHelper(appContxt);

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.JD_TITLE, "CEO");
        cv.put(DatabaseHelper.JD_COMPANY, "RainForests Inc.");
        cv.put(DatabaseHelper.JD_SALARY, "50000");
        cv.put(DatabaseHelper.JD_BONUS, "10000");
        cv.put(DatabaseHelper.JD_RELOC_STIPEND, "400");
        cv.put(DatabaseHelper.JD_LOCATION_ID, "3");

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long firstRowId = db.insert(DatabaseHelper.TABLE_JOB_DETAILS, null, cv);
        assertTrue(firstRowId != -1);
    }

    @Test
    public void test_DataInsertLocationTable(){
        DatabaseHelper dbhelper = new DatabaseHelper(appContxt);

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.L_ID, "5");
        cv.put(DatabaseHelper.L_COST_OF_LIVING, 80000);
        cv.put(DatabaseHelper.L_CITY, "Wonderland");
        cv.put(DatabaseHelper.L_STATE, String.valueOf(State.OR));

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long locRowId = db.insert(DatabaseHelper.TABLE_LOCATIONS, null, cv);
        assertTrue(locRowId != -1);
    }

    @After
    public void tearDown(){
        appContxt.deleteDatabase(DatabaseHelper.DB_NAME);
    }

}