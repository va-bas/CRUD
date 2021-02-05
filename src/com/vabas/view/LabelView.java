package com.vabas.view;

import com.vabas.model.Label;

public class LabelView {
    public static final String dell = "This label was deleted";

    public static void show(){
            System.out.println(ForConsole.BORDER.getMessage());
            String mainMessage = "Choose an action with labels:\n" +
                    " 1. Create\n" +
                    " 2. Edit\n" +
                    " 3. Delete\n" +
                    " 4. Show labels\n" +
                    " 5. Main menu\n" +
                    " 6. Exit";
            System.out.println(mainMessage);
            System.out.println(ForConsole.BORDER.getMessage());

    }

    public static void create(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter label name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter label id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter new label name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showLabelsList(){
        System.out.println("Labels list:");
        Label.labelList.stream().filter((a) -> !a.getName().equals(LabelView.dell)).
                forEach((a) -> System.out.println("Id: " + a.getId() + "  Name: " + a.getName()));
    }
}
