package com.example.whoward3.expensetracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_EXPENSE_REQUEST = 1;
    public static final int EDIT_EXPENSE_REQUEST = 2;
    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddExpense = findViewById(R.id.button_add_expense);
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);


        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpences().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {
                //this is where our RecyclerView should be updated
                adapter.setExpenses(expenses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                expenseViewModel.delete(adapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Expense Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExpenseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(MainActivity.this,AddEditExpenseActivity.class);
                intent.putExtra(AddEditExpenseActivity.EXTRA_ID, expense.get_id());
                intent.putExtra(AddEditExpenseActivity.EXTRA_NAME,expense.getName());
                intent.putExtra(AddEditExpenseActivity.EXTRA_AMOUNT,expense.getAmount());
                intent.putExtra(AddEditExpenseActivity.EXTRA_CATEGORY,expense.getCategory());
                intent.putExtra(AddEditExpenseActivity.EXTRA_DATE,expense.getDate());
                intent.putExtra(AddEditExpenseActivity.EXTRA_NOTE,expense.getNote());
                startActivityForResult(intent,EDIT_EXPENSE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_EXPENSE_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddEditExpenseActivity.EXTRA_NAME);
            String category = data.getStringExtra(AddEditExpenseActivity.EXTRA_CATEGORY);
            String date = data.getStringExtra(AddEditExpenseActivity.EXTRA_DATE);
            String note = data.getStringExtra(AddEditExpenseActivity.EXTRA_NOTE);
            float amount = data.getFloatExtra(AddEditExpenseActivity.EXTRA_AMOUNT, 1);

            Expense expense = new Expense(name,category,date,amount,note);
            expenseViewModel.insert(expense);

            Toast.makeText(this,"Expense Saved",Toast.LENGTH_LONG).show();
        }else if(requestCode == EDIT_EXPENSE_REQUEST && resultCode == RESULT_OK) {
            long id = data.getLongExtra(AddEditExpenseActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(this, "Expense can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditExpenseActivity.EXTRA_NAME);
            String category = data.getStringExtra(AddEditExpenseActivity.EXTRA_CATEGORY);
            String date = data.getStringExtra(AddEditExpenseActivity.EXTRA_DATE);
            String note = data.getStringExtra(AddEditExpenseActivity.EXTRA_NOTE);
            float amount = data.getFloatExtra(AddEditExpenseActivity.EXTRA_AMOUNT, 1);

            Expense expense = new Expense(name,category,date,amount,note);
            expense.set_id(id);
            expenseViewModel.update(expense);
            Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Expense not Saved",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_expenses:
                expenseViewModel.deleteAllExpences();
                Toast.makeText(this, "All expenses deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
