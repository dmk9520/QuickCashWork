package com.example.findmyschool.Activity.Recruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.findmyschool.Adapter.Employee_Home_Adapter;
import com.example.findmyschool.Adapter.Recruiter_Home_Adapter;
import com.example.findmyschool.R;
import com.example.findmyschool.view.CustomViewPager;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;

public class RecruiterMainActivity extends AppCompatActivity {

    private CustomViewPager recruiter_viewpager;
    private LinearLayout ll_centerbutton,ll_post_job,ll_profile;
    private ImageView iv_post_job,iv_profile;
    private TextView tv_post_job,tv_profile;
    private RelativeLayout rl_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);
        initView(savedInstanceState);
        initAdapter();
        initListener();
    }

    private void initAdapter() {
        Recruiter_Home_Adapter recruiter_home_adapter = new Recruiter_Home_Adapter(getSupportFragmentManager(), 2);
        recruiter_viewpager.setAdapter(recruiter_home_adapter);
        recruiter_viewpager.setOffscreenPageLimit(2);
    }

    private void initListener() {
        ll_centerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecruiterMainActivity.this, RecruiterJobPostActivity.class).putExtra("type", 0));
            }
        });

        ll_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelect(iv_post_job,tv_post_job,iv_profile,tv_profile);
                recruiter_viewpager.setCurrentItem(0);
                rl_bottom.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelect(iv_profile,tv_profile,iv_post_job,tv_post_job);
                recruiter_viewpager.setCurrentItem(1);
                rl_bottom.setBackgroundColor(getResources().getColor(R.color.lite_gray));
            }
        });
    }

    private void initView(Bundle savedInstanceState) {
        ll_centerbutton=(LinearLayout)findViewById(R.id.ll_centerbutton);
        ll_post_job=(LinearLayout)findViewById(R.id.ll_post_job);
        ll_profile=(LinearLayout)findViewById(R.id.ll_profile);
        iv_post_job=(ImageView)findViewById(R.id.iv_post_job);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        tv_post_job=(TextView)findViewById(R.id.tv_post_job);
        tv_profile=(TextView)findViewById(R.id.tv_profile);
        recruiter_viewpager = (CustomViewPager) findViewById(R.id.recruiter_viewpager);
        rl_bottom=(RelativeLayout)findViewById(R.id.rl_bottom);

        recruiter_viewpager.disableScroll(false);

        setSelect(iv_post_job,tv_post_job,iv_profile,tv_profile);
        recruiter_viewpager.setCurrentItem(0);
        rl_bottom.setBackgroundColor(getResources().getColor(R.color.white));

    }

    private void setSelect(ImageView image1,TextView text1,ImageView image2,TextView text2){
        image1.setColorFilter(getResources().getColor(R.color.ColorPrimary));
        text1.setTextColor(getResources().getColor(R.color.ColorPrimary));
        image2.setColorFilter(getResources().getColor(R.color.gray));
        text2.setTextColor(getResources().getColor(R.color.gray));
    }
}