package com.ailbb.ajj.lang;

import java.util.ArrayList;
import java.util.List;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $List {

    public List<Integer> indexOfList(String r, String str) {
        List<Integer> li = new ArrayList<>();

        if(isEmptyOrNull(str)) return li;

        int i = -1;

        while((i= str.indexOf(r)) != -1) {
            li.add(i + (li.size() > 0 ? li.get(li.size()-1) + 1 : 0));
            str =  str.substring(i + 1, str.length());
        }

        return li;
    }

}
