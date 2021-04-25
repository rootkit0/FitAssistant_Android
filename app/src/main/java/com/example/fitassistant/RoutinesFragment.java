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
 * Use the {@link RoutinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutinesFragment extends Fragment {

    List<RoutineElement> routines;

    public RoutinesFragment() {
        // Required empty public constructor
    }

    public static RoutinesFragment newInstance() {
        RoutinesFragment fragment = new RoutinesFragment();
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
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    private void createExamples() {
        routines = new ArrayList<RoutineElement>();
        routines.add(new RoutineElement("Dieta Hipercalòrica vegana", R.drawable.ic_routine_example_24,
                "Feta per guanyar massa muscular"));
        routines.add(new RoutineElement("Dieta Hipercalòrica", R.drawable.ic_routine_example_24,
                ""));
        routines.add(new RoutineElement("Dieta manteniment", R.drawable.ic_routine_example_24,
                "Dieta per mantenir la massa muscular"));
        routines.add(new RoutineElement("Dieta hipocalòrica vegana", R.drawable.ic_routine_example_24,
                "Ideal per aprimar"));
        routines.add(new RoutineElement("Dieta Hipocalòrica", R.drawable.ic_routine_example_24,
                "Ideal per aprimar"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RoutineListAdapter routineListAdapter = new RoutineListAdapter(routines, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.diet_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routineListAdapter);

    }
}