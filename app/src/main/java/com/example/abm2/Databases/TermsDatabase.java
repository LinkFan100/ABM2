package com.example.abm2.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.abm2.Model.Assessments;
import com.example.abm2.Model.Courses;
import com.example.abm2.Model.Terms;

import java.util.ArrayList;

public class TermsDatabase extends SQLiteOpenHelper {

    // database name.
    private static final String DB_NAME = "termsDB";

    //  database version
    private static final int DB_VERSION = 5;

    // Terms table and Columns
    private static final String TERM_TABLE = "Terms";
    private static final String TERM_ID_COL = "id";
    private static final String Term_Name_Col = "term";
    private static final String Start_Date_Col = "startDate";
    private static final String End_Date_Col = "endDate";

    //Course table and Columns
    private static final String COURSE_TABLE = "Courses";
    private static final String Courses_ID_COL = "idCourse";
    private static final String Courses_Name_Col = "course";
    private static final String Courses_Start_Date_Col = "startDateCourses";
    private static final String Courses_End_Date_Col = "endDateCourses";
    private static final String Courses_Status_Col = "courseStatus";
    private static final String Course_Instructor_Name = "instructorName";
    private static final String Course_Instructor_Phone = "phone";
    private static final String Course_Instructor_Email = "email";
    private static final String Term_Course_Id = "termId";

    //Assessment table and Columns
    private static final String ASSESSMENT_TABLE = "Assessment";
    private static final String Assessment_ID_COL = "idAssessment";
    private static final String Assessment_Name_Col = "assessment";
    private static final String Assessment_Start_Date_Col = "startDateAssessment";
    private static final String Assessment_End_Date_Col = "endDateAssessment";
    private static final String Assessment_Type = "assessmentType";
    private static final String Assessment_Info = "assessmentInfo";
    private static final String Course_Assessment_Id = "courseId";





