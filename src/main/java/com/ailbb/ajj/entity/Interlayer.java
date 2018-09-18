package com.ailbb.ajj.entity;

/**
 * 数据处理过程中的中间层
 * Created by Wz on 9/14/2018.
 */
public interface Interlayer<T> {
    T doAs(Object... objects);
}
