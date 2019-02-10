package com.example.whoward3.caseTracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "case_table")
public class Case {
    private String person;
    private String tribe;
    private String type;
    private String subtype;
    private String openDate;
    private String closeDate;
    private String caseNotes;

    @PrimaryKey(autoGenerate = true)
    private Long _id;

    public String getPerson() {
        return person;
    }

    public String getTribe() {
        return tribe;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getCaseNotes() {
        return caseNotes;
    }

    public Long get_id() {
        return _id;
    }

    public Case(String person, String tribe, String type, String subtype, String openDate, String closeDate, String caseNotes) {
        this.person = person;
        this.tribe = tribe;
        this.type = type;
        this.subtype = subtype;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.caseNotes = caseNotes;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
