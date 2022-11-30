package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;

public class CompareJobOffersActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private int checkCount;
    private String jd1;
    private String jd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_job_offers);
        tableLayout = (TableLayout) findViewById(R.id.compareJobsTable);
        checkCount = 0;
        loadData();
    }



    public void loadData() {
        List<JobDetails> jobDetails = JobDetails.listJobDetails(this);
        ComparisonSettings comparisonSettings = ComparisonSettings.get(this);
        for(JobDetails jd : jobDetails) {
            jd.calculateScore(comparisonSettings);
        }
        Collections.sort(jobDetails, Comparator.comparing(JobDetails::getScore));
        Collections.reverse(jobDetails);

        TableRow header = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );

        tableRowParams.setMargins(3, 3, 2, 10);

        header.setLayoutParams(tableRowParams);

        header.setBackgroundResource(com.google.android.material.R.color.design_default_color_primary);
        TextView checkboxCol = new TextView(this);
        //checkboxCol.setBackgroundColor(Color.DKGRAY);
        header.addView(checkboxCol);
        TextView titleCol = new TextView(this);
        titleCol.setText("Title");
        titleCol.setTextSize(18);
        titleCol.setPadding(0,12,15,25);

        titleCol.setTextColor(Color.WHITE);
        header.addView(titleCol);
        TextView companyCol = new TextView(this);
        companyCol.setText("Company");
        //companyCol.setBackgroundColor(Color.DKGRAY);
        companyCol.setTextColor(Color.WHITE);
        companyCol.setTextSize(18);
        companyCol.setPadding(0,12,15,25);
        header.addView(companyCol);
        TextView scoreCol = new TextView(this);
        scoreCol.setText("Score");
        //scoreCol.setBackgroundColor(Color.DKGRAY);
        scoreCol.setTextColor(Color.WHITE);
        scoreCol.setTextSize(18);
        scoreCol.setPadding(0,12,15,25);
        header.addView(scoreCol);
        tableLayout.addView(header);

        for(JobDetails jd : jobDetails) {
            TableRow tableRow = new TableRow(this);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setChecked(false);
            checkBox.setContentDescription(String.valueOf(jd.getId()));
            checkBox.setId(jd.getId());
            Context context = this;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(checkCount >= 2 && isChecked){
                        errorToast(getString(R.string.compare_job_err_only_two));
                        buttonView.setChecked(false);
                    } else if(isChecked){
                        checkCount += 1;
                        if(jd1 == null){
                            jd1 = buttonView.getContentDescription().toString();
                        } else {
                            jd2 = buttonView.getContentDescription().toString();
                        }
                    } else {
                        checkCount -= 1;
                        if(jd1 == buttonView.getContentDescription().toString()){
                            jd1 = null;
                        } else {
                            jd2 = null;
                        }
                    }
                }
            });
            tableRow.addView(checkBox);

            TextView title = new TextView(this);
            title.setText(jd.getTitle());
            title.setTextSize(16);

            TextView company = new TextView(this);
            company.setText(jd.getCompany());
            company.setTextSize(16);

            TextView score = new TextView(this);
            score.setText(String.format("%.2f", jd.getScore()));
            score.setTextSize(16);

            if (jd.isCurrentJob()) {
                title.setTextColor(Color.BLUE);
                company.setTextColor(Color.BLUE);
                score.setTextColor(Color.BLUE);
            }
            tableRow.addView(title);
            tableRow.addView(company);
            tableRow.addView(score);
            tableLayout.addView(tableRow);
        }
    }

    public void handleCancelClick(View view){
        finish();
    }

    public void handleCompareClick(View view){
        if(checkCount == 2) {
            Bundle b = new Bundle();
            b.putString("screenTitle", "Current Job Details");
            b.putString("jobDetails1", jd1);
            b.putString("jobDetails2", jd2);
            Intent intent = new Intent(this, ComparisonResultsActivity.class);
            intent.putExtras(b);
            this.startActivity(intent);
        } else {
            errorToast(getString(R.string.compare_job_err_two_or_more));
        }
    }

    /**
     * Create Error Toast that is visible to the user
     * **/
    private void errorToast(String textErr) {
        Toast errToast = Toast.makeText(this, textErr, Toast.LENGTH_LONG);
        TextView v = (TextView) errToast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.rgb(204, 0, 0));
        v.setTextSize(18);
        v.setGravity(Gravity.CENTER);
        v.setShadowLayer(1.5f, -1, 1, Color.BLACK);

        errToast.setGravity(Gravity.BOTTOM, 0, 450);
        errToast.show();
    }
}