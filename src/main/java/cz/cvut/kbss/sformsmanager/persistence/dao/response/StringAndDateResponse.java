package cz.cvut.kbss.sformsmanager.persistence.dao.response;

import java.util.Date;

public class StringAndDateResponse {
    private String string;
    private Date date;

    public StringAndDateResponse() {
    }

    public StringAndDateResponse(String string, Date date) {
        this.string = string;
        this.date = date;
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
}