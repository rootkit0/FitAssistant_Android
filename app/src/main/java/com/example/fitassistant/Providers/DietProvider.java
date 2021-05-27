package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.DietModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DietProvider {
    private CollectionReference collectionReference;

    public DietProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection("diets");
    }

    public Task<Void> createDiet(DietModel diet) {
        DocumentReference documentReference = collectionReference.document();
        diet.setId(documentReference.getId());
        return documentReference.set(diet);
    }

    public Task<DocumentSnapshot> getDietById(String dietId) {
        return collectionReference.document(dietId).get();
    }

    public Query getAllDiets() {
        return collectionReference.orderBy("dietType", Query.Direction.ASCENDING);
    }
}
