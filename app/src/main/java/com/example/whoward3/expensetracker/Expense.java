package com.example.whoward3.expensetracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    private String name;
    private String category;
    private String date;
    private Float amount;
    private String note;

    @PrimaryKey(autoGenerate = true)
    private Long _id;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public Float getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public Long get_id() {
        return _id;
    }

    public Expense(String name, String category, String date, Float amount, String note) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
