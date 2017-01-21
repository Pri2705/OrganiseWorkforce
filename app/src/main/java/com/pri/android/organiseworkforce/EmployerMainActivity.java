package com.pri.android.organiseworkforce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployerMainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private UserObject mCurretnUser;
    private CompanyModel mCurrentCompany;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_main);
        mCurretnUser = (UserObject)getIntent().getSerializableExtra("currentUser");
        mCurrentCompany = (CompanyModel) getIntent().getSerializableExtra("currentUserCompany");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserRef = mFirebaseDatabase.getReference().child("company").child(mCurretnUser.getEmail().replace(".", ","));

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCurrentCompany = (CompanyModel) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mViewPager = (ViewPager)findViewById(R.id.vpPager);
        FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);

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
                    return AllOffersByCurrentEmployerFragment.newInstance("All Offers", mCurrentCompany);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return HiredEmployeesFragment.newInstance("Hired Employees", mCurrentCompany);
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
