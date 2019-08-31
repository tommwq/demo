package com.tq.applogmanagement.storage;

import java.io.IOException;

/**
 * Block storage.
 */
public interface BlockStorage {
    void open() throws IOException;

    int blockSize();

    int blockCount();

    byte[] read(int blockNumber) throws IOException;

    void write(int blockNumber, byte[] data) throws IOException;

    void close() throws IOException;
}
