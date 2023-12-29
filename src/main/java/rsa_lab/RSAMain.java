package rsa_lab;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAMain {

    public static void main(String[] args) throws IOException {
        RSAGenerator gen = new RSAGenerator(2048);
        encrypt(gen);
        decrypt(gen);
    }

    public static void encrypt(RSAGenerator gen) throws IOException {
        File input = new File("./src/main/resources/input.txt");

        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        List<String> inputText = new ArrayList<>();

        String line;

        while((line = br.readLine()) != null){
            inputText.add(line);
        }

        List<BigInteger> toEncrypt = new ArrayList<>();

        inputText.forEach(i -> toEncrypt.add(new BigInteger(i.getBytes())));


        List<BigInteger> encryptedMessages = new ArrayList<>();
        toEncrypt.forEach(te -> encryptedMessages.add(gen.encrypt(te)));



        File encryptOut = new File("./src/main/resources/encrypted.txt");

        FileWriter fw = new FileWriter(encryptOut);

        encryptedMessages.forEach(em -> {
            try {
                fw.write(em + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fw.flush();
    }

    public static void decrypt(RSAGenerator gen) throws IOException {
        File input = new File("./src/main/resources/encrypted.txt");

        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);


        String line;

        List<String> lines = new ArrayList<>();

        while((line = br.readLine()) != null){
            lines.add(line);
        }

        List<BigInteger> toDecrypt = new ArrayList<>();
        lines.forEach(l -> toDecrypt.add(new BigInteger(l)));

        List<BigInteger> decryptedMessages = new ArrayList<>();

        toDecrypt.forEach(td -> decryptedMessages.add(gen.decrypt(td)));

        List<String> decryptedStrings = new ArrayList<>();

        decryptedMessages.forEach(dm -> decryptedStrings.add(new String(dm.toByteArray())));


        File decryptOut = new File("./src/main/resources/decrypted.txt");

        FileWriter fw = new FileWriter(decryptOut);
        decryptedStrings.forEach(ds -> {
            try {
                fw.write(ds + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fw.flush();
    }

}
