package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;

public class ComparisonResultsActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private JobDetails jobDetails1;
    private JobDetails jobDetails2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_results);
        tableLayout = (TableLayout) findViewById(R.id.compareAnalysisTable);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        if(getIntent().getExtras() != null) {
            jobDetails1 = JobDetails.getJobDetail(this, Integer.parseInt(getIntent().getStringExtra("jobDetails1")));
            jobDetails2 = JobDetails.getJobDetail(this, Integer.parseInt(getIntent().getStringExtra("jobDetails2")));
        }

        loadData();
    }

    public void loadData(){
        TableRow header = new TableRow(this);
        header.setGravity(Gravity.CENTER_HORIZONTAL);


        header.setBackgroundResource(com.google.android.material.R.color.design_default_color_primary);
        header.setPadding(0,12,15,25);
        TextView atterCol = new TextView(this);
        //atterCol.setBackgroundColor(Color.DKGRAY);
        header.addView(atterCol);
        TextView job1Col = new TextView(this);
        job1Col.setText("Job 1");
        //job1Col.setBackgroundColor(Color.DKGRAY);
        job1Col.setTextColor(Color.WHITE);
        header.addView(job1Col);
        TextView job2Col = new TextView(this);
        job2Col.setText("Job 2");
        //job2Col.setBackgroundColor(Color.DKGRAY);
        job2Col.setTextColor(Color.WHITE);
        header.addView(job2Col);
        tableLayout.addView(header);
        tableLayout.addView(addRow("Title", jobDetails1.getTitle(), jobDetails2.getTitle()));
        tableLayout.addView(addRow("Company", jobDetails1.getCompany(), jobDetails2.getCompany()));
        Location j1Loc = jobDetails1.getLocation();
        Location j2Loc = jobDetails2.getLocation();
        String j1 = j1Loc.getCity() + ", " + j1Loc.getState();
        String j2 = j2Loc.getCity() + ", " + j2Loc.getState();
        tableLayout.addView(addRow("Location", j1, j2));
        j1 = String.valueOf((int)(jobDetails1.getAnnualSalary() * (100.0 / j1Loc.getCostOfLiving())));
        j2 = String.valueOf((int)(jobDetails2.getAnnualSalary() * (100.0 / j2Loc.getCostOfLiving())));
        tableLayout.addView(addRow("Adjusted Annual Salary", j1, j2));
        j1 = String.valueOf((int)(jobDetails1.getAnnualBonus() * (100.0 / j1Loc.getCostOfLiving())));
        j2 = String.valueOf((int)(jobDetails2.getAnnualBonus() * (100.0 / j2Loc.getCostOfLiving())));
        tableLayout.addView(addRow("Adjusted Yearly Bonus", j1, j2));
        j1 = String.valueOf(jobDetails1.getRetirementBenefitsPct());
        j2 = String.valueOf(jobDetails2.getRetirementBenefitsPct());
        tableLayout.addView(addRow("Retirement Match", j1, j2));
        j1 = String.valueOf(jobDetails1.getRelocStipend());
        j2 = String.valueOf(jobDetails2.getRelocStipend());
        tableLayout.addView(addRow("Relocation", j1, j2));
        j1 = String.valueOf(jobDetails1.getRestrictedStockUnit());
        j2 = String.valueOf(jobDetails2.getRestrictedStockUnit());
        tableLayout.addView(addRow("Stock Award", j1, j2));
    }

    @SuppressLint("ResourceAsColor")
    public TableRow addRow(String atter, String job1, String job2){
        TableRow row = new TableRow(this);
        TextView atterCol = new TextView(this);
        atterCol.setText(atter);
        atterCol.setBackgroundResource(com.google.android.material.R.color.design_default_color_primary);
        atterCol.setTextColor(Color.WHITE);
        atterCol.setPadding(25,12,15,25);
        row.addView(atterCol);
        TextView job1Col = new TextView(this);
        job1Col.setText(job1);
        job1Col.setPadding(25,12,15,15);
        row.addView(job1Col);
        TextView job2Col = new TextView(this);
        job2Col.setText(job2);
        job2Col.setPadding(25,12,15,15);
        row.addView(job2Col);
        return row;
    }

    public void handleCompareMoreClick(View view){
        finish();
    }

    public void handleMainMenuClick(View view){
        Intent intent = new Intent(getApplicationContext(), OfferComparisonAppActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
