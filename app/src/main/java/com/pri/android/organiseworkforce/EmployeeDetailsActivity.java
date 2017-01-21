package com.pri.android.organiseworkforce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;

public class EmployeeDetailsActivity extends AppCompatActivity {

    ScrollView scrollView;
    EditText etWorkerAge;
    Spinner workerEducationSpinner;
    Spinner experianceSpinner;
    EditText etAdhaarNumber;
    EditText etPhoneNumber;
    EditText etAccountNumber;
    Spinner prefLocationSpinner;
    Spinner prefJobProfile;
    RadioGroup rgSelectedWorkingTime;
    Spinner expectedPaySpinner;
    Button editBt;
    WorkerModel mCurrentUser;


    ArrayList<String> educationArrayList = new ArrayList<>();
    ArrayList<String> mJobProfilesArrayList = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();
    ArrayList<String> expectedPay = new ArrayList<>();
    ArrayList<String> mExperienceArrayList = new ArrayList<>();
    ArrayList<String> mDurationsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        init();
    }

    private void init() {
        scrollView = (ScrollView) findViewById(R.id.sign_up_scroll_form);
        etWorkerAge = (EditText) findViewById(R.id.sign_up_age);
        workerEducationSpinner = (Spinner) findViewById(R.id.sign_up_education_spinner);
        experianceSpinner = (Spinner) findViewById(R.id.sign_up_experience_spinner);
        etAdhaarNumber = (EditText) findViewById(R.id.sign_up_adhaar_number);
        etPhoneNumber = (EditText) findViewById(R.id.sign_up_phone_number);
        etAccountNumber = (EditText) findViewById(R.id.sign_up_account_number);
        prefLocationSpinner = (Spinner) findViewById(R.id.sign_up_perf_location_spinner);
        prefJobProfile = (Spinner) findViewById(R.id.sign_up_job_profile_spinner);
        rgSelectedWorkingTime = (RadioGroup) findViewById(R.id.sign_up_work_time_radio);
        expectedPaySpinner = (Spinner) findViewById(R.id.sign_up_expected_pay_spinner);
        editBt = (Button)findViewById(R.id.edit_profile_bt);

        mCurrentUser = (WorkerModel)getIntent().getSerializableExtra("currentUser");

        etWorkerAge.setText(mCurrentUser.getAge());
        etAdhaarNumber.setText(mCurrentUser.getAdhaarNumber());
        etPhoneNumber.setText(mCurrentUser.getPhoneNumber());
        etAccountNumber.setText(mCurrentUser.getAccountNumber());

        populateArrayLists();
        setUpSpinners();

        workerEducationSpinner.setSelection(mCurrentUser.getEducationPos());
        experianceSpinner.setSelection(mCurrentUser.getExperiencePos());
        prefLocationSpinner.setSelection(mCurrentUser.getPrefLocationPos());
        prefJobProfile.setSelection(mCurrentUser.getPrefJobProfilePos());
        expectedPaySpinner.setSelection(mCurrentUser.getExpectedPayPos());

        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentUser.setAge(etWorkerAge.getText().toString());
                mCurrentUser.setEducation(workerEducationSpinner.getSelectedItem().toString());
                mCurrentUser.setExperience(experianceSpinner.getSelectedItem().toString());
                mCurrentUser.setAdhaarNumber(etAdhaarNumber.getText().toString());
                mCurrentUser.setPhoneNumber(etPhoneNumber.getText().toString());
                mCurrentUser.setAccountNumber(etAccountNumber.getText().toString());
                mCurrentUser.setPrefWorkingTime(getSelectedTime());


                mCurrentUser.setPrefLocation(prefLocationSpinner.getSelectedItem().toString());
                mCurrentUser.setPrefJobProfile(prefJobProfile.getSelectedItem().toString());
                mCurrentUser.setExpectedPay(expectedPaySpinner.getSelectedItem().toString());

            }
        });
    }

    private String getSelectedTime() {
        if (rgSelectedWorkingTime.getCheckedRadioButtonId() == R.id.sign_up_work_time_day) {
            return "day";
        } else {
            return "night";
        }
    }

    private void populateArrayLists() {
        educationArrayList.add("illiterate");
        educationArrayList.add("know to read and write");
        educationArrayList.add("matrix pass");
        educationArrayList.add("Graduate");

        location.add("");

        mJobProfilesArrayList.add("Construction Worker");
        mJobProfilesArrayList.add("Industrial Worker");
        mJobProfilesArrayList.add("Office Helper");
        mJobProfilesArrayList.add("Farmer");

        expectedPay.add("5000");
        expectedPay.add("10000");
        expectedPay.add("15000");
        expectedPay.add("20000");

        mDurationsArrayList.add("Short Term");
        mDurationsArrayList.add("Long Term");

        mExperienceArrayList.add("0+ years");
        mExperienceArrayList.add("1+ years");
        mExperienceArrayList.add("2+ years");
        mExperienceArrayList.add("3+ years");
        mExperienceArrayList.add("4+ years");

    }

    private void setUpSpinners() {
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<String>(EmployeeDetailsActivity.this, android.R.layout.simple_spinner_item,
                educationArrayList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workerEducationSpinner.setAdapter(educationAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(EmployeeDetailsActivity.this, android.R.layout.simple_spinner_item,
                location);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefLocationSpinner.setAdapter(locationAdapter);


        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<String>(EmployeeDetailsActivity.this, android.R.layout.simple_spinner_item,
                mExperienceArrayList);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experianceSpinner.setAdapter(experienceAdapter);


        ArrayAdapter<String> jobProfileAdapter = new ArrayAdapter<String>(EmployeeDetailsActivity.this, android.R.layout.simple_spinner_item,
                mJobProfilesArrayList);
        jobProfileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefJobProfile.setAdapter(jobProfileAdapter);

        ArrayAdapter<String> expectedPayAdapter = new ArrayAdapter<String>(EmployeeDetailsActivity.this, android.R.layout.simple_spinner_item,
                expectedPay);

        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expectedPaySpinner.setAdapter(expectedPayAdapter);

    }
}
