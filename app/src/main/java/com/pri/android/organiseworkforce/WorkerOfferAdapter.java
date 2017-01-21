package com.pri.android.organiseworkforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 21-01-2017.
 */

public class WorkerOfferAdapter extends RecyclerView.Adapter<WorkerOfferAdapter.ViewHolder> {

    private  List<JobOfferObject> mValues;
    private Context mContext;
//    private  OnListFragmentInteractionListener mListener;

    public WorkerOfferAdapter(ArrayList<JobOfferObject> workerOffers) {
        mValues = workerOffers;
//        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.worker_offer_list_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.etCompanyName.setText(mValues.get(position).getCompanyName());
        holder.etSalary.setText(mValues.get(position).getBasicPay());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView etCompanyName;
        public  TextView etSalary;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            etCompanyName = (TextView) view.findViewById(R.id.worker_offers_company_name);
            etSalary = (TextView) view.findViewById(R.id.worker_offers_salary);
        }
    }
}
