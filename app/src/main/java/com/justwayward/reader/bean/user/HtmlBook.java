package com.justwayward.reader.bean.user;

public class HtmlBook {

    private String name;
    private String bookid;
    private String size;

    public HtmlBook() {
    }

    public HtmlBook(String name, String bookid, String size) {
        this.name = name;
        this.size = size;
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
}
