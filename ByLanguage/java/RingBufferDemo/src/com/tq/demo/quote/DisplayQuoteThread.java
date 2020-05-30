package com.tq.demo.quote;

import com.tq.container.Rank;
import com.tq.event.DualEventConsumer;

public class DisplayQuoteThread extends DualEventConsumer<Long, Rank<String, SimpleQuote>> {
    Rank<String, SimpleQuote> rank = null;

    @Override
    public void onEvent1(Long timestamp) {
        if (rank == null) {
            return;
        }

        rank.rank();
        System.out.println(rank);
    }

    @Override
    public void onEvent2(Rank<String, SimpleQuote> rank) {
        this.rank = rank;
    }
}

