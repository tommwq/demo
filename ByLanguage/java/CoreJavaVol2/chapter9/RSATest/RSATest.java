import java.io.*;
import java.security.*;
import javax.crypto.*;

public class RSATest {
    public static void main(String[] args) {
        try {
            if (args[0].equals("-genkey")) {
                KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
                SecureRandom random = new SecureRandom();
                pairgen.initialize(KEYSIZE, random);
                KeyPair keyPair = pairgen.generateKeyPair();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(keyPair.getPublic());
                out.close();
                out = new ObjectOutputStream(new FileOutputStream(args[2]));
                out.writeObject(keyPair.getPrivate());
                out.close();
            } else if (args[0].equals("-encrypt")) {
                KeyGenerator keygen = KeyGenerator.getInstance("AES");
                SecureRandom random = new SecureRandom();
                keygen.init(random);
                SecretKey key = keygen.generateKey();
