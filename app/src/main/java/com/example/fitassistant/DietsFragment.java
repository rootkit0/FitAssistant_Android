package com.example.fitassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Models.DietModel;

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
        diets.add(new DietModel("Dieta Hipercalòrica vegana", R.drawable.ic_dumbbell,
                true,"Feta per guanyar massa muscular"));
        diets.add(new DietModel("Rutina push-pull", R.drawable.ic_dumbbell,
                false, "Ideal per guanyar força en braços i hombros"));
        diets.add(new DietModel("Rutina cames", R.drawable.ic_dumbbell,
                false, "Pensada per guanyar força en les cames"));
        diets.add(new DietModel("Rutina esquena", R.drawable.ic_dumbbell,
                true, "Per obtenir una esquena ampla"));
        diets.add(new DietModel("Rutina pit", R.drawable.ic_dumbbell,
                false, "Perfecta per guanyar volum de pit"));
    }
}
