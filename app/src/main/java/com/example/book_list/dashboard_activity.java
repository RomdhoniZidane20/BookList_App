package com.example.book_list;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard_activity extends AppCompatActivity {

    private Button add_book;
    ImageView back_page_nav;
    RecyclerView recyclerView;
    DatabaseReference database;
    BookAdapter bookAdapter;
    ArrayList<DataBase> list;
    FirebaseAuth firebaseAuth;
    ArrayList<String> book_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        add_book = findViewById(R.id.add_book);
        recyclerView = findViewById(R.id.user_list);
        back_page_nav = findViewById(R.id.back_page_icon);
        database = FirebaseDatabase.getInstance().getReference("BookData");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        bookAdapter = new BookAdapter(this, list);

        recyclerView.setAdapter(bookAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataBase book = dataSnapshot.getValue(DataBase.class);
                    list.add(book);
                }

                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate_page = new Intent(dashboard_activity.this, add_book_activity.class);
                startActivity(navigate_page);
            }
        });

//        back_page_nav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent navigate_page = new Intent(dashboard_activity.this, MainActivity.class);
//                startActivity(navigate_page);
//            }
//        });


    }


}