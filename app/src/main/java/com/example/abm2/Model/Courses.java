package com.example.abm2.Model;

public class Courses {
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseInsName;
    private String courseInsPhone;
    private String courseInsEmail;
    private int courseID;
    private int termId;

    public Courses(String courseName, String courseStart, String courseEnd, String courseStatus, String courseInsName, String courseInsPhone, String courseInsEmail,int termId) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseInsName = courseInsName;
        this.courseInsPhone = courseInsPhone;
        this.courseInsEmail = courseInsEmail;
        this.termId = termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseInsName() {
        return courseInsName;
    }

    public void setCourseInsName(String courseInsName) {
        this.courseInsName = courseInsName;
    }

    public String getCourseInsPhone() {
        return courseInsPhone;
    }

    public void setCourseInsPhone(String courseInsPhone) {
        this.courseInsPhone = courseInsPhone;
    }

    public String getCourseInsEmail() {
        return courseInsEmail;
    }

    public void setCourseInsEmail(String courseInsEmail) {
        this.courseInsEmail = courseInsEmail;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
