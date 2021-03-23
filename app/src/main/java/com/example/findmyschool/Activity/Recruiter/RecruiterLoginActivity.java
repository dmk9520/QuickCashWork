package com.example.findmyschool.Activity.Recruiter;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.example.findmyschool.view.CustomTextView;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heetch.countrypicker.Country;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;

import java.util.concurrent.TimeUnit;

public class RecruiterLoginActivity extends AppCompatActivity {

    private Pinview otpview;
    private LinearLayout ll_otplayout;
    private LinearLayout ll_loginlayout;
    private TextView tv_sendotp;
    private TextView tv_countrycode;
    private FirebaseAuth mAuth;
    private EditText et_orphanageemailid;
    private CountDownTimer countDownTimer;
    public int counter = 60;
    private CustomTextView resend;
    private String mVerificationId;
    private String temp_code;
    private TextView tv_login;
    private TextView verification_string;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_login);

        initView();
        initClick();
    }

    private void initClick() {
        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_orphanageemailid.getText().toString().trim().length() == 10) {
                    progress.setVisibility(View.VISIBLE);
                    sendVerificationCode(et_orphanageemailid.getText().toString().trim(), tv_countrycode.getText());
                } else {
                    Toast.makeText(RecruiterLoginActivity.this, "Enter mobile number.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tv_countrycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountryPickerDialog(RecruiterLoginActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {
                        tv_countrycode.setText("+" + country.getDialingCode());
                    }
                }).show();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(otpview.getValue().toString().trim())) {
                    String code = otpview.getValue().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        Toast.makeText(RecruiterLoginActivity.this, "Enter valid code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progress.setVisibility(View.VISIBLE);
                    verifyVerificationCode(code);
                } else {
                    Toast.makeText(RecruiterLoginActivity.this, "Please enter otp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_orphanageemailid.getText().toString().trim().length() == 10) {
                    progress.setVisibility(View.VISIBLE);
                    sendVerificationCode(et_orphanageemailid.getText().toString().trim(), tv_countrycode.getText());
                } else {
                    Toast.makeText(RecruiterLoginActivity.this, "Enter mobile number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyVerificationCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            Toast.makeText(this, "OTP is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(RecruiterLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String id = tv_countrycode.getText().toString().replace("+", "") + et_orphanageemailid.getText().toString().trim();
                                    if (dataSnapshot.child("Recruiter").hasChild(id)) {
                                        Recruiter recruiter = dataSnapshot.child("Recruiter").child(id).getValue(Recruiter.class);
                                        Application.setRecruiter(recruiter);
                                        startActivity(new Intent(RecruiterLoginActivity.this, RecruiterMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        Application.putLogin(true);
                                        Application.putLoginType(0);
                                        finish();
                                    } else {
                                        startActivity(new Intent(RecruiterLoginActivity.this, RecruiterDetailActivity.class).putExtra("number", et_orphanageemailid.getText().toString().trim()).putExtra("code", tv_countrycode.getText().toString().replace("+", "")));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            progress.setVisibility(View.GONE);
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(RecruiterLoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String no, CharSequence text) {
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(text.toString() + no)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks).build());
        verification_string.setText("We have sent OTP via SMS to " + tv_countrycode.getText() + " " + no + " for verification");
        ll_loginlayout.setVisibility(View.GONE);
        ll_otplayout.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resend.setClickable(false);
                resend.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                resend.setText("RESEND OTP");
                counter = 60;
                progress.setVisibility(View.GONE);
                resend.setClickable(true);
            }
        }.start();
        Log.d("data", "sendVerificationCode: ====>" + tv_countrycode.getText() + "" + no);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                temp_code = code;
            }
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RecruiterLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            resend.setText("RESEND OTP");
            counter = 60;
            countDownTimer.cancel();
            resend.setClickable(true);
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            progress.setVisibility(View.GONE);
        }
    };

    private void initView() {
        otpview = (Pinview) findViewById(R.id.otpview);
        ll_otplayout = (LinearLayout) findViewById(R.id.ll_otplayout);
        progress = (LinearLayout) findViewById(R.id.progress);
        ll_loginlayout = (LinearLayout) findViewById(R.id.ll_loginlayout);
        tv_sendotp = (TextView) findViewById(R.id.tv_sendotp);
        tv_countrycode = (TextView) findViewById(R.id.tv_countrycode);
        et_orphanageemailid = (EditText) findViewById(R.id.et_orphanageemailid);
        resend = (CustomTextView) findViewById(R.id.resend);
        tv_login = (TextView) findViewById(R.id.tv_login);
        verification_string = (TextView) findViewById(R.id.verification_string);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    @Override
    public void onBackPressed() {
        if (ll_otplayout.getVisibility() == View.VISIBLE) {
            ll_loginlayout.setVisibility(View.VISIBLE);
            ll_otplayout.setVisibility(View.GONE);
            resend.setText("RESEND OTP");
            counter = 60;
            countDownTimer.cancel();
            resend.setClickable(true);
            return;
        }
        super.onBackPressed();
    }
}