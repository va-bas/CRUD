package com.vabas.controller;

import com.vabas.view.ForConsole;
import com.vabas.view.MainMenu;

import java.util.Scanner;

public class MainController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            //Show view for main menu
            MainMenu.startM();
            String response = sc.next();
            switch (response) {
                case "1":
                    isExit = true;
                    LabelController.startM();
                    break;
                case "2":
                    WriterController.startM();
                    isExit = true;
                    break;
                case "3":
                    isExit = true;
                    PostController.startM();
                    break;
                case "4":
                    isExit = true;
                    break;
                default:
                    System.out.println(ForConsole.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
        sc.close();
    }
}
