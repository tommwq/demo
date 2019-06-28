package com.tq.demo.quote;

import com.tq.container.Rank;
import com.tq.container.RingBuffer;
import com.tq.demo.quote.DisplayQuoteThread;

public class Main {

    public static void main(String[] args) throws Exception {

        RingBuffer<SimpleQuote> quoteBuffer = new RingBuffer(64 * 1024);
        RingBuffer<Rank<String, SimpleQuote>> rankBuffer = new RingBuffer<>(64 * 1024);
        RingBuffer<Long> timeBuffer = new RingBuffer<>(64 * 1024);


        Thread t1 = new Thread(new QuotePublishThread()
                .setOutput(quoteBuffer));
        Thread t2 = new Thread(new RankThread()
                .setInput(quoteBuffer)
                .setOutput(rankBuffer));
        Thread t3 = new Thread(new TimeTickThread()
                .setOutput(timeBuffer));
        Thread t4 = new Thread(new DisplayQuoteThread()
                .setInput1(timeBuffer)
                .setInput2(rankBuffer));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Hello World!");
    }
}


