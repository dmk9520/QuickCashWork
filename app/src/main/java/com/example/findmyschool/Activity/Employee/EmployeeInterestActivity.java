package com.example.findmyschool.Activity.Employee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Activity.SplashActivity;
import com.example.findmyschool.Adapter.EmployeeInterestAdapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Interested;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeInterestActivity extends AppCompatActivity {

    private ImageView iv_back;
    private RecyclerView rv_interest;
    private TextView tv_submit;
    ArrayList<Interested> interesteds = new ArrayList<>();
    ArrayList<Interested> filteredinteresteds = new ArrayList<>();
    String selected_interested;
    int[] tital = new int[]{R.string.tital1, R.string.tital2, R.string.tital3, R.string.tital4, R.string.tital5, R.string.tital6, R.string.tital7, R.string.tital8, R.string.tital9, R.string.tital10, R.string.tital11, R.string.tital12, R.string.tital13, R.string.tital14, R.string.tital15, R.string.tital16, R.string.tital17, R.string.tital18, R.string.tital19, R.string.tital20, R.string.tital21, R.string.tital22, R.string.tital23, R.string.tital24, R.string.tital25, R.string.tital26, R.string.tital27, R.string.tital28, R.string.tital29, R.string.tital30, R.string.tital31, R.string.tital32, R.string.tital33, R.string.tital34, R.string.tital35, R.string.tital36, R.string.tital37, R.string.tital38, R.string.tital39, R.string.tital40, R.string.tital41, R.string.tital42, R.string.tital43, R.string.tital44};
    int[] description = new int[]{R.string.decription1, R.string.decription2, R.string.decription3, R.string.decription4, R.string.decription5, R.string.decription6, R.string.decription7, R.string.decription8, R.string.decription9, R.string.decription10, R.string.decription11, R.string.decription12, R.string.decription13, R.string.decription14, R.string.decription15, R.string.decription16, R.string.decription17, R.string.decription18, R.string.decription19, R.string.decription20, R.string.decription21, R.string.decription22, R.string.decription23, R.string.decription24, R.string.decription25, R.string.decription26, R.string.decription27, R.string.decription28, R.string.decription29, R.string.decription30, R.string.decription31, R.string.decription32, R.string.decription33, R.string.decription34, R.string.decription35, R.string.decription36, R.string.decription37, R.string.decription38, R.string.decription39, R.string.decription40, R.string.decription41, R.string.decription42, R.string.decription43, R.string.decription44};
    int[] icon = new int[]{R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8, R.drawable.i9, R.drawable.i10, R.drawable.i11, R.drawable.driver, R.drawable.i12, R.drawable.i13, R.drawable.i14, R.drawable.i15, R.drawable.i16, R.drawable.i17, R.drawable.i18, R.drawable.i19, R.drawable.i20, R.drawable.i21, R.drawable.i22, R.drawable.i23, R.drawable.i24, R.drawable.i25, R.drawable.i26, R.drawable.i27, R.drawable.i28, R.drawable.i29, R.drawable.i30, R.drawable.i31, R.drawable.i32, R.drawable.i33, R.drawable.i34, R.drawable.i35, R.drawable.i36, R.drawable.i37, R.drawable.i38, R.drawable.i39, R.drawable.i40, R.drawable.i41, R.drawable.i42, R.drawable.i43};
    private LinearLayoutManager layoutManager;
    private EmployeeInterestAdapter adapter;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private int type;
    private Employees emp;
    private EditText search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_interest);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        initView();
        initClick();
        initData();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            emp = Application.getEmployee();
            if (emp.getInterested() != null) {
                for (int i = 0; i < interesteds.size(); i++) {
                    if (emp.getInterested().toLowerCase().contains(interesteds.get(i).getTitle().toLowerCase())) {
                        interesteds.get(i).setSelected(true);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        interesteds.clear();
        for (int i = 0; i < tital.length; i++) {
            Interested interested = new Interested(getResources().getString(tital[i]), getResources().getString(description[i]), getResources().getDrawable(icon[i]), false);
            interesteds.add(interested);
        }
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_interest.setLayoutManager(layoutManager);
        adapter = new EmployeeInterestAdapter(this, interesteds);
        rv_interest.setAdapter(adapter);

    }

    private void initClick() {
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    filteredinteresteds.clear();
                    filteredinteresteds.addAll(interesteds);
                    adapter.setExperiences(filteredinteresteds);
                } else {
                    filteredinteresteds.clear();
                    for (int i = 0; i < interesteds.size(); i++) {
                       if (interesteds.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())){
                           filteredinteresteds.add(interesteds.get(i));
                       }
                    }
                    adapter.setExperiences(filteredinteresteds);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < interesteds.size(); i++) {
                    if (interesteds.get(i).isSelected()) {
                        if (TextUtils.isEmpty(selected_interested)) {
                            selected_interested = interesteds.get(i).getTitle();
                        } else {
                            selected_interested = selected_interested + "," + interesteds.get(i).getTitle();
                        }
                    }
                }
                if (TextUtils.isEmpty(selected_interested)) {
                    Toast.makeText(EmployeeInterestActivity.this, "Select your interest...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type == 0) {
                    employees.setInterested(selected_interested);
                    final StorageReference ref = storageReference.child("Employees").child(employees.getId()).child("profile");
                    UploadTask uploadTask = ref.putFile(Uri.parse(employees.getProfile()));
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                String stringUri;
                                stringUri = downloadUri.toString();
                                employees.setProfile(stringUri);
                                databaseReference.getRoot().child("Employees").child(employees.getId()).setValue(employees);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Employees employ1ees1 = snapshot.child("Employees").child(employees.getId()).getValue(Employees.class);
                                        Application.setEmployee(employ1ees1);
                                        Application.putLogin(true);
                                        Application.putLoginType(1);
                                        Intent intent = new Intent(EmployeeInterestActivity.this, EmployeeMainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    emp.setInterested(selected_interested);
                    databaseReference.getRoot().child("Employees").child(emp.getId()).setValue(emp);
                    Application.setEmployee(emp);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_interest = (RecyclerView) findViewById(R.id.rv_interest);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        search_text=(EditText)findViewById(R.id.search_text);
    }
}