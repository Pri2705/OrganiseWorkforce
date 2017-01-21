package com.pri.android.organiseworkforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Priyanshu on 21-01-2017.
 */

public class HiredEmployeeHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView etEmail;
    public final TextView etName;
    public final CircleImageView userAvatar;

    public View getmView() {
        return mView;
    }
    public void setEmail(String email){
        etEmail.setText(email);
    }
    public void setName(String name){
        etName.setText(name);
    }
    public void setProfileImage(String url, Context context){
        Picasso.with(context).load(url).placeholder(R.drawable.avatar).noFade().into(userAvatar);
    }

    public HiredEmployeeHolder(View view) {
        super(view);
        mView = view;
        etEmail = (TextView) view.findViewById(R.id.user_email);
        etName = (TextView) view.findViewById(R.id.user_name);
        userAvatar = (CircleImageView)view.findViewById(R.id.friend_avatar);
    }
}
