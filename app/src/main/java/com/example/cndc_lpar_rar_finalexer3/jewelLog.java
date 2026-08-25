package com.example.cndc_lpar_rar_finalexer3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class jewelLog extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private JewelryAdapter adapter;
    private RecyclerView recyclerViewJewelry;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jewel_log);

        recyclerViewJewelry = findViewById(R.id.recyclerViewJewelry);
        recyclerViewJewelry.setLayoutManager(new LinearLayoutManager(this));

        // Open the database
        db = openOrCreateDatabase("jewelry", MODE_PRIVATE, null);

        // Query all items from the database
        cursor = db.rawQuery("SELECT * FROM tableJewels", null);

        // Create and set the adapter
        adapter = new JewelryAdapter(this, cursor);
        recyclerViewJewelry.setAdapter(adapter);

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}