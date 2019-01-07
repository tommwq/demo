package com.tq.demo.quote;

import com.tq.container.Rank;
import com.tq.event.EventProcessThread;

public class RankThread extends EventProcessThread<SimpleQuote, Rank<String, SimpleQuote>> {

    private Rank<String, SimpleQuote> rank = new Rank<>(new SimpleQuoteComparator());

    @Override
    public Rank<String, SimpleQuote> process(SimpleQuote q) {
        rank.update(q.symbol, q);
        return rank.copy();
    }
}
