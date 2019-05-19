package com.labs.group8.client.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "bookInstances")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookInstances implements Serializable {

    @XmlElement(name = "bookInstance")
    private List<BookInstance> list = null;

    public List<BookInstance> getList() {
        return list;
    }

    public void setList(List<BookInstance> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BookInstances{" +
                "list=" + list +
                '}';
    }
}
