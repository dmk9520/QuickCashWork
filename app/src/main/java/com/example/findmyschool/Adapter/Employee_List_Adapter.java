package com.example.findmyschool.Adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Activity.Recruiter.Employee_Show_Profile;
import com.example.findmyschool.Activity.Recruiter.Employees_Show_Activity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;

import java.util.ArrayList;

public class Employee_List_Adapter extends RecyclerView.Adapter<Employee_List_Adapter.ViewHolder> {
    Employees_Show_Activity activity;
    ArrayList<Employees> interested_employee;
    LayoutInflater inflater;

    public Employee_List_Adapter(Employees_Show_Activity employees_show_activity, ArrayList<Employees> interested_employee) {
        activity = employees_show_activity;
        this.interested_employee = interested_employee;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.employee_name.setText(interested_employee.get(position).getName());
        holder.employee_location.setText(interested_employee.get(position).getCity() + " | " + interested_employee.get(position).getState());
        holder.employee_qualification.setText(interested_employee.get(position).getQualification());
        holder.employee_exp.setText(interested_employee.get(position).getExperince());
        holder.employee_english.setText(interested_employee.get(position).getSpeak());
        holder.employee_gender.setText(interested_employee.get(position).getGender());
        holder.employee_skill.setText(interested_employee.get(position).getSkill());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + interested_employee.get(position).getNumber()));
                    activity.startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 100);
                }
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + interested_employee.get(position).getEmail()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri sms_uri = Uri.parse("smsto:" + interested_employee.get(position).getNumber());
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                activity.startActivity(sms_intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Employee_Show_Profile.class).putExtra("data", interested_employee.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return interested_employee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout call, whatsapp, sms;
        TextView employee_name, employee_location, employee_qualification, employee_exp, employee_english, employee_gender, employee_skill;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employee_name = (TextView) itemView.findViewById(R.id.employee_name);
            employee_location = (TextView) itemView.findViewById(R.id.employee_location);
            employee_qualification = (TextView) itemView.findViewById(R.id.employee_qualification);
            employee_exp = (TextView) itemView.findViewById(R.id.employee_exp);
            employee_english = (TextView) itemView.findViewById(R.id.employee_english);
            employee_gender = (TextView) itemView.findViewById(R.id.employee_gender);
            employee_skill = (TextView) itemView.findViewById(R.id.employee_skill);
            call = (LinearLayout) itemView.findViewById(R.id.call);
            whatsapp = (LinearLayout) itemView.findViewById(R.id.whatsapp);
            sms = (LinearLayout) itemView.findViewById(R.id.sms);
        }
    }
}
