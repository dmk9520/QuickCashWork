package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Certificate;
import com.example.findmyschool.R;

import java.util.ArrayList;

import static com.example.findmyschool.Activity.Employee.EmployeeCertificateActivity.iv_nodata;


public class EmployeeCertificateAdapter extends RecyclerView.Adapter<EmployeeCertificateAdapter.ViewHolder> {

    Context context;
    ArrayList<Certificate> certificates = new ArrayList<Certificate>();
    LayoutInflater inflater;

    public EmployeeCertificateAdapter(Context context, ArrayList<Certificate> certificates) {
        this.context = context;
        this.certificates = certificates;
        inflater = LayoutInflater.from(context);
    }

    public void setCertificates(ArrayList<Certificate> certificates) {
        this.certificates = certificates;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee_certificate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_certificatename.setText(certificates.get(position).getCertiname());
        holder.tv_institude_name.setText(certificates.get(position).getCertiinsti());
        holder.tv_issued_date.setText(certificates.get(position).getDate());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                certificates.remove(position);
                notifyDataSetChanged();
                if (certificates.size() == 0) {
                    iv_nodata.setVisibility(View.VISIBLE);
                } else {
                    iv_nodata.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return certificates.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_certificatename;
        private final TextView tv_institude_name;
        private final TextView tv_issued_date;
        private final ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_certificatename = (TextView) itemView.findViewById(R.id.tv_certificatename);
            tv_institude_name = (TextView) itemView.findViewById(R.id.tv_institude_name);
            tv_issued_date = (TextView) itemView.findViewById(R.id.tv_issued_date);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

}
