package com.example.book_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class add_book_activity extends AppCompatActivity {

    private ImageView back_icon;
    private Button add_book;
    private EditText Book_code, Book_title, Book_year, Book_publisher, Book_author;
    public static String book_code = "", book_title = "", book_year = "", book_publisher = "", book_author = "";
    FirebaseAuth firebaseAuth;
    ProgressDialog dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        firebaseAuth = FirebaseAuth.getInstance();
        dialogBox = new ProgressDialog(this);
        dialogBox.setTitle("Please Wait...!");
        dialogBox.setCanceledOnTouchOutside(false);

        back_icon = findViewById(R.id.back_page_icon);
        add_book = findViewById(R.id.add_book_2);

        Book_code = findViewById(R.id.book_code);
        Book_title = findViewById(R.id.book_title);
        Book_year = findViewById(R.id.book_year);
        Book_publisher = findViewById(R.id.book_publisher);
        Book_author = findViewById(R.id.book_author);



        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate_page = new Intent(add_book_activity.this, dashboard_activity.class);
                startActivity(navigate_page);
            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookData();
            }
        });


    }

    private void addBookData() {
        dialogBox.setMessage("adding book data...!");
        dialogBox.show();

        String bookUid = firebaseAuth.getUid();

        book_code = Book_code.getText().toString().trim();
        book_title = Book_title.getText().toString().trim();
        book_year = Book_year.getText().toString().trim();
        book_publisher = Book_publisher.getText().toString().trim();
        book_author = Book_author.getText().toString().trim();

        HashMap<String, Object> bookDB = new HashMap<>();
        bookDB.put("book_code", book_code);
        bookDB.put("book_title", book_title);
        bookDB.put("book_year", book_year);
        bookDB.put("book_publisher", book_publisher);
        bookDB.put("book_author", book_author);
        bookDB.put("book_Uid", bookUid);

        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("BookData");
        bookRef.child(book_year)
                .setValue(bookDB)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialogBox.dismiss();
                        Toast.makeText(add_book_activity.this, "Create Account Succeed", Toast.LENGTH_SHORT).show();

                        Intent navigate_page = new Intent(add_book_activity.this, dashboard_activity.class);
                        startActivity(navigate_page);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_book_activity.this, "Failed to add book..!!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}