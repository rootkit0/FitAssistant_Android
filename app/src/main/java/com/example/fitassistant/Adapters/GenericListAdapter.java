package com.example.fitassistant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Fragments.ExercisesFragment;
import com.example.fitassistant.Fragments.HomeFragment;
import com.example.fitassistant.Fragments.ReceiptsFragment;
import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.Models.ReceiptModel;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.R;

import java.util.ArrayList;
import java.util.List;

public class GenericListAdapter extends RecyclerView.Adapter<GenericListAdapter.ViewHolder> {
    private List itemList = new ArrayList();
    private LayoutInflater layoutInflater;
    private Context context;
    //To switch to receipt and exercices fragments
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    public GenericListAdapter(List itemList, Context context, FragmentManager fragmentManager) {
        this.itemList = itemList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public GenericListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element, null);
        return new GenericListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenericListAdapter.ViewHolder holder, int position) {
        holder.bindData(itemList.get(position));
        holder.itemView.setOnClickListener(
                v -> {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if(itemList.get(position).getClass().equals(DietModel.class)) {
                        fragmentTransaction.replace(R.id.fragment, new ReceiptsFragment(), String.valueOf(position));
                    }
                    else if(itemList.get(position).getClass().equals(WorkoutModel.class)) {
                        fragmentTransaction.replace(R.id.fragment, new ExercisesFragment(), String.valueOf(position));
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
        );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.list_image);
            name = itemView.findViewById(R.id.list_name);
            description = itemView.findViewById(R.id.list_description);
        }

        private void bindData(final Object item) {
            if(item.getClass().equals(DietModel.class)) {
                //Diet object
                name.setText(((DietModel) item).getName());
                description.setText(((DietModel) item).getDescription());
            }
            else if(item.getClass().equals(ReceiptModel.class)) {
                //Receipt object
                name.setText(((ReceiptModel) item).getName());
                description.setText(((ReceiptModel) item).getDescription());
            }
            else if(item.getClass().equals(WorkoutModel.class)) {
                //Workout object
                name.setText(((WorkoutModel) item).getName());
                description.setText(((WorkoutModel) item).getDescription());
            }
            else if(item.getClass().equals(ExerciseModel.class)) {
                //Exercise object
                name.setText(((ExerciseModel) item).getName());
                description.setText(((ExerciseModel) item).getDescription());
            }
        }
    }
}
