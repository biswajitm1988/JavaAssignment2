package com.fsd.example.dao;

import com.fsd.example.model.Book;
import com.fsd.example.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public static Long getNextBookId() throws Exception {
        long bookId=0l;
        String getMaxBookId = "select MAX(bookid) from book"; //Can use sequence here
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(getMaxBookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            bookId=resultSet.getLong(1);
        }
        return bookId+1;
    }

    public static boolean addBook(Book newBook) throws Exception {
        String addBookSql="insert into book (bookid, title, price, volume, publish_date) values (?,?,?,?,?)";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(addBookSql);
        preparedStatement.setLong(1,newBook.getBookId());
        preparedStatement.setString(2,newBook.getTitle());
        preparedStatement.setDouble(3,newBook.getPrice());
        preparedStatement.setInt(4, newBook.getVolume());
        preparedStatement.setDate(5, Date.valueOf(newBook.getPublishDate()));
        int updateRows = preparedStatement.executeUpdate();
        return true;
    }

    public static List<Book> getAllBooks() throws Exception {
        String getBookCountSql = "select * from book";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(getBookCountSql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<Book>();
        while(resultSet.next()){
            Book book = new Book();
            book.setBookId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setPrice(resultSet.getDouble(3));
            book.setVolume(resultSet.getInt(4));
            book.setPublishDate(resultSet.getDate(5).toLocalDate());
            bookList.add(book);
        }
        return bookList;
    }

    public static List<Book> searchForBooks(String bookTitle) throws Exception {
        String getBookCountSql = "select * from book where lower(title) like lower(?)"; //Can use sequence here
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(getBookCountSql);
        preparedStatement.setString(1,"%" + bookTitle + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<Book>();
        while(resultSet.next()){
            Book book = new Book();
            book.setBookId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setPrice(resultSet.getDouble(3));
            book.setVolume(resultSet.getInt(4));
            book.setPublishDate(resultSet.getDate(5).toLocalDate());
            bookList.add(book);
        }
        return bookList;
    }

    public static int deleteBook(String bookTitle) throws Exception {
        String addBookSql="delete from book where lower(title) like lower(?)";
        PreparedStatement  preparedStatement = DBConnectionManager.getConnection().prepareStatement(addBookSql);
        preparedStatement.setString(1,"%" + bookTitle + "%");
        return preparedStatement.executeUpdate();
    }

    public static void createBookTable() throws Exception {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS BOOK"
                + "  (BOOKID           LONG PRIMARY KEY,"
                + "   TITLE            VARCHAR ,"
                + "   PRICE          DOUBLE ,"
                + "   VOLUME           INT ,"
                + "   PUBLISH_DATE     DATE)";

        Statement stmt = DBConnectionManager.getConnection().createStatement();
        stmt.execute(sqlCreate);
        stmt.close();
    }
}
