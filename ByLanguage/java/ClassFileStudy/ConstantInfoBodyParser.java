import java.nio.*;

public interface ConstantInfoBodyParser {
    int parse(ByteBuffer buffer, ConstantInfoRecord record);
}

