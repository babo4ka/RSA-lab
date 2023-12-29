package rsaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import quickExpTask.QuickExp.QuickBigMath;
import rsa_lab.RSAGenerator;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSATest {

    static final String inputPath = "./src/test/resources/input.txt";
    static final String encryptPath = "./src/test/resources/encrypt.txt";
    static final String decryptPath = "./src/test/resources/decrypt.txt";

    static List<String> encryptedStrings = new ArrayList<>();

    static RSAGenerator generator;

    @BeforeAll
    static void setup() throws IOException {
        generator = new RSAGenerator(100);

        File inputFile = new File(inputPath);

        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while((line = br.readLine()) != null){
            String [] symbols = line.split("");

            List<BigInteger> encrypted = new ArrayList<>();

            Arrays.stream(symbols).forEach(s -> encrypted.add(new BigInteger(s.getBytes()).modPow(generator.getE(), generator.getN())));

            StringBuilder sb = new StringBuilder();
            encrypted.forEach(e -> sb.append(e).append(" "));

            encryptedStrings.add(sb.toString());
        }

    }


    @Test
    void testEncrypt() throws IOException {
        File inputFile = new File(inputPath);

        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);

        List<List<BigInteger>> encrypted = new ArrayList<>();

        String line;

        while((line = br.readLine()) != null){
            encrypted.add(generator.encrypt(line));
        }

        List<String> encStrings = new ArrayList<>();
        encrypted.forEach(e -> {
            StringBuilder sb = new StringBuilder();
            e.forEach(el -> sb.append(el).append(" "));
            encStrings.add(sb.toString());
        });

        Assertions.assertArrayEquals(encStrings.toArray(), encryptedStrings.toArray());

        File outputFile = new File(encryptPath);

        FileWriter fw = new FileWriter(outputFile);

        encStrings.forEach(es -> {
            try {
                fw.write(es + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fw.flush();
    }

    @Test
    void testDecrypt() throws IOException{
        File inputFile = new File(inputPath);
        File inputEncryptedFile = new File(encryptPath);

        FileReader inputReader = new FileReader(inputFile);
        FileReader encryptedReader = new FileReader(inputEncryptedFile);

        BufferedReader inputBReader = new BufferedReader(inputReader);
        BufferedReader encryptedBReader = new BufferedReader(encryptedReader);

        List<String> inputStrings = new ArrayList<>();
        String inputLine;

        String encryptedLine;

        while((inputLine = inputBReader.readLine()) != null){
            inputStrings.add(inputLine);
        }

        List<String> encryptedLines = new ArrayList<>();
        while((encryptedLine = encryptedBReader.readLine()) != null){
            encryptedLines.add(encryptedLine);
        }

        List<String> decryptedMessages = new ArrayList<>();
        encryptedLines.forEach(el -> decryptedMessages.add(generator.decrypt(el)));



        Assertions.assertArrayEquals(inputStrings.toArray(), decryptedMessages.toArray());
    }

}
