package com.vabas.controller;

import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.PostStatus;
import com.vabas.repository.BooleanRepository;
import com.vabas.repository.impl.LabelRepositoryImpl;
import com.vabas.repository.impl.PostRepositoryImpl;
import com.vabas.view.ForConsole;
import com.vabas.view.LabelView;
import com.vabas.view.PostView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PostController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            PostView.show();
            String resp = sc.next();
            switch (resp) {
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
                    show();
                    break;
                case "5":
                    addListElementsForPost();
                    break;
                case "6":
                    isExit = true;
                    MainController.startM();
                    break;
                case "7":
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
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        //Получить список из файла если это еще не делалось
        PostRepositoryImpl.getListFromFile(Post.postList);
        try {
                PostView.create();
                String content = sc.next();
                //Получить maxId, если список пустой то maxId = 0
                int maxId = BooleanRepository.getMaxId(Post.postList);
                Post.postList.add(new Post(maxId + 1, content));
                Post.postList.get(maxId).setPostLabelList(new ArrayList<>());
                PostView.showCreateList();
                boolean tmpB = false;
                do {
                    String response = sc.next();
                    //Случай когда список пустой
                    switch (response) {
                        case "1" -> {
                            //Получить список из файла если это еще не делалось
                            LabelRepositoryImpl.getListFromFile(Label.labelList);
                            //Показать список лейблов доступных для добавления
                            PostView.labelsListForPost(maxId);
                            LabelView.editId();
                            String newId = sc.next();
                            int intId = Integer.parseInt(newId.trim());
                            if (Label.labelList.size() >= intId && intId > 0 &&
                                    //проверка содержится ли Label c этим id в
                                    //списке labels для этого поста
                                    !Post.postList.get(maxId).getPostLabelList().
                                            contains(Label.labelList.get(intId - 1))
                                    //проверка удален ли лейбл в списке лейблов
                            && !Label.labelList.get(intId - 1).getName().equals(LabelView.dell)) {
                                Post.postList.get(maxId).getPostLabelList().add(Label.labelList.get(intId - 1));
                                PostView.showCreateList();
                            } else {
                                PostView.idNotEx();
                                PostView.showCreateList();
                            }
                        }
                        case "2" -> {
                            tmpB = true;
                        }
                        default -> System.out.println(ForConsole.ERROR_INPUT.getMessage());
                    }
                }while (!tmpB);
                //Заполняем дату создания и обновления
                Post.postList.get(maxId).setCreated(new Date().toString());
                Post.postList.get(maxId).setUpdated(Post.postList.get(maxId).getCreated());
                Post.postList.get(maxId).setPostStatus(PostStatus.ACTIVE);
                //Not rewrite all file for Posts just add one string
                postRepository.writeEntityToFile(postRepository.
                        convertEntityToString(Post.postList.get(maxId)));
        }catch (NumberFormatException nfe){
                System.out.println("NumberFormatException: " + nfe.getMessage());
            }
    }

    public static void delete() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        PostRepositoryImpl.getListFromFile(Post.postList);
        try {
            do {
                //Список постов
                PostView.showPostsList();
                PostView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxPostId = BooleanRepository.getMaxId(Post.postList);
                if(maxPostId >= intId && intId > 0){
                    if (!Post.postList.get(intId - 1).getPostStatus().equals(PostStatus.DELETED))
                    {
                        Post.postList.get(intId - 1).setPostStatus(PostStatus.DELETED);
                        Post.postList.get(intId - 1).setUpdated(new Date().toString());
                        //Очистка файла
                        postRepository.clearFile();
                        //Запись в файл нового списка
                        postRepository.writeFullEntityToFile();
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Post was deleted");
                    }
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
        }catch (NumberFormatException nfe)
        {
            System.out.println("It's not a number !!!");
        }
    }

    public static void show() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        PostRepositoryImpl.getListFromFile(Post.postList);
        try {
            do {
                //Список постов
                PostView.showPostsList();
                PostView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxPostId = BooleanRepository.getMaxId(Post.postList);
                if(maxPostId >= intId && intId > 0){
                    if (!Post.postList.get(intId - 1).getPostStatus().equals(PostStatus.DELETED)) {
                        System.out.println(Post.postList.get(intId - 1));
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Post was deleted");
                    }
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
        }catch (NumberFormatException nfe)
        {
            System.out.println("It's not a number !!!");
        }
    }

    public static void edit() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        PostRepositoryImpl.getListFromFile(Post.postList);
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        try {
            int intId;
            do {
                //Список постов
                PostView.showPostsList();
                PostView.editId();
                String newId = sc.next();
                intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxPostId = BooleanRepository.getMaxId(Post.postList);
                if(maxPostId >= intId && intId > 0){
                    if (!Post.postList.get(intId - 1).getPostStatus().equals(PostStatus.DELETED)) {
                        //edit body
                        //edit content
                        System.out.println("Enter new content: ");
                        String content = sc.next();
                        Post.postList.get(intId - 1).setContent(content);
                        //edit Label list for this post
                        //можно объединить edit для контента и списка лейблов
                        //в одном пункте меню
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Post was deleted");
                    }
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
            //Отчистка файла
            postRepository.clearFile();
            //Запись в файл нового списка
            Post.postList.get(intId - 1).setUpdated(new Date().toString());
            postRepository.writeFullEntityToFile();
        }catch (NumberFormatException nfe)
        {
            System.out.println("It's not a number !!!");
        }
    }

    public static void addListElementsForPost() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        PostRepositoryImpl.getListFromFile(Post.postList);
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        try {
            do {
                //Получить maxId, если список пустой то maxId = 0
                int maxPostId = BooleanRepository.getMaxId(Post.postList);
                //Список постов
                PostView.showPostsList();
                PostView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                if(maxPostId >= intId && intId > 0 &&
                !Post.postList.get(intId - 1).getPostStatus().equals(PostStatus.DELETED)){
                    //Вывод списка лейблов для этого поста
                    PostView.showLabelsListForPost(intId);
                    //Список лейблов которые можно добавить
                    PostView.labelsListForPost(intId - 1);
                    //Создаем список лейблов для поста
                    ArrayList<Label> tmpL = addListLabelsForPostByPostId(intId);
                    Post.postList.get(intId - 1).setPostLabelList(tmpL);
                    //Отчистка файла
                    postRepository.clearFile();
                    //Запись в файл нового списка
                    Post.postList.get(intId - 1).setUpdated(new Date().toString());
                    postRepository.writeFullEntityToFile();
                    tmpBool = true;
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
        }catch (NumberFormatException nfe)
        {
            System.out.println("It's not a number !!!");
        }
    }

    public static ArrayList<Label> addListLabelsForPostByPostId(int postId) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean tmpBool = false;
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        //Если считался null создать экземпляр
        if (Post.postList.get(postId - 1).getPostLabelList() == null){
            Post.postList.get(postId - 1).setPostLabelList(new ArrayList<>());
        }
        try {
            do {
                PostView.showCancel();
                LabelView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                if (Label.labelList.size() >= intId && intId > 0 &&
                        //---------------------------------------
                        /*!Post.postList.get(postId - 1).getPostLabelList().
                                contains(Label.labelList.get(intId - 1))*/

                        !postRepository.containsPost(Post.postList.get(postId - 1).
                                getPostLabelList(),Label.labelList.get(intId - 1))
                        //---------------------------------------
                && !Label.labelList.get(intId - 1).getName().equals(LabelView.dell))
                //----------------------------------------------------------
                {
                    Post.postList.get(postId - 1).getPostLabelList().
                            add(Label.labelList.get(intId - 1));
                    return Post.postList.get(postId - 1).getPostLabelList();
                }
                else if(intId == 0){
                    tmpBool = true;
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
        }catch (NumberFormatException nfe)
            {
                System.out.println("It's not a number !!!");
            }
        return null;
    }

}
