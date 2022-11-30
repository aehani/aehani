package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;

import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_BONUS;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_COMPANY;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_CURRENT;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_LOCATION_ID;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_RETIREMENT;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_SALARY;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_STOCK;
import static edu.gatech.seclass.jobcompare6300.model.DatabaseHelper.JD_TITLE;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;
import edu.gatech.seclass.jobcompare6300.model.State;

@RunWith(AndroidJUnit4.class)
public class OfferComparisonAppActivityTest {
    @Rule
    public ActivityTestRule<OfferComparisonAppActivity> activityRule = new ActivityTestRule<>(OfferComparisonAppActivity.class);

    private OfferComparisonAppActivity offerComparisonAppActivity = null;
    @Rule public TestName name = new TestName();
    Instrumentation.ActivityMonitor monitor =  getInstrumentation().addMonitor(JobDetailsActivity.class.getName(), null, false);
    private final Context appContxt = getInstrumentation().getTargetContext();

    /**
     * Helper method to insert job records in DB for setting up the button state
     * **/
    private boolean insertSingleJob(String title, String companyName) throws Exception {
        try{
            DatabaseHelper dbHelper = new DatabaseHelper(appContxt);
            ContentValues cv = new ContentValues();
            cv.put(dbHelper.JD_TITLE, title);
            cv.put(dbHelper.JD_COMPANY, companyName);
            cv.put(dbHelper.JD_SALARY, "50000");
            cv.put(dbHelper.JD_BONUS, "10000");
            cv.put(dbHelper.JD_RELOC_STIPEND, "400");
            cv.put(dbHelper.JD_LOCATION_ID, "3");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long firstRowId = db.insert(dbHelper.TABLE_JOB_DETAILS, null, cv);

            assertNotEquals("Unable to insert into the database", -1, firstRowId);
            db.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @Test
    // Description: Test for checking if all the buttons are displayed in Main Menu screen
    public void testMenuOptionsDisplayed() {
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.editCurrJobBtn));
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.addNewOfferBtn));
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.compareOffersBtn));
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.adjustSettingsBtn));
    }

    @Test
    // Description: Test for checking if Current job details is disabled when no job exists in DB
    public void testValidateEditJobEnabledWhenNoJobExists() {
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.editCurrJobBtn));
        onView(withId(R.id.editCurrJobBtn))
                .check(matches(isEnabled()));
    }

    @Test
    // Description: Test for checking if Current job details is disabled when no job exists in DB
    public void testValidateAddJobEnabledWhenNoJobExists() {
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.addNewOfferBtn));
        onView(withId(R.id.addNewOfferBtn))
                .check(matches(isEnabled()));
    }

    @Test
    // Description: Test for checking if Add new jo offer navigates user to next screen
    public void testNavigationToAddNewJob() {
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.addNewOfferBtn));
        onView(withId(R.id.addNewOfferBtn)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    @Test
    // Description: Test for checking if Compare Job Offers is disabled when no job exists in DB
    public void testCompareOffersDisabledWhenNoJobsExist() {
        onView(withId(R.id.compareOffersBtn)).perform(click())
                .check(matches(isNotEnabled()));
    }

    @Test
    // Description: Test for checking if Compare Job Offers is enabled when 2 jobs exists in DB
    public void testCompareOfferEnabledWhen2JobsExist() throws Exception {
        Thread.sleep(1000);
        List<JobDetails> jobs = JobDetails.listJobDetails(appContxt);
        assertEquals(2, jobs.size());
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.compareOffersBtn));
        onView(withId(R.id.compareOffersBtn))
                .check(matches(isEnabled()));
    }

    @Test
    // Description: Test for checking if compare settings navigates user to next screen
    public void testNavigationComparisonSettingsActivity() {
        Instrumentation.ActivityMonitor monitor1 =  getInstrumentation().addMonitor(ComparisonSettingsActivity.class.getName(), null, false);
        assertNotNull(offerComparisonAppActivity.findViewById(R.id.adjustSettingsBtn));
        onView(withId(R.id.adjustSettingsBtn)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor1, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    @Before
    public void setUp() throws Exception {
        offerComparisonAppActivity = activityRule.getActivity();
        if(name.getMethodName().equals("testCompareOfferEnabledWhen2JobsExist") ||
        name.getMethodName().equals("testNavigationOfferCompareActivity")){
            boolean row1Done  = insertSingleJob("CEO", "Amazon");
            assertTrue(row1Done);
            boolean row2Done = insertSingleJob("President", "United States of America");
            assertTrue(row2Done);
            updateButtonState();
        }

    }

    @After
    public void tearDown() throws Exception {
        activityRule.getActivity().deleteDatabase(DatabaseHelper.DB_NAME);
        activityRule = null;
    }

    public void updateButtonState() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    offerComparisonAppActivity.updateCompareBtnState();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
