package com.example.fitassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.ViewHolder> {

    private List<RoutineElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public RoutineListAdapter(List<RoutineElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RoutineListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.diet_list_element, null);
        return new RoutineListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RoutineListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<RoutineElement> items) {
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

        public void bindData(final RoutineElement item) {
            iconImage.setImageResource(item.image);
            name.setText(item.name);
            description.setText(item.description);
        }
    }
}
