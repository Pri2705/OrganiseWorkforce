package com.pri.android.organiseworkforce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;

import co.ceryle.radiorealbutton.library.RadioRealButton;
import co.ceryle.radiorealbutton.library.RadioRealButtonGroup;

public class SignUp extends AppCompatActivity {
    RadioRealButton workerButton;
    RadioRealButton companyButton;
    RadioRealButtonGroup radioButtonGroup;
    int selectedButton = -1;
    boolean firstRadioButtonClick = true;
    RelativeLayout relativeLayoutRoot;
    LinearLayout llFormWrapper;
    LinearLayout llWorkerForm;
    LinearLayout llCompanyForm;
    EditText etWorkerAge;
    Spinner workerEducationSpinner;
    EditText etExperience;
    EditText etAdhaarNumber;
    EditText etPhoneNumber;
    EditText etAccountNumber;
    Spinner prefLocationSpinner;
    Spinner prefJobProfile;
    RadioGroup rgSelectedWorkingTime;
    Spinner expectedPaySpinner;
    ScrollView scrollView;

    ArrayList<String> educationArrayList = new ArrayList<>();
    ArrayList<String> mJobProfilesArrayList = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();
    ArrayList<String> expectedPay = new ArrayList<>();
    ArrayList<String> mExperienceArrayList = new ArrayList<>();
    ArrayList<String> mDurationsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        relativeLayoutRoot = (RelativeLayout) findViewById(R.id.activity_sign_up);
        radioButtonGroup = (RadioRealButtonGroup) findViewById(R.id.sign_up_radio_group);
        workerButton = (RadioRealButton) findViewById(R.id.sign_up_worker_radio_button);
        companyButton = (RadioRealButton) findViewById(R.id.sign_up_company_radio_button);
        llWorkerForm = (LinearLayout) findViewById(R.id.sign_up_worker_form);
        scrollView = (ScrollView) findViewById(R.id.sign_up_scroll_form);
        etWorkerAge = (EditText) findViewById(R.id.sign_up_age);
        workerEducationSpinner = (Spinner) findViewById(R.id.sign_up_education_spinner);
        etExperience = (EditText) findViewById(R.id.sign_up_experience);
        etAdhaarNumber = (EditText) findViewById(R.id.sign_up_adhaar_number);
        etPhoneNumber = (EditText) findViewById(R.id.sign_up_phone_number);
        etAccountNumber = (EditText) findViewById(R.id.sign_up_account_number);
        prefLocationSpinner = (Spinner) findViewById(R.id.sign_up_perf_location_spinner);
        prefJobProfile = (Spinner) findViewById(R.id.sign_up_job_profile_spinner);
        rgSelectedWorkingTime = (RadioGroup) findViewById(R.id.sign_up_work_time_radio);
        expectedPaySpinner = (Spinner) findViewById(R.id.sign_up_expected_pay_spinner);
        llCompanyForm = (LinearLayout) findViewById(R.id.sign_up_company_form);
        populateArrayLists();
        setUpSpinners();
        radioButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, final int position) {

                if (selectedButton != position) {
                    selectedButton = position;
                    if (firstRadioButtonClick) {
                        final int distance = -1 * (relativeLayoutRoot.getHeight() / 2
                                - (int) (radioButtonGroup.getHeight() / 1.6));
                        final int formDistance = radioButtonGroup.getHeight();
                        radioButtonGroup.animate()
                                .translationY(distance)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        scrollView.setVisibility(View.VISIBLE);
                                        scrollView.animate()
                                                .translationY(0)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        super.onAnimationEnd(animation);

                                                        if (position == 0) {
                                                            llWorkerForm.setVisibility(View.VISIBLE);
                                                            llCompanyForm.setVisibility(View.GONE);

                                                        } else {
                                                            llWorkerForm.setVisibility(View.GONE);
                                                            llCompanyForm.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                });
                                    }
                                });


                    } else {
                        if (position == 0) {
                            llWorkerForm.setVisibility(View.VISIBLE);
                            llCompanyForm.setVisibility(View.GONE);
                        } else {
                            llWorkerForm.setVisibility(View.GONE);
                            llCompanyForm.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
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

        mExperienceArrayList.add("0+");
        mExperienceArrayList.add("1+");
        mExperienceArrayList.add("2+");
        mExperienceArrayList.add("3+");
        mExperienceArrayList.add("4+");

    }

    private void setUpSpinners() {
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                educationArrayList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workerEducationSpinner.setAdapter(educationAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                location);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefLocationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<String> jobProfileAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                mJobProfilesArrayList);
        jobProfileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefJobProfile.setAdapter(jobProfileAdapter);

        ArrayAdapter<String> expectedPayAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                expectedPay);

        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expectedPaySpinner.setAdapter(expectedPayAdapter);

    }
}
