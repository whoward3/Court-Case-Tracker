package com.example.whoward3.expensetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditExpenseActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.whoward3.expensetracker.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.whoward3.expensetracker.EXTRA_NAME";
    public static final String EXTRA_CATEGORY = "com.example.whoward3.expensetracker.EXTRA_CATEGORY";
    public static final String EXTRA_DATE = "com.example.whoward3.expensetracker.EXTRA_DATE";
    public static final String EXTRA_AMOUNT = "com.example.whoward3.expensetracker.EXTRA_AMOUNT";
    public static final String EXTRA_NOTE = "com.example.whoward3.expensetracker.EXTRA_NOTE";

    private EditText editTextName;
    private EditText editTextCategory;
    private EditText editTextDate;
    private EditText editTextAmount;
    private EditText editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editTextAmount = findViewById(R.id.edit_text_amount);
        editTextName = findViewById(R.id.edit_text_name);
        editTextNote = findViewById(R.id.edit_text_note);
        editTextCategory = findViewById(R.id.edit_text_category);
        editTextDate = findViewById(R.id.edit_text_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Expense");
                      editTextAmount.setText(String.valueOf(intent.getFloatExtra(EXTRA_AMOUNT,1.0F)));                                                //Might cause issue late on :(
            editTextCategory.setText(intent.getStringExtra(EXTRA_CATEGORY));
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextNote.setText(intent.getStringExtra(EXTRA_NOTE));
        }else {
            setTitle("Add Expense");
        }
    }

    private void saveExpense(){
        String name = editTextName.getText().toString();
        String category = editTextCategory.getText().toString();
        String date = editTextDate.getText().toString();
        float amount;
        try{amount = Float.valueOf(editTextAmount.getText().toString());}catch (Exception a){amount = (float)-9.99;}
        String note = editTextNote.getText().toString();

        if(name.trim().isEmpty() || category.trim().isEmpty() || date.trim().isEmpty() || amount < 0.00){
            Toast.makeText(this,"All expenses must have a name, category, date, and amount.",Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_NOTE, note);

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
        menuInflater.inflate(R.menu.add_expense_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_expense:
                saveExpense();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
