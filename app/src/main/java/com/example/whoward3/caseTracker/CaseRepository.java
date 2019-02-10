package com.example.whoward3.caseTracker;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CaseRepository {
    private CaseDao caseDao;
    private LiveData<List<Case>> allExpences;

    public CaseRepository(Application application){
        CaseDatabase database = CaseDatabase.getInstance((application));
        caseDao = database.expenseDao();
        allExpences = caseDao.getAllExpenses();
    }

    public void insert(Case aCase){
        new InsertExpenseAsyncTask(caseDao).execute(aCase);
    }

    public void update(Case aCase){
        new UpdateExpenseAsyncTask(caseDao).execute(aCase);
    }

    public void delete(Case aCase){
        new DeleteExpenseAsyncTask(caseDao).execute(aCase);
    }

    public void deleteAllExpences(){
        new DeleteAllExpenseAsyncTask(caseDao).execute();
    }

    public LiveData<List<Case>> getAllExpences() {
        return allExpences;
    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private InsertExpenseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... expens){
            caseDao.insert(expens[0]);
        return null;
    }
    }
    private static class UpdateExpenseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private UpdateExpenseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... expens){
            caseDao.update(expens[0]);
            return null;
        }
    }
    private static class DeleteExpenseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private DeleteExpenseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... expens){
            caseDao.delete(expens[0]);
            return null;
        }
    }
    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void,Void>{
        private CaseDao caseDao;

        private DeleteAllExpenseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Void... expenses){
            caseDao.deleteAllExpenses();
            return null;
        }
    }
}
