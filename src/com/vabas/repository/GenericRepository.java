package com.vabas.repository;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public interface GenericRepository<T> {
    void writeEntityToFile(String string);
    ArrayList<String> readEntityFromFile() throws FileNotFoundException;
    void clearFile() throws FileNotFoundException;
    T convertStringToEntity(String string) throws ParseException;
    String convertEntityToString(T t);
    void writeFullEntityToFile() throws FileNotFoundException;
    void readFullEntityFromFile() throws FileNotFoundException;
}
