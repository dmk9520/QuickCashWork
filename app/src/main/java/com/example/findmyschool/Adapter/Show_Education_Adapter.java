package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Certificate;
import com.example.findmyschool.Model.Education;
import com.example.findmyschool.R;

import java.util.ArrayList;
import java.util.List;


public class Show_Education_Adapter extends RecyclerView.Adapter<Show_Education_Adapter.ViewHolder> {

    Context context;
    List<Education> educations = new ArrayList<>();
    LayoutInflater inflater;

    public Show_Education_Adapter(Context context, List<Education> educations) {
        this.context = context;
        this.educations = educations;
        inflater = LayoutInflater.from(context);
    }

    public void setEducation(ArrayList<Education> educations) {
        this.educations = educations;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_show_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.education_name.setText(educations.get(position).getSchoolname() + " (" + educations.get(position).getBoard() + ")");
        holder.education_level.setText(educations.get(position).getLevel());
        holder.education_date.setText(educations.get(position).getSdate() + "  -  " + educations.get(position).getEdate());
        holder.education_marks.setText(educations.get(position).getMark());
        holder.tv_education_no.setText(String.valueOf(position + 1)+")");
    }


    @Override
    public int getItemCount() {
        return educations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView education_name;
        private final TextView education_level;
        private final TextView education_date;
        private final TextView education_marks;
        private final TextView tv_education_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            education_name = (TextView) itemView.findViewById(R.id.education_name);
            education_level = (TextView) itemView.findViewById(R.id.education_level);
            education_date = (TextView) itemView.findViewById(R.id.education_date);
            education_marks = (TextView) itemView.findViewById(R.id.education_marks);
            tv_education_no = (TextView) itemView.findViewById(R.id.tv_education_no);
        }
    }

}
