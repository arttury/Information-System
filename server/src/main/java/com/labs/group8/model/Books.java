package com.labs.group8.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
public class Books implements Serializable {

    @XmlElement(name = "books")
    private List<Book> book = null;

    public List<Book> getBookList() {
        return book;
    }

    public void setBookList(List<Book> bookList) {
        this.book = bookList;
    }

    @Override
    public String toString() {
        return "Books{" +
                "book=" + book +
                '}';
    }
}
