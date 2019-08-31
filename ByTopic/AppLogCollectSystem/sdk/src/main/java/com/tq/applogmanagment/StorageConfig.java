package com.tq.applogmanagement;

public class StorageConfig {
  private String fileName = "";
  private int blockCount = 16;
  private int blockSize = 4096;

  public int getBlockSize() {
    return blockSize;
  }

  public StorageConfig setBlockSize(int size) {
    blockSize = size;
    return this;
  }

  public int getBlockCount() {
    return blockCount;
  }

  public StorageConfig setBlockCount(int count) {
    blockCount = count;
    return this;
  }

  public String getFileName() {
    return fileName;
  }

  public StorageConfig setFileName(String aFileName) {
    fileName = aFileName;
    return this;
  }
}
