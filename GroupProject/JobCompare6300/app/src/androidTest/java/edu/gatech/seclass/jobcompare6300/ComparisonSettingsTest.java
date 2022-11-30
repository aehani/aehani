package edu.gatech.seclass.jobcompare6300;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import edu.gatech.seclass.jobcompare6300.model.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;

@RunWith(AndroidJUnit4.class)
public class ComparisonSettingsTest {
    private int yearlySalaryWeight;
    private int yearlyBonusWeight;
    private int retirementBenefitsWeight;
    private int relocationStipendWeight;
    private int restrictedStockAwardWeight;
    private ComparisonSettings comparisonSettings = new ComparisonSettings(yearlySalaryWeight,yearlyBonusWeight,retirementBenefitsWeight,relocationStipendWeight,restrictedStockAwardWeight);

    @Rule
    public ActivityTestRule<ComparisonSettingsActivity> comparisonSettingsTestRule =
            new ActivityTestRule<ComparisonSettingsActivity>(ComparisonSettingsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, ComparisonSettingsActivity.class);
                    result.putExtra("screenTitle", "Comparison Settings");
                    return result;
                }
            };
    private ComparisonSettingsActivity comparisonSettingsActivity = null;
    Instrumentation.ActivityMonitor monitor =  getInstrumentation().addMonitor(ComparisonSettingsActivity.class.getName(), null, false);
    @Test
    public void testComparisonSettingsDisplayed() {
        //Save and cancel buttons
        assertNotNull(comparisonSettingsActivity.findViewById(R.id.saveButton));
        assertNotNull(comparisonSettingsActivity.findViewById(R.id.cancelButton));

        //Comparison settings
        assertNotNull(comparisonSettingsActivity.getAnnualSalary());
        assertNotNull(comparisonSettingsActivity.getYearlyBonus());
        assertNotNull(comparisonSettingsActivity.getRelocation());
        assertNotNull(comparisonSettingsActivity.getRetirement());
        assertNotNull(comparisonSettingsActivity.getStock());

        //Screen specific title
        assertNotNull(comparisonSettingsActivity.getScreenTitle());
    }
    @Test
    public void testSaveBttn() {
        //Verify either default or user settings exist in DB
        assertNotNull(ComparisonSettings.get(comparisonSettingsActivity));

        save();

        //Verify Comparison settings view is running
        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }
    public void save() {

        //Click save button
        onView(withId(R.id.saveButton)).perform(click());

        //Get comparison settings from DB
        ComparisonSettings comparisonSettingsDB = ComparisonSettings.get(comparisonSettingsActivity);

        //Verify comparison settings was found in DB
        assertNotNull(comparisonSettingsDB);

        //Verify the values pulled from DB as the same as the ones saved on the screen for comparison settings
        assertEquals(comparisonSettings.getYearlySalaryWeight(), comparisonSettingsDB.getYearlySalaryWeight());
        assertEquals(comparisonSettings.getYearlyBonusWeight(), comparisonSettingsDB.getYearlyBonusWeight());
        assertEquals(comparisonSettings.getRetirementBenefitsWeight(), comparisonSettingsDB.getRetirementBenefitsWeight());
        assertEquals(comparisonSettings.getRelocationStipendWeight(), comparisonSettingsDB.getRelocationStipendWeight());
        assertEquals(comparisonSettings.getRestrictedStockAwardWeight(), comparisonSettingsDB.getRestrictedStockAwardWeight());
    }

    @Test
    public void testCancelBttn() {
        //Verify that either default settings or user settings exist in DB
        assertNotNull(ComparisonSettings.get(comparisonSettingsActivity));

        //Click cancel button
        onView(withId(R.id.cancelButton)).perform(click());

        //Get comparison settings from DB
        ComparisonSettings comparisonSettingsDB = ComparisonSettings.get(comparisonSettingsActivity);

        //Verify comparison settings was found in DB
        assertNotNull(comparisonSettingsDB);
        //Verify the values pulled from DB are not the same because cancel button is clicked unless default
        if(comparisonSettings.getYearlySalaryWeight() != 0 && comparisonSettings.getYearlyBonusWeight() != 0 &&
                comparisonSettings.getRetirementBenefitsWeight() != 0 && comparisonSettings.getRelocationStipendWeight() != 0
         && comparisonSettings.getRestrictedStockAwardWeight() != 0) {
            assertNotEquals(comparisonSettings.getYearlySalaryWeight(), comparisonSettingsDB.getYearlySalaryWeight());
            assertNotEquals(comparisonSettings.getYearlyBonusWeight(), comparisonSettingsDB.getYearlyBonusWeight());
            assertNotEquals(comparisonSettings.getRetirementBenefitsWeight(), comparisonSettingsDB.getRetirementBenefitsWeight());
            assertNotEquals(comparisonSettings.getRelocationStipendWeight(), comparisonSettingsDB.getRelocationStipendWeight());
            assertNotEquals(comparisonSettings.getRestrictedStockAwardWeight(), comparisonSettingsDB.getRestrictedStockAwardWeight());
        }


        //Verify Comparison settings view is running
        Activity act1  = getInstrumentation().waitForMonitorWithTimeout(monitor, 3000);
        assertNotNull(act1);
        act1.finish();
    }
    @Test
    public void testEditComparisonSettings() {
        comparisonSettingsActivity = comparisonSettingsTestRule.getActivity();

        comparisonSettings.setYearlySalaryWeight(2);
        comparisonSettings.setYearlyBonusWeight(3);
        comparisonSettings.setRelocationStipendWeight(4);
        comparisonSettings.setRetirementBenefitsWeight(2);
        comparisonSettings.setRestrictedStockAwardWeight(6);

        loadComparisonSettingsOnUI();
        comparisonSettingsActivity = comparisonSettingsTestRule.getActivity();
        //Save updated comparison settings and verify values in DB
        save();
    }




    @Before
    public void setUp() throws Exception {
        comparisonSettingsActivity = comparisonSettingsTestRule.getActivity();
        comparisonSettingsActivity.deleteDatabase(DatabaseHelper.DB_NAME);

        loadComparisonSettingsOnUI();
    }

    @After
    public void tearDownAfterAll() throws Exception {
        comparisonSettingsTestRule.getActivity().deleteDatabase(DatabaseHelper.DB_NAME);
        comparisonSettingsTestRule = null;
    }

    public void loadComparisonSettingsOnUI() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    comparisonSettingsActivity.loadComparisonSettings(comparisonSettings);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
