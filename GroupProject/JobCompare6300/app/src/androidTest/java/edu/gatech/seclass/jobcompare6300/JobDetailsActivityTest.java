package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

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
public class JobDetailsActivityTest {
    private JobDetails jobDetails = new JobDetails();

    @Rule
    public ActivityTestRule<JobDetailsActivity> activityRule =
            new ActivityTestRule<JobDetailsActivity>(JobDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, JobDetailsActivity.class);
                    result.putExtra("isCurrentJob", true);
                    result.putExtra("screenTitle", "Current Job Details");
                    return result;
                }
            };

    @Rule
    public ActivityTestRule<OfferComparisonAppActivity> offerCompActivityRule = new ActivityTestRule<>(OfferComparisonAppActivity.class);

    private JobDetailsActivity jobDetailsActivity = null;
    private OfferComparisonAppActivity offerComparisonAppActivity = null;

    Instrumentation.ActivityMonitor monitor =  getInstrumentation().addMonitor(OfferComparisonAppActivity.class.getName(), null, false);

    /**
     * Verifies that the job details are present on the screen
     */
    @Test
    public void testJobDetailsDisplayed() {
        //Save and cancel buttons
        assertNotNull(jobDetailsActivity.findViewById(R.id.saveButton));
        assertNotNull(jobDetailsActivity.findViewById(R.id.cancelButton));

        //Location info
        assertNotNull(jobDetailsActivity.getCityInput());
        assertNotNull(jobDetailsActivity.getStateSpinner());
        assertNotNull(jobDetailsActivity.getCoiInput());

        //Job details
        assertNotNull(jobDetailsActivity.getTitleInput());
        assertNotNull(jobDetailsActivity.getCompanyInput());
        assertNotNull(jobDetailsActivity.getAnnualSalaryInput());
        assertNotNull(jobDetailsActivity.getYearlyBonusInput());
        assertNotNull(jobDetailsActivity.getRetirementMatchInput());
        assertNotNull(jobDetailsActivity.getRelocationStipendInput());
        assertNotNull(jobDetailsActivity.getRestrictedStockAwardInput());

        //Screen specific title
        assertNotNull(jobDetailsActivity.getScreenTitle());
    }

    /**
     * Verifies that the current job details screen title is correct
     */
    @Test
    public void testCurrentJobDetailsScreenTitle() {
        onView(withId(R.id.screenTitleText)).check(matches(withText("Current Job Details")));
    }

    /**
     * Verifies saving the current job details on the screen and verifying results in the DB
     */
    @Test
    public void testSave() {
        //Verify a current job does not exist in DB
        assertNull(JobDetails.getCurrentJob(jobDetailsActivity));

        save();

        //Verify the offer comp app activity is running
        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    /**
     * Clicks the save button and verifies expected results match the DB find
     */
    public void save() {
        //Click save button
        onView(withId(R.id.saveButton)).perform(click());

        //Get current job details from DB
        JobDetails currentJobFromDB = JobDetails.getCurrentJob(jobDetailsActivity);

        //Verify a current job was found
        assertNotNull(currentJobFromDB);

        //Verify the values pulled from DB as the same as the ones saved on the screen for current job
        assertEquals(jobDetails.isCurrentJob(), currentJobFromDB.isCurrentJob());
        assertEquals(jobDetails.getTitle(), currentJobFromDB.getTitle());
        assertEquals(jobDetails.getCompany(), currentJobFromDB.getCompany());
        assertEquals(jobDetails.getAnnualBonus(), currentJobFromDB.getAnnualBonus(), 0);
        assertEquals(jobDetails.getAnnualSalary(), currentJobFromDB.getAnnualSalary(), 0);
        assertEquals(jobDetails.getRelocStipend(), currentJobFromDB.getRelocStipend(), 0);
        assertEquals(jobDetails.getRestrictedStockUnit(), currentJobFromDB.getRestrictedStockUnit());
        assertEquals(jobDetails.getRetirementBenefitsPct(), currentJobFromDB.getRetirementBenefitsPct());
        assertEquals(jobDetails.getLocation().getCity(), currentJobFromDB.getLocation().getCity());
        assertEquals(jobDetails.getLocation().getState(), currentJobFromDB.getLocation().getState());
        assertEquals(jobDetails.getLocation().getCostOfLiving(), currentJobFromDB.getLocation().getCostOfLiving());
    }

    /**
     * Verifies that when the cancel button is clicked, the job details are not saved
     */
    @Test
    public void testCancel() {
        //Verify a current job is not found in DB
        assertNull(JobDetails.getCurrentJob(jobDetailsActivity));

        //Click cancel button
        onView(withId(R.id.cancelButton)).perform(click());

        //Verify the current job details have not been saved in the DB after cancel
        assertNull(JobDetails.getCurrentJob(jobDetailsActivity));

        //Verify the offer comp app activity is running
        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }

    /**
     * Verifies that the current job details can be saved and then modified in DB via activity
     */
    @Test
    public void testEditCurrentJobDetails() {
        //Verify a current job does not exist in DB
        assertNull(JobDetails.getCurrentJob(jobDetailsActivity));

        //Initial save for current job details
        save();

        //Click Current Job Details button
        offerComparisonAppActivity = offerCompActivityRule.getActivity();
        onView(withId(R.id.editCurrJobBtn)).perform(click());

        jobDetailsActivity = activityRule.getActivity();

        //Edit job details
        jobDetails.setTitle("Test updated title");
        jobDetails.getLocation().setCity("Indianapolis");
        jobDetails.getLocation().setState(State.IN);
        jobDetails.getLocation().setCostOfLiving(100);
        jobDetails.setId(1);

        loadJobDetailsOnUI();

        //Save updated current job details and verify values in DB
        save();
    }

    /**
     * Verifies that the job details fields show required error messages when no value is provided
     *
     */
    @Test
    public void testValidationErrorDisplayed() {
        onView(withId(R.id.titleInput)).perform(clearText(), replaceText(""));
        onView(withId(R.id.companyInput)).perform(clearText(), replaceText(""));
        onView(withId(R.id.annualSalaryInput)).perform(clearText(), replaceText("0"));
        onView(withId(R.id.coiInput)).perform(clearText(), replaceText("0"));
        onView(withId(R.id.cityInput)).perform(clearText(), replaceText("a"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.saveButton)).perform(click());
        onView(withId(R.id.titleInput)).check(matches(hasErrorText("Title is required")));
        onView(withId(R.id.coiInput)).check(matches(hasErrorText("Cost of living cannot be 0")));
        onView(withId(R.id.annualSalaryInput)).check(matches(hasErrorText("Annual Salary cannot be 0")));
        onView(withId(R.id.cityInput)).check(matches(hasErrorText("City is required and should at least be 3 characters long")));
        onView(withId(R.id.companyInput)).check(matches(hasErrorText("Company Name is required")));
    }

    @Before
    public void setUp() throws Exception {
        jobDetailsActivity = activityRule.getActivity();
        jobDetailsActivity.deleteDatabase(DatabaseHelper.DB_NAME);

        //Set Location info
        Location location = new Location("Chicago", State.IL, 200);
        jobDetails.setLocation(location);

        //Set other Job details
        jobDetails.setTitle("Test Title");
        jobDetails.setCompany("Test Company");
        jobDetails.setAnnualSalary(80000F);
        jobDetails.setAnnualBonus(8000F);
        jobDetails.setRetirementBenefitsPct(3);
        jobDetails.setRelocStipend(10000F);
        jobDetails.setRestrictedStockUnit(100);
        jobDetails.setCurrentJob(true);

        loadJobDetailsOnUI();
    }

    @After
    public void tearDownAfterAll() throws Exception {
        activityRule.getActivity().deleteDatabase(DatabaseHelper.DB_NAME);
        activityRule = null;
    }

    public void loadJobDetailsOnUI() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jobDetailsActivity.loadJobDetails(jobDetails);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
