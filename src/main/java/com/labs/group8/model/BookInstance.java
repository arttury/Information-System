package com.labs.group8.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookInstance {
    private int id;
    private Book book;
    private boolean isIssued;

    public BookInstance() {
    }

    public BookInstance(int id, Book book, boolean isIssued) {
        this.id = id;
        this.book = book;
        this.isIssued = isIssued;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    @XmlElement
    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isIssued() {
        return isIssued;
    }

    @XmlElement
    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInstance that = (BookInstance) o;

        if (id != that.id) return false;
        if (isIssued != that.isIssued) return false;
        return book.equals(that.book);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + book.hashCode();
        result = 31 * result + (isIssued ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", book=" + book +
                ", isIssued=" + isIssued +
                '}';
    }
}
