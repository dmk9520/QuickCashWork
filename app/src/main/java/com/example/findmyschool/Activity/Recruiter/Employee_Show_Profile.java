package com.example.findmyschool.Activity.Recruiter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.Employee_Review_Activity;
import com.example.findmyschool.Activity.Employee.Employee_profile_Activity;
import com.example.findmyschool.Adapter.Employee_Review_Adapter;
import com.example.findmyschool.Adapter.Show_Certificate_Adapter;
import com.example.findmyschool.Adapter.Show_Education_Adapter;
import com.example.findmyschool.Adapter.Show_Exp_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.Model.Review;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Employee_Show_Profile extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private ShapeableImageView employee_profile;
    private TextView employee_name;
    private TextView employee_number;
    private TextView employee_location;
    private TextView employee_about;
    private TextView employee_experience;
    private TextView employee_look;
    private TextView employee_skill;
    private TextView employee_certi;
    private TextView employee_education;
    private TextView employee_hobbies;
    private TextView employee_p_number;
    private TextView employee_p_email;
    private TextView employee_p_gender;
    private TextView employee_p_birth;
    private TextView employee_p_location;
    private RecyclerView employee_exp_recycler;
    private RecyclerView employee_education_recycler;
    private TextView employee_p_language;
    private Employees employee;
    private RecyclerView employee_certi_recycler;
    private DatabaseReference databaseReference;
    private Recruiter recruiter;
    Review reviews = null;
    ArrayList<Review> reviews_list = new ArrayList<>();
    private ImageView review_star;
    private RecyclerView review_recycler;
    private Employee_Review_Adapter employee_review_adapter;
    private TextView review_title;
    private ImageView resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_employees_show);
        recruiter = Application.getRecruiter();
        employee = (Employees) getIntent().getSerializableExtra("data");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initListener();
        initSetData();
    }

    private void initSetData() {
        Glide.with(Employee_Show_Profile.this).load(employee.getProfile()).into(employee_profile);
        employee_name.setText(employee.getName());
        employee_number.setText(employee.getNumber());
        employee_location.setText(employee.getCity() + " | " + employee.getState());
        employee_about.setText(employee.getAbout());
        employee_hobbies.setText(employee.getHobbie());
        employee_p_number.setText(employee.getNumber());
        employee_p_email.setText(employee.getEmail());
        employee_p_gender.setText(employee.getGender());
        employee_p_birth.setText(employee.getBirth());
        employee_p_location.setText(employee.getCity() + " | " + employee.getState());
        employee_p_language.setText(employee.getLanguage());
        employee_skill.setText(employee.getSkill());
        if (employee.getCertificate() != null) {
            if (employee.getCertificate().size() > 0) {
                employee_certi_recycler.setVisibility(View.VISIBLE);
                employee_certi.setVisibility(View.GONE);
            } else {
                employee_certi_recycler.setVisibility(View.GONE);
                employee_certi.setVisibility(View.VISIBLE);
                employee_certi.setText("--");
            }
        } else {
            employee_certi_recycler.setVisibility(View.GONE);
            employee_certi.setVisibility(View.VISIBLE);
            employee_certi.setText("--");
        }

        if (employee.getEducation() != null) {
            if (employee.getEducation().size() > 0) {
                employee_education_recycler.setVisibility(View.VISIBLE);
            } else {
                employee_education_recycler.setVisibility(View.GONE);
            }
        } else {
            employee_education_recycler.setVisibility(View.GONE);
        }

        if (employee.getJob() != null) {
            if (employee.getExperince().equals("Experience")) {
                if (employee.getJob().size() > 0) {
                    employee_exp_recycler.setVisibility(View.VISIBLE);
                    employee_experience.setVisibility(View.GONE);
                } else {
                    employee_exp_recycler.setVisibility(View.GONE);
                    employee_experience.setVisibility(View.VISIBLE);
                    employee_experience.setText("Fresher");
                }
            } else {
                employee_exp_recycler.setVisibility(View.GONE);
                employee_experience.setVisibility(View.VISIBLE);
                employee_experience.setText("Fresher");
            }
        } else {
            employee_exp_recycler.setVisibility(View.GONE);
            employee_experience.setVisibility(View.VISIBLE);
            employee_experience.setText("Fresher");
        }
        employee_look.setText(employee.getInterested());
        Show_Certificate_Adapter show_certificate_adapter = new Show_Certificate_Adapter(Employee_Show_Profile.this, employee.getCertificate());
        employee_certi_recycler.setLayoutManager(new LinearLayoutManager(Employee_Show_Profile.this, LinearLayoutManager.VERTICAL, false));
        employee_certi_recycler.setAdapter(show_certificate_adapter);
        employee_experience.setText(employee.getExperince());
        employee_education.setText(employee.getQualification());

        if (employee.getEducation() != null) {
            Show_Education_Adapter show_education_adapter = new Show_Education_Adapter(Employee_Show_Profile.this, employee.getEducation());
            employee_education_recycler.setLayoutManager(new LinearLayoutManager(Employee_Show_Profile.this, LinearLayoutManager.VERTICAL, false));
            employee_education_recycler.setAdapter(show_education_adapter);
        }
        if (employee.getJob() != null) {
            Show_Exp_Adapter show_exp_adapter = new Show_Exp_Adapter(Employee_Show_Profile.this, employee.getJob());
            employee_exp_recycler.setLayoutManager(new LinearLayoutManager(Employee_Show_Profile.this, LinearLayoutManager.VERTICAL, false));
            employee_exp_recycler.setAdapter(show_exp_adapter);
        }
    }

    private void initListener() {
        back.setOnClickListener(this);
        review_star.setOnClickListener(this);
        resume.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        resume = (ImageView) findViewById(R.id.resume);
        employee_profile = (ShapeableImageView) findViewById(R.id.employee_profile);
        employee_name = (TextView) findViewById(R.id.employee_name);
        employee_number = (TextView) findViewById(R.id.employee_number);
        employee_location = (TextView) findViewById(R.id.employee_location);
        employee_about = (TextView) findViewById(R.id.employee_about);
        employee_experience = (TextView) findViewById(R.id.employee_experience);
        employee_look = (TextView) findViewById(R.id.employee_look);
        employee_skill = (TextView) findViewById(R.id.employee_skill);
        employee_certi = (TextView) findViewById(R.id.employee_certi);
        employee_education = (TextView) findViewById(R.id.employee_education);
        employee_hobbies = (TextView) findViewById(R.id.employee_hobbies);
        employee_p_number = (TextView) findViewById(R.id.employee_p_number);
        employee_p_email = (TextView) findViewById(R.id.employee_p_email);
        employee_p_gender = (TextView) findViewById(R.id.employee_p_gender);
        employee_p_birth = (TextView) findViewById(R.id.employee_p_birth);
        employee_p_location = (TextView) findViewById(R.id.employee_p_location);
        employee_p_language = (TextView) findViewById(R.id.employee_p_language);
        review_title = (TextView) findViewById(R.id.review_title);
        employee_certi_recycler = (RecyclerView) findViewById(R.id.employee_certi_recycler);
        review_recycler = (RecyclerView) findViewById(R.id.review_recycler);
        review_star = (ImageView) findViewById(R.id.review);
        employee_education_recycler = (RecyclerView) findViewById(R.id.employee_education_recycler);
        employee_exp_recycler = (RecyclerView) findViewById(R.id.employee_exp_recycler);
        initReviewAdapter();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Review")) {
                    reviews_list.clear();
                    if (employee.getId() != null) {
                        if (employee.getReview() != null) {
                            if (!TextUtils.isEmpty(employee.getReview())) {
                                ArrayList<String> check_reviews = new ArrayList<String>(Arrays.asList(employee.getReview().split(",")));
                                for (DataSnapshot postSnapshot : snapshot.child("Review").getChildren()) {
                                    Review review = postSnapshot.getValue(Review.class);
                                    if (check_reviews.contains(review.getId())) {
                                        if (review.getToid().equals(employee.getId())) {
                                            reviews_list.add(review);
                                            if (review.getFromid().equals(recruiter.getId())) {
                                                reviews = review;
                                            }
                                        }
                                    }
                                }
                                if (reviews_list.size() > 0) {
                                    review_title.setVisibility(View.VISIBLE);
                                } else {
                                    review_title.setVisibility(View.GONE);
                                }
                                employee_review_adapter.setData(reviews_list);
                                if (reviews != null) {
                                    review_star.setImageDrawable(ContextCompat.getDrawable(Employee_Show_Profile.this, R.drawable.iv_fill_star));
                                } else {
                                    review_star.setImageDrawable(ContextCompat.getDrawable(Employee_Show_Profile.this, R.drawable.iv_unfill_star));
                                }
                            } else {
                                review_title.setVisibility(View.GONE);
                            }
                        } else {
                            review_title.setVisibility(View.GONE);
                        }
                    } else {
                        review_title.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        float radius = getResources().getDimension(R.dimen._30sdp);
        employee_profile.setShapeAppearanceModel(employee_profile.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());
    }

    private void initReviewAdapter() {
        employee_review_adapter = new Employee_Review_Adapter(Employee_Show_Profile.this, reviews_list);
        review_recycler.setLayoutManager(new LinearLayoutManager(Employee_Show_Profile.this, LinearLayoutManager.VERTICAL, false));
        review_recycler.setAdapter(employee_review_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.resume:
                startActivity(new Intent(Employee_Show_Profile.this, Resume_Show_Activity.class).putExtra("data", employee));
                break;
            case R.id.review:
                final Dialog reviewDialog = new Dialog(this, R.style.WideDialog);
                reviewDialog.setContentView(R.layout.dialog_review);
                reviewDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                EditText review = (EditText) reviewDialog.findViewById(R.id.review);
                RatingBar rate = (RatingBar) reviewDialog.findViewById(R.id.rate);
                Button cancel = (Button) reviewDialog.findViewById(R.id.cancel);
                Button ok = (Button) reviewDialog.findViewById(R.id.ok);
                if (reviews != null) {
                    review.setText(reviews.getReview());
                    rate.setRating(Float.parseFloat(reviews.getStar()));
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reviewDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp_r = review.getText().toString().trim();
                        Float temp_s = rate.getRating();
                        if (TextUtils.isEmpty(temp_r)) {
                            Toast.makeText(Employee_Show_Profile.this, "Enter review...!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (temp_s == 0) {
                            Toast.makeText(Employee_Show_Profile.this, "Give rate...!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Review review1 = new Review();
                        String id;
                        if (reviews != null) {
                            id = reviews.getId();
                        } else {
                            id = String.valueOf(System.currentTimeMillis());
                        }
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
                        review1.setId(id);
                        review1.setDate(dateString);
                        review1.setFromid(recruiter.getId());
                        review1.setFromname(recruiter.getName());
                        review1.setFromprofile(recruiter.getProfile());
                        review1.setToid(employee.getId());
                        review1.setToname(employee.getName());
                        review1.setToprofile(employee.getProfile());
                        review1.setReview(temp_r);
                        review1.setStar(String.valueOf(temp_s));
                        databaseReference.getRoot().child("Review").child(id).setValue(review1);
                        String e_review = employee.getReview();
                        String r_review = recruiter.getReviewpost();
                        if (TextUtils.isEmpty(e_review)) {
                            e_review = id;
                        } else {
                            e_review = e_review + "," + id;
                        }
                        if (TextUtils.isEmpty(r_review)) {
                            r_review = id;
                        } else {
                            r_review = r_review + "," + id;
                        }
                        databaseReference.getRoot().child("Employees").child(employee.getId()).child("review").setValue(e_review);
                        databaseReference.getRoot().child("Recruiter").child(recruiter.getId()).child("reviewpost").setValue(r_review);
                        reviewDialog.dismiss();
                    }
                });
                reviewDialog.show();
                break;
        }
    }
}