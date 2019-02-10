package com.example.whoward3.caseTracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CaseViewModel extends AndroidViewModel {
    private CaseRepository repository;
    private LiveData<List<Case>> allExpences;

    public CaseViewModel(@NonNull Application application) {
        super(application);
        repository = new CaseRepository(application);
        allExpences = repository.getAllExpences();
    }

    public void insert(Case aCase){
        repository.insert(aCase);
    }

    public void update(Case aCase){
        repository.update(aCase);
    }

    public void delete(Case aCase){
        repository.delete(aCase);
    }

    public void deleteAllExpences(){
        repository.deleteAllExpences();
    }

    public LiveData<List<Case>> getAllCases(){
        return allExpences;
    }
}
