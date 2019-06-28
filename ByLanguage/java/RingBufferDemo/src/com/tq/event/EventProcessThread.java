package com.tq.event;

import com.tq.container.RingBuffer;

import java.util.ArrayList;


public abstract class EventProcessThread<In, Out> implements Runnable {

    RingBuffer<In> inputBuffer;
    RingBuffer<Out> outputBuffer;

    public EventProcessThread setInput(RingBuffer<In> inputBuffer) {
        this.inputBuffer = inputBuffer;
        return this;
    }

    public EventProcessThread setOutput(RingBuffer<Out> OutputBuffer) {
        this.outputBuffer = OutputBuffer;
        return this;
    }

    public void run() {
        int pos = inputBuffer.position();

        while (true) {
            while (pos != inputBuffer.position()) {
                ArrayList<In> batch = new ArrayList();
                pos = inputBuffer.fetch(pos, batch);

                for (In input : batch) {
                    Out output = process(input);
                    if (output != null) {
                        outputBuffer.update(output);
                    }
                }
            }
        }
    }

    public abstract Out process(In input);
}
