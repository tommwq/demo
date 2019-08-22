package com.tq.applogcollect;

public class StorageConfig {
  private String fileName = "";
  private int blockCount = 16;
  private int blockSize = 4096;

  public int getBlockSize() {
    return blockSize;
  }

  public void setBlockSize(int size) {
    blockSize = size;
  }

  public int getBlockCount() {
    return blockCount;
  }

  public void setBlockCount(int count) {
    blockCount = count;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String aFileName) {
    fileName = aFileName;
  }
}
