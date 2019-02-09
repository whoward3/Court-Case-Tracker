package com.example.whoward3.expensetracker;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Expense.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance (Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExpenseDatabase.class,"expense_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
    private ExpenseDao expenseDao;
    private PopulateDbAsyncTask(ExpenseDatabase db){
        expenseDao = db.expenseDao();
    }

        @Override
        protected Void doInBackground(Void... voids){
           //expenseDao.insert(new Expense("Name1", "Category1","Date1",(float)0.1,"Note1"));  //For my Testing Purposes
           //expenseDao.insert(new Expense("Name2", "Category2","Date2",(float)0.1,"Note2"));
           //expenseDao.insert(new Expense("Name3", "Category3","Date3",(float)0.1,"Note3"));
            return null;
        }
    }
}
