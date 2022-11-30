package edu.gatech.seclass.jobcompare6300;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.jobcompare6300.model.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;
import edu.gatech.seclass.jobcompare6300.model.State;


@RunWith(AndroidJUnit4.class)
public class ComparisonResultsActivityTest {
    private JobDetails jobDetails1 = new JobDetails();
    private JobDetails jobDetails2 = new JobDetails();

    private void initJobDetails(JobDetails jobDetails, String title, String company, Location location, float salary, float bonus, float relocStipend, int retirement, int stock, boolean curr){
        jobDetails.setTitle(title);
        jobDetails.setCompany(company);
        jobDetails.setLocation(location);
        jobDetails.setAnnualSalary(salary);
        jobDetails.setAnnualBonus(bonus);
        jobDetails.setRelocStipend(relocStipend);
        jobDetails.setRetirementBenefitsPct(retirement);
        jobDetails.setRestrictedStockUnit(stock);
        jobDetails.setCurrentJob(curr);
    }

    public void initDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Location location = new Location("city1", State.AK, 100);
        initJobDetails(jobDetails1,"test1", "test1", location, 100000, 10000, 10000, 10, 10000, true);
        jobDetails1.save(context);
        location.setCity("city2");
        location.setCostOfLiving(120);
        initJobDetails(jobDetails2, "test2", "test2", location, 120000, 12000, 12000, 12, 12000, false);
        jobDetails2.save(context);
    }

    @Rule
    public ActivityTestRule<ComparisonResultsActivity> comparisonResultsTestRule =
            new ActivityTestRule<ComparisonResultsActivity>(ComparisonResultsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    initDb();
                    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Bundle b = new Bundle();
                    b.putString("screenTitle", "Current Job Details");
                    b.putString("jobDetails1", "1");
                    b.putString("jobDetails2", "2");
                    Intent intent = new Intent(context, ComparisonResultsActivity.class);
                    intent.putExtras(b);

                    return intent;
                }
            };
    public ActivityTestRule<CompareJobOffersActivity> activityRule =
            new ActivityTestRule<CompareJobOffersActivity>(CompareJobOffersActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, CompareJobOffersActivity.class);
                    result.putExtra("screenTitle", "Compare Job Offers");
                    return result;
                }
            };

    private ComparisonResultsActivity comparisonResultsActivity = null;
    Instrumentation.ActivityMonitor monitor =  getInstrumentation().addMonitor(ComparisonResultsActivity.class.getName(), null, false);

    @Test
    // Description: Test if return to main menu button goes back.
    public void testCancel() {
        assertNotNull(comparisonResultsActivity.findViewById(R.id.cancelButton));
        onView(withId(R.id.cancelButton)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    @Test
    // Description: Test if compare more button goes back.
    public void testCompareMore() {
        assertNotNull(comparisonResultsActivity.findViewById(R.id.compareMoreButton));

        onView(withId(R.id.compareMoreButton)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    @Before
    public void setUp() throws Exception {
        comparisonResultsActivity = comparisonResultsTestRule.getActivity();


        loadComparisonResultsOnUI();
    }

    @After
    public void tearDownAfterAll() throws Exception {
        comparisonResultsTestRule.getActivity().deleteDatabase(DatabaseHelper.DB_NAME);
        comparisonResultsTestRule = null;
    }

    public void loadComparisonResultsOnUI() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    comparisonResultsActivity.loadData();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
