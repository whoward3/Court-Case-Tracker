package com.example.whoward3.caseTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditCaseActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.whoward3.caseTracker.EXTRA_ID";
    public static final String EXTRA_PERSON = "com.example.whoward3.caseTracker.EXTRA_PERSON";
    public static final String EXTRA_TRIBE = "com.example.whoward3.caseTracker.EXTRA_TRIBE";
    public static final String EXTRA_TYPE = "com.example.whoward3.caseTracker.EXTRA_TYPE";
    public static final String EXTRA_SUBTYPE = "com.example.whoward3.caseTracker.EXTRA_SUBTYPE";
    public static final String EXTRA_EVENTCOUNT = "com.example.whoward3.caseTracker.EXTRA_EVENTCOUNT";
    public static final String EXTRA_OPENDATE = "com.example.whoward3.caseTracker.EXTRA_OPENDATE";
    public static final String EXTRA_CLOSEDATE = "com.example.whoward3.caseTracker.EXTRA_CLOSEDATE";
    public static final String EXTRA_CASENOTES = "com.example.whoward3.caseTracker.EXTRA_CASENOTES";

    private EditText editTextPerson;
    private EditText editTextTribe;
    private EditText editTextCaseType;
    private EditText editTextOpenDate;
    private EditText editTextCaseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        editTextTribe = findViewById(R.id.edit_text_tribe);
        editTextPerson = findViewById(R.id.edit_text_person);
        editTextCaseNotes = findViewById(R.id.edit_text_caseNotes);
        editTextCaseType = findViewById(R.id.edit_text_caseType);
        editTextOpenDate = findViewById(R.id.edit_text_openDate);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Case");
                      editTextTribe.setText(String.valueOf(intent.getFloatExtra(EXTRA_EVENTCOUNT,1.0F)));                                                //Might cause issue late on :(
            editTextCaseType.setText(intent.getStringExtra(EXTRA_TRIBE));
            editTextOpenDate.setText(intent.getStringExtra(EXTRA_OPENDATE));
            editTextPerson.setText(intent.getStringExtra(EXTRA_PERSON));
            editTextCaseNotes.setText(intent.getStringExtra(EXTRA_CASENOTES));
        }else {
            setTitle("Add Case");
        }
    }

    private void saveCase(){
        String person = editTextPerson.getText().toString();
        String category = editTextCaseType.getText().toString();
        String date = editTextOpenDate.getText().toString();
        float amount;
        try{amount = Float.valueOf(editTextTribe.getText().toString());}catch (Exception a){amount = (float)-9.99;}
        String note = editTextCaseNotes.getText().toString();

        if(person.trim().isEmpty() || category.trim().isEmpty() || date.trim().isEmpty() || amount < 0.00){
            Toast.makeText(this,"All expenses must have a person, category, date, and amount.",Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_PERSON, person);
        data.putExtra(EXTRA_TRIBE, category);
        data.putExtra(EXTRA_OPENDATE, date);
        data.putExtra(EXTRA_EVENTCOUNT, amount);
        data.putExtra(EXTRA_CASENOTES, note);

        Long id = getIntent().getLongExtra(EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_case_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_case:
                saveCase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
