package com.example.findmyschool.Utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

public class Application extends android.app.Application {
    static SharedPreferences preferences;
    static SharedPreferences.Editor prefEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        preferences = getSharedPreferences("find_job", MODE_PRIVATE);
        prefEditor = preferences.edit();
        prefEditor.commit();
    }

    public static boolean getLogin() {
        return preferences.getBoolean("login", false);
    }

    public static void putLogin(boolean login) {
        prefEditor.putBoolean("login", login);
        prefEditor.commit();
    }

    public static int getLoginType() {
        return preferences.getInt("login_type", 0);
    }

    public static void putLoginType(int login_type) {
        prefEditor.putInt("login_type", login_type);
        prefEditor.commit();
    }

    public static void setEmployee(Employees employee) {
        if (employee != null) {
            String json = new Gson().toJson(employee);
            prefEditor.putString("employee", json);
        } else {
            prefEditor.putString("employee", "");
        }
        prefEditor.commit();
    }

    public static Employees getEmployee() {
        String user = preferences.getString("employee", "");
        if (!TextUtils.isEmpty(user)) {
            return new Gson().fromJson(user, Employees.class);
        }
        return null;
    }


    public static void setRecruiter(Recruiter recruiter) {
        if (recruiter != null) {
            String json = new Gson().toJson(recruiter);
            prefEditor.putString("recruiter", json);
        } else {
            prefEditor.putString("recruiter", "");
        }
        prefEditor.commit();
    }

    public static Recruiter getRecruiter() {
        String user = preferences.getString("recruiter", "");
        if (!TextUtils.isEmpty(user)) {
            return new Gson().fromJson(user, Recruiter.class);
        }
        return null;
    }
}
