package com.example.whoward3.caseTracker;

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
    public static final int ADD_CASE_REQUEST = 1;
    public static final int EDIT_CASE_REQUEST = 2;
    private CaseViewModel caseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddCase = findViewById(R.id.button_add_case);
        buttonAddCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditCaseActivity.class);
                startActivityForResult(intent, ADD_CASE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final CaseAdapter adapter = new CaseAdapter();
        recyclerView.setAdapter(adapter);

        caseViewModel = ViewModelProviders.of(this).get(CaseViewModel.class);
        caseViewModel.getAllCases().observe(this, new Observer<List<Case>>() {
            @Override
            public void onChanged(@Nullable List<Case> casess) {
                //this is where our RecyclerView should be updated
                adapter.setCases(casess);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                caseViewModel.delete(adapter.getCaseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Case Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Case aCase) {
                Intent intent = new Intent(MainActivity.this, AddEditCaseActivity.class);
                intent.putExtra(AddEditCaseActivity.EXTRA_ID, aCase.get_id());
                intent.putExtra(AddEditCaseActivity.EXTRA_PERSON, aCase.getPerson());
                intent.putExtra(AddEditCaseActivity.EXTRA_TRIBE, aCase.getTribe());
                intent.putExtra(AddEditCaseActivity.EXTRA_OPENDATE, aCase.getOpenDate());
                intent.putExtra(AddEditCaseActivity.EXTRA_CASENOTES, aCase.getCaseNotes());
                startActivityForResult(intent, EDIT_CASE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CASE_REQUEST && resultCode == RESULT_OK){
            String person = data.getStringExtra(AddEditCaseActivity.EXTRA_PERSON);
            String tribe = data.getStringExtra(AddEditCaseActivity.EXTRA_TRIBE);
            String type = data.getStringExtra(AddEditCaseActivity.EXTRA_TYPE);
            String subtype = data.getStringExtra(AddEditCaseActivity.EXTRA_SUBTYPE);
            String opendate = data.getStringExtra(AddEditCaseActivity.EXTRA_OPENDATE);
            String closedate = data.getStringExtra(AddEditCaseActivity.EXTRA_CLOSEDATE);
            String casenotes = data.getStringExtra(AddEditCaseActivity.EXTRA_CASENOTES);

            Case aCase = new Case(person,tribe,type,subtype,opendate,closedate,casenotes);
            caseViewModel.insert(aCase);

            Toast.makeText(this,"Case saved",Toast.LENGTH_LONG).show();
        }else if(requestCode == EDIT_CASE_REQUEST && resultCode == RESULT_OK) {
            long id = data.getLongExtra(AddEditCaseActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(this, "Case can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String person = data.getStringExtra(AddEditCaseActivity.EXTRA_PERSON);
            String tribe = data.getStringExtra(AddEditCaseActivity.EXTRA_TRIBE);
            String type = data.getStringExtra(AddEditCaseActivity.EXTRA_TYPE);
            String subtype = data.getStringExtra(AddEditCaseActivity.EXTRA_SUBTYPE);
            String opendate = data.getStringExtra(AddEditCaseActivity.EXTRA_OPENDATE);
            String closedate = data.getStringExtra(AddEditCaseActivity.EXTRA_CLOSEDATE);
            String casenotes = data.getStringExtra(AddEditCaseActivity.EXTRA_CASENOTES);

            Case aCase = new Case(person,tribe,type,subtype,opendate,closedate,casenotes);
            aCase.set_id(id);
            caseViewModel.update(aCase);
            Toast.makeText(this, "Case updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Case not Saved",Toast.LENGTH_LONG).show();
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
            case R.id.delete_all_cases:
                caseViewModel.deleteAllCases();
                Toast.makeText(this, "All cases deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
