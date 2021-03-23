package com.example.findmyschool.Activity.Recruiter;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.findmyschool.Common.OnNextPreviousClick;
import com.example.findmyschool.Fragment.Job.CompanyDetailFragment;
import com.example.findmyschool.Fragment.Job.JobDescriptionFragment;
import com.example.findmyschool.Fragment.Job.JobDetailFragment;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.example.findmyschool.view.NonSwipeableViewPager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RecruiterJobPostActivity extends AppCompatActivity {

    private NonSwipeableViewPager vp_viewpager;
    private ViewPagerAdapter adapter;
    public static PostJobs job = null;
    private DatabaseReference databaseReference;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_job_post);
        type = getIntent().getIntExtra("type", 0);
        job = new PostJobs();
        if (type == 1) {
            job = (PostJobs) getIntent().getSerializableExtra("data");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        setupViewPager(vp_viewpager);

    }


    private void initView() {
        vp_viewpager = (NonSwipeableViewPager) findViewById(R.id.vp_viewpager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new JobDetailFragment(type, new OnNextPreviousClick() {
            @Override
            public void onNextClick() {
                vp_viewpager.setCurrentItem(1);
            }

            @Override
            public void onPreviousClick() {

            }
        }), "Job Details");
        adapter.addFrag(new JobDescriptionFragment(type, new OnNextPreviousClick() {
            @Override
            public void onNextClick() {
                vp_viewpager.setCurrentItem(2);
            }

            @Override
            public void onPreviousClick() {
                vp_viewpager.setCurrentItem(0);
            }
        }), "Job Description");
        adapter.addFrag(new CompanyDetailFragment(type, new OnNextPreviousClick() {
            @Override
            public void onNextClick() {
                if (type == 0) {
                    String id = String.valueOf(System.currentTimeMillis());
                    job.setId(id);
                    databaseReference.getRoot().child("Jobs").child(id).setValue(job);
                    String j_post = "";
                    if (Application.getRecruiter().getJobpost() != null) {
                        j_post = Application.getRecruiter().getJobpost();
                    }
                    if (TextUtils.isEmpty(j_post)) {
                        j_post = id;
                    } else {
                        j_post = j_post + "," + id;
                    }
                    databaseReference.getRoot().child("Recruiter").child(Application.getRecruiter().getId()).child("jobpost").setValue(j_post);
                    Application.getRecruiter().setJobpost(j_post);
                } else {
                    databaseReference.getRoot().child("Jobs").child(job.getId()).setValue(job);
                }
                finish();
            }

            @Override
            public void onPreviousClick() {
                vp_viewpager.setCurrentItem(1);
            }
        }), "Company Details");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

}