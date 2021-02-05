package com.vabas.view;

public class MainMenu {

    public static void startM(){
        System.out.println(ForConsole.BORDER.getMessage());
        String menuMessage = "Choose an action with:\n" +
                "1. Labels\n" +
                "2. Writers\n" +
                "3. Posts\n" +
                "4. Exit program";
        System.out.println(menuMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }
}
