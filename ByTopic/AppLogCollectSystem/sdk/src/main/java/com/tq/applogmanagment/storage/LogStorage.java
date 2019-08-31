package com.tq.applogmanagement.storage;

import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Adler32;
import static com.tq.applogmanagement.Constant.*;

public class LogStorage implements BlockStorage {
    private static final int MIN_BLOCK_SIZE = 1024;
    private static final int MIN_BLOCK_COUNT = 8;

    private final byte[] emptyBlock;
    private AnchorBlock anchorBlock;
    private LogBlock logBlock;
    private BlockStorage storage;
    private int pingBlockNumber;
    private int pongBlockNumber;

    public LogStorage(BlockStorage aStorage) {
        storage = aStorage;
        emptyBlock = new byte[blockSize()];
    }

    @Override
    synchronized public void open() throws IOException {
        storage.open();

        if (blockSize() < MIN_BLOCK_SIZE) {
            throw new RuntimeException("do not support storage which block size < 1024");
        }

        if (blockCount() < MIN_BLOCK_COUNT) {
            throw new RuntimeException("do not support storage which block count < 8");
        }

        readAnchor();
        readLogBlock();
    }

    @Override
    synchronized public void close() throws IOException {
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
    synchronized public byte[] read(int blockNumber) throws IOException {
        return storage.read(blockNumber);
    }

    @Override
    synchronized public void write(int blockNumber, byte[] data) throws IOException {
        long checkSum = getCheckSum(data);
        ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(checkSum);
        buffer.position(blockSize() - ADLER32_SIZE);
        buffer.putLong(checkSum);

        storage.write(blockNumber, data);
    }

    private long getCheckSum(byte[] block) {
        Adler32 adler32 = new Adler32();
        adler32.update(block, ADLER32_SIZE, blockSize() - ADLER32_SIZE - ADLER32_SIZE); // head, tail
        return adler32.getValue();
    }

    private void readLogBlock() throws IOException {
        int first = anchorBlock.firstBlockNumber();
        int last = anchorBlock.lastBlockNumber();

        // ping-pong write
        int candidate = last - 1;
        byte[] bl, bc;
        boolean okl, okc;
        LogBlock lbl = new LogBlock(blockSize());
        LogBlock lbc = new LogBlock(blockSize());

        bl = readAndVerify(last);
        bc = readAndVerify(candidate);
        okl = (bl != null);
        okc = (bc != null);

        if (okl) {
            try {
                lbl.read(bl);
            } catch (InvalidProtocolBufferException e) {
                okl = false;
            }
        }

        if (okc) {
            try {
                lbc.read(bc);
            } catch (InvalidProtocolBufferException e) {
                okc = false;
            }
        }

        if (!okl && !okc) {
            throw new RuntimeException("cannot read last block");
        }

        if (okl && okc) {
            logBlock = lbl;
            if (lbc.dataLength() > lbl.dataLength()) {
                logBlock = lbc;
            }
            return;
        }

        logBlock = lbl;
        if (okc) {
            logBlock = lbc;
        }
    }

    private void syncAnchor() throws IOException {
        byte[] block = new byte[blockSize()];
        anchorBlock.write(block);
        write(0, block);
        write(1, block);
    }

    private void readAnchor() throws IOException {
        // 首先读取0、1两个块，尝试重建锚点。
        // 锚点块的格式为：adler32:int64, data_length:int16, version:int64, first_block_no, last_block_no: int32, ... adler32:int64
        byte[] b0, b1;
        boolean ok0, ok1;

        b0 = readAndVerify(0);
        b1 = readAndVerify(1);
        ok0 = (b0 != null);
        ok1 = (b1 != null);

        if (!ok0 && !ok1) {
            throw new RuntimeException("all anchor blocks are bad");
        }

        byte[] newest = b0;
        if (!ok0 && ok1) {
            newest = b1;
        }

        if (ok0 && ok1) {
            int t0 = ByteBuffer.wrap(b0, ADLER32_SIZE + DATALENGTH_SIZE, VERSION_SIZE).order(ByteOrder.LITTLE_ENDIAN).getShort();
            int t1 = ByteBuffer.wrap(b1, ADLER32_SIZE + DATALENGTH_SIZE, VERSION_SIZE).order(ByteOrder.LITTLE_ENDIAN).getShort();
            if (t1 > t0) {
                newest = b1;
            }
        }

        anchorBlock = new AnchorBlock(blockCount());
        anchorBlock.read(newest);
        updatePingPong();
    }

    private void updatePingPong() {
        pingBlockNumber = anchorBlock.lastBlockNumber();
        pongBlockNumber = pingBlockNumber - 1;
        if (pongBlockNumber < FIRST_LOG_BLOCK) {
            pongBlockNumber = blockCount() - 1;
        }
    }

    private void swapPingPong() {
        int x = pingBlockNumber;
        pingBlockNumber = pongBlockNumber;
        pongBlockNumber = x;
    }

    private boolean fastVerifyBlock(byte[] block) {
        int size = blockSize();
        return Arrays.equals(Arrays.copyOfRange(block, 0, ADLER32_SIZE),
                Arrays.copyOfRange(block, size - ADLER32_SIZE, size));
    }

    private boolean isEmptyBlock(byte[] block) {
        return Arrays.equals(block, emptyBlock);
    }

    private boolean verifyBlock(byte[] block) {
        if (!fastVerifyBlock(block)) {
            return false;
        }

        if (isEmptyBlock(block)) {
            return true;
        }

        long expect = getCheckSum(block);
        long actural = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN).getLong();

        return expect == actural;
    }

