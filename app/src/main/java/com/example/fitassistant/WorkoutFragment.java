package com.example.fitassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.Models.WorkoutModel;

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
        routines.add(new WorkoutModel("Rutina Fullbody", R.drawable.ic_dumbbell,
                "Perfecta per tot el cos"));
        routines.add(new WorkoutModel("Rutina push-pull", R.drawable.ic_dumbbell
                , "Ideal per guanyar força en braços i hombros"));
        routines.add(new WorkoutModel("Rutina cames", R.drawable.ic_dumbbell
                , "Pensada per guanyar força en les cames"));
        routines.add(new WorkoutModel("Rutina esquena", R.drawable.ic_dumbbell
                , "Per obtenir una esquena ampla"));
        routines.add(new WorkoutModel("Rutina pit", R.drawable.ic_dumbbell
                , "Perfecta per guanyar volum de pit"));
    }
}
