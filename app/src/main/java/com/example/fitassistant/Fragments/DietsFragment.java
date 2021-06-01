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
import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.Providers.RealtimeDBProvider;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DietsFragment extends Fragment {
    private List<DietModel> diets;
    private RealtimeDBProvider dbProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diets = new ArrayList<>();
        dbProvider = new RealtimeDBProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.diets));
        dbProvider.dietsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                diets = dbProvider.getDietsData(snapshot);
                GenericListAdapter dietListAdapter = new GenericListAdapter(diets, getContext(), getFragmentManager());
                RecyclerView recyclerView = view.findViewById(R.id.list_recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(dietListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
    }
}
