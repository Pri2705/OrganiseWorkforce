package com.pri.android.organiseworkforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

public class EmployeeDetailsActivity extends AppCompatActivity {

    ScrollView scrollView;
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
    Button editBt;

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
        etExperience = (EditText) findViewById(R.id.sign_up_experience);
        etAdhaarNumber = (EditText) findViewById(R.id.sign_up_adhaar_number);
        etPhoneNumber = (EditText) findViewById(R.id.sign_up_phone_number);
        etAccountNumber = (EditText) findViewById(R.id.sign_up_account_number);
        prefLocationSpinner = (Spinner) findViewById(R.id.sign_up_perf_location_spinner);
        prefJobProfile = (Spinner) findViewById(R.id.sign_up_job_profile_spinner);
        rgSelectedWorkingTime = (RadioGroup) findViewById(R.id.sign_up_work_time_radio);
        expectedPaySpinner = (Spinner) findViewById(R.id.sign_up_expected_pay_spinner);
        editBt = (Button)findViewById(R.id.edit_profile_bt);

        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
