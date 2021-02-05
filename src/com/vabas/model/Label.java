package com.vabas.model;

import java.util.ArrayList;

public class Label {
    //cash labelsList
    public static ArrayList<Label> labelList;

    private int id;
    private String name;

    public Label() {
    }

    public Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Labels{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
