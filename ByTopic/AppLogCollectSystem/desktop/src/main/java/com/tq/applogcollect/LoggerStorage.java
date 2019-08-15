package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class LoggerStorage implements BlockStorage {
  
  private static class Block {
    int crc;
  }
  
  private static class AnchorBlock extends Block {
     long timestamp;
     int firstBlockNumber;
     int lastBlockNumber;
    
  }

  private byte[] tmp;
  private BlockStorage storage;

    public LoggerStorage(BlockStorage storage) {
    tmp = new byte[blockSize()];
  }

  @Override
  public void open() throws IOException, FileNotFoundException {
    storage.open();
    setupAnchor();
  }

  @Override
  public void close() throws IOException {
    storage.close();
  }

  @Override
  public int blockSize() {
    return storage.blockSize();
  }

  @Override
  public int blockCount() {
    return storage.blockCount();
  }

  @Override
  public byte[] read(int blockNumber) throws IOException {
    return storage.read(blockNumber);
  }

  @Override
  public void write(int blockNumber, byte[] data) throws IOException {
    storage.write(blockNumber, data);
  }

  private void setupAnchor() {
    // 首先读取0、1两个块，尝试重建锚点。
    // 锚点块的格式为：crc:int32, data_size:int16, timestamp:int64, first_block_no, last_block_no: int32, ... crc:int32
    
  }

  public void write(LogRecord log) {
    // 日志块的格式为：crc:int32，data_size:int16, log_content:binary, log_length:int16, sequence:int64, ... crc:int32
    // TODO 处理日志
  }

  public List<LogRecord> read(long sequence, int count) {
    return null;
  }
}
