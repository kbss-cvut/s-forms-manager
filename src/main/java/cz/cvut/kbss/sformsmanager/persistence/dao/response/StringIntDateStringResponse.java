package cz.cvut.kbss.sformsmanager.persistence.dao.response;

import java.util.Date;

public class StringIntDateStringResponse {
    private String string;
    private int integer;
    private Date date;
    private String string1;

    public StringIntDateStringResponse() {
    }

    public StringIntDateStringResponse(String string, int integer, Date date, String string1) {
        this.string = string;
        this.integer = integer;
        this.date = date;
        this.string1 = string1;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getString() {
        return string;
    }

    public Date getDate() {
        return date;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }
}