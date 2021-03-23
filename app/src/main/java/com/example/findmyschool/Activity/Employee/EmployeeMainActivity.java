package com.example.findmyschool.Activity.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.findmyschool.Adapter.Employee_Home_Adapter;
import com.example.findmyschool.R;
import com.example.findmyschool.view.CustomViewPager;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class EmployeeMainActivity extends AppCompatActivity {

    private SmoothBottomBar bottomBar;
    private CustomViewPager home_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);
        initView();
        initAdapter();
    }

    private void initAdapter() {
        Employee_Home_Adapter employee_home_adapter = new Employee_Home_Adapter(getSupportFragmentManager(), 2);
        home_viewpager.setAdapter(employee_home_adapter);
        home_viewpager.setOffscreenPageLimit(2);

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                home_viewpager.setCurrentItem(i);
                return false;
            }
        });
    }

    private void initView() {
        bottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);
        home_viewpager = (CustomViewPager) findViewById(R.id.home_viewpager);
        home_viewpager.disableScroll(true);
    }
}