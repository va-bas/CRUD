package com.vabas.repository;

import java.util.ArrayList;

public class BooleanRepository {

    public static <T> int getMaxId(ArrayList<T> t){
        int maxId;
        if(t.isEmpty()){
            maxId = 0;
        }
        else {
            maxId = t.size();
        }
        return maxId;
    }

}
