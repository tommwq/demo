import java.io.*;
import java.util.*;
import java.nio.*;

public class PrintClassFile {
    public static void main(String... args) throws Exception {
        String classFilename = "PrintClassFile.class";

        JavaClassFile jcf = new JavaClassFile.Builder()
            .buildFromFile(classFilename);

        jcf.display();
    }
}
