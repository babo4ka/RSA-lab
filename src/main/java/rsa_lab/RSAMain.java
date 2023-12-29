package rsa_lab;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAMain {

    public static void main(String[] args) throws IOException {
        RSAGenerator gen = new RSAGenerator(15);
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

        List<List<BigInteger>> encrytpedLines = new ArrayList<>();

        inputText.forEach(it -> encrytpedLines.add(gen.encrypt(it)));

//        List<BigInteger> toEncrypt = new ArrayList<>();
//
//        inputText.forEach(i -> toEncrypt.add(new BigInteger(i.getBytes())));
//
//
//        List<BigInteger> encryptedMessages = new ArrayList<>();
//        toEncrypt.forEach(te -> encryptedMessages.add(gen.encrypt(te)));



        File encryptOut = new File("./src/main/resources/encrypted.txt");

        FileWriter fw = new FileWriter(encryptOut);

        encrytpedLines.forEach(el -> {
            el.forEach(l -> {
                try {
                    fw.write(l + " ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                fw.write("\n");
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

        List<String> decryptedMessages = new ArrayList<>();
        lines.forEach(l -> decryptedMessages.add(gen.decrypt(l)));


        File decryptOut = new File("./src/main/resources/decrypted.txt");

        FileWriter fw = new FileWriter(decryptOut);
        decryptedMessages.forEach(ds -> {
            try {
                fw.write(ds + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fw.flush();
    }

}
