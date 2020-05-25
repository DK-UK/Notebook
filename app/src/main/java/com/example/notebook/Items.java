package com.example.notebook;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Items {
    public String getNote() {
        return note;
    }

    public Items(String note,String time)
    {
        this.note = note;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    String note;
    String time;
}
