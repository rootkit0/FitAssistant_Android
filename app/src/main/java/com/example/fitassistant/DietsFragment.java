package com.example.fitassistant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietsFragment extends Fragment {

    List<DietElement> diets;

    public DietsFragment() {
        // Required empty public constructor
    }

    public static DietsFragment newInstance() {
        DietsFragment fragment = new DietsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createExamples();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diets, container, false);
    }

    private void createExamples() {
        diets = new ArrayList<DietElement>();
        diets.add(new DietElement("Dieta Hipercalòrica vegana", R.drawable.ic_food,
                true, "Feta per guanyar massa muscular"));
        diets.add(new DietElement("Dieta Hipercalòrica", R.drawable.ic_food,
                false, ""));
        diets.add(new DietElement("Dieta manteniment", R.drawable.ic_food,
                false, "Dieta per mantenir la massa muscular"));
        diets.add(new DietElement("Dieta hipocalòrica vegana", R.drawable.ic_food,
                true, "Ideal per aprimar"));
        diets.add(new DietElement("Dieta Hipocalòrica", R.drawable.ic_food,
                false, "Ideal per aprimar"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DietListAdapter dietListAdapter = new DietListAdapter(diets, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.diet_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(dietListAdapter);

    }
}