package com.tq.event;

import com.tq.container.RingBuffer;

import java.util.ArrayList;

public abstract class DualEventConsumer<In1, In2> implements Runnable {
    RingBuffer<In1> inputBuffer1;
    RingBuffer<In2> inputBuffer2;

    public DualEventConsumer setInput1(RingBuffer<In1> inputBuffer1) {
        this.inputBuffer1 = inputBuffer1;
        return this;
    }

    public DualEventConsumer setInput2(RingBuffer<In2> inputBuffer2) {
        this.inputBuffer2 = inputBuffer2;
        return this;
    }

    public void run() {
        int pos1 = inputBuffer1.position();
        int pos2 = inputBuffer2.position();


        while (true) {
            while (pos1 == inputBuffer1.position() && pos2 == inputBuffer2.position()) {
            }

            while (pos1 != inputBuffer1.position()) {
                ArrayList<In1> batches = new ArrayList();
                pos1 = inputBuffer1.fetch(pos1, batches);

                for (In1 input : batches) {
                    onEvent1(input);
                }
            }

            while (pos2 != inputBuffer2.position()) {
                ArrayList<In2> batch = new ArrayList();
                pos2 = inputBuffer2.fetch(pos2, batch);

                for (In2 input : batch) {
                    onEvent2(input);
                }
            }
        }
    }

    public abstract void onEvent1(In1 event);

    public abstract void onEvent2(In2 event);
}
