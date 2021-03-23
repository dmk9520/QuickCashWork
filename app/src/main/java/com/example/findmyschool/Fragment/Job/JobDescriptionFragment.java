package com.example.findmyschool.Fragment.Job;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.findmyschool.Common.OnNextPreviousClick;
import com.example.findmyschool.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.findmyschool.Activity.Recruiter.RecruiterJobPostActivity.job;

public class JobDescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout ll_below_10, ll_10_pass, ll_12_pass_above, ll_graduate_above;
    private LinearLayout ll_0_6_months, ll_1_2_year, ll_above_2_year;
    private LinearLayout ll_male, ll_female, ll_both;
    private LinearLayout ll_not_speak, ll_thoda_speak, ll_good_speak, ll_fluent_speak;
    private EditText et_skills;
    private EditText et_description;
    private TextView et_work_timings;
    private TextView et_interview;
    OnNextPreviousClick onNextPreviousClick;
    private LinearLayout ll_next;
    private LinearLayout ll_previous;
    String qualification = "Below 10th";
    String experience = "0 - 6 months";
    String gender = "Male";
    String language = "Does not speak english";
    private int type;
    private LinearLayout ll_work_timings;
    private String day;
    private LinearLayout ll_interview;

    public JobDescriptionFragment() {
        // Required empty public constructor
    }

    public JobDescriptionFragment(int type, OnNextPreviousClick onNextPreviousClick) {
        this.type = type;
        this.onNextPreviousClick = onNextPreviousClick;
    }

    // TODO: Rename and change types and number of parameters
    public static JobDescriptionFragment newInstance(String param1, String param2) {
        JobDescriptionFragment fragment = new JobDescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_description, container, false);
        initView(view);
        initClick();
        if (type == 1) {
            setData();
        }
        return view;
    }

    private void setData() {
        qualification = job.getQualification();
        experience = job.getWorkexperience();
        gender = job.getGender();
        language = job.getLanguage();
        et_skills.setText(job.getSkills());
        et_description.setText(job.getJobrole());
        et_work_timings.setText(job.getJobtime());
        et_interview.setText(job.getInterviewtime());
        if (qualification.equals("Below 10th")) {
            selectedView(ll_below_10, ll_10_pass, ll_12_pass_above, ll_graduate_above);
        } else if (qualification.equals("10th Pass & Above")) {
            selectedView(ll_10_pass, ll_below_10, ll_12_pass_above, ll_graduate_above);
        } else if (qualification.equals("12th Pass & Above")) {
            selectedView(ll_12_pass_above, ll_10_pass, ll_below_10, ll_graduate_above);
        } else if (qualification.equals("Graduate & Above")) {
            selectedView(ll_graduate_above, ll_12_pass_above, ll_10_pass, ll_below_10);
        }

        if (language.equals("Fluent English")) {
            selectedView(ll_fluent_speak, ll_thoda_speak, ll_good_speak, ll_not_speak);
        } else if (language.equals("Good English")) {
            selectedView(ll_good_speak, ll_thoda_speak, ll_not_speak, ll_fluent_speak);
        } else if (language.equals("Little English")) {
            selectedView(ll_thoda_speak, ll_not_speak, ll_good_speak, ll_fluent_speak);
        } else if (language.equals("No English")) {
            selectedView(ll_not_speak, ll_thoda_speak, ll_good_speak, ll_fluent_speak);
        }

        if (gender.equals("Male")) {
            selectedView(ll_male, ll_female, ll_both);
        } else if (gender.equals("Female")) {
            selectedView(ll_female, ll_male, ll_both);
        } else if (gender.equals("Both")) {
            selectedView(ll_both, ll_female, ll_male);
        }

        if (experience.equals("0 - 6 months")) {
            selectedView(ll_0_6_months, ll_1_2_year, ll_above_2_year);
        } else if (experience.equals("1 - 2 years")) {
            selectedView(ll_1_2_year, ll_0_6_months, ll_above_2_year);
        } else if (experience.equals("more than 2 year")) {
            selectedView(ll_above_2_year, ll_0_6_months, ll_1_2_year);
        }
    }

    private void showaddTimeDialog(TextView view) {
        Dialog timedialog = new Dialog(getContext(), R.style.WideDialog);
        timedialog.setContentView(R.layout.dialog_add_time);
        timedialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_ok = (TextView) timedialog.findViewById(R.id.tv_ok);
        TextView tv_start_time = (TextView) timedialog.findViewById(R.id.tv_start_time);
        TextView tv_end_time = (TextView) timedialog.findViewById(R.id.tv_end_time);
        TextView tv_start_day = (TextView) timedialog.findViewById(R.id.tv_start_day);
        TextView tv_end_day = (TextView) timedialog.findViewById(R.id.tv_end_day);
        LinearLayout ll_start_time = (LinearLayout) timedialog.findViewById(R.id.ll_start_time);
        LinearLayout ll_end_time = (LinearLayout) timedialog.findViewById(R.id.ll_end_time);
        LinearLayout ll_start_day = (LinearLayout) timedialog.findViewById(R.id.ll_start_day);
        LinearLayout ll_end_day = (LinearLayout) timedialog.findViewById(R.id.ll_end_day);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = tv_start_time.getText() + " - " + tv_end_time.getText() + " | " + tv_start_day.getText() + " to " + tv_end_day.getText();
                view.setText(time);
                timedialog.dismiss();
            }
        });
        ll_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();

                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = "09:30am";
                                DecimalFormat twodigits = new DecimalFormat("00");
                                if (hourOfDay >= 0 && hourOfDay < 12) {
                                    time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " am";
                                } else {
                                    if (hourOfDay == 12) {
                                        time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " pm";
                                    } else {
                                        hourOfDay = hourOfDay - 12;
                                        time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " pm";
                                    }
                                }
                                tv_start_time.setText(time);
                            }
                        }, hour, minute, true);


                timePickerDialog.show();
            }
        });
        ll_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();

                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = "06:30pm";
                                DecimalFormat twodigits = new DecimalFormat("00");
                                if (hourOfDay >= 0 && hourOfDay < 12) {
                                    time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " am";
                                } else {
                                    if (hourOfDay == 12) {
                                        time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " pm";
                                    } else {
                                        hourOfDay = hourOfDay - 12;
                                        time = twodigits.format(hourOfDay) + ":" + twodigits.format(minute) + " pm";
                                    }
                                }
                                tv_end_time.setText(time);
                            }
                        }, hour, minute, true);


                timePickerDialog.show();
            }
        });
        ll_start_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayDialog(tv_start_day);
            }
        });
        ll_end_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayDialog(tv_end_day);
            }
        });
        timedialog.show();

    }

    private void showDayDialog(TextView view) {
        Dialog daydialog = new Dialog(getContext(), R.style.WideDialog);
        daydialog.setContentView(R.layout.dialog_days);
        daydialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RelativeLayout rl_sunday = (RelativeLayout) daydialog.findViewById(R.id.rl_sunday);
        RelativeLayout rl_monday = (RelativeLayout) daydialog.findViewById(R.id.rl_monday);
        RelativeLayout rl_tuesday = (RelativeLayout) daydialog.findViewById(R.id.rl_tuesday);
        RelativeLayout rl_wednesday = (RelativeLayout) daydialog.findViewById(R.id.rl_wednesday);
        RelativeLayout rl_thursday = (RelativeLayout) daydialog.findViewById(R.id.rl_thursday);
        RelativeLayout rl_friday = (RelativeLayout) daydialog.findViewById(R.id.rl_friday);
        RelativeLayout rl_saturday = (RelativeLayout) daydialog.findViewById(R.id.rl_saturday);
        rl_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Sunday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Monday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Tuesday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Wednesday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Thursday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Friday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        rl_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "Saturday";
                view.setText(day);
                daydialog.dismiss();
            }
        });
        daydialog.show();

    }


    private void initClick() {
        ll_work_timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddTimeDialog(et_work_timings);
            }
        });
        ll_interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddTimeDialog(et_interview);
            }
        });
        ll_below_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualification = "Below 10th";
                selectedView(ll_below_10, ll_10_pass, ll_12_pass_above, ll_graduate_above);
            }
        });
        ll_10_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualification = "10th Pass & Above";
                selectedView(ll_10_pass, ll_below_10, ll_12_pass_above, ll_graduate_above);
            }
        });
        ll_12_pass_above.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualification = "12th Pass & Above";
                selectedView(ll_12_pass_above, ll_below_10, ll_10_pass, ll_graduate_above);
            }
        });
        ll_graduate_above.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualification = "Graduate & Above";
                selectedView(ll_graduate_above, ll_below_10, ll_10_pass, ll_12_pass_above);
            }
        });


        ll_0_6_months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experience = "0 - 6 months";
                selectedView(ll_0_6_months, ll_1_2_year, ll_above_2_year);
            }
        });
        ll_1_2_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experience = "1 - 2 years";
                selectedView(ll_1_2_year, ll_0_6_months, ll_above_2_year);
            }
        });
        ll_above_2_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experience = "more than 2 year";
                selectedView(ll_above_2_year, ll_0_6_months, ll_1_2_year);
            }
        });


        ll_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";
                selectedView(ll_male, ll_female, ll_both);
            }
        });
        ll_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                selectedView(ll_female, ll_male, ll_both);
            }
        });
        ll_both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Both";
                selectedView(ll_both, ll_male, ll_female);
            }
        });


        ll_not_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "No English";
                selectedView(ll_not_speak, ll_thoda_speak, ll_good_speak, ll_fluent_speak);
            }
        });
        ll_thoda_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "Little English";
                selectedView(ll_thoda_speak, ll_not_speak, ll_good_speak, ll_fluent_speak);
            }
        });
        ll_good_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "Good English";
                selectedView(ll_good_speak, ll_not_speak, ll_thoda_speak, ll_fluent_speak);
            }
        });
        ll_fluent_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language = "Fluent English";
                selectedView(ll_fluent_speak, ll_not_speak, ll_thoda_speak, ll_good_speak);
            }
        });

        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skill = et_skills.getText().toString().trim();
                String job_role = et_description.getText().toString().trim();
                String work_time = et_work_timings.getText().toString().trim();
                String inter_time = et_interview.getText().toString().trim();
                if (TextUtils.isEmpty(skill)) {
                    et_skills.setError("Enter skill...!");
                    return;
                }
                if (TextUtils.isEmpty(job_role)) {
                    et_description.setError("Enter job role...!");
                    return;
                }
                if (!job.getJobtype().equals("Mandatory")) {
                    if (TextUtils.isEmpty(work_time)) {
                        et_work_timings.setError("Enter work time...!");
                        return;
                    }
                }
                if (TextUtils.isEmpty(inter_time)) {
                    et_interview.setError("Enter interview time...!");
                    return;
                }
                job.setQualification(qualification);
                job.setWorkexperience(experience);
                job.setGender(gender);
                job.setLanguage(language);
                job.setJobrole(job_role);
                job.setSkills(skill);
                job.setJobtime(work_time);
                job.setInterviewtime(inter_time);
                onNextPreviousClick.onNextClick();
            }
        });
        ll_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextPreviousClick.onPreviousClick();
            }
        });

    }

    private void initView(View view) {
        ll_below_10 = (LinearLayout) view.findViewById(R.id.ll_below_10);
        ll_10_pass = (LinearLayout) view.findViewById(R.id.ll_10_pass);
        ll_12_pass_above = (LinearLayout) view.findViewById(R.id.ll_12_pass_above);
        ll_graduate_above = (LinearLayout) view.findViewById(R.id.ll_graduate_above);

        ll_0_6_months = (LinearLayout) view.findViewById(R.id.ll_0_6_months);
        ll_1_2_year = (LinearLayout) view.findViewById(R.id.ll_1_2_year);
        ll_above_2_year = (LinearLayout) view.findViewById(R.id.ll_above_2_year);

        ll_male = (LinearLayout) view.findViewById(R.id.ll_male);
        ll_female = (LinearLayout) view.findViewById(R.id.ll_female);
        ll_both = (LinearLayout) view.findViewById(R.id.ll_both);

        ll_not_speak = (LinearLayout) view.findViewById(R.id.ll_not_speak);
        ll_thoda_speak = (LinearLayout) view.findViewById(R.id.ll_thoda_speak);
        ll_good_speak = (LinearLayout) view.findViewById(R.id.ll_good_speak);
        ll_fluent_speak = (LinearLayout) view.findViewById(R.id.ll_fluent_speak);

        et_skills = (EditText) view.findViewById(R.id.et_skills);
        et_description = (EditText) view.findViewById(R.id.et_description);
        et_work_timings = (TextView) view.findViewById(R.id.et_work_timings);
        et_interview = (TextView) view.findViewById(R.id.et_interview);

        ll_next = (LinearLayout) view.findViewById(R.id.ll_next);
        ll_previous = (LinearLayout) view.findViewById(R.id.ll_previous);
        ll_work_timings = (LinearLayout) view.findViewById(R.id.ll_work_timings);
        ll_interview = (LinearLayout) view.findViewById(R.id.ll_interview);
    }

    void selectedView(LinearLayout view1, LinearLayout view2, LinearLayout view3, LinearLayout view4) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
        view3.setBackground(getResources().getDrawable(R.drawable.unselected));
        view4.setBackground(getResources().getDrawable(R.drawable.unselected));
    }

    void selectedView(LinearLayout view1, LinearLayout view2, LinearLayout view3) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
        view3.setBackground(getResources().getDrawable(R.drawable.unselected));
    }

    void selectedView(LinearLayout view1, LinearLayout view2) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
    }
}