package com.example.book_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {

    private TextView register_nav_button;
    private Button login_button;
    public static String email = "", password = "";
    private EditText Email, Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();

        dialogBox = new ProgressDialog(this);
        dialogBox.setTitle("Please Wait...!");
        dialogBox.setCanceledOnTouchOutside(false);
        register_nav_button = findViewById(R.id.register_nav);
        login_button = findViewById(R.id.login_button);
        Email = findViewById(R.id.log_email);
        Password = findViewById(R.id.log_password);


        register_nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate_page = new Intent(Login_activity.this, Register_activity.class);
                startActivity(navigate_page);            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccount();
            }
        });

        //add login intent later


    }

    private void getAccount() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(Login_activity.this, "Fill your username!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(Login_activity.this, "Fill your Password!", Toast.LENGTH_SHORT).show();
        }
        else {
            userAccount();
        }
    }

    private void userAccount() {
        dialogBox.setMessage("Logging in...!");
        dialogBox.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser userDB = firebaseAuth.getCurrentUser();

                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
                        userReference.child(userDB.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        Intent navigate_page = new Intent(Login_activity.this, dashboard_activity.class);
                                        startActivity(navigate_page);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Toast.makeText(Login_activity.this, "Canceled Login ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogBox.dismiss();
                        Toast.makeText(Login_activity.this, "Login Failed....!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}