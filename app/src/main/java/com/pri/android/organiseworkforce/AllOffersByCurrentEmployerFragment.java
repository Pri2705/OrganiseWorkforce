package com.pri.android.organiseworkforce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class AllOffersByCurrentEmployerFragment extends Fragment {

    private Context mContext;
    private UserObject mCurrentUser;
    private RecyclerView mRecyclerView;
    private View mRoot;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPostsRef;
    private DatabaseReference mUserRef;
    private FirebaseRecyclerAdapter mAdapter;
    private TextView mEmptyRvTv;
    private ProgressDialog mProgress;
    private FloatingActionButton mNewPostFab;

    public AllOffersByCurrentEmployerFragment() {
        // Required empty public constructor
    }


    public static AllOffersByCurrentEmployerFragment newInstance(String param1, UserObject currentUser) {
        AllOffersByCurrentEmployerFragment fragment = new AllOffersByCurrentEmployerFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_all_offers_by_current_employer, container, false);

        mRecyclerView = (RecyclerView)mRoot.findViewById(R.id.all_offers_recyclerview);
        mEmptyRvTv = (TextView)mRoot.findViewById(R.id.empty_recyclerview_textview);
        mNewPostFab = (FloatingActionButton)mRoot.findViewById(R.id.new_post_fab);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserRef = mFirebaseDatabase.getReference().child("employers").child(mCurrentUser.getEmail().replace(".", ","));
        mPostsRef = mFirebaseDatabase.getReference().child("employers").child(mCurrentUser.getEmail().replace(".", ",")).child("posts");
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Loading...");
        mProgress.show();
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("posts")){
                    mEmptyRvTv.setVisibility(View.GONE);
                    populateRecyclerView();
                }else {
                    mProgress.dismiss();
                    mEmptyRvTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mNewPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewOfferActivity.class);
                intent.putExtra("currentUser", mCurrentUser);
                startActivity(intent);
            }
        });

        return mRoot;
    }

    private void populateRecyclerView() {
        mAdapter = new FirebaseRecyclerAdapter<JobOfferObject, JobOffersHolder>(JobOfferObject.class, R.layout.all_offers_recyclerview_row, JobOffersHolder.class, mPostsRef) {
            @Override
            protected void populateViewHolder(JobOffersHolder viewHolder, JobOfferObject model, int position) {
                mProgress.dismiss();
                Date postDate = new Date(Long.valueOf(model.getTimestamp()));
                viewHolder.setDate(postDate.getDate() + "");
                viewHolder.setJobProfile(model.getJobProfile());
                viewHolder.setBasicPay(model.getBasicPay());
                viewHolder.setNumOfEmployees(model.getNumOfEmployees() + "");
                viewHolder.setStatus(model.getStatus());

                viewHolder.getmView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO show details
                    }
                });
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
