package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.GenericListAdapter;
import com.example.fitassistant.Models.NutritionModel;
import com.example.fitassistant.Models.ReceiptModel;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsFragment extends Fragment {
    private List<ReceiptModel> receipts = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference receiptsReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        receiptsReference = database.getReference("/receipts");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Receptes");
        receiptsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0; i<snapshot.getChildrenCount(); ++i) {
                    String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
                    String description = snapshot.child(String.valueOf(i)).child("description").getValue().toString();
                    int dietType = (int) snapshot.child(String.valueOf(i)).child("dietType").getValue();
                    int time = (int) snapshot.child(String.valueOf(i)).child("time").getValue();
                    int servings = (int) snapshot.child(String.valueOf(i)).child("servings").getValue();
                    //Get ingredients
                    ArrayList<String> ingredients = new ArrayList<>();
                    for(int j=0; j<snapshot.child(String.valueOf(i)).child("ingredients").getChildrenCount(); ++j) {
                        ingredients.add(snapshot.child(String.valueOf(i)).child("ingredients").child(String.valueOf(j)).getValue().toString());
                    }
                    //Get nutrition object data
                    int calories = (int) snapshot.child(String.valueOf(i)).child("nutrition").child("calories").getValue();
                    double protein = (double) snapshot.child(String.valueOf(i)).child("nutrition").child("protein").getValue();
                    double carbs = (double) snapshot.child(String.valueOf(i)).child("nutrition").child("carbs").getValue();
                    double fat = (double) snapshot.child(String.valueOf(i)).child("nutrition").child("fat").getValue();
                    NutritionModel nutrition = new NutritionModel(calories, protein, carbs, fat);
                    //Only add the receipts that are from the selected diet
                    if(dietType == Integer.valueOf(getTag())) {
                        receipts.add(new ReceiptModel(name, description, time, servings, ingredients, nutrition));
                    }
                }

                GenericListAdapter receiptListAdapter = new GenericListAdapter(receipts, getContext(), getFragmentManager());
                RecyclerView recyclerView = view.findViewById(R.id.list_recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(receiptListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
    }
}
