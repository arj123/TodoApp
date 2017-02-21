package com.example.arjun.todoapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import android.widget.AdapterView;
import android.provider.MediaStore.Files;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lVItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lVItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lVItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }
    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }
        catch (IOException e)
        {
            items = new ArrayList<String>();
        }
    }
    private void writeItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try
        {
            FileUtils.writeLines(todoFile, items);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    private void setupListViewListener() {
        lVItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                String message = items.get(position);
                Log.d("app","hellooo" + Integer.toString(position));
                intent.putExtra("text",message);
                intent.putExtra("position",position);
                startActivityForResult(intent, 1);

            }
        });
        lVItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);

                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        Log.d("app","hellooo");
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // Please, use a final int instead of hardcoded
            // int value
            if (resultCode == RESULT_OK) {
                String text = (String) data.getExtras().getString("newText");
                int position = (Integer) data.getExtras().getInt("position");
                items.set(position, text);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Log.d("app",Integer.toString(position) + ".." + text);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etEditText);
        String etText = etNewItem.getText().toString();
        if (etText.isEmpty())
            return;
        itemsAdapter.add(etText);
        etNewItem.setText("");
        writeItems();


    }
}
