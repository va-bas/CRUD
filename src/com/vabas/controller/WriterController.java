package com.vabas.controller;

import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.PostStatus;
import com.vabas.model.Writer;
import com.vabas.repository.BooleanRepository;
import com.vabas.repository.impl.LabelRepositoryImpl;
import com.vabas.repository.impl.PostRepositoryImpl;
import com.vabas.repository.impl.WriterRepositoryImpl;
import com.vabas.view.ForConsole;
import com.vabas.view.PostView;
import com.vabas.view.WriterView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WriterController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            WriterView.show();
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
                    addListElementsForWriter();
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
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        try {
            WriterView.showFirsName();
            String firstName = sc.next();
            //Получить maxId, если список пустой то maxId = 0
            int maxId = BooleanRepository.getMaxId(Writer.writerList);
            Writer.writerList.add(new Writer(maxId + 1, firstName, ""));
            WriterView.showLastName();
            String lastName = sc.next();
            Writer.writerList.get(maxId).setLastName(lastName);

            Writer.writerList.get(maxId).setPostsList(new ArrayList<>());
            WriterView.showCreateList();
            boolean tmpB = false;
            do {
                String response = sc.next();
                //Случай когда список пустой
                switch (response) {
                    case "1" -> {
                        //Получить список из файла если это еще не делалось
                        PostRepositoryImpl.getListFromFile(Post.postList);
                        //Показать список Posts доступных для добавления
                        WriterView.postsListForWriter(maxId);
                        PostView.editId();
                        String newId = sc.next();
                        int intId = Integer.parseInt(newId.trim());
                        if (Post.postList.size() >= intId && intId > 0 &&
                                !Writer.writerList.get(maxId).getPostsList().
                                        contains(Post.postList.get(intId - 1))
                        && !Post.postList.get(intId - 1).getPostStatus().equals(PostStatus.DELETED)) {
                            Writer.writerList.get(maxId).getPostsList().add(Post.postList.get(intId - 1));
                            WriterView.showCreateList();
                        } else {
                            PostView.idNotEx();
                            WriterView.showCreateList();
                        }
                    }
                    case "2" -> {
                        tmpB = true;
                    }
                    default -> System.out.println(ForConsole.ERROR_INPUT.getMessage());
                }
            }while (!tmpB);
            //Add in file just 1 string, not to rewrite all file
            writerRepository.writeEntityToFile(writerRepository.
                    convertEntityToString(Writer.writerList.get(maxId)));
        }catch (NumberFormatException nfe){
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
    }

    public static void edit() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        try{
            int intId;
            do {
                //Список постов
                WriterView.showWritersList();
                WriterView.editId();
                String newId = sc.next();
                intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxWriterId = BooleanRepository.getMaxId(Writer.writerList);
                if(maxWriterId >= intId && intId > 0){
                    if (!Writer.writerList.get(intId - 1).getLastName().equals(WriterView.dell)) {
                        //edit body
                        //edit name
                        System.out.println("Enter new firstname: ");
                        String firstname = sc.next();
                        Writer.writerList.get(intId - 1).setFirstName(firstname);
                        System.out.println("Enter new lastname: ");
                        String lastname = sc.next();
                        Writer.writerList.get(intId - 1).setLastName(lastname);
                        //edit Label list for this post
                        //можно объединить edit для контента и списка лейблов
                        //в одном пункте меню
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Writer was deleted");
                    }
                }
                else {
                    PostView.idNotEx();
                }
            }while (!tmpBool);
            //Отчистка файла
            writerRepository.clearFile();
            //Запись в файл нового списка
            writerRepository.writeFullEntityToFile();
        }catch (NumberFormatException nfe)
        {
            System.out.println("It's not a number !!!");
        }
    }

    public static void delete() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        //System.out.println(Writer.writerList.toString());
        try {
            do {
                WriterView.showWritersList();
                WriterView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxWriterId = BooleanRepository.getMaxId(Writer.writerList);
                if(maxWriterId >= intId && intId > 0){
                    if (!Writer.writerList.get(intId - 1).getLastName().equals(WriterView.dell))
                    {
                        Writer.writerList.get(intId - 1).setLastName(WriterView.dell);
                        //Очистка файла
                        writerRepository.clearFile();
                        //Запись в файл нового списка
                        writerRepository.writeFullEntityToFile();
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Writer was deleted");
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
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        PostRepositoryImpl.getListFromFile(Post.postList);
        try {
            do {
                //Writers list
                WriterView.showWritersList();
                WriterView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                //Получить maxId, если список пустой то maxId = 0
                int maxWrId = BooleanRepository.getMaxId(Writer.writerList);
                if(maxWrId >= intId && intId > 0){
                    if (!Writer.writerList.get(intId - 1).getLastName().equals(WriterView.dell)) {
                        WriterView.showWriterAndPosts(intId);
                        tmpBool = true;
                    }
                    else {
                        System.out.println("This Writer was deleted");
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

    public static void addListElementsForWriter() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        PostRepositoryImpl.getListFromFile(Post.postList);
        LabelRepositoryImpl.getListFromFile(Label.labelList);
        try {
            do {
                //Получить maxId, если список пустой то maxId = 0
                int maxWriterId = BooleanRepository.getMaxId(Writer.writerList);
                //Writers list
                WriterView.showWritersList();
                WriterView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                if(maxWriterId >= intId && intId > 0 &&
                !Writer.writerList.get(intId - 1).getLastName().equals(WriterView.dell)){
                    //Show posts list for this writer
                    WriterView.showContainsPostsForWriter(intId);
                    //You can add this posts for writer
                    WriterView.postsListForWriter(intId - 1);
                    //Create posts list for this writer
                    Writer.writerList.get(intId - 1).setPostsList(addListPostsForWriterById(intId));
                    //Отчистка файла
                    writerRepository.clearFile();
                    //Запись в файл нового списка
                    writerRepository.writeFullEntityToFile();
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

    public static ArrayList<Post> addListPostsForWriterById(int wrId) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        boolean tmpBool = false;
        //Получить список из файла если это еще не делалось
        WriterRepositoryImpl.getListFromFile(Writer.writerList);
        PostRepositoryImpl.getListFromFile(Post.postList);
        //Если считался null создать экземпляр
        if (Writer.writerList.get(wrId - 1).getPostsList() == null){
            Writer.writerList.get(wrId - 1).setPostsList(new ArrayList<>());
        }
        try {
            do {
                PostView.showCancel();
                PostView.editId();
                String newId = sc.next();
                int intId = Integer.parseInt(newId.trim());
                if (Post.postList.size() >= intId && intId > 0 &&
                        //проверка содержится ли пост в списке для этого writer
                        !writerRepository.containsWriter(
                                Writer.writerList.get(wrId - 1).getPostsList(),
                                Post.postList.get(intId - 1))
                        /*!Writer.writerList.get(wrId - 1).getPostsList().
                                contains(Label.labelList.get(intId - 1))*/
                        && !Post.postList.get(intId - 1).getPostStatus().
                        equals(PostStatus.DELETED))
                {
                    //--------------------------------------------
                    Writer.writerList.get(wrId - 1).getPostsList().
                            add(Post.postList.get(intId - 1));
                    return Writer.writerList.get(wrId - 1).getPostsList();
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