    // creating a constructor for our database handler.
    public TermsDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {


        String query1 = "CREATE TABLE " + TERM_TABLE + " ("
                + TERM_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Term_Name_Col + " TEXT,"
                + Start_Date_Col + " TEXT,"
                + End_Date_Col + " TEXT)";


        String query2 = "CREATE TABLE " + COURSE_TABLE + " ("
                + Courses_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Courses_Name_Col + " TEXT,"
                + Courses_Start_Date_Col + " TEXT,"
                + Courses_End_Date_Col + " TEXT,"
                + Courses_Status_Col + " TEXT,"
                + Course_Instructor_Name + " TEXT,"
                + Course_Instructor_Phone + " TEXT,"
                + Course_Instructor_Email + " TEXT,"
                + Term_Course_Id +" TEXT)";


        String query3 = "CREATE TABLE " + ASSESSMENT_TABLE + " ("
                + Assessment_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Assessment_Name_Col + " TEXT,"
                + Assessment_Start_Date_Col + " TEXT,"
                + Assessment_End_Date_Col + " TEXT,"
                + Assessment_Type + " TEXT,"
                + Assessment_Info + " TEXT,"
                + Course_Assessment_Id + " TEXT)";



        // method to execute above sql query
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);

    }

    //  add new course to sqlite database.
    public void addNewTerm(String termName, String termStart, String termEnd) {


        // writing data into database.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Term_Name_Col, termName);
        values.put(Start_Date_Col, termStart);
        values.put(End_Date_Col, termEnd);


        db.insert(TERM_TABLE, null, values);

        // closing database.
        db.close();
    }

    public void addNewCourse(String courseName, String courseStart, String courseEnd, String courseStatus, String courseInsName, String courseInsPhone, String courseInsEmail, String courseTermId) {


        // writing data into database.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Courses_Name_Col, courseName);
        values.put(Courses_Start_Date_Col, courseStart);
        values.put(Courses_End_Date_Col, courseEnd);
        values.put(Courses_Status_Col, courseStatus);
        values.put(Course_Instructor_Name, courseInsName);
        values.put(Course_Instructor_Phone, courseInsPhone);
        values.put(Course_Instructor_Email, courseInsEmail);
        values.put(Term_Course_Id, courseTermId);


        db.insert(COURSE_TABLE, null, values);

        // closing database.
        db.close();
    }

    public void addNewAssessment(String assessmentName, String assessmentStart, String assessmentEnd, String assessmentType,String assessmentInfo,String courseAssessmentId) {


        // writing data into database.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Assessment_Name_Col, assessmentName);
        values.put(Assessment_Start_Date_Col, assessmentStart);
        values.put(Assessment_End_Date_Col, assessmentEnd);
        values.put(Assessment_Type, assessmentType);
        values.put(Assessment_Info,assessmentInfo);
        values.put(Course_Assessment_Id,courseAssessmentId);


        db.insert(ASSESSMENT_TABLE, null, values);

        // closing database.
        db.close();
    }

    public ArrayList<Terms> readTerms()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorTerms
                = db.rawQuery("SELECT * FROM " + TERM_TABLE, null);

        // on below line we are creating a new array list.
        ArrayList<Terms> TermsArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorTerms.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                TermsArrayList.add(new Terms(
                        cursorTerms.getString(1),
                        cursorTerms.getString(2),
                        cursorTerms.getString(3)));
            } while (cursorTerms.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorTerms.close();
        return TermsArrayList;
    }

    public ArrayList<Courses> readCourses()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM " + COURSE_TABLE, null);

        // on below line we are creating a new array list.
        ArrayList<Courses> coursesArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                coursesArrayList.add(new Courses(
                        cursorCourses.getString(1),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8),
                        cursorCourses.getString(2),
                        cursorCourses.getInt(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return coursesArrayList;
    }
    public ArrayList<Courses> readCoursesByTermId(int s)
    {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM " + COURSE_TABLE + " Where " + TermsDatabase.Term_Course_Id+
                " = '"+s+"'",null);

        // on below line we are creating a new array list.
        ArrayList<Courses> coursesArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                coursesArrayList.add(new Courses(
                        cursorCourses.getString(1),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8),
                        cursorCourses.getString(2),
                        cursorCourses.getInt(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return coursesArrayList;
    }
    public ArrayList<Assessments> readAssessmentsByCourseId(String s) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor assessmentCourses
                = db.rawQuery("SELECT * FROM " + ASSESSMENT_TABLE + " Where " + TermsDatabase.Course_Assessment_Id+
                " = '"+s+"'",null);

        // on below line we are creating a new array list.
        ArrayList<Assessments> assessmentsArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (assessmentCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                assessmentsArrayList.add(new Assessments(
                        assessmentCourses.getString(1),
                        assessmentCourses.getString(4),
                        assessmentCourses.getString(5),
                        assessmentCourses.getString(2),
                        assessmentCourses.getString(3)));
            } while (assessmentCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        assessmentCourses.close();
        return assessmentsArrayList;
    }
    public ArrayList<Assessments> readAssessments()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorAssessments
                = db.rawQuery("SELECT * FROM " + ASSESSMENT_TABLE, null);

        // on below line we are creating a new array list.
        ArrayList<Assessments> assessmentsArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorAssessments.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                assessmentsArrayList.add(new Assessments(
                        cursorAssessments.getString(1),
                        cursorAssessments.getString(4),
                        cursorAssessments.getString(5),
                        cursorAssessments.getString(2),
                        cursorAssessments.getString(3)));
            } while (cursorAssessments.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorAssessments.close();
        return assessmentsArrayList;
    }

    public void deleteTerm(String termName){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TERM_TABLE, "term=?", new String[]{termName});
        db.close();
    }
    public void deleteCourse(String courseId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(COURSE_TABLE, "idCourse=?", new String[]{courseId});
        db.close();
    }
    public void deleteAssessment(String assessmentId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ASSESSMENT_TABLE, "idAssessment=?", new String[]{assessmentId});
        db.close();
    }
    public void deleteCourseCascadeAssessments(String assessmentId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ASSESSMENT_TABLE, "courseId=?", new String[]{assessmentId});
        db.close();
    }
    public void updateTerm(String oldTermName,String termName, String termStart, String termEnd){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Term_Name_Col, termName);
        values.put(Start_Date_Col, termStart);
        values.put(End_Date_Col, termEnd);

        db.update(TERM_TABLE, values, "term=?", new String[]{oldTermName});
        db.close();
    }
    public void updateCourse(String oldCourseName, String courseName, String courseStart, String courseEnd, String courseStatus, String courseInsName, String courseInsEmail, String courseInsPhone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Courses_Name_Col, courseName);
        values.put(Courses_Start_Date_Col,courseStart);
        values.put(Courses_End_Date_Col,courseEnd);
        values.put(Courses_Status_Col,courseStatus);
        values.put(Course_Instructor_Name,courseInsName);
        values.put(Course_Instructor_Email,courseInsEmail);
        values.put(Course_Instructor_Phone,courseInsPhone);

        db.update(COURSE_TABLE, values, "course=?", new String[]{oldCourseName});
        db.close();

    }
    public void updateAssessment(String oldAssessmentName, String assessmentName, String assessmentStart, String assessmentEnd, String assessmentType, String assessmentInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Assessment_Name_Col, assessmentName);
        values.put(Assessment_Start_Date_Col,assessmentStart);
        values.put(Assessment_End_Date_Col,assessmentEnd);
        values.put(Assessment_Type,assessmentType);
        values.put(Assessment_Info,assessmentInfo);

        db.update(ASSESSMENT_TABLE, values, "assessment=?", new String[]{oldAssessmentName});
        db.close();
    }

    public String setTermStartEnd(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {TermsDatabase.Start_Date_Col, TermsDatabase.End_Date_Col};
        Cursor cursorT = db.query(TermsDatabase.TERM_TABLE,col,TermsDatabase.Term_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer buffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Start_Date_Col);
            int i2 = cursorT.getColumnIndex(TermsDatabase.End_Date_Col);
            String termSDate = cursorT.getString(i1);
            String termEDate = cursorT.getString(i2);
             buffer.append(termSDate + " " + termEDate);
        }

        return buffer.toString();
    }
    public String[] setCourseDetails2(String n) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {TermsDatabase.Courses_Start_Date_Col, TermsDatabase.Courses_End_Date_Col, TermsDatabase.Courses_Status_Col,
                TermsDatabase.Course_Instructor_Name, TermsDatabase.Course_Instructor_Email, TermsDatabase.Course_Instructor_Phone};
        Cursor cursorT = db.query(TermsDatabase.COURSE_TABLE, col, TermsDatabase.Courses_Name_Col +
                " = '" + n + "'", null, null, null, null);


        String[] cDD = null;
        while (cursorT.moveToNext()) {
            int i1 = cursorT.getColumnIndex(TermsDatabase.Courses_Start_Date_Col);
            int i2 = cursorT.getColumnIndex(TermsDatabase.Courses_End_Date_Col);
            int i3 = cursorT.getColumnIndex(TermsDatabase.Courses_Status_Col);
            int i4 = cursorT.getColumnIndex(TermsDatabase.Course_Instructor_Name);
            int i5 = cursorT.getColumnIndex(TermsDatabase.Course_Instructor_Email);
            int i6 = cursorT.getColumnIndex(TermsDatabase.Course_Instructor_Phone);
            String courseSDate = cursorT.getString(i1);
            String courseEDate = cursorT.getString(i2);
            String courseStatus = cursorT.getString(i3);
            String courseInsName = cursorT.getString(i4);
            String courseInsEmail = cursorT.getString(i5);
            String courseInsPhone = cursorT.getString(i6);
            cDD = new String[]{courseSDate, courseEDate, courseStatus, courseInsName, courseInsEmail, courseInsPhone};

        }

        return cDD;
    }
    public String[] setAssessmentDetails(String n) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {TermsDatabase.Assessment_Start_Date_Col, TermsDatabase.Assessment_End_Date_Col, TermsDatabase.Assessment_Type,
                TermsDatabase.Assessment_Info};
        Cursor cursorT = db.query(TermsDatabase.ASSESSMENT_TABLE, col, TermsDatabase.Assessment_Name_Col +
                " = '" + n + "'", null, null, null, null);


        String[] cAD = null;
        while (cursorT.moveToNext()) {
            int i1 = cursorT.getColumnIndex(TermsDatabase.Assessment_Start_Date_Col);
            int i2 = cursorT.getColumnIndex(TermsDatabase.Assessment_End_Date_Col);
            int i3 = cursorT.getColumnIndex(TermsDatabase.Assessment_Type);
            int i4 = cursorT.getColumnIndex(TermsDatabase.Assessment_Info);
            String assessmentSDate = cursorT.getString(i1);
            String assessmentEDate = cursorT.getString(i2);
            String assessmentType = cursorT.getString(i3);
            String assessmentInfo = cursorT.getString(i4);
            cAD = new String[]{assessmentSDate, assessmentEDate, assessmentType, assessmentInfo};


        }

        return cAD;
    }
    public String getTermId(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.TERM_ID_COL};
        Cursor cursorT = db.query(TermsDatabase.TERM_TABLE,col,TermsDatabase.Term_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.TERM_ID_COL);
            String termId = cursorT.getString(i1);
            Namebuffer.append(termId);
        }

        return Namebuffer.toString();
    }
    public String getTermName(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.Term_Name_Col};
        Cursor cursorT = db.query(TermsDatabase.TERM_TABLE,col,TermsDatabase.TERM_ID_COL+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Term_Name_Col);
            String termId = cursorT.getString(i1);
            Namebuffer.append(termId);
        }

        return Namebuffer.toString();
    }
    public String getCourseId(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.Courses_ID_COL};
        Cursor cursorT = db.query(TermsDatabase.COURSE_TABLE,col,TermsDatabase.Courses_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Courses_ID_COL);
            String termName = cursorT.getString(i1);
            Namebuffer.append(termName);
        }

        return Namebuffer.toString();
    }
    public String getCourseTermId(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.Term_Course_Id};
        Cursor cursorT = db.query(TermsDatabase.COURSE_TABLE,col,TermsDatabase.Courses_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Term_Course_Id);
            String termName = cursorT.getString(i1);
            Namebuffer.append(termName);
        }

        return Namebuffer.toString();
    }
    public String getAssessmentCourseId(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.Course_Assessment_Id};
        Cursor cursorT = db.query(TermsDatabase.ASSESSMENT_TABLE,col,TermsDatabase.Assessment_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Course_Assessment_Id);
            String termName = cursorT.getString(i1);
            Namebuffer.append(termName);
        }

        return Namebuffer.toString();
    }
    public String getAssessmentId(String n){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] col  = {TermsDatabase.Assessment_ID_COL};
        Cursor cursorT = db.query(TermsDatabase.ASSESSMENT_TABLE,col,TermsDatabase.Assessment_Name_Col+
                " = '"+n+"'",null,null,null,null);

        StringBuffer Namebuffer = new StringBuffer();
        while (cursorT.moveToNext()){
            int i1 = cursorT.getColumnIndex(TermsDatabase.Assessment_ID_COL);
            String termName = cursorT.getString(i1);
            Namebuffer.append(termName);
        }

        return Namebuffer.toString();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TERM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSESSMENT_TABLE);
        onCreate(db);
    }


        public boolean checkIfExists(String tableName, String columnName, String value) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{value});

            boolean exists = cursor.getCount() > 0;

            cursor.close();
            db.close();

            return exists;
        }
    public boolean checkIfExistsUpdate(String tableName, String columnName,String columnName2,  String value, String value2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?" + " AND "
                + columnName2 + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{value,value2});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }


}


