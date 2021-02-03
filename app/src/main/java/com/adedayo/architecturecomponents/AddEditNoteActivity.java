package com.adedayo.architecturecomponents;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.adedayo.architecturecomponents.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.adedayo.architecturecomponents.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.adedayo.architecturecomponents.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.adedayo.architecturecomponents.EXTRA_PRIORITY";

    private EditText edit_text_title;
    private EditText edit_text_description;
    private NumberPicker num_picker_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edit_text_title = findViewById(R.id.edit_text_title);
        edit_text_description = findViewById(R.id.edit_text_description);
        num_picker_priority = findViewById(R.id.number_picker_priority);

        num_picker_priority.setMinValue(1);
        num_picker_priority.setMaxValue(10);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        //This get the intent to display the AddNoteActivity or EditNote if there's an id edit note eould be shown at the top 
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE));
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            num_picker_priority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }
        else {
            setTitle("Add note");
        }
    }


    private void saveNote() {
        String title = edit_text_title.getText().toString();
        String description = edit_text_description.getText().toString();
        int priority = num_picker_priority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this,
                    "Please Insert a title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}