package com.vabas.controller;

import com.vabas.model.Label;
import com.vabas.repository.BooleanRepository;
import com.vabas.repository.impl.LabelRepositoryImpl;
import com.vabas.view.ForConsole;
import com.vabas.view.LabelView;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LabelController {

    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            LabelView.show();
            String response = sc.next();
            switch (response) {
                case "1":
                    create();
                    break;
                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    showAll();
                    break;
                case "5":
                    isExit = true;
                    MainController.startM();
                    break;
                case "6":
                    isExit = true;
                    break;
                default:
                    System.out.println(ForConsole.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
        sc.close();
    }

    public static void create() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        LabelRepositoryImpl labelsRepository = new LabelRepositoryImpl();
        //Получить список из файла если это еще не делалось
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        LabelView.create();
        String res = sc.next();
        //Получить maxId, если список пустой то maxId = 0
        int maxId = BooleanRepository.getMaxId(Label.labelList);
        Label tmpLabel = new Label(maxId + 1, res);
        Label.labelList.add(tmpLabel);
        //Not rewrite all file just add one string
        labelsRepository.writeEntityToFile(labelsRepository.convertEntityToString(tmpLabel));
    }

    public static void edit() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        LabelRepositoryImpl labelsRepository = new LabelRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        LabelView.showLabelsList();
        try{
            do {
            LabelView.editId();
            String id = sc.next();
            //именно здесь String преобразуется в int
            int intId = Integer.parseInt(id.trim());
            //Check was this label deleted or not and
            //check does label id contain in labels list
            if(intId <= Label.labelList.size() && intId > 0 &&
                    !Label.labelList.get(intId - 1).getName().equals(LabelView.dell)) {
                LabelView.editName();
                String name = sc.next();
                Label.labelList.get(intId - 1).setName(name);
                //Отчистка файла
                labelsRepository.clearFile();
                //запись в файл нового списка
                labelsRepository.writeFullEntityToFile();
                tmpBool = true;
                }
            else{
                System.out.println("This Id not exist");
                }
            }while (!tmpBool);
        }
        catch (NumberFormatException nfe){
            System.out.println("It's not a number !!!");
        }
    }

    public static void delete() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        LabelRepositoryImpl labelsRepository = new LabelRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        try {
            do {
                LabelView.showLabelsList();
                LabelView.editId();
                String id = sc.next();
                //именно здесь String преобразуется в int
                int intId = Integer.parseInt(id.trim());
                if(intId <= Label.labelList.size() && intId > 0 &&
                !Label.labelList.get(intId - 1).getName().equals(LabelView.dell)){
                    Label.labelList.get(intId - 1).setName(LabelView.dell);
                    //Отчистка файла
                    labelsRepository.clearFile();
                    //запись в файл нового списка
                    labelsRepository.writeFullEntityToFile();
                    tmpBool = true;
                }
                else{
                    System.out.println("This Id not exist");
                }
            }while (!tmpBool);
        }
        catch (NumberFormatException nfe){
            System.out.println("It's not a number !!!");
        }
    }

    public static void showAll() throws FileNotFoundException {
        //Получить список из файла если это еще не делалось
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        LabelView.showLabelsList();
    }
    //------------------------------------------------------------
    /*public static void showById() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        LabelRepositoryImpl labelsRepository = new LabelRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        try {
            do {
                LabelView.editId();
                String id = sc.next();
                // именно здесь String преобразуется в int
                int intId = Integer.parseInt(id.trim());
                if(intId <= Label.labelList.size() && intId > 0){
                    if(!Label.labelList.get(intId - 1).getName().equals(LabelView.dell)) {
                        System.out.println(Label.labelList.get(intId - 1));
                        tmpBool = true;
                    }
                    else{
                        System.out.println(LabelView.dell);
                    }
                }
                else{
                    System.out.println("This Id not exist");
                }
            }while (!tmpBool);
        }
        catch (NumberFormatException nfe){
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
    }*/
}
