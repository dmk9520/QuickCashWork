package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Education;
import com.example.findmyschool.R;

import java.util.ArrayList;

import static com.example.findmyschool.Activity.Employee.EmployeeEducationMainActivity.iv_nodata;


public class EmployeeEducationAdapter extends RecyclerView.Adapter<EmployeeEducationAdapter.ViewHolder> {

    Context context;
    ArrayList<Education> educations = new ArrayList<Education>();
    LayoutInflater inflater;

    public EmployeeEducationAdapter(Context context, ArrayList<Education> educations) {
        this.context = context;
        this.educations = educations;
        inflater = LayoutInflater.from(context);
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_sclorclg_name.setText(educations.get(position).getSchoolname());
        holder.tv_bordoruni_name.setText(educations.get(position).getBoard());
        holder.tv_stdordegree_name.setText(educations.get(position).getLevel());
        holder.tv_start_year.setText(educations.get(position).getSdate());
        holder.tv_end_year.setText(educations.get(position).getEdate());
        holder.tv_perorcgpa_name.setText("" + educations.get(position).getMark());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educations.remove(position);
                notifyDataSetChanged();
                if (educations.size() == 0) {
                    iv_nodata.setVisibility(View.VISIBLE);
                } else {
                    iv_nodata.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return educations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_sclorclg_name;
        private final TextView tv_stdordegree_name;
        private final TextView tv_bordoruni_name;
        private final TextView tv_perorcgpa_name;
        private final TextView tv_start_year;
        private final TextView tv_end_year;
        private final ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sclorclg_name = (TextView) itemView.findViewById(R.id.tv_sclorclg_name);
            tv_stdordegree_name = (TextView) itemView.findViewById(R.id.tv_stdordegree_name);
            tv_bordoruni_name = (TextView) itemView.findViewById(R.id.tv_bordoruni_name);
            tv_perorcgpa_name = (TextView) itemView.findViewById(R.id.tv_perorcgpa_name);
            tv_start_year = (TextView) itemView.findViewById(R.id.tv_start_year);
            tv_end_year = (TextView) itemView.findViewById(R.id.tv_end_year);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

}
