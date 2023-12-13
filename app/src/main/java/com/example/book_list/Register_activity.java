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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register_activity extends AppCompatActivity {

    private TextView login_nav;

    private EditText Username, Email, Pass, Confirm_pass;

    private Button register_button;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dialogBox;

    private String username = "", email = "", password = "", confirm_password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        dialogBox = new ProgressDialog(this);
        dialogBox.setTitle("Please Wait....!");
        dialogBox.setCanceledOnTouchOutside(false);

        register_button = findViewById(R.id.reg_button);
        login_nav = findViewById(R.id.login_nav);
        Username = findViewById(R.id.reg_username);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.reg_password);
        Confirm_pass = findViewById(R.id.confirm_password);



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyingData();
            }
        });

        login_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate_page = new Intent(Register_activity.this, Login_activity.class);
                startActivity(navigate_page);
            }
        });
    }

    private void verifyingData() {

        username = Username.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Pass.getText().toString().trim();
        confirm_password = Confirm_pass.getText().toString().trim();


        if(TextUtils.isEmpty(username)) {
            Toast.makeText(Register_activity.this, "Fill your username!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(Register_activity.this, "Fill your Password!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)) {
            Toast.makeText(Register_activity.this, "Fill your email!", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirm_password)) {
            Toast.makeText(Register_activity.this, "Please Check your password!", Toast.LENGTH_SHORT).show();
        }
        else {
            addUserAccount();
        }

    }

    private void addUserAccount() {

        dialogBox.setMessage("Creating Account....!");
        dialogBox.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        dialogBox.dismiss();
                        Toast.makeText(Register_activity.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        dialogBox.setMessage("User Account Saved..");

                        String uuid = firebaseAuth.getUid();
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("uuid", uuid);
                        hashMap.put("email", email);
                        hashMap.put("password", password);
                        hashMap.put("userType", "user");

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(uuid)
                                .setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogBox.dismiss();
                                        Toast.makeText(Register_activity.this, "Create Account Succeed", Toast.LENGTH_SHORT).show();

                                        Intent navigate_page = new Intent(Register_activity.this, Login_activity.class);
                                        startActivity(navigate_page);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(Register_activity.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                                    }
                                });
                         }
                });
    }
}