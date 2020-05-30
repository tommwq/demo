package com.tq.string;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    public int compare(String obj1, String obj2) {
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return obj1.compareTo(obj2);
    }
}
