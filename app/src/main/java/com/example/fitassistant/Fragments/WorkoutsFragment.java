package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.GenericListAdapter;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.Providers.RealtimeDBProvider;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsFragment extends Fragment {
    private List<WorkoutModel> workouts;
    private RealtimeDBProvider dbProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workouts = new ArrayList<>();
        dbProvider = new RealtimeDBProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.routines));
        dbProvider.workoutsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workouts = dbProvider.getWorkoutsData(snapshot);
                //Set recyclerview adapter with data
                GenericListAdapter workoutListAdapter = new GenericListAdapter(workouts, getContext(), getFragmentManager());
                RecyclerView recyclerView = view.findViewById(R.id.list_recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(workoutListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
    }
}
