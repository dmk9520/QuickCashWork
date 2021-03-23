package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Common.OnItemClickListner;
import com.example.findmyschool.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    OnItemClickListner onItemClickListner;
    int[] category;
    private long date;

    public CategoryAdapter(Context context, int[] category) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.category = category;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_category.setText("" + context.getResources().getString(category[position]));
        holder.ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onItemClick(view, holder.getAdapterPosition());
            }
        });
    }

    public void OnItemClickListener(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @Override
    public int getItemCount() {
        return category.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout ll_category;
        private final TextView tv_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_category = itemView.findViewById(R.id.ll_category);
            tv_category = itemView.findViewById(R.id.tv_category);
        }
    }

}
