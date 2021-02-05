package com.vabas.repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WriteAndRead {


    public static void WriteToFile(String data, File filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true)))
        {
            bw.write(data);
            bw.newLine();
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    //Id=3:/@$%null

    public static ArrayList<String> ReadFromFile(File filename) throws FileNotFoundException {
        ArrayList<String> myList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null)
            {
                myList.add(line);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return myList;
    }

}
