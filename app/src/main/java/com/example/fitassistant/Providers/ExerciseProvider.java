package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.ExerciseModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExerciseProvider {
    private CollectionReference collectionReference;

    public ExerciseProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection("exercises");
    }

    public Task<Void> postExercise(ExerciseModel exercise) {
        DocumentReference documentReference = collectionReference.document();
        exercise.setId(documentReference.getId());
        return documentReference.set(exercise);
    }

    public Task<DocumentSnapshot> getExerciseById(String exerciseId) {
        return collectionReference.document(exerciseId).get();
    }

    public Query getAllExercises() {
        return collectionReference.orderBy("exerciseType", Query.Direction.ASCENDING);
    }
}
