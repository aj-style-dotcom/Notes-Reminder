package com.codelancer.notesreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        intent=new Intent(Intent.ACTION_VIEW);
    }

    public void rateUs(View v){
        //TODO rate on playstore
    }


    public void reqPay(View v){
        intent.setData(Uri.parse(getString(R.string.paypal_link)));
        startActivity(intent);
    }


    @SuppressLint("NonConstantResourceId")
    public void followClick(View v){

        switch (v.getId()){
            case R.id.insta:
                intent.setData(Uri.parse(getString(R.string.instagram_link)));
                startActivity(intent);
                break;
            case R.id.twitter:
                intent.setData(Uri.parse(getString(R.string.twitter_link)));
                startActivity(intent);
                break;
            case R.id.youtube:
                intent.setData(Uri.parse(getString(R.string.youtube_link)));
                startActivity(intent);
                break;
            case R.id.medium:
                intent.setData(Uri.parse(getString(R.string.medium_link)));
                startActivity(intent);
                break;
        }
    }


}