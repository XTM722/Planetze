package com.example.planetze;

import androidx.annotation.NonNull;

import com.example.planetze.models.Question;
import com.example.planetze.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Model {
    private static Model instance;

    private DatabaseReference usersRef;
    private DatabaseReference questionsRef;
    private FirebaseAuth auth;

    private Model() {
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        questionsRef = FirebaseDatabase.getInstance().getReference("Questions");
        auth = FirebaseAuth.getInstance();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    public void authenticate(String email, String password, Consumer<User> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    callback.accept(null);
                }
                else {
                    usersRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            callback.accept(user);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


}