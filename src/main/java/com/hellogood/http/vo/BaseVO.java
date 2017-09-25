package com.hellogood.http.vo;

/**
 * Created by kejian on 2017/9/16.
 */
public abstract class BaseVO<T> {

    public abstract void domain2Vo(T t);

    public abstract void vo2Domain(T t);
}
