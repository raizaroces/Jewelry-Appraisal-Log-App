package com.example.cndc_lpar_rar_finalexer3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    EditText etName;
    EditText etValue;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.editTextNumber);
        etName = findViewById(R.id.editTextName);
        etValue = findViewById(R.id.editTextValue);
        db = openOrCreateDatabase("jewelry", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS tableJewels(id INTEGER PRIMARY KEY, jname VARCHAR NOT NULL, value DOUBLE)");
    }

    public void doAdd(View v) {
        if (etNumber.getEditableText().toString().trim().isEmpty() ||
                etName.getEditableText().toString().trim().isEmpty() ||
                etValue.getEditableText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please do not leave empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(etNumber.getEditableText().toString().trim()) <= 0) {
            Toast.makeText(this, "ID must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor = db.rawQuery("SELECT id FROM tableJewels WHERE id = " + etNumber.getEditableText().toString().trim(), null);
        if (cursor.getCount() > 0) {
            Toast.makeText(this, "Record with this ID already exists", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        if (cursor != null) {
            cursor.close();
        }

        db.execSQL("INSERT INTO tableJewels(id, jname, value) VALUES(" +
                Integer.parseInt(etNumber.getEditableText().toString().trim()) + ", '" +
                sanitizeInput(etName.getEditableText().toString().trim()) + "', " +
                Double.parseDouble(etValue.getEditableText().toString().trim()) + ")");
        Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private String sanitizeInput(String input) {
        return input.replace("'", "''");
    }

    private void clearFields() {
        etNumber.setText("");
        etName.setText("");
        etValue.setText("");
    }

    public void doUpdate(View v) {
        if (etNumber.getEditableText().toString().trim().isEmpty() ||
                etName.getEditableText().toString().trim().isEmpty() ||
                etValue.getEditableText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please do not leave empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(etNumber.getEditableText().toString().trim()) <= 0) {
            Toast.makeText(this, "ID must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor = db.rawQuery("SELECT id FROM tableJewels WHERE id = " + etNumber.getEditableText().toString().trim(), null);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Record with this ID does not exist", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        if (cursor != null) {
            cursor.close();
        }

        db.execSQL("UPDATE tableJewels SET jname = '" +
                sanitizeInput(etName.getEditableText().toString().trim()) + "', value = " +
                Double.parseDouble(etValue.getEditableText().toString().trim()) +
                " WHERE id = " + etNumber.getEditableText().toString().trim());
        Toast.makeText(this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    public void doDelete(View v) {
        if (etNumber.getEditableText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter an ID to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(etNumber.getEditableText().toString().trim()) <= 0) {
            Toast.makeText(this, "ID must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor = db.rawQuery("SELECT id FROM tableJewels WHERE id = " + etNumber.getEditableText().toString().trim(), null);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Record with this ID does not exist", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        if (cursor != null) {
            cursor.close();
        }

        db.execSQL("DELETE FROM tableJewels WHERE id = " + etNumber.getEditableText().toString().trim());
        Toast.makeText(this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    public void doSearch(View v) {
        if (etNumber.getEditableText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter an ID to search", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(etNumber.getEditableText().toString().trim()) <= 0) {
            Toast.makeText(this, "ID must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor = db.rawQuery("SELECT * FROM tableJewels WHERE id = " + etNumber.getEditableText().toString().trim(), null);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Item Not Found", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            cursor.moveToFirst();
            etNumber.setText(cursor.getString(0));
            etName.setText(cursor.getString(1));
            double value = cursor.getDouble(2);
            etValue.setText(String.format("%.2f", value));etValue.setText(cursor.getString(2));
            cursor.close();
        }
    }

    public void doList(View v) {
        Intent intent = new Intent(this, jewelLog.class);
        startActivity(intent);
    }
}
