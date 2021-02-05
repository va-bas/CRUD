package com.vabas.view;

import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.PostStatus;

public class PostView {
    public static final String dell = "This post was deleted";
    public static void show(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Choose an action with posts:\n" +
                " 1. Create\n" +
                " 2. Edit content\n" +
                " 3. Delete\n" +
                " 4. Show post by id\n" +
                " 5. Create or edit labels list for post by ID\n" +
                " 6. Main menu\n" +
                " 7. Exit";
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showCreateList(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Add label in labels list for post ?\n" +
                " 1. Yes\n" +
                " 2. No" ;
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());

    }

    public static void create(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter post content: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void idNotEx(){
        System.out.println("This Id not exist");
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter post id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter new post content: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void errNumb(){
        System.out.println("It's not a number !!!");
    }

    public static void labelsListForPost(int maxId){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("You can add this Labels for this post: ");
        Label.labelList.stream().filter((a) -> !a.getName().equals(LabelView.dell)).filter(
                (a) -> {
                    //этот фильтр проверяет есть ли такой лейбл в списке лейблов для этого поста
                    for (int i = 0; i < Post.postList.get(maxId).getPostLabelList().size(); i++){
                        if (a.getId() == Post.postList.get(maxId).getPostLabelList().get(i).getId()){
                            return false;
                        }
                    }
                    return true;
                }
        )
                .forEach((a) -> System.out.println("Id: " + a.getId() +
                        "  Label name: " + a.getName()));
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showPostsList(){
        System.out.println("Posts list: ");
        Post.postList.stream().filter((a) -> !a.getPostStatus().equals(PostStatus.DELETED))
                .forEach((w) -> System.out.println("Id: " + w.getId() + "  Content: " + w.getContent()));
    }

    public static void showLabelsListForPost(int intId){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Post contains this labels: ");
        Post.postList.get(intId - 1).getPostLabelList().stream().filter((a) ->
                !a.getName().equals(LabelView.dell))
                .forEach(System.out::println);
    }

    public static void showCancel(){
        //System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("press '0' for cancel");
        //System.out.println(ForConsole.BORDER.getMessage());
    }
}
