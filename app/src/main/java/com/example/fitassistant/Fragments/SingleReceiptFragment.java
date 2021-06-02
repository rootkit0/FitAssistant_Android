package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.Models.ReceiptModel;
import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.ImageProvider;
import com.example.fitassistant.Providers.RealtimeDBProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SingleReceiptFragment extends Fragment {
    private ImageView receipt_iv;
    private RealtimeDBProvider dbProvider;
    private AuthProvider authProvider;
    private UserProvider userProvider;
    private ImageProvider imageProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbProvider = new RealtimeDBProvider();
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        imageProvider = new ImageProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receipt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        receipt_iv = view.findViewById(R.id.receipt_iv);
        TextView name = view.findViewById(R.id.receipt_name);
        TextView description = view.findViewById(R.id.receipt_description);
        TextView time = view.findViewById(R.id.receipt_time);
        TextView servings = view.findViewById(R.id.receipt_servings);
        TextView ingredients = view.findViewById(R.id.receipt_ingredients);
        dbProvider.receiptsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Objects.requireNonNull(getActivity()).setTitle(getTag());
                ReceiptModel receipt = dbProvider.getReceiptByName(snapshot, getTag());
                name.setText(receipt.getName());
                description.setText(receipt.getDescription());
                time.setText(String.valueOf(receipt.getTime()));
                servings.setText(String.valueOf(receipt.getServings()));
                ArrayList<String> ingredientsList = receipt.getIngredients();
                for(int i=0; i<ingredientsList.size(); ++i) {
                    ingredients.append(ingredientsList.get(i) + ", ");
                }
                imageProvider.getImage("receipt", receipt_iv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
        Button addFavorites = view.findViewById(R.id.receipt_favs);
        addFavorites.setOnClickListener(
                v -> {
                    userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                            documentSnapshot -> {
                                if(documentSnapshot.exists()) {
                                    UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                                    ArrayList<String> favReceipts = actualUser.getFavReceipts();
                                    favReceipts.add(name.getText().toString());
                                    actualUser.setFavReceipts(favReceipts);
                                    userProvider.updateUser(actualUser);
                                    Toast.makeText(getContext(), R.string.receipts_to_favs, Toast.LENGTH_SHORT);
                                }
                            }
                    );
                }
        );
    }
}
