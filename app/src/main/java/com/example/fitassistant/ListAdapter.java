package com.example.fitassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<DietElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<DietElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.diet_list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<DietElement> items){
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

        public void bindData(final DietElement item){
            iconImage.setImageResource(item.image);
            name.setText(item.name);
            description.setText(item.description);
            if(!item.isVegan) isVegan.setColorFilter(R.color.material_on_primary_disabled);
        }
    }
}
