package com.hellogood.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kejian on 2017/9/19.
 */
public class Page<T> implements Serializable {

    private List<T> list;
    //当前页的数量
    private int size;

    public Page() {
    }

    public Page(List<T> list, int size) {
        this.list = list;
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Page{" +
                "list=" + list +
                ", size=" + size +
                '}';
    }
}
