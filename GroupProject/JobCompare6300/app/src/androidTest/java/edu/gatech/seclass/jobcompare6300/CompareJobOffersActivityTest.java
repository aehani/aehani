package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.gatech.seclass.jobcompare6300.model.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;
import edu.gatech.seclass.jobcompare6300.model.State;

public class CompareJobOffersActivityTest {
    private JobDetails jobDetails1 = new JobDetails();
    private JobDetails jobDetails2 = new JobDetails();
    private JobDetails jobDetails3 = new JobDetails();

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
        location.setCity("city3");
        location.setCostOfLiving(130);
        initJobDetails(jobDetails3, "test3", "test3", location, 130000, 13000, 13000, 13, 13000, false);
        jobDetails3.save(context);
    }

    @Rule
    public ActivityTestRule<CompareJobOffersActivity> compareJobOffersRule =
            new ActivityTestRule<CompareJobOffersActivity>(CompareJobOffersActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, CompareJobOffersActivity.class);
                    result.putExtra("screenTitle", "Compare Job Offers");
                    return result;
                }
            };
    public ActivityTestRule<OfferComparisonAppActivity> activityRule =
            new ActivityTestRule<OfferComparisonAppActivity>(OfferComparisonAppActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, OfferComparisonAppActivity.class);
                    return result;
                }
            };

    private CompareJobOffersActivity compareJobOffersActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(CompareJobOffersActivity.class.getName(), null, false);

    @Test
    // Description: Test if return to main menu button goes back.
    public void testCancel() {
        assertNotNull(compareJobOffersActivity.findViewById(R.id.cancelButton));
        onView(withId(R.id.cancelButton)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    @Test
    // Description: Test if nothing selected then toast
    public void testNoneSelected() throws Exception {
        assertNotNull(compareJobOffersActivity.findViewById(R.id.compareButton));
        onView(withId(R.id.compareButton)).perform(click());
        String toastMsg = compareJobOffersActivity.getString(R.string.compare_job_err_two_or_more);
        onView(withText(toastMsg)).inRoot(withDecorView(not(is(compareJobOffersRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Thread.sleep(4000);
    }

    @Test
    // Description: Test if more than 2 selected then toast
    public void testThreeSelected() throws Exception {
        onView(withId(jobDetails1.getId())).perform(click());
        onView(withId(jobDetails2.getId())).perform(click());
        onView(withId(jobDetails3.getId())).perform(click());
        String toastMsg = compareJobOffersActivity.getString(R.string.compare_job_err_only_two);
        onView(withText(toastMsg)).inRoot(withDecorView(not(is(compareJobOffersRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Thread.sleep(4000);
    }

    @Test
    // Description: Test navigating to compare.
    public void testNavigationToCompare() {
        onView(withId(jobDetails1.getId())).perform(click());
        onView(withId(jobDetails2.getId())).perform(click());
        assertNotNull(compareJobOffersActivity.findViewById(R.id.compareButton));
        onView(withId(R.id.compareButton)).perform(click());

        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }


    @Before
    public void setUp() throws Exception {
        initDb();
        compareJobOffersActivity = compareJobOffersRule.getActivity();

        loadResultsOnUI();
    }

    @After
    public void tearDown() throws Exception {
        compareJobOffersRule.getActivity().deleteDatabase(DatabaseHelper.DB_NAME);
        compareJobOffersActivity.finish();
        compareJobOffersRule = null;
    }

    public void loadResultsOnUI() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    compareJobOffersActivity.loadData();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}