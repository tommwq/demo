package com.tq.applogcollect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

public interface BlockStorage {
  void open() throws IOException, FileNotFoundException;
  int blockSize();
  int blockCount();
  byte[] read(int blockNumber) throws IOException;
  void write(int blockNumber, byte[] data) throws IOException;
  void close() throws IOException;
}
