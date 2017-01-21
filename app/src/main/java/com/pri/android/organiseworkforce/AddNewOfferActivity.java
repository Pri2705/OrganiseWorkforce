package com.pri.android.organiseworkforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;

public class AddNewOfferActivity extends AppCompatActivity {

    private Spinner mJobProfileSpinner, mExperienceSpinner, mWorkingTimeSpinner, mDurationSpinner;
    private EditText mBasicPayEt, mOvertimePayEt, mNumOfEmployees;
    private RadioGroup mMedicalBenefitsRadiogrp;
    private RadioButton mYesBt, mNoBt;
    private Button mSubmitBt;
    private ArrayList<String> mJobProfilesArrayList, mExperienceArrayList, mWorkingTimesArrayList, mDurationsArrayList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPostsRef;
    private UserObject mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_offer);

        mCurrentUser = (UserObject)getIntent().getSerializableExtra("currentUser");
        init();
    }

    private void init() {
        mJobProfileSpinner = (Spinner)findViewById(R.id.job_profile_spinner);
        mExperienceSpinner = (Spinner)findViewById(R.id.experience_spinner);
        mWorkingTimeSpinner = (Spinner)findViewById(R.id.working_time_spinner);
        mDurationSpinner = (Spinner)findViewById(R.id.duration_spinner);
        mBasicPayEt = (EditText)findViewById(R.id.basic_salry_et);
        mOvertimePayEt = (EditText)findViewById(R.id.overtime_salary_et);
        mNumOfEmployees = (EditText)findViewById(R.id.num_employees_et);
        mMedicalBenefitsRadiogrp = (RadioGroup)findViewById(R.id.medical_benefits_radiogroup);
        mYesBt = (RadioButton)findViewById(R.id.yes_radiobt);
        mNoBt = (RadioButton)findViewById(R.id.no_radiobt);
        mSubmitBt = (Button)findViewById(R.id.submit_new_post_bt);

        mJobProfilesArrayList = new ArrayList<>();
        mExperienceArrayList = new ArrayList<>();
        mWorkingTimesArrayList = new ArrayList<>();
        mDurationsArrayList = new ArrayList<>();

        populateArrayLists();

        setUpSpinners();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPostsRef = mFirebaseDatabase.getReference().child("employers").child(mCurrentUser.getEmail().replace(".", ",")).child("offers");

        mSubmitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmptyFields()){
                    JobOfferObject newOffer = new JobOfferObject();
                    newOffer.setJobProfile(mJobProfileSpinner.getSelectedItem().toString());
                    newOffer.setExperience(mExperienceSpinner.getSelectedItem().toString());
                    newOffer.setWorkingTime(mWorkingTimeSpinner.getSelectedItem().toString());
                    newOffer.setDuration(mDurationSpinner.getSelectedItem().toString());
                    newOffer.setBasicPay(mBasicPayEt.getText().toString());
                    newOffer.setOvertimePay(mOvertimePayEt.getText().toString());
                    newOffer.setNumOfEmployees(Integer.valueOf(mNumOfEmployees.getText().toString()));
                    int selectedButtonId = mMedicalBenefitsRadiogrp.getCheckedRadioButtonId();
                    RadioButton tempButton = (RadioButton)findViewById(selectedButtonId);
                    newOffer.setMedicalBenefits(tempButton.getText().toString());

                    DatabaseReference currentPostRef = mPostsRef.push();
                    String key = currentPostRef.getKey();
                    newOffer.setKey(key);
                    currentPostRef.setValue(newOffer);
                    currentPostRef.child("timestamp").setValue(ServerValue.TIMESTAMP);
                }
            }
        });

    }

    private void setUpSpinners() {
        ArrayAdapter<String> jobProfileAdapter = new ArrayAdapter<String>(AddNewOfferActivity.this, android.R.layout.simple_spinner_item,
                mJobProfilesArrayList);
        jobProfileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJobProfileSpinner.setAdapter(jobProfileAdapter);

        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<String>(AddNewOfferActivity.this, android.R.layout.simple_spinner_item,
                mExperienceArrayList);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExperienceSpinner.setAdapter(experienceAdapter);

        ArrayAdapter<String> workingTimeAdapter = new ArrayAdapter<String>(AddNewOfferActivity.this, android.R.layout.simple_spinner_item,
                mWorkingTimesArrayList);
        workingTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWorkingTimeSpinner.setAdapter(workingTimeAdapter);

        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>(AddNewOfferActivity.this, android.R.layout.simple_spinner_item,
                mDurationsArrayList);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDurationSpinner.setAdapter(durationAdapter);


    }

    private boolean checkEmptyFields() {
        if(mBasicPayEt.getText().toString().isEmpty()){
            mBasicPayEt.setError("Please Enter Basic Pay");
            return false;
        }else if(mOvertimePayEt.getText().toString().isEmpty()){
            mOvertimePayEt.setError("Please Enter Overtime Pay");
            return false;
        }else if(mNumOfEmployees.getText().toString().isEmpty()){
            mNumOfEmployees.setError("Please Enter Number Of Employees Required");
            return false;
        }else{
            return true;
        }
    }

    private void populateArrayLists() {
        mJobProfilesArrayList.add("Construction Worker");
        mJobProfilesArrayList.add("Industrial Worker");
        mJobProfilesArrayList.add("Office Helper");
        mJobProfilesArrayList.add("Farmer");

        mExperienceArrayList.add("0+");
        mExperienceArrayList.add("1+");
        mExperienceArrayList.add("2+");
        mExperienceArrayList.add("3+");
        mExperienceArrayList.add("4+");

        mWorkingTimesArrayList.add("Day (9-5)");
        mWorkingTimesArrayList.add("Night (7-4)");
        mWorkingTimesArrayList.add("Flexible");

        mDurationsArrayList.add("Short Term");
        mDurationsArrayList.add("Long Term");
    }
}
