package com.pri.android.organiseworkforce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    Spinner experianceSpinner;
    EditText etAdhaarNumber;
    EditText etPhoneNumber;
    EditText etAccountNumber;
    Spinner prefLocationSpinner;
    Spinner prefJobProfile;
    RadioGroup rgSelectedWorkingTime;
    Spinner expectedPaySpinner;
    ScrollView scrollView;

    EditText etCompanyName, etCompanyTinNumber, etCompanyPhoneNumber;

    Button btWorkerSubmit;
    Button btCompanySubmit;
    UserObject userObject;

    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference mWorker;
    private DatabaseReference mHiredWorker;
    private DatabaseReference mUnhiredWorker;

    private DatabaseReference mCompany;


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
        userObject = (UserObject) getIntent().getSerializableExtra(getString(R.string.user_data_login_to_signup));
        init();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public interface OnGetDataListener {
        public void onStart();

        public void onSuccess();
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
        experianceSpinner = (Spinner) findViewById(R.id.sign_up_experience_spinner);
        etAdhaarNumber = (EditText) findViewById(R.id.sign_up_adhaar_number);
        etPhoneNumber = (EditText) findViewById(R.id.sign_up_phone_number);
        etAccountNumber = (EditText) findViewById(R.id.sign_up_account_number);
        prefLocationSpinner = (Spinner) findViewById(R.id.sign_up_perf_location_spinner);
        prefJobProfile = (Spinner) findViewById(R.id.sign_up_job_profile_spinner);
        rgSelectedWorkingTime = (RadioGroup) findViewById(R.id.sign_up_work_time_radio);
        expectedPaySpinner = (Spinner) findViewById(R.id.sign_up_expected_pay_spinner);
        llCompanyForm = (LinearLayout) findViewById(R.id.sign_up_company_form);

        etCompanyName = (EditText) findViewById(R.id.sign_up_company_name);
        etCompanyTinNumber = (EditText) findViewById(R.id.sign_up_company_tin_number);
        etCompanyPhoneNumber = (EditText) findViewById(R.id.sign_up_company_phone_number);

        btCompanySubmit = (Button) findViewById(R.id.sign_up_company_submit);
        btWorkerSubmit = (Button) findViewById(R.id.sign_up_worker_submit);

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
        btWorkerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkerModel workerModel = new WorkerModel();
                workerModel.setUserObject(userObject);
                workerModel.setAge(etWorkerAge.getText().toString());
                workerModel.setEducation(workerEducationSpinner.getSelectedItem().toString());
                workerModel.setExperience(experianceSpinner.getSelectedItem().toString());
                workerModel.setAdhaarNumber(etAdhaarNumber.getText().toString());
                workerModel.setPhoneNumber(etPhoneNumber.getText().toString());
                workerModel.setAccountNumber(etAccountNumber.getText().toString());
                workerModel.setPrefWorkingTime(getSelectedTime());

                workerModel.setPrefLocation(prefLocationSpinner.getSelectedItem().toString());
                workerModel.setPrefJobProfile(prefJobProfile.getSelectedItem().toString());
                workerModel.setExpectedPay(expectedPaySpinner.getSelectedItem().toString());
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);

                pushWorkerData(new OnGetDataListener() {
                    @Override
                    public void onStart() {

                        progressDialog.setMessage("Uploading Data...");
                        progressDialog.show();

                    }

                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                    }
                }, workerModel);

            }
        });

        btCompanySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyModel companyModel = new CompanyModel();
                companyModel.setUserObject(userObject);

                companyModel.setCompanyName(etCompanyName.getText().toString());
                companyModel.setPhoneNumber(etCompanyPhoneNumber.getText().toString());
                companyModel.setTinNumber(etCompanyTinNumber.getText().toString());
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);

                pushCompanyModel(new OnGetDataListener() {
                    @Override
                    public void onStart() {

                        progressDialog.setMessage("Uploading Data...");
                        progressDialog.show();

                    }

                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                    }
                }, companyModel);
            }
        });

    }

    private void pushCompanyModel(final OnGetDataListener onGetDataListener, final CompanyModel companyModel) {
        onGetDataListener.onStart();
        mCompany = mFirebaseDatabase.getReference().child("company").child(companyModel.getEmail().replace(".", ","));
        mCompany.setValue(companyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onGetDataListener.onSuccess();
                Intent intent = new Intent(SignUp.this,EmployerMainActivity.class);
                intent.putExtra("currentUserCompany",companyModel);
                supportFinishAfterTransition();
                startActivity(intent);
            }
        });


    }

    private void pushWorkerData(final OnGetDataListener onGetDataListener, final WorkerModel workerModel) {
        onGetDataListener.onStart();
        mWorker = mFirebaseDatabase.getReference().child("workers").child(workerModel.getEmail().replace(".", ","));
        mWorker.setValue(workerModel);

        mUnhiredWorker = mFirebaseDatabase.getReference().child("unHiredWorker").child(workerModel.getEmail().replace(".", ","));
        mUnhiredWorker.setValue(workerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onGetDataListener.onSuccess();
                Intent intent = new Intent(SignUp.this,EmployeeMainActivity.class);
                intent.putExtra("currentUserWorker",workerModel);
                supportFinishAfterTransition();
                startActivity(intent);
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
        educationArrayList.add("Illiterate");
        educationArrayList.add("Know to read and write");
        educationArrayList.add("Matrix pass");
        educationArrayList.add("Graduate");

        location.add("Delhi");
        location.add("Mumbai");
        location.add("Bengaluru");
        location.add("Kolkata");
        location.add("Chennai");
        location.add("Hyderabad");
        location.add("Ahmedabad");
        location.add("Pune");

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
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                educationArrayList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workerEducationSpinner.setAdapter(educationAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                location);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefLocationSpinner.setAdapter(locationAdapter);


        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,
                mExperienceArrayList);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experianceSpinner.setAdapter(experienceAdapter);


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
