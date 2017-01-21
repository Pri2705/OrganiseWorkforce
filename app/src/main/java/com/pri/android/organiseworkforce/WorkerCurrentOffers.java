package com.pri.android.organiseworkforce;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerCurrentOffers extends Fragment {

    private DatabaseReference workerOffers;
    private Context mContext;
    RecyclerView recyclerView;
    ArrayList<JobOfferObject> jobOfferObjects = new ArrayList<>();
    WorkerOfferAdapter workerOfferAdapter = new WorkerOfferAdapter(jobOfferObjects);
    UserObject userObject;
    public WorkerCurrentOffers() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;

    }

    public static WorkerCurrentOffers newInstance(String param1, UserObject currentUser) {
        WorkerCurrentOffers fragment = new WorkerCurrentOffers();
        Bundle args = new Bundle();
        args.putSerializable("currentUser", currentUser);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_worker_current_offers, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.worker_offers);
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        userObject = (UserObject) getArguments().getSerializable("currentUser");
        getOffers(new OnGetDataListener() {
            @Override
            public void onStart() {

                progressDialog.setMessage("fetching Data...");
                progressDialog.show();

            }

            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                recyclerView.setAdapter(workerOfferAdapter);
            }
        });
        return view;
    }

    public interface OnGetDataListener {
        public void onStart();

        public void onSuccess();
    }

    private void getOffers(final OnGetDataListener onGetDataListener) {
        onGetDataListener.onStart();
        workerOffers = FirebaseDatabase.getInstance().getReference().child("workers")
                .child(userObject.getEmail().replace('.',',')).child("offers");
        workerOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    JobOfferObject workerOffer = messageSnapshot.getValue(JobOfferObject.class);
                    jobOfferObjects.add(workerOffer);
                }
                workerOfferAdapter.notifyDataSetChanged();
                onGetDataListener.onSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
