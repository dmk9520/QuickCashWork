package com.example.findmyschool.Fragment.Recruiter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.Recruiter_Job_Adapter;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recruiter_Job_Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private Recruiter recruiter;
    private DatabaseReference databaseReference;
    private RecyclerView job_recycler;
    private ProgressBar progress;
    ArrayList<PostJobs> jobs = new ArrayList<>();
    ArrayList<PostJobs> filter_jobs = new ArrayList<>();
    private Recruiter_Job_Adapter recruiter_job_adapter;
    private ImageView recruiter_filter;

    public Recruiter_Job_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recruiter_job, container, false);
        recruiter = Application.getRecruiter();
        initView();
        initListener();
        initAdapter();
        getData();
        return view;
    }

    private void initListener() {
        recruiter_filter.setOnClickListener(this);
    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobs.clear();
                job_recycler.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                if (dataSnapshot.hasChild("Jobs")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Jobs").getChildren()) {
                        PostJobs postJobs = postSnapshot.getValue(PostJobs.class);
                        if (postJobs.getRecruiterid().toLowerCase().equals(recruiter.getId().toLowerCase())) {
                            jobs.add(postJobs);
                        }
                    }
                    if (jobs.size() > 0) {
                        progress.setVisibility(View.GONE);
                        job_recycler.setVisibility(View.VISIBLE);
                    } else {
                        progress.setVisibility(View.GONE);
                        job_recycler.setVisibility(View.GONE);
                    }
                    if (filter == 0) {
                        recruiter_job_adapter.setList(jobs);
                    } else if (filter == 1) {
                        filter_jobs.clear();
                        for (int i = 0; i < jobs.size(); i++) {
                            if (Boolean.parseBoolean(jobs.get(i).getFind())) {
                                filter_jobs.add(jobs.get(i));
                            }
                        }
                        recruiter_job_adapter.setList(filter_jobs);
                    } else if (filter == 2) {
                        filter_jobs.clear();
                        for (int i = 0; i < jobs.size(); i++) {
                            if (!Boolean.parseBoolean(jobs.get(i).getFind())) {
                                filter_jobs.add(jobs.get(i));
                            }
                        }
                        recruiter_job_adapter.setList(filter_jobs);
                    }
                } else {
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void initAdapter() {
        recruiter_job_adapter = new Recruiter_Job_Adapter(getActivity(), jobs, databaseReference);
        job_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        job_recycler.setAdapter(recruiter_job_adapter);
    }

    private void initView() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        job_recycler = (RecyclerView) view.findViewById(R.id.job_recycler);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        recruiter_filter = (ImageView) view.findViewById(R.id.recruiter_filter);
    }

    int filter = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recruiter_filter:
                final Dialog filterDialog = new Dialog(getActivity(), R.style.WideDialog);
                filterDialog.setContentView(R.layout.dialog_recruiter_filter);
                filterDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button cancel = (Button) filterDialog.findViewById(R.id.cancel);
                Button done = (Button) filterDialog.findViewById(R.id.done);
                LinearLayout all_job = (LinearLayout) filterDialog.findViewById(R.id.all_job);
                LinearLayout active_job = (LinearLayout) filterDialog.findViewById(R.id.active_job);
                LinearLayout close_job = (LinearLayout) filterDialog.findViewById(R.id.close_job);
                ImageView check1 = (ImageView) filterDialog.findViewById(R.id.check1);
                ImageView check2 = (ImageView) filterDialog.findViewById(R.id.check2);
                ImageView check3 = (ImageView) filterDialog.findViewById(R.id.check3);
                if (filter == 0) {
                    check1.setVisibility(View.VISIBLE);
                    check2.setVisibility(View.GONE);
                    check3.setVisibility(View.GONE);
                } else if (filter == 1) {
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.VISIBLE);
                    check3.setVisibility(View.GONE);
                } else if (filter == 2) {
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.GONE);
                    check3.setVisibility(View.VISIBLE);
                }
                all_job.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter = 0;
                        check1.setVisibility(View.VISIBLE);
                        check2.setVisibility(View.GONE);
                        check3.setVisibility(View.GONE);
                    }
                });
                active_job.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter = 1;
                        check1.setVisibility(View.GONE);
                        check2.setVisibility(View.VISIBLE);
                        check3.setVisibility(View.GONE);
                    }
                });
                close_job.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter = 2;
                        check1.setVisibility(View.GONE);
                        check2.setVisibility(View.GONE);
                        check3.setVisibility(View.VISIBLE);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterDialog.dismiss();
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (filter == 0) {
                            recruiter_job_adapter.setList(jobs);
                        } else if (filter == 1) {
                            filter_jobs.clear();
                            for (int i = 0; i < jobs.size(); i++) {
                                if (Boolean.parseBoolean(jobs.get(i).getFind())) {
                                    filter_jobs.add(jobs.get(i));
                                }
                            }
                            recruiter_job_adapter.setList(filter_jobs);
                        } else if (filter == 2) {
                            filter_jobs.clear();
                            for (int i = 0; i < jobs.size(); i++) {
                                if (!Boolean.parseBoolean(jobs.get(i).getFind())) {
                                    filter_jobs.add(jobs.get(i));
                                }
                            }
                            recruiter_job_adapter.setList(filter_jobs);
                        }
                        filterDialog.dismiss();
                    }
                });
                filterDialog.show();
                break;
        }
    }
}