package com.example.findmyschool.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Interested;
import com.example.findmyschool.R;

import java.util.ArrayList;


public class EmployeeInterestAdapter extends RecyclerView.Adapter<EmployeeInterestAdapter.ViewHolder>{

    Context context;
    ArrayList<Interested> interesteds = new ArrayList<Interested>();
    LayoutInflater inflater;

    public EmployeeInterestAdapter(Context context, ArrayList<Interested> interesteds) {
        this.context = context;
        this.interesteds = interesteds;
        inflater = LayoutInflater.from(context);
    }

    public void setExperiences(ArrayList<Interested> interesteds) {
        this.interesteds = interesteds;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee_interest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_tital.setText(interesteds.get(position).getTitle());
        holder.tv_description.setText(interesteds.get(position).getDescription());
        holder.iv_image.setImageDrawable(interesteds.get(position).getDrawable());
        if (interesteds.get(position).isSelected()) {
            holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.selected));
        } else {
            holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.unselected));
        }
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interesteds.get(position).isSelected()) {
                    interesteds.get(position).setSelected(false);
                    holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.unselected));
                } else {
                    interesteds.get(position).setSelected(true);
                    holder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.selected));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return interesteds.size();
    }

//    @Override
//    public Filter getFilter() {
//        if (filter == null) {
//            filter = new CustomFilter();
//        }
//        return filter;
//    }
//


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_tital;
        private final TextView tv_description;
        private final ImageView iv_image;
        private final LinearLayout ll_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tital = (TextView) itemView.findViewById(R.id.tv_tital);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
        }
    }

//    class CustomFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            if (!TextUtils.isEmpty(constraint)) {
//                if (constraint.length() > 0) {
//                    //CONSTARINT TO UPPER
//                    constraint = constraint.toString().toLowerCase();
//                    ArrayList<Interested> filters = new ArrayList<Interested>();
//                    //get specific items
//                    for (int i = 0; i < filteredinteresteds.size(); i++) {
//                        Interested foridata = filteredinteresteds.get(i);
//                        if (!TextUtils.isEmpty(foridata.getTitle())) {
//                            if (foridata.getTitle().toLowerCase().replaceAll(" ", "").contains(constraint.toString().toLowerCase())) {
//                                filters.add(foridata);
//                            }
//                        }
//
//                    }
//                    results.count = filters.size();
//                    results.values = filters;
//                } else {
//                    results.count = filteredinteresteds.size();
//                    results.values = filteredinteresteds;
//                }
//            } else {
//                results.count = filteredinteresteds.size();
//                results.values = filteredinteresteds;
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            interesteds = (ArrayList<Interested>) results.values;
//            notifyDataSetChanged();
//        }
//    }

}
