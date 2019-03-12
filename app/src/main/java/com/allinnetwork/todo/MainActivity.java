package com.allinnetwork.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static int EDIT_REQUEST_CODE = 2;

    ListView listview;
    ArrayAdapter<String> adapter;
    ArrayList<String> item;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);

        readItems();
//        item = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_style, item);
        listview = findViewById(R.id.lv_item);
        listview.setAdapter(adapter);



//        item.add("first Item");
//        item.add("second Item");
        setupListViewListener();

    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menuover, menu);
        return true;
    }

    public void onAddItem (View view) {
        EditText entry = findViewById(R.id.edt_entry);
        String todoitem = entry.getText().toString();
        if(todoitem.length() != 0) {
            adapter.add(todoitem);
            entry.setText("");
            Toast.makeText(this, "Todo added successfully.",Toast.LENGTH_SHORT).show();
            writeItems();
        } else {
            Toast.makeText(this, "you must enter a todo", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupListViewListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "item removed from list", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItem.class);
                intent.putExtra("item_text", item.get(position));
                intent.putExtra("item_position", position);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            String updatedItem = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("item_position");
            item.set(position, updatedItem);
            adapter.notifyDataSetChanged();
            writeItems();
            Toast.makeText(this, "item edited successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile() {
        return new File(getFilesDir() , "todolist.txt");
    }

    private void readItems() {
        try {
            item = new ArrayList<>(FileUtils.readLines(getDataFile() , Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity" , "error reading file" , e);
            item = new ArrayList<>();
        }

    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile() , item);
        } catch (IOException e) {
            Log.e("MainActivity" , "error writing file" , e);
        }
    }

}
