package com.vabas.repository.impl;

import com.vabas.model.Post;
import com.vabas.model.Writer;
import com.vabas.repository.WriteAndRead;
import com.vabas.repository.WriterRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class WriterRepositoryImpl implements WriterRepository {
    File fileName = new File("./src/com/vasbas/resource/Writers.txt");
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

    @Override
    public Writer convertStringToEntity(String string) {
        String[] inpStrArr = string.split("%%@33//");
        return new Writer(Integer.parseInt(inpStrArr[0]), inpStrArr[1], inpStrArr[3],
                convArrStringToArrLabel(labelRepository.convertStringToArray(inpStrArr[2])));
    }

    @Override
    public String convertEntityToString(Writer writer) {
        ArrayList<String> postsArray = new ArrayList<>();
        //Здесь произходит преобразование списка постов в
        //строку с разделителем
        String res = "";
        if (writer.getPostsList() != null) {
            writer.getPostsList().stream().forEach(
                    (a) -> postsArray.add(convertPostToStringForWriter(a)));
            res = labelRepository.convertArrayToString(postsArray);
        }
        return writer.getId() + "%%@33//" + writer.getFirstName() +
                "%%@33//" + res + "%%@33//" + writer.getLastName();
    }

    @Override
    public void writeFullEntityToFile() throws FileNotFoundException {
        readFullEntityFromFile();
        Writer.writerList.forEach((a) -> writeEntityToFile(convertEntityToString(a)));
    }

    @Override
    public void readFullEntityFromFile() throws FileNotFoundException {
        Stream<Writer> writerStream = readEntityFromFile().stream().map((a) ->
                new Writer(convertStringToEntity(a).getId(),
                        convertStringToEntity(a).getFirstName(),
                        convertStringToEntity(a).getLastName(),
                        convertStringToEntity(a).getPostsList()));
        writerStream.forEach((a) -> Writer.writerList.add(a));
    }

    //Получаем пост из строки которую получим из файла для
    //списка постов у врайтера ("/%@77@%//" разделитель для
    // id и content поста в списке постов у писателя)
    public static Post convertStringToPostForWriter(String string) {
        String[] inpStrArr = string.split("/%@77@%//");
        return new Post(Integer.parseInt(inpStrArr[0]), inpStrArr[1]);
    }

    //Получим строку с разделителем из поста
    public static String convertPostToStringForWriter(Post post) {
        return post.getId() + "/%@77@%//" + post.getContent();
    }

    public static ArrayList<Post> convArrStringToArrLabel(ArrayList<String> as){
        ArrayList<Post> al = new ArrayList<>();
        as.stream().filter((q) -> !q.equals("")).forEach((a) ->
                al.add(convertStringToPostForWriter(a)));
        return al;

    }

    public static <T> void getListFromFile(ArrayList<T> t) throws FileNotFoundException {
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        if(t.isEmpty()) {
            t = new ArrayList<>();
            writerRepository.readFullEntityFromFile();
        }
    }

    public boolean containsWriter(ArrayList<Post> p1, Post p2){
        AtomicBoolean p = new AtomicBoolean(false);
        p1.forEach((a) -> {
            if (a.getId() == p2.getId() && a.getContent().equals(p2.getContent())) {
                p.set(true);
            }
        });
        return p.get();
        /*
        for (int i = 0; i < p1.size(); i++){
            if (p1.get(i).getId() == p2.getId()){
                return true;
            }
        }
        return false;
         */
    }
}
