package com.tq.demo.quote;

import com.tq.event.EventEmitter;

import java.util.Random;

public class QuotePublishThread extends EventEmitter<SimpleQuote> {

    @Override
    public void run() {

        String[] symbols = new String[]{
                "A", "B", "C", "D", "E"
        };

        int stock = 0;
        Random random = new Random();
        random.setSeed(System.nanoTime());

        while (true) {
            try {
                emit(new SimpleQuote(symbols[stock], random.nextDouble() * 100));
                stock = (stock + 1) % symbols.length;
                Thread.sleep(5);
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
