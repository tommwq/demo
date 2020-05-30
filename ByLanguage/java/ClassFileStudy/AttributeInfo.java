import java.io.*;
import java.util.*;
import java.nio.*;

class AttributeInfo {
    private AttributeInfo(){}
    
    public short attributeNameIndex;
    public int attributeLength;
    public byte[] info;
    
    public static class Parser {
        public AttributeInfo parse(ByteBuffer buffer) {
            AttributeInfo a = new AttributeInfo();
            a.attributeNameIndex = buffer.getShort();
            a.attributeLength = buffer.getInt();
            a.info = new byte[a.attributeLength];
            buffer.get(a.info);
            return a;
        }
    }
}
