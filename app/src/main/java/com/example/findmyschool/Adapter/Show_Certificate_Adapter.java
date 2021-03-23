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
import java.util.List;


public class Show_Certificate_Adapter extends RecyclerView.Adapter<Show_Certificate_Adapter.ViewHolder> {

    Context context;
    List<Certificate> certificates = new ArrayList<>();
    LayoutInflater inflater;

    public Show_Certificate_Adapter(Context context, List<Certificate> certificates) {
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
        View view = inflater.inflate(R.layout.item_show_certificate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_certificatename.setText(certificates.get(position).getCertiname());
        holder.tv_issued_date.setText(certificates.get(position).getDate());
        holder.tv_certi_no.setText((position + 1) + ")");
    }


    @Override
    public int getItemCount() {
        return certificates.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_certificatename;
        private final TextView tv_issued_date;
        private final TextView tv_certi_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_certificatename = (TextView) itemView.findViewById(R.id.tv_certificatename);
            tv_issued_date = (TextView) itemView.findViewById(R.id.tv_issued_date);
            tv_certi_no = (TextView) itemView.findViewById(R.id.tv_certi_no);
        }
    }

}
