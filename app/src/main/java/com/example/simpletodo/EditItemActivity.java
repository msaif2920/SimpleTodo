package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    //define the accessing variables
    private Button saveButton;
    private EditText editItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        getSupportActionBar().setTitle("Edit item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //access the widgets
        saveButton = findViewById(R.id.saveButton);
        editItem = findViewById(R.id.editTextItem);

        //access the string passed through intent
        Intent intent=getIntent();
        String item=intent.getStringExtra(MainActivity.KEY_ITEM_TEXT);

        //add the text to our edit text
        editItem.setText(item);

        //onClick listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();

                in.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                in.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                setResult(RESULT_OK, in);
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}