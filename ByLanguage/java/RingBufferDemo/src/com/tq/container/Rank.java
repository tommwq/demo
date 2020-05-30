package com.tq.container;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Rank<K, V> {

    Comparator<V> vComparator;
    HashMap<K, V> items = new HashMap<>();

    ArrayList<V> rankResult = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (V v : rankResult) {
            b.append(v.toString())
                    .append("\n");
        }
        return b.toString();
    }

    public void rank() {
        rankResult.addAll(items.values());
        rankResult.sort(vComparator);
    }

    public Rank<K,V> copy() {
        Rank<K,V> c = new Rank<>(vComparator);
        c.items.putAll(items);
        return c;
    }

    public Rank(Comparator<V> vComparator) {
        this.vComparator = vComparator;
        items = new HashMap();
    }

    public void update(K k, V v) {
        items.put(k, v);
    }
}
