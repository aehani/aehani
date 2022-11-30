package edu.gatech.seclass.jobcompare6300.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private int id;
    private String city;
    private State state;
    //Expressed as an index
    private int costOfLiving;

    public Location(String city, State state, int costOfLiving) {
        this.city = city;
        this.state = state;
        this.costOfLiving = costOfLiving;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(int costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    /**
     * Saves a location in the DB. 
     * Updates an existing location if ID is set.
     *
     * @param context The activity context
     * @return The ID of the saved location
     */
    public long save(Context context) {
        long locationId;
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbhelper.L_CITY, this.city);
        cv.put(dbhelper.L_STATE, this.state.getStateName());
        cv.put(dbhelper.L_COST_OF_LIVING, this.costOfLiving);

        // If id is 0 then location is new, then let the db auto gen an id. Otherwise set id.
        if(this.id != 0) {
            cv.put(dbhelper.L_ID, this.id);
            locationId = this.id;
            db.update(DatabaseHelper.TABLE_LOCATIONS, cv, "id="+this.id, null);
        } else {
            locationId = db.insert(dbhelper.TABLE_LOCATIONS, null, cv);
        }
        dbhelper.close();
        db.close();
        return locationId;
    }

    /**
     * @param id The job ID
     * @param context The activity context
     * @return The location found based on the given ID, null if not found
     */
    @Nullable
    public static Location get(int id, Context context) {
        Location l = null;
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_LOCATIONS
                + " WHERE " + dbhelper.L_ID + " = '" + id + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            String city = c.getString(c.getColumnIndex(dbhelper.L_CITY));
            State state = State.valueOfStateName(c.getString(c.getColumnIndex(dbhelper.L_STATE)));
            int costOfLiving = c.getInt(c.getColumnIndex(dbhelper.L_COST_OF_LIVING));
            l = new Location(city, state, costOfLiving);
            l.setId(c.getInt(c.getColumnIndex(dbhelper.L_ID)));
        }
        dbhelper.close();
        db.close();
        return l;
    }

    /**
     * @param city The city
     * @param state The state
     * @param context The activity context
     * @return A found location based on the given city and state, null if not found
     */
    @Nullable
    public static Location getLocation(String city, State state, Context context) {
        Location l = null;
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_LOCATIONS
                + " WHERE " + dbhelper.L_CITY + " = '" + city
                    + "' AND " + dbhelper.L_STATE + " = '" + state.getStateName() + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            int costOfLiving = c.getInt(c.getColumnIndex(dbhelper.L_COST_OF_LIVING));
            l = new Location(city, state, costOfLiving);
            l.setId(c.getInt(c.getColumnIndex(dbhelper.L_ID)));
        }

        dbhelper.close();
        db.close();
        return l;
    }

    /**
     * @param context The activity context
     * @return A list of the current locations in DB
     */
    public static List<Location> listLocations(Context context){
        List<Location> locations = new ArrayList<Location>();
        DatabaseHelper dbhelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbhelper.TABLE_LOCATIONS;
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            String city = c.getString(c.getColumnIndex(dbhelper.L_CITY));
            State state = State.valueOfStateName(c.getString(c.getColumnIndex(dbhelper.L_STATE)));
            int costOfLiving = c.getInt(c.getColumnIndex(dbhelper.L_COST_OF_LIVING));
            Location l = new Location(city, state, costOfLiving);
            l.setId(c.getInt(c.getColumnIndex(dbhelper.L_ID)));
            locations.add(l);
        }

        dbhelper.close();
        db.close();
        return locations;
    }

}
