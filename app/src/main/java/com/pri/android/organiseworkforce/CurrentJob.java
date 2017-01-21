package com.pri.android.organiseworkforce;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentJob extends Fragment {


    public CurrentJob() {
        // Required empty public constructor
    }

    public static CurrentJob newInstance(String param1, UserObject currentUser) {
        CurrentJob fragment = new CurrentJob();
        Bundle args = new Bundle();
        args.putSerializable("currentUser", currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_worker_history, container, false);

        return view;
    }

}
