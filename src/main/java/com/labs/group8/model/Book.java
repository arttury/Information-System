package com.labs.group8.model;

import com.labs.group8.utils.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement
public class Book implements Serializable {
    private String authors;
    private String title;
    private LocalDate date;
    private int pages;

    public Book() {
    }

    public Book(String authors, String title, LocalDate date, int pages) {
        this.authors = authors;
        this.title = title;
        this.date = date;
        this.pages = pages;
    }

    public String getAuthors() {
        return authors;
    }

    @XmlElement
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPages() {
        return pages;
    }

    @XmlElement
    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (pages != book.pages) return false;
        if (!authors.equals(book.authors)) return false;
        if (!title.equals(book.title)) return false;
        return date.equals(book.date);
    }

    @Override
    public int hashCode() {
        int result = authors.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + pages;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", pages=" + pages +
                '}';
    }
}
