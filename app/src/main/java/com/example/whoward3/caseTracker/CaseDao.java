package com.example.whoward3.caseTracker;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CaseDao {

    @Insert
    void insert(Case aCase);

    @Update
    void update(Case aCase);

    @Delete
    void delete(Case aCase);

    @Query("DELETE FROM `Case`")
    void deleteAllExpenses();

    @Query("SELECT * FROM `Case`")
    LiveData<List<Case>> getAllExpenses();
}
