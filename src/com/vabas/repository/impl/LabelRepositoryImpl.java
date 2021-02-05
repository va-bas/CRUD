package com.vabas.repository.impl;

import com.vabas.model.Label;
import com.vabas.repository.LabelRepository;
import com.vabas.repository.WriteAndRead;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class LabelRepositoryImpl implements LabelRepository {
    File fileName = new File("./src/com/vasbas/resource/Labels.txt");

    //IO operations with file
    @Override
    public void writeEntityToFile(String data) {
        WriteAndRead.WriteToFile(data, fileName);
    }

    @Override
    public ArrayList<String> readEntityFromFile() throws FileNotFoundException {
        return WriteAndRead.ReadFromFile(fileName);
    }

    @Override
    public void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    //Convert operations with entity to string (using splitter)
    @Override
    public Label convertStringToEntity(String string) {
        if (string != null && !string.equals("")) {
            String[] inpStrArr = string.split("@_/%/_@");
            return new Label(Integer.parseInt(inpStrArr[0]), inpStrArr[1]);
        }
        return null;
    }

    public Label convertStringToEntityPost(String string) {
                String[] inpStrArr = string.split("@_/%/_@");
                return new Label(Integer.parseInt(inpStrArr[0]), inpStrArr[1]);
           }

    @Override
    public String convertEntityToString(Label label) {
        return String.valueOf(label.getId()) + "@_/%/_@" + label.getName();
    }

    @Override
    public void writeFullEntityToFile() throws FileNotFoundException {
        readFullEntityFromFile();
        Label.labelList.forEach((a) -> writeEntityToFile(convertEntityToString(a)));
    }

    @Override
    public void readFullEntityFromFile() throws FileNotFoundException {
        Stream<Label> labelsStream = readEntityFromFile().stream().map((a) ->
                new Label(convertStringToEntity(a).getId(),
                        convertStringToEntity(a).getName()));
        labelsStream.forEach((a) -> Label.labelList.add(a));
    }

    public static <T> void getListFromFile(ArrayList<T> t) throws FileNotFoundException {
        LabelRepositoryImpl labelsRepository = new LabelRepositoryImpl();
        if(t.isEmpty()) {
            t = new ArrayList<>();
            labelsRepository.readFullEntityFromFile();
        }
    }

    //label1 = 1@_/%/_@name1
    //label2 = 2@_/%/_@name2 .....
    //labelsList = label1%@%%77//label2%@%%77//......
    //labelsList = 1@_/%/_@name1%@%%77//2@_/%/_@name2%@%%77//......
    //ArrayList<String> arrList = [label1, label2, ....]
    //arrList => String labelsList
    public String convertArrayToString(ArrayList<String> lists) {
        String res = "";
        if (lists.size() > 1) {
            for (int i = 0; i < lists.size() - 1; i++) {
                res += lists.get(i) + "%@%%77//";
            }
            res = res + lists.get(lists.size() - 1);
            return res;
        }
        else if(lists.size() == 1){
            res = lists.get(0);
        }
        return res;
    }

    //Str1%@%%77//Str2%@%%77//Str3
    //inpStrArr[0] = Str1, inpStrArr[1] = Str2, ......
    public ArrayList<String> convertStringToArray(String string) {
        ArrayList<String> strList = new ArrayList<>();
        String[] inpStrArr = string.split("%@%%77//");
        if (inpStrArr.length >= 2) {
            return new ArrayList<>(Arrays.asList(inpStrArr));
        }
        strList.add(string);
        return strList;
    }
}
