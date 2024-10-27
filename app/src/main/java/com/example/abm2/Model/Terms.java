package com.example.abm2.Model;

public class Terms {

    private String termName;
private String startDate;
private String endDate;
private int id;

    public Terms(String termName, String startDate, String endDate) {
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
