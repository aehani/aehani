package edu.gatech.seclass.jobcompare6300;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;

public class ComparisonSettingsActivity extends AppCompatActivity {
    private Button saveJobBtn;
    private Button cancelBtn;
    Spinner annualSalary;
    Spinner yearlyBonus;
    Spinner retirement;
    Spinner relocation;
    Spinner stock;
    private TextView screenTitle;
    private static final String JD_TAG = "ComparisonSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_settings);
        screenTitle = (TextView) findViewById(R.id.screenTitleText);
        annualSalary = (Spinner) findViewById(R.id.annual_salary_spinner);
        yearlyBonus = (Spinner) findViewById(R.id.yearly_bonus_spinner);
        retirement = (Spinner) findViewById(R.id.retirement_match_spinner);
        relocation = (Spinner) findViewById(R.id.relocation_stipend_spinner);
        stock = (Spinner) findViewById(R.id.restricted_stock_award_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ComparisonSettingsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.setting_choices));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ComparisonSettings comparisonSettings = ComparisonSettings.get(ComparisonSettingsActivity.this);
        annualSalary.setAdapter(myAdapter);
        annualSalary.setSelection(comparisonSettings.getYearlySalaryWeight());
        yearlyBonus.setAdapter(myAdapter);
        yearlyBonus.setSelection(comparisonSettings.getYearlyBonusWeight());
        retirement.setAdapter(myAdapter);
        retirement.setSelection(comparisonSettings.getRetirementBenefitsWeight());
        relocation.setAdapter(myAdapter);
        relocation.setSelection(comparisonSettings.getRelocationStipendWeight());
        stock.setAdapter(myAdapter);
        stock.setSelection(comparisonSettings.getRestrictedStockAwardWeight());
        Log.d(JD_TAG, "On Create: starting");
        saveJobBtn = (Button) findViewById(R.id.saveButton);
        saveJobBtn.setOnClickListener(v -> {
            Log.d(JD_TAG, "On SaveJob Click: Saving??");
            int annualSalaryWeight = Integer.parseInt(annualSalary.getSelectedItem().toString());
            int yearlyBonusWeight = Integer.parseInt(yearlyBonus.getSelectedItem().toString());
            int retirementMatchWeight = Integer.parseInt(retirement.getSelectedItem().toString());
            int relocationWeight = Integer.parseInt(relocation.getSelectedItem().toString());
            int stockWeight = Integer.parseInt(stock.getSelectedItem().toString());
            Log.d(annualSalary.getSelectedItem().toString(), yearlyBonus.getSelectedItem().toString());
            ComparisonSettings comparison = new ComparisonSettings(annualSalaryWeight,
                    yearlyBonusWeight,retirementMatchWeight, relocationWeight,stockWeight);
            comparison.save(ComparisonSettingsActivity.this);
            // Send back to Main Menu
            finish();
        });

        cancelBtn = (Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(v -> {
            // Send back to Main Menu
            finish();
        });

    }


    public void loadComparisonSettings(ComparisonSettings comparisonSettings) {
        annualSalary.setSelection(comparisonSettings.getYearlySalaryWeight());
        yearlyBonus.setSelection(comparisonSettings.getYearlyBonusWeight());
        retirement.setSelection(comparisonSettings.getRetirementBenefitsWeight());
        relocation.setSelection(comparisonSettings.getRelocationStipendWeight());
        stock.setSelection(comparisonSettings.getRestrictedStockAwardWeight());
    }

    public Spinner getAnnualSalary() {
        return annualSalary;
    }
    public Spinner getYearlyBonus() {
        return yearlyBonus;
    }
    public Spinner getRelocation() {
        return relocation;
    }
    public Spinner getRetirement() {
        return retirement;
    }
    public Spinner getStock() {
        return stock;
    }
    public TextView getScreenTitle() {
        return screenTitle;
    }
}