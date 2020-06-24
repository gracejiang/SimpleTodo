package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();

        // remove an item
        ItemsAdapter.OnLongClickListener onLongClickListener= new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // delete item from model
                String itemRemoved = items.get(position);
                items.remove(position);

                // notify adapter @ which position item was deleted
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), itemRemoved + " was removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        // edits an item
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position " + position);
                // create new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                // pass data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);

                // display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        // add a new item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                // add item to the model
                items.add(todoItem);

                // notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");

                // notify user that item was added
                Toast.makeText(getApplicationContext(), todoItem + " was added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }


    // handle result of edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // retrieve updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            // extract original position of edited item
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // get original item
            String oldItem = items.get(position);

            // update item in that position
            items.set(position, itemText);

            // notify adapter
            itemsAdapter.notifyItemChanged(position);

            // persist the changes
            saveItems();

            // notify user
            Toast.makeText(getApplicationContext(),  oldItem + " updated to " + itemText, Toast.LENGTH_SHORT).show();
        } else {
            Log.w("mainActivity", "Unknown call to onActivityResult");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "error reading items", e);
            items = new ArrayList<>();
        }

    }

    // saves items by writing them into data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "error writing items", e);
        }
    }

}