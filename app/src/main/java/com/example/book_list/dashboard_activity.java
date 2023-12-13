package com.example.book_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dashboard_activity extends AppCompatActivity {

    private Button add_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add_book = findViewById(R.id.add_book);

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigate_page = new Intent(dashboard_activity.this, add_book_activity.class);
                startActivity(navigate_page);
            }
        });
    }
}