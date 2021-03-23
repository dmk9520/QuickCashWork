package com.example.findmyschool.Activity.Employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.findmyschool.Activity.MainActivity;
import com.example.findmyschool.Activity.Recruiter.Recruiter_Setting_Activity;
import com.example.findmyschool.BuildConfig;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.FirebaseDatabase;

public class Employee_Setting_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private LinearLayout share, rate, contact_us, privacy;
    private Button logout;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setting);
        initView();
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        rate.setOnClickListener(this);
        contact_us.setOnClickListener(this);
        privacy.setOnClickListener(this);
        logout.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        share = (LinearLayout) findViewById(R.id.share);
        rate = (LinearLayout) findViewById(R.id.rate);
        contact_us = (LinearLayout) findViewById(R.id.contact_us);
        privacy = (LinearLayout) findViewById(R.id.privacy);
        logout = (Button) findViewById(R.id.logout);
        delete = (Button) findViewById(R.id.delete);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getResources().getString(R.string.app_name))));
                break;
            case R.id.contact_us:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Demo@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.privacy:

                break;
            case R.id.logout:
                Application.putLogin(false);
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
            case R.id.share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete this account ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Employees").child(Application.getEmployee().getId()).removeValue();
                                Application.putLogin(false);
                                Application.setEmployee(null);
                                Application.setRecruiter(null);
                                Intent i = new Intent(Employee_Setting_Activity.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }


}