package com.example.whoward3.caseTracker;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Case.class},version = 1)
public abstract class CaseDatabase extends RoomDatabase {

    private static CaseDatabase instance;

    public abstract CaseDao caseDao();

    public static synchronized CaseDatabase getInstance (Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CaseDatabase.class,"case_database")
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
    private CaseDao caseDao;
    private PopulateDbAsyncTask(CaseDatabase db){
        caseDao = db.caseDao();
    }
        @Override
        protected Void doInBackground(Void... voids){
            return null;
        }
    }
}