    private void syncAndMoveForward() throws IOException {
        byte[] block = new byte[blockSize()];
        logBlock.write(block);
        write(pongBlockNumber, block); // make ping = pong, pingBlock already wrote

        anchorBlock.moveForward();
        updatePingPong();

        logBlock.clear();
        logBlock.write(block);
        write(pingBlockNumber, block);
        write(pongBlockNumber, block);
        syncAnchor();
    }

    private void syncLog() throws IOException {
        byte[] block = new byte[blockSize()];
        logBlock.write(block);
        write(pingBlockNumber, block);
        swapPingPong();
    }

    private long getMaxSequenceInBlock(byte[] block) {
        if (block == null) {
            return INVALID_SEQUENCE;
        }

        ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(ADLER32_SIZE);
        short dataLength = buffer.getShort();
        buffer.position(ADLER32_SIZE + DATALENGTH_SIZE + dataLength - SEQUENCE_SIZE);
        return buffer.getLong();
    }

    private boolean readLogBlock(int blockNumber, LogBlock block) {
        try {
            byte[] data = readAndVerify(blockNumber);
            if (data == null) {
                return false;
            }

            block.read(data);
            return true;
        } catch (InvalidProtocolBufferException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private byte[] readAndVerify(int blockNumber) throws IOException {
        byte[] data = read(blockNumber);
        if (!verifyBlock(data)) {
            return null;
        }

        return data;
    }

    synchronized public void write(Log log) throws IOException {
        if (!logBlock.tryInsertLog(log)) {
            syncAndMoveForward();

            if (!logBlock.tryInsertLog(log)) {
                throw new RuntimeException("cannot insert log");
            }
        }

        syncLog();
        syncAnchor();
    }

    synchronized public List<Log> read(long sequence, int count) {
        ArrayList<Log> records = new ArrayList<>();

        if (sequence > logBlock.maxSequence()) {
            return records;
        }

        Predicate<Log> filter = (log) -> {
            // long seq = log.getSequence();
            // return (seq > sequence - count && seq <= sequence);
          return true;
        };

        if (sequence - count + 1 > logBlock.minSequence()) {
            return logBlock.logs()
                    .stream()
                    .filter(filter)
                    .collect(Collectors.toList());
        }

        LogBlock block = new LogBlock(blockSize());
        for (int blockNumber = anchorBlock.firstBlockNumber();
             blockNumber <= anchorBlock.lastBlockNumber() - 2; // skip ping-pong blocks
             blockNumber++) {

            if (readLogBlock(blockNumber, block)) {
                records.addAll(block.logs()
                        .stream()
                        .filter(filter)
                        .collect(Collectors.toList()));
            }
        }

        if (sequence > logBlock.minSequence()) {
            records.addAll(logBlock.logs()
                    .stream()
                    .filter(filter)
                    .collect(Collectors.toList()));
        }

        return records;
    }

    public long maxSequence() {
        return logBlock.maxSequence();
    }
}
