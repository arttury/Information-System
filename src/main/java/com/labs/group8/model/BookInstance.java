package com.labs.group8.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class BookInstance implements Serializable {

    private ArrayList<Book> books;
    private int id;
    private boolean isIssued;

    public BookInstance() {
    }

    public BookInstance(int id, ArrayList<Book> books, boolean isIssued) {
        this.id = id;
        this.books = books;
        this.isIssued = isIssued;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @XmlElement
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
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
        return books.equals(that.books);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + books.hashCode();
        result = 31 * result + (isIssued ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", books=" + books +
                ", isIssued=" + isIssued +
                '}';
    }
}
