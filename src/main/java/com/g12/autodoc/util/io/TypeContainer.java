package com.g12.autodoc.util.io;

/**
 * @Author sjx
 * @Classname TypeContainer
 * @Description TODO
 * @Date 2019/7/7 19:27
 */
public class TypeContainer<T> {

    private T value ;

    public TypeContainer(T value){}

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
