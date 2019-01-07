package com.tq.demo.quote;

import com.tq.demo.quote.SimpleQuote;

import java.util.Comparator;

public class SimpleQuoteComparator implements Comparator<SimpleQuote> {
    public int compare(SimpleQuote left, SimpleQuote right) {
        if (left == right) {
            return 0;
        }

        if (left.price > right.price) {
            return -1;
        }

        if (left.price == right.price && left.symbol.compareTo(right.symbol) < 0) {
            return -1;
        }

        if (left.price == right.price && left.symbol.equals(right.symbol)) {
            return 0;
        }

        return 1;
    }

    public boolean equals(Object o) {
        return true;
    }
}
