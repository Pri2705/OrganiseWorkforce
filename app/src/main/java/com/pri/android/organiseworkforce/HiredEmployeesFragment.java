package com.pri.android.organiseworkforce;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HiredEmployeesFragment extends Fragment {

    private Context mContext;
    private UserObject mCurrentUser;
    private RecyclerView mRecyclerView;
    private View mRoot;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mHiredByCurrentUserRef;
    private DatabaseReference mHiredRef;
    private FirebaseRecyclerAdapter mAdapter;

    public HiredEmployeesFragment() {
        // Required empty public constructor
    }


    public static HiredEmployeesFragment newInstance(String param1, UserObject currentUser) {
        HiredEmployeesFragment fragment = new HiredEmployeesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putSerializable("currentUser", currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            mCurrentUser = (UserObject)getArguments().getSerializable("currentUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_hired_employees, container, false);

        mRecyclerView = (RecyclerView)mRoot.findViewById(R.id.all_hired_recyclerview);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mHiredRef = mFirebaseDatabase.getReference().child("hired");
        mHiredByCurrentUserRef = mFirebaseDatabase.getReference().child("hired").child(mCurrentUser.getEmail().replace(".", ","));

        mHiredRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("posts")){
                    populateRecyclerView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mRoot;
    }

    private void populateRecyclerView() {
        //TODO populate recycler view based on the employeeUserObject
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
