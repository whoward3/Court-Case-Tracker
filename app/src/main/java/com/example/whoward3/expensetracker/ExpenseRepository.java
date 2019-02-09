package com.example.whoward3.expensetracker;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpences;

    public ExpenseRepository(Application application){
        ExpenseDatabase database = ExpenseDatabase.getInstance((application));
        expenseDao = database.expenseDao();
        allExpences = expenseDao.getAllExpenses();
    }

    public void insert(Expense expense){
        new InsertExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void update(Expense expense){
        new UpdateExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void delete(Expense expense){
        new DeleteExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void deleteAllExpences(){
        new DeleteAllExpenseAsyncTask(expenseDao).execute();
    }

    public LiveData<List<Expense>> getAllExpences() {
        return allExpences;
    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void,Void>{
        private ExpenseDao expenseDao;

        private InsertExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses){
            expenseDao.insert(expenses[0]);
        return null;
    }
    }
    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void,Void>{
        private ExpenseDao expenseDao;

        private UpdateExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses){
            expenseDao.update(expenses[0]);
            return null;
        }
    }
    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void,Void>{
        private ExpenseDao expenseDao;

        private DeleteExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses){
            expenseDao.delete(expenses[0]);
            return null;
        }
    }
    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void,Void>{
        private ExpenseDao expenseDao;

        private DeleteAllExpenseAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Void... expenses){
            expenseDao.deleteAllExpenses();
            return null;
        }
    }
}
