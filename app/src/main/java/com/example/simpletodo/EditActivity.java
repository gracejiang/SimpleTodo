package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editText;
    Button btnSave;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.editItem);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // find original item
        String originalItem = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        editText.setText(originalItem);

        // set title of new activity to name of item
        getSupportActionBar().setTitle("Edit " + originalItem);

        // update task when user clicks on save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create intent which contains results
                Intent intent = new Intent();

                // pass the data (aka new task)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // set result of intent
                setResult(RESULT_OK, intent);

                // finish activity, close screen, and go back
                finish();
            }
        });

        // go back to home screen when cancelled
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}