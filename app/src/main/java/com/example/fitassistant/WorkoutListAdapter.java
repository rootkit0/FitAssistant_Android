package com.example.fitassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Models.WorkoutModel;

import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {
    private List<WorkoutModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    public WorkoutListAdapter(List<WorkoutModel> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public WorkoutListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.diet_list_element, null);
        return new WorkoutListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkoutListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<WorkoutModel> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name;
        TextView description;
        ImageView isVegan;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.diet_image);
            name = itemView.findViewById(R.id.diet_name);
            description = itemView.findViewById(R.id.diet_description);
            isVegan = itemView.findViewById(R.id.isVegan);
        }

        public void bindData(final WorkoutModel item) {
            iconImage.setImageResource(item.image);
            name.setText(item.name);
            description.setText(item.description);
        }
    }
}
