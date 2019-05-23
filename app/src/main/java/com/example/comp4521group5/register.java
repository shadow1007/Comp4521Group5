package com.example.comp4521group5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
     FirebaseFirestore db;
     FirebaseAuth mAuth;
    EditText Email, Password, Password2;
    Button registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Password2 = (EditText) findViewById(R.id.password2);
        registerButton = (Button) findViewById(R.id.register);
        loginButton = (Button) findViewById(R.id.login);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                final  String dbEmail = Email.getText().toString();
                String password = Password.getText().toString();
                String password2 = Password2.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!(password.equals(password2))) {
                    Toast.makeText(getApplicationContext(), "Password must be the same"+password+password2, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password2)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    final String userID=user.getUid();
//                                    updateUI(user);
                                       // addToDatabase(db,dbEmail);
                                        db = FirebaseFirestore.getInstance();

                                        Map<String, Object> userNew = new HashMap<>();
                                        userNew.put("email", dbEmail);
                                        int init=0;
                                        userNew.put("profileInit",init);
                                       // userNew.put("userID",userID);
                                        db.collection("users").document(userID).set(userNew)
                                               // .add(userNew)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), "DocumentSnapshot added", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Error adding document", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        Toast.makeText(getApplicationContext(), "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "createUserWithEmail:failure", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), Login.class));
//        }
    }
    private void addToDatabase(FirebaseFirestore a,String e){
        Map<String, Object> user = new HashMap<>();
        user.put("email", e);
        int init=0;
        user.put("profileInit",init);
        a.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error adding document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


