package com.pri.android.organiseworkforce;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Priyanshu on 21-01-2017.
 */

public class JobOffersHolder extends RecyclerView.ViewHolder{

    private TextView date, jobProfile, basicPay, numOfEmployees, status;
    private View mView;

    public View getmView() {
        return mView;
    }

    public void setDate(String dateStr) {
        date.setText(dateStr);
    }

    public void setJobProfile(String jobProfileStr) {
        jobProfile.setText(jobProfileStr);
    }

    public void setBasicPay(String basicPayStr) {
        basicPay.setText(basicPayStr);
    }

    public void setNumOfEmployees(String numOfEmployeesStr) {
        numOfEmployees.setText(numOfEmployeesStr);
    }

    public void setStatus(String statusStr) {
        status.setText(statusStr);
    }

    public JobOffersHolder(View itemView) {
        super(itemView);
        date = (TextView)itemView.findViewById(R.id.date_tv);
        jobProfile = (TextView)itemView.findViewById(R.id.job_profile_tv);
        basicPay = (TextView)itemView.findViewById(R.id.basic_pay_tv);
        numOfEmployees = (TextView)itemView.findViewById(R.id.num_employees_tv);
        status = (TextView)itemView.findViewById(R.id.status_tv);
    }
}
