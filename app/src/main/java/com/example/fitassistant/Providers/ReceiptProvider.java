package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.ReceiptModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ReceiptProvider {
    private CollectionReference collectionReference;

    public ReceiptProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection("receipts");
    }

    public Task<Void> createReceipt(ReceiptModel receipt) {
        DocumentReference documentReference = collectionReference.document();
        receipt.setId(documentReference.getId());
        return documentReference.set(receipt);
    }

    public Task<DocumentSnapshot> getReceiptById(String receiptId) {
        return collectionReference.document(receiptId).get();
    }

    public Query getAllReceipts() {
        return collectionReference.orderBy("receiptType", Query.Direction.ASCENDING);
    }
}
