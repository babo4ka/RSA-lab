package rsa_lab;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RSAGenerator gen = new RSAGenerator(512);


        String m = "hello";
        System.out.println(Arrays.toString(m.getBytes()));
        BigInteger message = new BigInteger(m.getBytes());
        System.out.println(message);
        BigInteger enc = gen.encrypt(message);

        BigInteger dec = gen.decrypt(enc);


        byte [] decB = dec.toByteArray();
        System.out.println(Arrays.toString(decB));
        String encMsg = new String(decB, StandardCharsets.UTF_8);
        System.out.println(encMsg);
    }
}
