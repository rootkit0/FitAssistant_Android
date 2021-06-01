package com.example.fitassistant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Fragments.ExercisesFragment;
import com.example.fitassistant.Fragments.HomeFragment;
import com.example.fitassistant.Fragments.ReceiptsFragment;
import com.example.fitassistant.Fragments.SingleExerciseFragment;
import com.example.fitassistant.Fragments.SingleReceiptFragment;
import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.Models.MessageModel;
import com.example.fitassistant.Models.ReceiptModel;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.ImageProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;

import java.util.ArrayList;
import java.util.List;

public class GenericListAdapter extends RecyclerView.Adapter<GenericListAdapter.ViewHolder> {
    private final List itemList;
    private final LayoutInflater layoutInflater;
    //To switch to receipt and exercices fragments
    private FragmentTransaction fragmentTransaction;
    private final FragmentManager fragmentManager;

    public GenericListAdapter(List itemList, Context context, FragmentManager fragmentManager) {
        this.itemList = itemList;
        this.layoutInflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public GenericListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenericListAdapter.ViewHolder holder, int position) {
        holder.bindData(itemList.get(position));
        holder.itemView.setOnClickListener(
                v -> {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
                    if(itemList.get(position).getClass().equals(DietModel.class)) {
                        fragmentTransaction.replace(R.id.fragment, new ReceiptsFragment(), String.valueOf(position));
                    }
                    else if(itemList.get(position).getClass().equals(WorkoutModel.class)) {
                        fragmentTransaction.replace(R.id.fragment, new ExercisesFragment(), String.valueOf(position));
                    }
                    else if(itemList.get(position).getClass().equals(ReceiptModel.class)) {
                        ReceiptModel receipt = (ReceiptModel) itemList.get(position);
                        fragmentTransaction.replace(R.id.fragment, new SingleReceiptFragment(), receipt.getName());
                    }
                    else if(itemList.get(position).getClass().equals(ExerciseModel.class)) {
                        ExerciseModel exercise = (ExerciseModel) itemList.get(position);
                        fragmentTransaction.replace(R.id.fragment, new SingleExerciseFragment(), exercise.getName());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageProvider imageProvider;
        private UserProvider userProvider;
        private AuthProvider authProvider;
        private final ImageView image;
        private final TextView name;
        private final TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            imageProvider = new ImageProvider();
            userProvider = new UserProvider();
            authProvider = new AuthProvider();
            image = itemView.findViewById(R.id.list_image);
            name = itemView.findViewById(R.id.list_name);
            description = itemView.findViewById(R.id.list_description);
        }

        private void bindData(final Object item) {
            if(item.getClass().equals(DietModel.class)) {
                //Diet object
                name.setText(((DietModel) item).getName());
                description.setText(((DietModel) item).getDescription());
                imageProvider.getImage(Constants.dietsPath, image);
            }
            else if(item.getClass().equals(ReceiptModel.class)) {
                //Receipt object
                name.setText(((ReceiptModel) item).getName());
                description.setText(((ReceiptModel) item).getDescription());
                imageProvider.getImage(Constants.receiptsPath, image);
            }
            else if(item.getClass().equals(WorkoutModel.class)) {
                //Workout object
                name.setText(((WorkoutModel) item).getName());
                description.setText(((WorkoutModel) item).getDescription());
                imageProvider.getImage(Constants.workoutsPath, image);
            }
            else if(item.getClass().equals(ExerciseModel.class)) {
                //Exercise object
                name.setText(((ExerciseModel) item).getName());
                description.setText(((ExerciseModel) item).getDescription());
                imageProvider.getImage(Constants.exercisesPath, image);
            }
            else if(item.getClass().equals(MessageModel.class)) {
                //Message object
                name.setText(((MessageModel) item).getFromUser());
                description.setText(((MessageModel) item).getMessage());
                userProvider.getUserImage(authProvider.getUserId(), image);
            }
        }
    }
}
