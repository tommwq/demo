package com.tq.event;

import com.tq.container.RingBuffer;

public abstract class EventEmitter<T> implements Runnable {
    private RingBuffer<T> outputBuffer;

    public EventEmitter setOutput(RingBuffer<T> outputBuffer) {
        this.outputBuffer = outputBuffer;
        return this;
    }

    public EventEmitter emit(T event) {
        outputBuffer.update(event);
        return this;
    }
}
