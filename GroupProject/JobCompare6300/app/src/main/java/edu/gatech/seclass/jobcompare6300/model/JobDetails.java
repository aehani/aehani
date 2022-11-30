package edu.gatech.seclass.jobcompare6300.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JobDetails {
    private int id;
    private String title;
    private String company;
    private Location location;
    private float annualSalary;
    private float annualBonus;
    private float relocStipend;
    private int retirementBenefitsPct;
    private int restrictedStockUnit;
    private float score;
    private boolean isCurrentJob;

    @Override
    public String toString() {
        return "JobDetails{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", annualSalary=" + annualSalary +
                ", annualBonus=" + annualBonus +
                ", relocStipend=" + relocStipend +
                ", retirementBenefitsPct=" + retirementBenefitsPct +
                ", restrictedStockUnit='" + restrictedStockUnit + '\'' +
                ", isCurrentJob=" + isCurrentJob +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(Float annualSalary) {
        this.annualSalary = annualSalary;
    }

    public float getAnnualBonus() {
        return annualBonus;
    }

    public void setAnnualBonus(Float annualBonus) {
        this.annualBonus = annualBonus;
    }

    public float getRelocStipend() {
        return relocStipend;
    }

    public void setRelocStipend(Float relocStipend) {
        this.relocStipend = relocStipend;
    }

    public int getRetirementBenefitsPct() {
        return retirementBenefitsPct;
    }

    public void setRetirementBenefitsPct(int retirementBenefitsPct) {
        this.retirementBenefitsPct = retirementBenefitsPct;
    }

    public int getRestrictedStockUnit() {
        return restrictedStockUnit;
    }

    public void setRestrictedStockUnit(int restrictedStockUnit) {
        this.restrictedStockUnit = restrictedStockUnit;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public float getScore() { return  score; }

    public void setScore(Float score) { this.score = score; }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    /**
     * Saves the details of a job to the DB. 
     * Updates an existing job if ID is set.
     *
     * @param context The activity context
     */
    public long save(Context context) {
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbhelper.JD_TITLE, this.title);
        cv.put(dbhelper.JD_COMPANY, this.company);
        long locId = this.location.save(context);
        cv.put(dbhelper.JD_LOCATION_ID, locId);
        cv.put(dbhelper.JD_SALARY, this.annualSalary);
        cv.put(dbhelper.JD_BONUS, this.annualBonus);
        cv.put(dbhelper.JD_RELOC_STIPEND, this.relocStipend);
        cv.put(dbhelper.JD_RETIREMENT, this.retirementBenefitsPct);
        cv.put(dbhelper.JD_STOCK, this.restrictedStockUnit);
        cv.put(dbhelper.JD_CURRENT, this.isCurrentJob);
        // If id is 0 then jobDetails is new, then let the db auto gen an id. Otherwise set id.
        long rowInserted = 0;
        if(this.id != 0) {
            cv.put(dbhelper.JD_ID, this.id);
            rowInserted = db.update(DatabaseHelper.TABLE_JOB_DETAILS, cv, "id="+this.id, null);
        } else {
            rowInserted = db.insert(dbhelper.TABLE_JOB_DETAILS, null, cv);
        }
        this.setId((int) rowInserted);
        dbhelper.close();
        db.close();
        return rowInserted;
    }

    /**
     * @param context The activity context
     * @return A list of the JobDetails currently in DB
     */
    public static List<JobDetails> listJobDetails(Context context){
        List<JobDetails> jobDetails = new ArrayList<JobDetails>();
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_JOB_DETAILS;
        Cursor c = db.rawQuery(query, null);

        //Make sure cursor is not null
        if(c != null &&  c.getCount()>0) {
            while (c.moveToNext()) {
                JobDetails jd = new JobDetails();
                jd.setId(c.getInt(c.getColumnIndex(dbhelper.JD_ID)));
                jd.setTitle(c.getString(c.getColumnIndex(dbhelper.JD_TITLE)));
                jd.setCompany(c.getString(c.getColumnIndex(dbhelper.JD_COMPANY)));
                jd.setAnnualSalary(c.getFloat(c.getColumnIndex(dbhelper.JD_SALARY)));
                jd.setAnnualBonus(c.getFloat(c.getColumnIndex(dbhelper.JD_BONUS)));
                jd.setRelocStipend(c.getFloat(c.getColumnIndex(dbhelper.JD_RELOC_STIPEND)));
                jd.setRetirementBenefitsPct(c.getInt(c.getColumnIndex(dbhelper.JD_RETIREMENT)));
                jd.setRestrictedStockUnit(c.getInt(c.getColumnIndex(dbhelper.JD_STOCK)));
                Boolean current = c.getInt(c.getColumnIndex(dbhelper.JD_CURRENT)) == 1 ? true : false;
                jd.setCurrentJob(current);
                Location location = Location.get(c.getInt(c.getColumnIndex(dbhelper.JD_LOCATION_ID)), context);
                jd.setLocation(location);
                jobDetails.add(jd);
            }
        }

        dbhelper.close();
        db.close();
        return jobDetails;
    }

    /**
     * @param context The activity context
     * @param id The ID of the job to search for
     * @return The job found in DB, null if not found
     */
    @Nullable
    public static JobDetails getJobDetail(Context context, int id){
        JobDetails jd = null;
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_JOB_DETAILS + " WHERE " + dbhelper.JD_ID + " = '" + id + "'";
        Cursor c = db.rawQuery(query, null);

        if (c != null &&  c.getCount()>0 && c.moveToFirst()) {
            jd = new JobDetails();
            jd.setId(c.getInt(c.getColumnIndex(dbhelper.JD_ID)));
            jd.setTitle(c.getString(c.getColumnIndex(dbhelper.JD_TITLE)));
            jd.setCompany(c.getString(c.getColumnIndex(dbhelper.JD_COMPANY)));
            jd.setAnnualSalary(c.getFloat(c.getColumnIndex(dbhelper.JD_SALARY)));
            jd.setAnnualBonus(c.getFloat(c.getColumnIndex(dbhelper.JD_BONUS)));
            jd.setRelocStipend(c.getFloat(c.getColumnIndex(dbhelper.JD_RELOC_STIPEND)));
            jd.setRetirementBenefitsPct(c.getInt(c.getColumnIndex(dbhelper.JD_RETIREMENT)));
            jd.setRestrictedStockUnit(c.getInt(c.getColumnIndex(dbhelper.JD_STOCK)));
            Boolean current = c.getInt(c.getColumnIndex(dbhelper.JD_CURRENT)) == 1 ? true : false;
            jd.setCurrentJob(current);
            Location location = Location.get(c.getInt(c.getColumnIndex(dbhelper.JD_LOCATION_ID)), context);
            jd.setLocation(location);
        }

        dbhelper.close();
        db.close();
        return jd;
    }

    /**
     * @param context The activity context
     * @return The current job if found, null if not
     */
    @Nullable
    public static JobDetails getCurrentJob(Context context){
        JobDetails jd = null;
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_JOB_DETAILS + " WHERE " + dbhelper.JD_CURRENT + " = '1'";
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            jd = new JobDetails();
            jd.setId(c.getInt(c.getColumnIndex(dbhelper.JD_ID)));
            jd.setTitle(c.getString(c.getColumnIndex(dbhelper.JD_TITLE)));
            jd.setCompany(c.getString(c.getColumnIndex(dbhelper.JD_COMPANY)));
            jd.setAnnualSalary(c.getFloat(c.getColumnIndex(dbhelper.JD_SALARY)));
            jd.setAnnualBonus(c.getFloat(c.getColumnIndex(dbhelper.JD_BONUS)));
            jd.setRelocStipend(c.getFloat(c.getColumnIndex(dbhelper.JD_RELOC_STIPEND)));
            jd.setRetirementBenefitsPct(c.getInt(c.getColumnIndex(dbhelper.JD_RETIREMENT)));
            jd.setRestrictedStockUnit(c.getInt(c.getColumnIndex(dbhelper.JD_STOCK)));
            Boolean current = c.getInt(c.getColumnIndex(dbhelper.JD_CURRENT)) == 1 ? true : false;
            jd.setCurrentJob(current);
            Location location = Location.get(c.getInt(c.getColumnIndex(dbhelper.JD_LOCATION_ID)), context);
            jd.setLocation(location);
        }

        dbhelper.close();
        db.close();
        return jd;
    }

    public Float calculateAdjustedYearlySalary() {
        return this.annualSalary * (float) (100.0 / getLocation().getCostOfLiving());
    }

    public Float calculateAdjustedYearlyBonus() {
        return this.annualBonus * (float) (100.0 / getLocation().getCostOfLiving());
    }

    public Float calculateScore(ComparisonSettings comparisonSettings) {
        float ays = calculateAdjustedYearlySalary();
        float rpb = ((float)this.retirementBenefitsPct * ays / 100) * ((float)comparisonSettings.getRetirementBenefitsWeight() / 7);
        ays *= ((float)comparisonSettings.getYearlySalaryWeight() / 7);
        float ayb = calculateAdjustedYearlyBonus() * ((float)comparisonSettings.getYearlyBonusWeight() / 7);
        float rs = this.relocStipend * ((float)comparisonSettings.getRelocationStipendWeight() / 7);
        float rsua = ((float)this.restrictedStockUnit / 4) * ((float)comparisonSettings.getRestrictedStockAwardWeight() / 7);
        this.score = ays + ayb + rs + rpb + rsua;
        return this.score;
    }
}