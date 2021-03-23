package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Job;
import com.example.findmyschool.R;

import java.util.ArrayList;

import static com.example.findmyschool.Activity.Employee.EmployeeExperienceActivity.ll_nodata;


public class EmployeeExperienceAdapter extends RecyclerView.Adapter<EmployeeExperienceAdapter.ViewHolder> {

    Context context;
    ArrayList<Job> experiences = new ArrayList<Job>();
    LayoutInflater inflater;

    public EmployeeExperienceAdapter(Context context, ArrayList<Job> experiences) {
        this.context = context;
        this.experiences = experiences;
        inflater = LayoutInflater.from(context);
    }
    public void setExperiences(ArrayList<Job> experiences){
        this.experiences = experiences;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee_experience, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_companyname.setText(experiences.get(position).getCname());
        holder.tv_designation.setText(experiences.get(position).getDesignation());
        holder.tv_start_date.setText(experiences.get(position).getSdate());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiences.remove(position);
                notifyDataSetChanged();
                if (experiences.size()==0){
                    ll_nodata.setVisibility(View.VISIBLE);
                }else {
                    ll_nodata.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return experiences.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_companyname;
        private final TextView tv_designation;
        private final TextView tv_start_date;
        private final ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_companyname=(TextView)itemView.findViewById(R.id.tv_companyname);
            tv_designation=(TextView)itemView.findViewById(R.id.tv_designation);
            tv_start_date=(TextView)itemView.findViewById(R.id.tv_start_date);
            iv_delete=(ImageView)itemView.findViewById(R.id.iv_delete);
        }
    }

}
