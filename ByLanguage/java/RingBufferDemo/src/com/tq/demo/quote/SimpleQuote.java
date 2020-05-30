package com.tq.demo.quote;

public class SimpleQuote {
    public String symbol;
    public double price;

    public SimpleQuote(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", symbol, price);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SimpleQuote)) {
            return false;
        }

        return ((SimpleQuote) other).symbol.equals(symbol);
    }
}
