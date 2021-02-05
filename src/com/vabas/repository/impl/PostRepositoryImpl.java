package com.vabas.repository.impl;

import com.vabas.model.Label;
import com.vabas.model.Post;
import com.vabas.model.PostStatus;
import com.vabas.repository.PostRepository;
import com.vabas.repository.WriteAndRead;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class PostRepositoryImpl implements PostRepository {
    File fileName = new File("./src/com/vasbas/resource/Posts.txt");
    LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();

    @Override
    public void writeEntityToFile(String data) {
        WriteAndRead.WriteToFile(data, fileName);
    }

    @Override
    public ArrayList<String> readEntityFromFile() throws FileNotFoundException {
        return WriteAndRead.ReadFromFile(fileName);
    }

    @Override
    public void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    //Строка из файла Posts.txt преобразуется в Post
    @Override
    public Post convertStringToEntity(String string) {
        String[] inpStrArr = string.split("%%@33//");
        return new Post(Integer.parseInt(inpStrArr[0]), inpStrArr[1], inpStrArr[2],
                inpStrArr[3],
                convArrStringToArrLabel(labelRepository.convertStringToArray(inpStrArr[4])),
                PostStatus.valueOf(inpStrArr[5]));
    }

    //Convert post to string for file
    @Override
    public String convertEntityToString(Post post) {
        ArrayList<String> labelsArray = new ArrayList<>();
        //Здесь произходит преобразование списка лейблов в
        //строку с разделителем
        String res = "";
        if (post.getPostLabelList() != null) {
            post.getPostLabelList().stream().forEach(
                    (a) -> labelsArray.add(labelRepository.convertEntityToString(a)));
            res = labelRepository.convertArrayToString(labelsArray);
        }
        return post.getId() + "%%@33//" + post.getContent() + "%%@33//" + post.getCreated()
                + "%%@33//" + post.getUpdated() + "%%@33//" + res
                + "%%@33//" + post.getPostStatus();
    }

    @Override
    public void writeFullEntityToFile() throws FileNotFoundException {
        readFullEntityFromFile();
        Post.postList.forEach((a) -> writeEntityToFile(convertEntityToString(a)));
    }

    @Override
    public void readFullEntityFromFile() throws FileNotFoundException {
        Stream<Post> postsStream = readEntityFromFile().stream().map((a) ->
                new Post(convertStringToEntity(a).getId(),
                        convertStringToEntity(a).getContent(), convertStringToEntity(a).getCreated(),
                        convertStringToEntity(a).getUpdated(), convertStringToEntity(a).getPostLabelList(),
                        convertStringToEntity(a).getPostStatus()));
        postsStream.forEach((a) -> Post.postList.add(a));
    }

    public static <T> void getListFromFile(ArrayList<T> t) throws FileNotFoundException {
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        if (t.isEmpty()) {
            t = new ArrayList<>();
            postRepository.readFullEntityFromFile();
        }
    }

    public static ArrayList<Label> convArrStringToArrLabel(ArrayList<String> as) {
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        ArrayList<Label> al = new ArrayList<>();
        as.stream().filter((q) -> !q.equals("")).forEach((a) ->
                al.add(labelRepository.convertStringToEntityPost(a)));
        return al;
    }

    public boolean containsPost(ArrayList<Label> p1, Label p2) {
        AtomicBoolean p = new AtomicBoolean(false);
        p1.forEach((a) -> {
            if (a.getId() == p2.getId() && a.getName().equals(p2.getName())) {
                p.set(true);
            }
            /*if (a.equals(p2)) {
                p.set(true);
            }*/
        });
        return p.get();
    }
}
