package com.fsd.example.dao;

import com.fsd.example.model.Book;
import com.fsd.example.model.Subject;
import com.fsd.example.util.DBConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {


    public static Long getNextSubjectId() throws Exception {
        long subjectId=0l;
        String getMaxBookid = "select MAX(subjectid) from subject"; //Can use sequence here
        PreparedStatement preparedStatement = DBConnectionManager.getConnection().prepareStatement(getMaxBookid);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            subjectId=resultSet.getLong(1);
        }
        return subjectId+1;
    }

    public static boolean addSubject(Subject newSubject) throws Exception {
        String addSubjectSql="insert into Subject (subjectid, subtitle, duration_in_hours) values (?,?,?)";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(addSubjectSql);
        preparedStatement.setLong(1,newSubject.getSubjectId());
        preparedStatement.setString(2,newSubject.getSubtitle());
        preparedStatement.setInt(3,newSubject.getDurationInHours());
        int updateRows = preparedStatement.executeUpdate();
        String addSubjectBookSql="insert into Subject_book (subjectid, bookid) values (?,?)";
        PreparedStatement  preparedStatement2 = DBConnectionManager.getConnection().prepareStatement(addSubjectBookSql);

        for(Book book:newSubject.getReferences()){
            preparedStatement2.setLong(1,newSubject.getSubjectId());
            preparedStatement2.setLong(2,book.getBookId());
            preparedStatement2.addBatch();
        }
        preparedStatement2.executeBatch();

        DBConnectionManager.getConnection().commit();
        return true;
    }

    public static int getAllSubjectCount() throws Exception {
        int subjectCount=0;
        String getSubjectCountSql = "select count(*) from subject";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(getSubjectCountSql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            subjectCount=resultSet.getInt(1);
        }
        return subjectCount;
    }

    public static List<Book> searchForSubjects(String subtitle) throws Exception {
        String getBookCountSql = "select * from subject s, book b, subject_book sb where s.subjectid=sb.subjectid and sb.bookid=b.bookid and lower(subtitle) like lower(?) ";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(getBookCountSql);
        preparedStatement.setString(1,"%" + subtitle + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<Book>();
        while(resultSet.next()){
            Book book = new Book();
            book.setBookId(resultSet.getLong("BOOKID"));
            book.setTitle(resultSet.getString("TITLE"));
            book.setPrice(resultSet.getDouble("PRICE"));
            book.setVolume(resultSet.getInt("VOLUME"));
            book.setPublishDate(resultSet.getDate("PUBLISH_DATE").toLocalDate());
            bookList.add(book);
        }
        return bookList;
    }

    public static int deleteSubject(String subtitle) throws Exception {
        String addBookSql="delete subject where lower(subtitle) like lower(?)";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(addBookSql);
        preparedStatement.setString(1,"%" + subtitle + "%");
        return preparedStatement.executeUpdate();
    }


    public static void createSubjectTable() throws Exception {
        String sqlSubjectCreate = "CREATE TABLE IF NOT EXISTS SUBJECT"
                + "  (SUBJECTID           LONG PRIMARY KEY,"
                + "   SUBTITLE            VARCHAR ,"
                + "   DURATION_IN_HOURS           INT )";

        Statement stmt = DBConnectionManager.getConnection().createStatement();
        stmt.execute(sqlSubjectCreate);
        stmt.close();

        String sqlSubjectBookRefCreate = "CREATE TABLE IF NOT EXISTS SUBJECT_BOOK"
                + "  (SUBJECTID            LONG ,"
                + "   BOOKID           LONG ,"
                + "   FOREIGN KEY(SUBJECTID) REFERENCES SUBJECT(SUBJECTID) ON DELETE CASCADE,"
                + "   FOREIGN KEY(BOOKID) REFERENCES BOOK(BOOKID) ON DELETE CASCADE)";

        stmt = DBConnectionManager.getConnection().createStatement();
        stmt.execute(sqlSubjectBookRefCreate);
        stmt.close();

    }

}
