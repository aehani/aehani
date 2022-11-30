package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.JobDetails;
import edu.gatech.seclass.jobcompare6300.model.Location;
import edu.gatech.seclass.jobcompare6300.model.State;

public class JobDetailsActivity extends AppCompatActivity {

    private Button saveJobBtn;
    private Button cancelBtn;
    private EditText titleInput;
    private EditText companyInput;
    private EditText cityInput;
    private EditText coiInput;
    private EditText annualSalaryInput;
    private EditText yearlyBonusInput;
    private EditText retirementMatchInput;
    private EditText relocationStipendInput;
    private EditText restrictedStockAwardInput;
    private TextView screenTitle;
    private Spinner stateSpinner;
    private static final String JD_TAG = "JobDetailsActivity";
    private boolean isCurrentJob;
    private int currentJobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Log.d(JD_TAG, "On Create: starting");

        saveJobBtn = (Button) findViewById(R.id.saveButton);
        saveJobBtn.setOnClickListener(v -> {
            Log.d(JD_TAG, "On SaveJob Click: Saving??");
            if(!validateFields()) {
                saveJobDetails();
                // Send back to Main Menu
                finish();
            }


        });

        cancelBtn = (Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(v -> {
            // Send back to Main Menu without save
            finish();
        });

        addItemsToStateSpinner();

        //Initialize screen fields
        titleInput = (EditText) findViewById(R.id.titleInput);
        companyInput = (EditText) findViewById(R.id.companyInput);
        cityInput = (EditText) findViewById(R.id.cityInput);
        coiInput = (EditText) findViewById(R.id.coiInput);
        annualSalaryInput = (EditText) findViewById(R.id.annualSalaryInput);
        yearlyBonusInput = (EditText) findViewById(R.id.yearlyBonusInput);
        retirementMatchInput = (EditText) findViewById(R.id.retirementMatchInput);
        relocationStipendInput = (EditText) findViewById(R.id.relocationStipendInput);
        restrictedStockAwardInput = (EditText) findViewById(R.id.restrictedStockAwardInput);
        screenTitle = (TextView) findViewById(R.id.screenTitleText);

        setScreenSpecificAttributes();
    }

    /**
     * Get the extra bundle values to set the screen-specific attributes based on
     * if it's the current job details or new job offer screen
     */
    private void setScreenSpecificAttributes() {
        Bundle bundle = getIntent().getExtras();
        screenTitle.setText(bundle.getString("screenTitle"));
        isCurrentJob = bundle.getBoolean("isCurrentJob");

        if (isCurrentJob) {
            loadJobDetails(JobDetails.getCurrentJob(JobDetailsActivity.this));
        }
    }

