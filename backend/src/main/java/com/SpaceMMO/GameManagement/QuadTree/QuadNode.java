package com.SpaceMMO.GameManagement.QuadTree;

//See https://github.com/alwex/QuadTree/blob/master/src/main/java/com/alwex/tree/QuadTree.java

/**
 * Created by aguiet on 28/05/2015.
 */
public class QuadNode<T> {
    QuadRectangle r;
    T element;

    QuadNode(QuadRectangle r, T element) {
        this.r = r;
        this.element = element;
    }

    @Override
    public String toString() {
        return r.toString();
    }
}