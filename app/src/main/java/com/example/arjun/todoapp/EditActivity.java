package com.example.arjun.todoapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.ViewGroup;

public class EditActivity extends AppCompatActivity {

    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        String message = intent.getStringExtra("text");
        position = (Integer) intent.getExtras().getInt("position");
        EditText editText = (EditText) findViewById(R.id.etTextItem);
        editText.setText(message);
        editText.setSelection(message.length());
        Log.d("App", "hello2" + Integer.toString(position));

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        //layout.addView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        EditText etNewItem = (EditText) findViewById(R.id.etTextItem);
        String etText = etNewItem.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newText",etText);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        Log.d("App","hello" + Integer.toString(position));
        finish();
        //itemsAdapter.add(etText);
        //etNewItem.setText("");
        //writeItems();


    }
}
