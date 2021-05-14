package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.WorkoutListAdapter;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {
    List<WorkoutModel> routines = new ArrayList<WorkoutModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createExamples();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WorkoutListAdapter routineListAdapter = new WorkoutListAdapter(routines, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.routine_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routineListAdapter);
    }

    private void createExamples() {
        routines.add(new WorkoutModel("Rutina de força", R.drawable.dumbbell
                , "Ideal per a guanyar força i poder aixecar més pes"));
        routines.add(new WorkoutModel("Rutina de volum", R.drawable.dumbbell
                , "Ideal per a trencar fibres musculars i guanyar volum"));
        routines.add(new WorkoutModel("Rutina de definició", R.drawable.dumbbell
                , "Ideal per a disminuir grassa corporal i definir els musculs"));
        routines.add(new WorkoutModel("Rutina full-body", R.drawable.dumbbell,
                "Rutina per a entrenar tot el cos"));
        routines.add(new WorkoutModel("Rutina calistenia", R.drawable.dumbbell
                , "Rutina d'exercicis amb el pes corporal"));
    }
}
