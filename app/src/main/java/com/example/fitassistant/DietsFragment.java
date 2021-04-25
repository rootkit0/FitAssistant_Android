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
        diets.add(new DietElement("Rutina Fullbody", R.drawable.ic_routine_example_24,
                true, "Perfecta per tot el cos"));
        diets.add(new DietElement("Rutina push-pull", R.drawable.ic_routine_example_24,
                false, "Ideal per guanyar força en braços i hombros"));
        diets.add(new DietElement("Rutina cames", R.drawable.ic_routine_example_24,
                false, "Pensada per guanyar força en les cames"));
        diets.add(new DietElement("Rutina esquena", R.drawable.ic_routine_example_24,
                true, "Per obtenir una esquena ampla"));
        diets.add(new DietElement("Rutina pit", R.drawable.ic_routine_example_24,
                false, "Perfecta per guanyar volum de pit"));
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