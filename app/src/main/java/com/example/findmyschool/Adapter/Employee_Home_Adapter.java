package com.example.findmyschool.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.findmyschool.Fragment.Employee.Employee_Job_Fragment;
import com.example.findmyschool.Fragment.Employee.Employee_Profile_Fragment;
import com.example.findmyschool.Fragment.Employee.Employee_Resume_Fragment;

public class Employee_Home_Adapter extends FragmentPagerAdapter {
    int i;

    public Employee_Home_Adapter(FragmentManager manager, int i) {
        super(manager, i);
        this.i = i;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Employee_Job_Fragment();
           /* case 1:
                return new Employee_Resume_Fragment();*/
            case 1:
                return new Employee_Profile_Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return i;
    }
}
