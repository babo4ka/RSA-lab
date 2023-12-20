package rsaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import quickExpTask.QuickExp.QuickBigMath;
import rsa_lab.RSAGenerator;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSATest {

    static final String inputPath = "./src/test/resources/input.txt";
    static final String encryptPath = "./src/test/resources/encrypt.txt";
    static final String decryptPath = "./src/test/resources/decrypt.txt";

    static List<BigInteger> encryptedStrings = new ArrayList<>();

    static RSAGenerator generator;

    @BeforeAll
    static void setup() throws IOException {
        generator = new RSAGenerator(512);

        File inputFile = new File(inputPath);

        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while((line = br.readLine()) != null){
            BigInteger num = new BigInteger(line.getBytes());
            encryptedStrings.add(num.modPow(generator.getE(), generator.getN()));
        }

    }


    @Test
    void testEncrypt() throws IOException {
        File inputFile = new File(inputPath);

        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);

        List<BigInteger> nums = new ArrayList<>();

        String line;

        while((line = br.readLine()) != null){
            BigInteger num = generator.encrypt(new BigInteger(line.getBytes()));
            nums.add(num);
        }

        Assertions.assertArrayEquals(nums.toArray(), encryptedStrings.toArray());

        File outputFile = new File(encryptPath);

        FileWriter fw = new FileWriter(outputFile);

        for(BigInteger b : nums){
            fw.write(b.toString() + "\n");
        }

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

        List<String> decryptedStrings = new ArrayList<>();
        String encryptedLine;

        while((inputLine = inputBReader.readLine()) != null){
            inputStrings.add(inputLine);
        }

        while((encryptedLine = encryptedBReader.readLine()) != null){
            BigInteger toDecrypt = new BigInteger(encryptedLine);

            BigInteger decrypted = generator.decrypt(toDecrypt);

            decryptedStrings.add(new String(decrypted.toByteArray()));
        }

        Assertions.assertArrayEquals(inputStrings.toArray(), decryptedStrings.toArray());
    }

}
