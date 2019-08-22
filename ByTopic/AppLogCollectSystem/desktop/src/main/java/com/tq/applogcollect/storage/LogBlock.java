package com.tq.applogcollect.storage;

import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.tq.applogcollect.Constant.ADLER32_SIZE;
import static com.tq.applogcollect.Constant.DATALENGTH_SIZE;
import static com.tq.applogcollect.Constant.LOGLENGTH_SIZE;
import static com.tq.applogcollect.Constant.SEQUENCE_SIZE;

public class LogBlock {
  private int dataLength;
  private ArrayList<LogRecord> logList = new ArrayList<>();
  private int blockSize;

  public LogBlock(int aBlockSize) {
    blockSize = aBlockSize;
  }

  public boolean containsLog(long sequence) {
    return logList.stream().anyMatch(log -> log.getSequence() == sequence);
  }

  public List<LogRecord> logs() {
    return logList;
  }
  
  /*
   * 0 for none
   */
  public long minSequence() {
    if (logList.isEmpty()) {
      return 0;
    }

    return logList.get(0).getSequence();
  }

  /*
   * 0 for none
   */
  public long maxSequence() {
    if (logList.isEmpty()) {
      return 0;
    }

    return logList.get(logList.size() - 1).getSequence();
  }

  public void updateDataLength() {
    dataLength = logList.stream()
      .mapToInt(log -> log.getSerializedSize())
      .map(rawLength -> rawLength + LOGLENGTH_SIZE + SEQUENCE_SIZE)
      .sum() + DATALENGTH_SIZE;
  }

  public int dataLength() {
    return dataLength;
  }
  
  public void clear() {
    logList.clear();
    updateDataLength();
  }

  public boolean tryInsertLog(LogRecord log) {
    updateDataLength();
    int newLength = dataLength + log.getSerializedSize();
        
    if (newLength < blockSize) {
      logList.add(log);
      return true;
    }

    return false;
  }

  public void load(byte[] block) throws InvalidProtocolBufferException {
      
    ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
    buffer.position(ADLER32_SIZE);
    dataLength = buffer.getShort();
    logList = new ArrayList<>();

    int remainDataLength = dataLength - DATALENGTH_SIZE;
    while (remainDataLength > 0) {
      int offset = ADLER32_SIZE + DATALENGTH_SIZE + remainDataLength - LOGLENGTH_SIZE - SEQUENCE_SIZE;
      buffer.position(offset);
      short logLength = buffer.getShort();
      LogRecord log = LogRecord.parser().parseFrom(block, offset - logLength, logLength);
      logList.add(log);
      remainDataLength -= (LOGLENGTH_SIZE + SEQUENCE_SIZE + logLength);
    }

    Collections.reverse(logList);
  }

  public void dump(byte[] block) {
    ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
    buffer.position(ADLER32_SIZE);
    updateDataLength();
    buffer.putShort((short) dataLength);
      
    logList.stream()
      .forEach(log -> buffer.put(log.toByteArray())
               .putShort((short) log.getSerializedSize())
               .putLong(log.getSequence()));
  }
}
