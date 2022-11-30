package edu.gatech.seclass.jobcompare6300.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ComparisonSettings {
    private int yearlySalaryWeight;
    private int yearlyBonusWeight;
    private int retirementBenefitsWeight;
    private int relocationStipendWeight;
    private int restrictedStockAwardWeight;
    private Object db;

    public ComparisonSettings(int yearlySalaryWeight, int yearlyBonusWeight, int retirementBenefitsWeight, int relocationStipendWeight, int restrictedStockAwardWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
        this.yearlyBonusWeight = yearlyBonusWeight;
        this.retirementBenefitsWeight = retirementBenefitsWeight;
        this.relocationStipendWeight = relocationStipendWeight;
        this.restrictedStockAwardWeight = restrictedStockAwardWeight;
    }

    public int getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public void setYearlySalaryWeight(int yearlySalaryWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
    }

    public int getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public void setYearlyBonusWeight(int yearlyBonusWeight) {
        this.yearlyBonusWeight = yearlyBonusWeight;
    }

    public int getRetirementBenefitsWeight() {
        return retirementBenefitsWeight;
    }

    public void setRetirementBenefitsWeight(int retirementBenefitsWeight) {
        this.retirementBenefitsWeight = retirementBenefitsWeight;
    }

    public int getRelocationStipendWeight() {
        return relocationStipendWeight;
    }

    public void setRelocationStipendWeight(int relocationStipendWeight) {
        this.relocationStipendWeight = relocationStipendWeight;
    }

    public int getRestrictedStockAwardWeight() {
        return restrictedStockAwardWeight;
    }

    public void setRestrictedStockAwardWeight(int restrictedStockAwardWeight) {
        this.restrictedStockAwardWeight = restrictedStockAwardWeight;
    }

    public void save(Context context) {
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM " + dbhelper.TABLE_COMPARISON_SETTINGS
                + " WHERE " + dbhelper.CS_ID + " = 1 ";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            cv.put(dbhelper.CS_ID, 1);
            cv.put(dbhelper.CS_SALARY, this.yearlySalaryWeight);
            cv.put(dbhelper.CS_BONUS, this.yearlyBonusWeight);
            cv.put(dbhelper.CS_RETIREMENT, this.retirementBenefitsWeight);
            cv.put(dbhelper.CS_RELOCATION, this.relocationStipendWeight);
            cv.put(dbhelper.CS_STOCK, this.restrictedStockAwardWeight);
            db.update(DatabaseHelper.TABLE_COMPARISON_SETTINGS, cv, "id="+1, null);
            dbhelper.close();
        }
        else {
            // Always save to 1. App only ever has one comparison settings.
            cv.put(dbhelper.CS_ID, 1);
            cv.put(dbhelper.CS_SALARY, this.yearlySalaryWeight);
            cv.put(dbhelper.CS_BONUS, this.yearlyBonusWeight);
            cv.put(dbhelper.CS_RETIREMENT, this.retirementBenefitsWeight);
            cv.put(dbhelper.CS_RELOCATION, this.relocationStipendWeight);
            cv.put(dbhelper.CS_STOCK, this.restrictedStockAwardWeight);
            db.insert(dbhelper.TABLE_COMPARISON_SETTINGS, null, cv);
            dbhelper.close();
        }
    }

    public static ComparisonSettings get(Context context) {
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_COMPARISON_SETTINGS
                + " WHERE " + dbhelper.CS_ID + " = 1 ";
        Cursor c = db.rawQuery(query, null);
        if (c != null)
            c.moveToFirst();
        int salary = c.getInt(c.getColumnIndex(dbhelper.CS_SALARY));
        int bonus = c.getInt(c.getColumnIndex(dbhelper.CS_BONUS));
        int retirement = c.getInt(c.getColumnIndex(dbhelper.CS_RETIREMENT));
        int relocation = c.getInt(c.getColumnIndex(dbhelper.CS_RELOCATION));
        int stock = c.getInt(c.getColumnIndex(dbhelper.CS_STOCK));
        ComparisonSettings cs = new ComparisonSettings(salary, bonus, retirement, relocation, stock);

        dbhelper.close();
        return cs;
    }
}
