package com.tq.applogcollect;

import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import static com.tq.applogcollect.Constant.ADLER32_SIZE;
import static com.tq.applogcollect.Constant.SEQUENCE_SIZE;
import static com.tq.applogcollect.Constant.DATALENGTH_SIZE;
import static com.tq.applogcollect.Constant.FIRST_LOG_BLOCK;
import static com.tq.applogcollect.Constant.LOGLENGTH_SIZE;

public class AnchorBlock {
  private short dataLength;
  private long version;
  private int firstBlockNumber;
  private int lastBlockNumber;
  private int blockCount;

  public AnchorBlock(int aBlockCount) {
    blockCount = aBlockCount;
  }

  public long version() {
    return version;
  }

  public int firstBlockNumber() {
    return firstBlockNumber;
  }

  public int lastBlockNumber() {
    return lastBlockNumber;
  }

  public void moveForward() {
    version++;
    lastBlockNumber++;
    if (lastBlockNumber == blockCount) {
      lastBlockNumber = FIRST_LOG_BLOCK;
    }

    if (lastBlockNumber == firstBlockNumber) {
      firstBlockNumber++;
    }
  }

  private void clear() {
    dataLength = 0;
    version = 0;
    firstBlockNumber = 0;
    lastBlockNumber = 0;
  }
    
  public void load(byte[] block) {
    ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
    buffer.getLong(); // skip adler32
    dataLength = buffer.getShort();
    version = buffer.getLong();
    firstBlockNumber = buffer.getInt();
    lastBlockNumber = buffer.getInt();

    // initialize new anchor
    if (firstBlockNumber == 0 && lastBlockNumber == 0) {
      // 0, 1 for anchor
      firstBlockNumber = FIRST_LOG_BLOCK;
      lastBlockNumber = firstBlockNumber + 1;
    }
  }

  public void dump(byte[] block) {
    ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
    dataLength = 18;
    buffer.position(8); // skip adler32
    buffer.putShort(dataLength)
      .putLong(version)
      .putInt(firstBlockNumber)
      .putInt(lastBlockNumber);
  }

  public String toString() {
    return String.join("\n", Arrays.asList("version = ", String.valueOf(version),
                                           "firstBlockNumber = ", String.valueOf(firstBlockNumber),
                                           "lastBlockNumber = ", String.valueOf(lastBlockNumber)));
  }
}