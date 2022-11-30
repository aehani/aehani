package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.model.JobDetails;

public class OfferComparisonAppActivity extends AppCompatActivity {

    private Button editCurrJobBtn;
    private Button addNewJobBtn;
    private Button compareJobBtn;
    private Button adjustCompareSettingsBtn;
    private static final String TAG = "OfferComparisonAppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "On Create: starting");
        editCurrJobBtn = (Button) findViewById(R.id.editCurrJobBtn);
        addNewJobBtn = (Button) findViewById(R.id.addNewOfferBtn);
        compareJobBtn =  (Button) findViewById(R.id.compareOffersBtn);
        compareJobBtn.setEnabled(false);
        adjustCompareSettingsBtn = (Button) findViewById(R.id.adjustSettingsBtn);

        updateCompareBtnState();
    }

    public void updateCompareBtnState() {
        List<JobDetails> jobs = JobDetails.listJobDetails(OfferComparisonAppActivity.this);
        Log.d(TAG, "Job size now is--" +String.valueOf(jobs.size()));
        if(jobs.size() > 1) {
            // Enable Compare Job when two job offers exist in Db
            compareJobBtn.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "INSIDE: onResume");
        updateCompareBtnState();
    }

    public void handleMenuClick(View view){
        /* Make sure the right button is clicked*/
        Log.d(TAG, "--- in handle Menu Click : starting. --- ");
        Bundle b = new Bundle();

        if(view.getId() == R.id.editCurrJobBtn){
            /* Launch the activity */
            Log.d(TAG, "In currJob Edit btn click.");
            Intent intent = new Intent(OfferComparisonAppActivity.this, JobDetailsActivity.class);
            b.putString("screenTitle", "Current Job Details");
            b.putBoolean("isCurrentJob", true);
            intent.putExtras(b);
            startActivity(intent);
        } else if (view.getId() == R.id.addNewOfferBtn){
            Log.d(TAG, "In Add new Offer btn click --- should use existing Job Details Activity.");
            Intent intent = new Intent(OfferComparisonAppActivity.this, JobDetailsActivity.class);
            b.putString("screenTitle", "New Job Offer");
            b.putBoolean("isCurrentJob", false);
            intent.putExtras(b);
            startActivityForResult(intent, 2);
        } else if (view.getId() == R.id.compareOffersBtn){
            Log.d(TAG, "In compare Offers btn click");
            Intent intent = new Intent(OfferComparisonAppActivity.this, CompareJobOffersActivity.class);
            b.putString("screenTitle", "Compare Job Offers");
            intent.putExtras(b);
            startActivity(intent);
        } else if (view.getId() == R.id.adjustSettingsBtn){
            Log.d(TAG, "In adjust compare setting btn click ");
            Intent intent = new Intent(this, ComparisonSettingsActivity.class);
            startActivity(intent);
        }

    }


}