package edu.gatech.seclass.jobcompare6300;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;
import edu.gatech.seclass.jobcompare6300.model.State;

/**
 * Test class for the functions of JobDetails
 */
public class JobDetailsTest {
    public JobDetails jobDetails = new JobDetails();

    public ComparisonSettings comparisonSettings = new ComparisonSettings(7, 7,  7,  7, 7);

    @Test
    public void test_calculateScore_weights7() {
        assertEquals(48705F, jobDetails.calculateScore(comparisonSettings), 0);
    }

    @Test
    public void test_calculateScore_weights1() {
        comparisonSettings = new ComparisonSettings(1, 1, 1, 1, 1);
        assertEquals(6957.858F, jobDetails.calculateScore(comparisonSettings), 0);
    }

    @Test
    public void test_calculateAdjustedYearlySalary_col200() {
        assertEquals(40000F, jobDetails.calculateAdjustedYearlySalary(), 0);
    }

    @Test
    public void test_calculateAdjustedYearlySalary_col100() {
        jobDetails.getLocation().setCostOfLiving(100);
        assertEquals(80000F, jobDetails.calculateAdjustedYearlySalary(), 0);
    }

    @Test
    public void test_calculateAdjustedYearlySalary_salary0() {
        jobDetails.setAnnualSalary(0F);
        assertEquals(0F, jobDetails.calculateAdjustedYearlySalary(), 0);
    }

    @Test
    public void test_calculateAdjustedYearlyBonus_col200() {
        assertEquals(2500F, jobDetails.calculateAdjustedYearlyBonus(), 0);
    }

    @Test
    public void test_calculateAdjustedYearlyBonus_col100() {
        jobDetails.getLocation().setCostOfLiving(100);
        assertEquals(5000F, jobDetails.calculateAdjustedYearlyBonus(), 0);
    }

    @Test
    public void test_calculateAdjustedYearlyBonus_bonus0() {
        jobDetails.setAnnualBonus(0F);
        assertEquals(0F, jobDetails.calculateAdjustedYearlyBonus(), 0);
    }

    @Before
    public void setup() {
        Location location = new Location("Chicago", State.IL, 200);
        jobDetails.setLocation(location);
        jobDetails.setAnnualSalary(80000F);
        jobDetails.setAnnualBonus(5000F);
        jobDetails.setRetirementBenefitsPct(3);
        jobDetails.setRelocStipend(5000F);
        jobDetails.setRestrictedStockUnit(20);
    }
}