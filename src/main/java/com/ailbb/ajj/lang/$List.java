package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.lang.reflect.Array;
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

    public <T> Object[] toArray(Collection<T> lists){
        return listToArray(lists);
    }

    public <T> Object[] toArray(Collection<T> lists, T[] types){
        return listToArray(lists, types);
    }

    public <T> List<T> arrayToList(T[] arrays){
        return Arrays.asList(arrays);
    }

    public <T> Object[] listToArray(Collection<T> lists){
        return lists.toArray();
    }

    public <T> T[] listToArray(Collection<T> lists, T[] types){
        return lists.toArray(types);
    }

    /**
     * 包含开头，不包含结尾
     * @param ts
     * @param startIndex
     * @param endIndex
     * @param <T>
     * @return
     */
    public <T> T[] subCollection(T[] ts, int startIndex, int endIndex){
        return $.list.listToArray(arrayToList(ts).subList(startIndex, endIndex), newArray(ts));
    }

    /**
     * 拷贝一个新数组
     * @param reference
     * @param length
     * @param <T>
     * @return
     */
    public static <T> T[] newArray(T[] reference, int length) {
        Class<?> type = reference.getClass().getComponentType();
        return  (T[]) Array.newInstance(type, length);
    }

    /**
     * 拷贝一个新数组
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T[] newArray(T[] reference) {
        return newArray(reference, 0);
    }
}
