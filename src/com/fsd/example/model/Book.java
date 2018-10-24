package com.fsd.example.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Comparable, Serializable {
    private static final long serialVersionUID =1l;
    private Long bookId;
    private String title;
    private double price;
    private int volume;
    private LocalDate publishDate;

    public Book(Long bookId, String title, double price, int volume, LocalDate publishDate) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.volume = volume;
        this.publishDate = publishDate;
    }

    public Book() {

    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int compareTo(Object o) {
        return this.bookId.compareTo(((Book) o).getBookId());
    }

    @Override
    public String toString() {
        return "\nBook{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", publishDate=" + publishDate +
                '}';
    }
}
