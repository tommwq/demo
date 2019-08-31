package com.tq.applogmanagement.storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

public class SimpleBlockStorage implements BlockStorage {
  private Path filePath;
  private int fileLength;
  private RandomAccessFile randomAccessFile;
  private int blockCount;
  private int blockSize;

  public SimpleBlockStorage(Path aFilePath, int aBlockCount, int aBlockSize) {
    filePath = aFilePath;
    blockCount = aBlockCount;
    blockSize = aBlockSize;
    fileLength = blockCount * blockSize;
  }

  /**
   * Open or create a storage file.
   */
  @Override
  public void open() throws IOException {
    File file = filePath.toFile();
    if (file.exists() && file.length() != fileLength) {
      throw new RuntimeException("invalid storage file");
    }

    blockCount = fileLength / blockSize();
    if (file.exists()) {
      randomAccessFile = new RandomAccessFile(file, "rw");
      return;
    }

    if (!file.createNewFile()) {
      throw new RuntimeException("cannot create file");
    }

    byte[] blank = new byte[fileLength];
    randomAccessFile = new RandomAccessFile(file, "rw");
    randomAccessFile.write(blank, 0, fileLength);
  }

  @Override
  public int blockSize() {
    return blockSize;
  }

  @Override
  public int blockCount() {
    return blockCount;
  }

  /**
   * Calculate block offset.
   */
  private int getBlockOffset(int blockNumber) {
    if (blockNumber < 0 || blockNumber >= blockCount) {
      throw new IllegalArgumentException("invalid block number: " + blockNumber);
    }

    return blockNumber * blockSize();
  }

  /**
   * Read a block.
   */
  @Override
  public byte[] read(int blockNumber) throws IOException {
    randomAccessFile.seek(getBlockOffset(blockNumber));
    byte[] data = new byte[blockSize()];
    randomAccessFile.read(data);
    return data;
  }

  /**
   * Write a block.
   */
  @Override
  public void write(int blockNumber, byte[] data) throws IOException {
    randomAccessFile.seek(getBlockOffset(blockNumber));
    randomAccessFile.write(Arrays.copyOf(data, blockSize()));
  }

  @Override
  public void close() throws IOException {
    if (randomAccessFile != null) {
      randomAccessFile.close();
    }
  }
}
