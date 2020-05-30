import java.io.*;
import java.util.*;
import java.nio.*;

class FieldInfo {
    protected FieldInfo(){}
    
    public short accessFlags;
    public short nameIndex;
    public short descriptorIndex;
    public short attributesCount;
    public AttributeInfo attributes[];

    public static class Parser {
        public FieldInfo parse(ByteBuffer buffer) {
            FieldInfo f = new FieldInfo();

            f.accessFlags = buffer.getShort();
            f.nameIndex = buffer.getShort();
            f.descriptorIndex = buffer.getShort();
            f.attributesCount = buffer.getShort();
            f.attributes = new AttributeInfo[f.attributesCount];
            for (short i = 0; i < f.attributesCount; i++) {
                f.attributes[i] = new AttributeInfo.Parser().parse(buffer);
            }

            return f;
        }
    }
}
