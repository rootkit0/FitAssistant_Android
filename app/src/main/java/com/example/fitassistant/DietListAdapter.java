package com.example.fitassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Models.DietModel;

import java.util.List;

public class DietListAdapter extends RecyclerView.Adapter<DietListAdapter.ViewHolder>{

    private List<DietModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    public DietListAdapter(List<DietModel> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public DietListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.diet_list_element, null);
        return new DietListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DietListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<DietModel> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name;
        TextView description;
        ImageView isVegan;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.diet_image);
            name = itemView.findViewById(R.id.diet_name);
            description = itemView.findViewById(R.id.diet_description);
            isVegan = itemView.findViewById(R.id.isVegan);
        }

        public void bindData(final DietModel item){
            iconImage.setImageResource(item.image);
            name.setText(item.name);
            description.setText(item.description);
            if(!item.isVegan) isVegan.setColorFilter(R.color.material_on_primary_disabled);
        }
    }
}