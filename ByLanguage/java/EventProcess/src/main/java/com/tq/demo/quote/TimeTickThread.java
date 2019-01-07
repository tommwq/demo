package com.tq.demo.quote;

import com.tq.event.EventEmitter;

public class TimeTickThread extends EventEmitter<Long> {

    public void run() {
        while (true) {
            try {
                emit(System.currentTimeMillis());
                Thread.sleep(1000);
            } catch (Exception e) {
                //ignore
            }
        }
    }
}

