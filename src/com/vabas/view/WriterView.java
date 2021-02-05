package com.vabas.view;

import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.PostStatus;
import com.vabas.model.Writer;

import java.util.ArrayList;

public class WriterView {
    public static final String dell = "This writer was deleted";

    public static void show(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Choose an action with writer:\n" +
                " 1. Create\n" +
                " 2. Edit name\n" +
                " 3. Delete\n" +
                " 4. Show writer by id\n" +
                " 5. Create or edit posts list for writer by ID\n" +
                " 6. Main menu\n" +
                " 7. Exit";
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showFirsName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter first name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showLastName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter last name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showCreateList(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Add post in posts list for writer ?\n" +
                " 1. Yes\n" +
                " 2. No" ;
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void postsListForWriter(int maxId){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("You can add this Posts for this writer: ");
        Post.postList.stream().filter((a) ->
                !Post.postList.get(a.getId() - 1).getPostStatus().
                        equals(PostStatus.DELETED)).filter(
                (a) -> {
                    //этот фильтр проверяет есть ли такой пост в списке постов для этого writer
                    for (int i = 0; i < Writer.writerList.get(maxId).getPostsList().size(); i++){
                        if (a.getId() == Writer.writerList.get(maxId).getPostsList().get(i).getId()){
                            return false;
                        }
                    }
                    return true;
                }
        )
                .forEach((a) -> System.out.println("Id: " + a.getId() +
                        "  Post content: " + a.getContent()));
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showWritersList(){
        System.out.println("Writers list: ");
        Writer.writerList.stream().filter((a) -> !a.getLastName().equals(WriterView.dell))
                .forEach((w) -> System.out.println("Id: " + w.getId() + "  Firstname: "
                        + w.getFirstName() + " Lastname: " + w.getLastName()));
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter writer id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showContainsPostsForWriter(int intId){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Writer contains this posts: ");
        if (Writer.writerList.get(intId - 1).getPostsList().isEmpty())
        {
            System.out.println("Posts list is empty");
        }
        else{
            Writer.writerList.get(intId - 1).getPostsList().stream().filter((a) ->
                    !Post.postList.get(a.getId() - 1).getPostStatus().
                            equals(PostStatus.DELETED))
                    .forEach((a) -> System.out.println("Id : " +
                            a.getId() + "  Content: " + a.getContent()));
        }
    }

    public static void showWriterAndPosts(int intId){
        Writer wr = Writer.writerList.get(intId - 1);
        System.out.println("Writer id: " + wr.getId() + "  First name: " +
                wr.getFirstName() + "  Last name: " + wr.getLastName());
        if (!wr.getPostsList().isEmpty() )
        {
                wr.getPostsList().stream().filter((a) ->
                        !Post.postList.get(a.getId() - 1).getPostStatus().
                                equals(PostStatus.DELETED)).forEach((s) -> System.out.print(
                        "--------------------------------\n" + "Post id: " +
                                s.getId() + "  Post content: " + s.getContent() +
                                "\nLabels list for this post: \n" + ifLabelsListIsEmpty(
                                Post.postList.get(s.getId() - 1).getPostLabelList())));
        }
        else{
            System.out.println("Posts list is empty");
        }
    }

    public static String ifLabelsListIsEmpty(ArrayList<Label> arr){
        String str = "";
        if (!arr.isEmpty()){
            for (Label label : arr) {
                if (!label.getName().equals(LabelView.dell))
                str += "Id: " + label.getId() + " Name: "
                        + label.getName() + "\n";
            }
            return str;
        }
        else{
            return "Labels is empty\n";
        }
    }

}