    /**
     * Add the States to the spinner on screen
     */
    private void addItemsToStateSpinner() {
        stateSpinner =  (Spinner) findViewById(R.id.stateInput);
        stateSpinner.setAdapter(new ArrayAdapter<State>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, State.values()));
    }

    /**
     * Save JobDetails instance to DB based on what the user entered on the screen
     */
    public void saveJobDetails() {

        JobDetails jobDetails = new JobDetails();
        jobDetails.setAnnualBonus(Float.parseFloat(yearlyBonusInput.getText().toString()));
        jobDetails.setCompany(companyInput.getText().toString());
        jobDetails.setAnnualSalary(Float.parseFloat(annualSalaryInput.getText().toString()));
        jobDetails.setRelocStipend(Float.parseFloat(relocationStipendInput.getText().toString()));
        jobDetails.setTitle(titleInput.getText().toString());
        jobDetails.setRestrictedStockUnit(Integer.parseInt(restrictedStockAwardInput.getText().toString()));
        jobDetails.setRetirementBenefitsPct(Integer.parseInt(retirementMatchInput.getText().toString()));
        jobDetails.setCurrentJob(isCurrentJob);
        if (currentJobId != 0) {
            jobDetails.setId(currentJobId);
        }

        String city = cityInput.getText().toString();
        State state = State.valueOf(stateSpinner.getSelectedItem().toString());
        int col = Integer.parseInt(coiInput.getText().toString());

        Location location = Location.getLocation(city, state, JobDetailsActivity.this);
        //Create new Location in DB if not found
        if (location == null) {
            location = new Location(city, state, col);
            location.setId(Math.toIntExact(location.save(JobDetailsActivity.this)));
        } else {
            //Set COL if updating existing Location from DB
            location.setCostOfLiving(col);
            location.save(JobDetailsActivity.this);
        }

        jobDetails.setLocation(location);

        long rowInserted = jobDetails.save(JobDetailsActivity.this);

        if(rowInserted != -1){
            makeToast("Job Details saved! ");
        } else {
            makeToast("Sorry, we couldn't save the job details. Please try again.");
        }

    }

    private boolean validateFields(){
        boolean hasErrors = false;
        if(titleInput.getText().toString() == null
                || titleInput.getText().toString().length() < 1
                || !containsOneAlphabet(titleInput.getText().toString())
        ){
            titleInput.setError("Title is required");
            hasErrors = true;
        }
        if(companyInput.getText().toString() == null
                || companyInput.getText().toString().length() < 1
                || !containsOneAlphabet(companyInput.getText().toString())
        ) {
            companyInput.setError("Company Name is required");
            hasErrors = true;
        }

        if(cityInput.getText().toString() == null
                || cityInput.getText().toString().length() < 3
                || !containsOneAlphabet(cityInput.getText().toString())
        ) {
            cityInput.setError("City is required and should at least be 3 characters long");
            hasErrors = true;
        }

        if(annualSalaryInput.getText().toString() == null
                || annualSalaryInput.getText().toString().length() < 1
        ) {
            annualSalaryInput.setError("Annual Salary is required");
            hasErrors = true;
        }

        if(coiInput.getText().toString() == null
                || coiInput.getText().toString().equals("0")
        ) {
            coiInput.setError("Cost of living cannot be 0");
            hasErrors = true;
        }

        if(annualSalaryInput.getText().toString() == null
                || annualSalaryInput.getText().toString().equals("0")
        ) {
            annualSalaryInput.setError("Annual Salary cannot be 0");
            hasErrors = true;
        }

        return hasErrors;

    }

    /**
     * Taken from A4: CS6300, author:nkumar323
     * **/
    private boolean containsOneAlphabet(String in){
        return in.matches(".*[a-zA-Z]+.*");
    }

    /**
     *
     * Make Toast that is visible
     * @param txtMsg text message that needs to be shown to the user.
     * **/
    private void makeToast(String txtMsg){
        Toast toast = Toast.makeText(this, txtMsg, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.rgb(0, 196, 15));
        v.setTextSize(22);
        v.setGravity(Gravity.CENTER);
        v.setShadowLayer(1.5f, -1, 1, Color.BLACK);

        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    /**
     * Load current job details on screen if previously entered
     * 
     * @param details Existing current job details
     */
    public void loadJobDetails(JobDetails details) {
        if (details != null) {
            annualSalaryInput.setText(Float.toString(details.getAnnualSalary()));
            companyInput.setText(details.getCompany());
            yearlyBonusInput.setText(Float.toString(details.getAnnualBonus()));
            relocationStipendInput.setText(Float.toString(details.getRelocStipend()));
            titleInput.setText(details.getTitle());
            restrictedStockAwardInput.setText(Integer.toString(details.getRestrictedStockUnit()));
            retirementMatchInput.setText(Integer.toString(details.getRetirementBenefitsPct()));
            if (details.getLocation() != null) {
                if (details.getLocation().getState() != null) {
                    stateSpinner.setSelection(details.getLocation().getState().ordinal());
                }
                cityInput.setText(details.getLocation().getCity());
                coiInput.setText(Integer.toString(details.getLocation().getCostOfLiving()));
            }
            currentJobId = details.getId();
        }
    }

    public EditText getTitleInput() {
        return titleInput;
    }

    public EditText getCompanyInput() {
        return companyInput;
    }

    public EditText getCityInput() {
        return cityInput;
    }

    public EditText getCoiInput() {
        return coiInput;
    }

    public EditText getAnnualSalaryInput() {
        return annualSalaryInput;
    }

    public EditText getYearlyBonusInput() {
        return yearlyBonusInput;
    }

    public EditText getRetirementMatchInput() {
        return retirementMatchInput;
    }

    public EditText getRelocationStipendInput() {
        return relocationStipendInput;
    }

    public EditText getRestrictedStockAwardInput() {
        return restrictedStockAwardInput;
    }

    public TextView getScreenTitle() {
        return screenTitle;
    }

    public Spinner getStateSpinner() {
        return stateSpinner;
    }
}