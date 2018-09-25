package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.util.*;

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

    public <T> List<T> collectionToList(Collection<T> collections){
        List<T> list = new ArrayList<T>();
        for(T o : collections) list.add(o);
        return list;
    }

    public <T> List<T> setToList(Set<T> sets){
        return collectionToList(sets);
    }

    public <T> List<T> toList(Collection<T> collections){
        return collectionToList(collections);
    }

    public <T> T[] toArray(Collection<T> lists){
        return listToArray(lists);
    }

    public <T> List<T> arrayToList(T[] arrays){
        return Arrays.asList(arrays);
    }

    public <T> T[] listToArray(Collection<T> lists){
        return (T[])lists.toArray();
    }
}
