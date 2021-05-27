package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.GenericListAdapter;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {
    private List<WorkoutModel> workouts = new ArrayList<WorkoutModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rutines");

        //TODO: GET DATA FROM "WORKOUTS" COLLECTION
        //TODO: SET DATA TO LIST OF WORKOUTS

        GenericListAdapter workoutListAdapter = new GenericListAdapter(workouts, getContext(), getFragmentManager());
        RecyclerView recyclerView = view.findViewById(R.id.list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(workoutListAdapter);
    }
}
