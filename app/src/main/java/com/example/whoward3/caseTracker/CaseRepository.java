package com.example.whoward3.caseTracker;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CaseRepository {
    private CaseDao caseDao;
    private LiveData<List<Case>> allCases;

    public CaseRepository(Application application){
        CaseDatabase database = CaseDatabase.getInstance((application));
        caseDao = database.caseDao();
        allCases = caseDao.getAllCases();
    }

    public void insert(Case aCase){
        new InsertCaseAsyncTask(caseDao).execute(aCase);
    }

    public void update(Case aCase){
        new UpdateCaseAsyncTask(caseDao).execute(aCase);
    }

    public void delete(Case aCase){
        new DeleteCaseAsyncTask(caseDao).execute(aCase);
    }

    public void deleteAllCases(){
        new DeleteAllCasesAsyncTask(caseDao).execute();
    }

    public LiveData<List<Case>> getAllCases() {
        return allCases;
    }

    private static class InsertCaseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private InsertCaseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... cases){
            caseDao.insert(cases[0]);
        return null;
    }
    }
    private static class UpdateCaseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private UpdateCaseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... cases){
            caseDao.update(cases[0]);
            return null;
        }
    }
    private static class DeleteCaseAsyncTask extends AsyncTask<Case, Void,Void>{
        private CaseDao caseDao;

        private DeleteCaseAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Case... cases){
            caseDao.delete(cases[0]);
            return null;
        }
    }
    private static class DeleteAllCasesAsyncTask extends AsyncTask<Void, Void,Void>{
        private CaseDao caseDao;

        private DeleteAllCasesAsyncTask(CaseDao caseDao){
            this.caseDao = caseDao;
        }

        @Override
        protected Void doInBackground(Void... cases){
            caseDao.deleteAllCases();
            return null;
        }
    }
}
