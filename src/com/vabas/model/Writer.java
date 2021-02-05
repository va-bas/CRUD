package com.vabas.model;

import java.util.ArrayList;

public class Writer {
    //cash
    public static ArrayList<Writer> writerList;

    private int id;
    private String firstName;
    private String lastName;
    private ArrayList<Post> postsList;

    public Writer() {

    }

    public Writer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(int id, String firstName, String lastName, ArrayList<Post> postsList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postsList = postsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Post> getPostsList() {
        return postsList;
    }

    public void setPostsList(ArrayList<Post> postsList) {
        this.postsList = postsList;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postsList=" + postsList +
                '}';
    }

}
