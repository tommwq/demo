package com.tq.number;

import java.util.Comparator;

public class NumberComparator<T extends Number> implements Comparator<T> {
    public int compare(T obj1, T obj2) {
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null || obj1.doubleValue() < obj2.doubleValue()) {
            return -1;
        }
        return 1;
    }
}
