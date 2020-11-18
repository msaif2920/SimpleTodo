package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "CLICKED_ITEM";
    public static final String KEY_ITEM_POSITION = "ITEM_POSITION";
    public static final int RETURN_CODE = 20;

    //defining the access variable for the widget
    private Button submitButton;
    private RecyclerView listOfTodo;
    private EditText editText;
    ItemAdapter itemAdapter;

    //define the arraylist to hold the data
    ArrayList<String> listOfTodoItem;

    //define a adapter that takes in the data and add it to listview
    //this represents this adapter is maid for current acitivity
     ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Accessing the defined views through the id
        submitButton = findViewById(R.id.submitButton);
        listOfTodo = findViewById(R.id.listOfItems);
        editText = findViewById(R.id.newTodoItem);

        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener(){

            @Override
            public void onItemLongClick(int position) {
                listOfTodoItem.remove(position);
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Item was remove", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemAdapter.OnClickListener onClickListener = new ItemAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);

                //passing the clicked string to the next activity
                intent.putExtra(KEY_ITEM_TEXT, listOfTodoItem.get(position));
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent, RETURN_CODE);
            }
        };

        itemAdapter = new ItemAdapter(listOfTodoItem, onLongClickListener, onClickListener);
        listOfTodo.setAdapter(itemAdapter);
        listOfTodo.setLayoutManager(new LinearLayoutManager(this));


        //update the list on add
        //use onclick listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();

                if(data.length() == 0){
                    Toast.makeText(getApplicationContext(), "Enter Some item to insert", Toast.LENGTH_SHORT).show();
                }else{
                    //add the new data to our collections
                    listOfTodoItem.add(data);

                    //call the adapter callback
                    itemAdapter.notifyItemInserted(listOfTodoItem.size()-1);

                    editText.setText("");

                    Toast.makeText(getApplicationContext(), "Item Successfully Added", Toast.LENGTH_SHORT).show();
                    saveItems();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == RETURN_CODE){
            String item = data.getStringExtra(KEY_ITEM_TEXT);

            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            listOfTodoItem.set(position, item);
            itemAdapter.notifyItemChanged(position);
            Toast.makeText(getApplicationContext(), "Updated the item", Toast.LENGTH_SHORT).show();
            saveItems();

        }else{
            Log.w("MainActivity", "Unknown call to OnActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            listOfTodoItem = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error Reading Items", e);
            listOfTodoItem = new ArrayList<>();

        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTodoItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}