package com.pri.android.organiseworkforce;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeMainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private UserObject mCurretnUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserRef;
    private WorkerModel mCurrentWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        mCurretnUser = (UserObject)getIntent().getSerializableExtra("currentUser");
        mCurrentWorker = (WorkerModel)getIntent().getSerializableExtra("currentUserWorker");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserRef = mFirebaseDatabase.getReference().child("company").child(mCurretnUser.getEmail().replace(".", ","));

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCurrentWorker = (WorkerModel)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mViewPager = (ViewPager)findViewById(R.id.vpPager);
        FragmentPagerAdapter adapterViewPager = new EmployeeMainActivity.MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.action_details:
                intent = new Intent(EmployeeMainActivity.this, EmployeeDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                LoginActivity.signOut(getApplicationContext());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0: // Fragment # 0 - This will show FirstFragment
                    return AllOffersByCurrentEmployerFragment.newInstance("All Offers", mCurretnUser);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return HiredEmployeesFragment.newInstance("Hired Employees", mCurretnUser);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
