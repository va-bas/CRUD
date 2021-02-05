package com.vabas;

import com.vabas.controller.MainController;
import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.Writer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        //Create empty lists (not null)
        Label.labelList = new ArrayList<>();
        Post.postList = new ArrayList<>();
        Writer.writerList = new ArrayList<>();
        //Run main controller
        MainController.startM();
    }
}
