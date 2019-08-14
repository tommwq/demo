package com.tq.applogcollect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

public class BlockStorage {
  private Path filePath;
  private int fileLength;
  private RandomAccessFile randomAccessFile;
  private int blockCount;

  public BlockStorage(Path aFilePath, int aFileLength) {
    filePath = aFilePath;
    fileLength = aFileLength;
  }

  /**
   * Open or create a storage file.
   */
  public void open() throws IOException, FileNotFoundException {
    File file = filePath.toFile();
    if (file.exists() && file.length() != fileLength) {
      throw new RuntimeException("invalid storage file");
    }

    blockCount = fileLength / blockSize();
    if (file.exists()) {
      return;
    }

    if (!file.createNewFile()) {
      throw new RuntimeException("cannot create file");
    }

    byte[] blank = new byte[fileLength];
    randomAccessFile = new RandomAccessFile(file, "rw");
    randomAccessFile.write(blank, 0, fileLength);
  }

  public int blockSize() {
    return 4096;
  }

  public int blockCount() {
    return blockCount;
  }

  /**
   * Calculate block offset.
   */
  private int getBlockOffset(int blockNumber) {
    if (blockNumber < 0 || blockNumber >= blockCount) {
      throw new IllegalArgumentException("invalid block number");
    }

    return blockNumber * blockSize();
  }

  /**
   * Read a block.
   */
  public byte[] read(int blockNumber) throws IOException {
    randomAccessFile.seek(getBlockOffset(blockNumber));
    byte[] data = new byte[blockSize()];
    randomAccessFile.read(data);
    return data;
  }

  /**
   * Write a block.
   */
  public void write(int blockNumber, byte[] data) throws IOException {
    randomAccessFile.seek(getBlockOffset(blockNumber));
    randomAccessFile.write(Arrays.copyOf(data, blockSize()));
  }

  public void close() throws IOException {
    if (randomAccessFile != null) {
      randomAccessFile.close();
    }
  }
}
