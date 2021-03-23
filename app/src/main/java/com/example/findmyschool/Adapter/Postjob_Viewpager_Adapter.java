package com.example.findmyschool.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.findmyschool.Fragment.Job.Company_Profile_Fragment;
import com.example.findmyschool.Fragment.Job.Postjob_Fragment;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.Model.Recruiter;

public class Postjob_Viewpager_Adapter extends FragmentPagerAdapter {
    PostJobs post_job;
    Recruiter recruiter;

    public Postjob_Viewpager_Adapter(@NonNull FragmentManager fm, int behavior, PostJobs post_job, Recruiter recruiter) {
        super(fm, behavior);
        this.post_job = post_job;
        this.recruiter = recruiter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Postjob_Fragment(post_job);
            case 1:
                return new Company_Profile_Fragment(recruiter);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
