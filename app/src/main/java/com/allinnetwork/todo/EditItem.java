package com.allinnetwork.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {
    EditText edittext;
    int position;
    Button savebut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);

        savebut = findViewById(R.id.btn_save);

        edittext = findViewById(R.id.edt_modif);
        edittext.setText(getIntent().getStringExtra("item_text"));
        position = getIntent().getIntExtra("item_position", 0);
        getSupportActionBar().setTitle("Edit Item");

    }


    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menuover, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem items) {
        int id = items.getItemId();
        if (id == R.id.btn_save) {
                Intent intent = new Intent();
                intent.putExtra("item_text", edittext.getText().toString());
                intent.putExtra("item_position", position);
                setResult(RESULT_OK, intent);
                finish();
        }
        return true;

    }

//    public void onSaveButton (View view) {
//        Intent intent = new Intent();
//        intent.putExtra("item_text", edittext.getText().toString());
//        intent.putExtra("item_position", position);
//        setResult(RESULT_OK, intent);
//    }
}
