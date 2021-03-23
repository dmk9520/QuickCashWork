package com.example.findmyschool.Fragment.Job;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.CategoryAdapter;
import com.example.findmyschool.Common.OnItemClickListner;
import com.example.findmyschool.Common.OnNextPreviousClick;
import com.example.findmyschool.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.findmyschool.Activity.Recruiter.RecruiterJobPostActivity.job;

public class JobDetailFragment extends Fragment implements OnItemClickListner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout ll_job_category;
    private TextView tv_category;
    private EditText tv_location;
    private EditText et_start_salary;
    private EditText et_end_salary;
    private EditText et_staff;
    private PopupWindow popupcategory;
    int[] category = new int[]{R.string.tital1, R.string.tital2, R.string.tital3, R.string.tital4, R.string.tital5, R.string.tital6, R.string.tital7, R.string.tital8, R.string.tital9, R.string.tital10, R.string.tital11, R.string.tital12, R.string.tital13, R.string.tital14, R.string.tital15, R.string.tital16, R.string.tital17, R.string.tital18, R.string.tital19, R.string.tital20, R.string.tital21, R.string.tital22, R.string.tital23, R.string.tital24, R.string.tital25, R.string.tital26, R.string.tital27, R.string.tital28, R.string.tital29, R.string.tital30, R.string.tital31, R.string.tital32, R.string.tital33, R.string.tital34, R.string.tital35, R.string.tital36, R.string.tital37, R.string.tital38, R.string.tital39, R.string.tital40, R.string.tital41, R.string.tital42, R.string.tital43, R.string.tital44};
    OnNextPreviousClick onNextPreviousClick;
    private LinearLayout ll_next;
    private EditText tv_job_post;
    private RadioGroup job_type;
    private View view;
    private LinearLayout job_date_lay;
    private LinearLayout job_date_click;
    private TextView tv_job_date;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int type;

    public JobDetailFragment() {

    }

    public JobDetailFragment(int type, OnNextPreviousClick onNextPreviousClick) {
        this.type = type;
        this.onNextPreviousClick = onNextPreviousClick;
    }


    public static JobDetailFragment newInstance(String param1, String param2) {
        JobDetailFragment fragment = new JobDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_job_detail, container, false);
        initView(view);
        initClick();
        if (type == 1) {
            setData();
        }
        return view;
    }

    private void setData() {
        if (job.getJobtype().equals("All Day")) {
            job_date_lay.setVisibility(View.GONE);
            job_type.check(R.id.type_1);
        } else if (job.getJobtype().equals("Part time")) {
            job_date_lay.setVisibility(View.GONE);
            job_type.check(R.id.type_2);
        } else if (job.getJobtype().equals("Mandatory")) {
            job_date_lay.setVisibility(View.VISIBLE);
            job_type.check(R.id.type_3);
            tv_job_date.setText(job.getJobdate());
        }
        tv_category.setText(job.getCompanytype());
        tv_job_post.setText(job.getJobtitle());
        tv_location.setText(job.getJobcity());
        et_staff.setText(job.getStaff());
        String[] salarys = job.getSalary().split("to");
        et_start_salary.setText(salarys[0].toString().trim());
        et_end_salary.setText(salarys[1].toString().trim());
    }

    private void initClick() {
        job_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.type_3) {
                    job_date_lay.setVisibility(View.VISIBLE);
                } else {
                    job_date_lay.setVisibility(View.GONE);
                }
            }
        });
        ll_job_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCategory(v);
            }
        });
        job_date_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = job_type.getCheckedRadioButtonId();
                String job_dates = "";
                if (selectedId == R.id.type_3) {
                    job_dates = tv_job_date.getText().toString().trim();
                    if (TextUtils.isEmpty(job_dates)) {
                        Toast.makeText(getActivity(), "Select job date...!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    job_dates = "";
                }
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                String job_types = radioButton.getText().toString().trim();
                String company_type = tv_category.getText().toString().trim();
                String job_title = tv_job_post.getText().toString().trim();
                String job_city = tv_location.getText().toString().trim();
                String start_salary = et_start_salary.getText().toString().trim();
                String end_salary = et_end_salary.getText().toString().trim();
                String staff = et_staff.getText().toString().trim();
                if (TextUtils.isEmpty(company_type)) {
                    Toast.makeText(getActivity(), "Select why hire a...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(job_title)) {
                    Toast.makeText(getActivity(), "Enter job post...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(job_city)) {
                    Toast.makeText(getActivity(), "Enter job city...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(start_salary)) {
                    Toast.makeText(getActivity(), "Enter start salary...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(end_salary)) {
                    Toast.makeText(getActivity(), "Enter end salary...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(staff)) {
                    Toast.makeText(getActivity(), "Enter no of staff need...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                job.setCompanytype(company_type);
                job.setJobtype(job_types);
                job.setJobdate(job_dates);
                job.setJobtitle(job_title);
                job.setJobcity(job_city);
                job.setStaff(staff);
                job.setSalary(start_salary + " to " + end_salary);
                onNextPreviousClick.onNextClick();
            }
        });
    }

    private void showDatePickerDialog() {
        if (day == 0) {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        new DatePickerDialog(getActivity(), datePickerListener, year, month, day).show();
    }

    String date;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.YEAR, selectedYear);
            date = String.valueOf(calendar.getTimeInMillis());
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(calendar.getTimeInMillis()));
            tv_job_date.setText(dateString);
        }
    };

    private void initView(View view) {
        ll_job_category = (LinearLayout) view.findViewById(R.id.ll_job_category);
        job_date_lay = (LinearLayout) view.findViewById(R.id.job_date_lay);
        job_date_click = (LinearLayout) view.findViewById(R.id.job_date_click);
        tv_job_date = (TextView) view.findViewById(R.id.tv_job_date);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_job_post = (EditText) view.findViewById(R.id.tv_job_post);
        tv_location = (EditText) view.findViewById(R.id.tv_location);
        et_start_salary = (EditText) view.findViewById(R.id.et_start_salary);
        et_end_salary = (EditText) view.findViewById(R.id.et_end_salary);
        et_staff = (EditText) view.findViewById(R.id.et_staff);

        ll_next = (LinearLayout) view.findViewById(R.id.ll_next);
        job_type = (RadioGroup) view.findViewById(R.id.job_type);
    }

    private void openDialogCategory(View v) {
        popupcategory = new PopupWindow(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_category, null);
        RecyclerView rv_category = view.findViewById(R.id.rv_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), category);
        rv_category.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        categoryAdapter.OnItemClickListener(this);
        rv_category.setAdapter(categoryAdapter);

        popupcategory.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupcategory.setElevation(20);
        }
        popupcategory.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.transperent_bg));
        popupcategory.setContentView(view);
        popupcategory.showAsDropDown(v);
    }

    @Override
    public void onItemClick(View v, int position) {
        tv_category.setText("" + getResources().getString(category[position]));
        popupcategory.dismiss();
    }
}