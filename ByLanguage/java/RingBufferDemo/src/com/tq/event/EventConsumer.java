package com.tq.event;

import com.tq.container.RingBuffer;

import java.util.ArrayList;

public abstract class EventConsumer<T> implements Runnable {
    RingBuffer<T> inputBuffer;

    public EventConsumer setInput(RingBuffer<T> inputBuffer) {
        this.inputBuffer = inputBuffer;
        return this;
    }

    public void run() {
        int pos = inputBuffer.position();

        while (true) {
            while (pos == inputBuffer.position()) {
            }

            if (pos != inputBuffer.position()) {
                ArrayList<T> batch = new ArrayList();
                pos = inputBuffer.fetch(pos, batch);

                for (T input : batch) {
                    onEvent(input);
                }
            }
        }
    }

    abstract void onEvent(T event);
}

