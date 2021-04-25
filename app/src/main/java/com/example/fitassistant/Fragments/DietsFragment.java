package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.DietListAdapter;
import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.R;

import java.util.ArrayList;
import java.util.List;

public class DietsFragment extends Fragment {
    List<DietModel> diets = new ArrayList<DietModel>();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createExamples();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diets, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DietListAdapter dietListAdapter = new DietListAdapter(diets, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.diet_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(dietListAdapter);
    }

    private void createExamples() {
        diets.add(new DietModel("Dieta hipercalòrica", R.drawable.rice, false,
                "Dieta per a guanyar massa muscular"));
        diets.add(new DietModel("Dieta hipercalòrica vegana", R.drawable.rice,
                true,"Dieta per a guanyar massa muscular amb aliments vegans"));
        diets.add(new DietModel("Dieta de manteniment", R.drawable.rice, false,
                "Dieta per a mantenir pes"));
        diets.add(new DietModel("Dieta hipocalòrica", R.drawable.rice, false,
                "Ideal per a perdre pes"));
        diets.add(new DietModel("Dieta hipocalòrica vegana", R.drawable.rice, true,
                "Ideal per a perdre pes amb aliments vegans"));
    }
}
