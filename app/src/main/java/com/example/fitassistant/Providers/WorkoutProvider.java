package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.WorkoutModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WorkoutProvider {
    private CollectionReference collectionReference;

    public WorkoutProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection("workouts");
    }

    public Task<Void> createWorkout(WorkoutModel workout) {
        DocumentReference documentReference = collectionReference.document();
        workout.setId(documentReference.getId());
        return documentReference.set(workout);
    }

    public Task<DocumentSnapshot> getWorkoutById(String workoutId) {
        return collectionReference.document(workoutId).get();
    }

    public Query getAllWorkouts() {
        return collectionReference.orderBy("workoutType", Query.Direction.ASCENDING);
    }
}
