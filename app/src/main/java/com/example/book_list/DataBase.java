package com.example.book_list;

public class DataBase {

    private String book_year;
    private String book_author;
    private String book_title;
    private String book_code;
    private String book_publisher;
    private String book_Uid;

    // Empty constructor required for Firebase
    public DataBase() {
    }

    // Getters and setters for each field

    public String getBook_year() {
        return book_year;
    }

    public void setBook_year(String book_year) {
        this.book_year = book_year;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_code() {
        return book_code;
    }

    public void setBook_code(String book_code) {
        this.book_code = book_code;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public String getBook_Uid() {
        return book_Uid;
    }

    public void setBook_Uid(String book_Uid) {
        this.book_Uid = book_Uid;
    }
}