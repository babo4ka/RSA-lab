package rsa_lab;

import java.io.*;
import java.math.BigInteger;

public class RSAMain {

    public static void main(String[] args) throws IOException {
        RSAGenerator gen = new RSAGenerator(256);
        encrypt(gen);
        decrypt(gen);
    }

    public static void encrypt(RSAGenerator gen) throws IOException {
        File input = new File("./src/main/resources/input.txt");

        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        StringBuilder inputText = new StringBuilder();

        String line;

        while((line = br.readLine()) != null){
            inputText.append(line);
        }


        BigInteger toEncrypt = new BigInteger(inputText.toString().getBytes());

        BigInteger encryptedMessage = gen.encrypt(toEncrypt);


        String encryptedString = new String(encryptedMessage.toByteArray());

        File encryptOut = new File("./src/main/resources/encrypted.txt");

        FileWriter fw = new FileWriter(encryptOut);
        fw.write(encryptedString);
        fw.flush();
    }

    public static void decrypt(RSAGenerator gen) throws IOException {
        File input = new File("./src/main/resources/encrypted.txt");

        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        StringBuilder inputText = new StringBuilder();

        String line;

        while((line = br.readLine()) != null){
            inputText.append(line);
        }


        BigInteger toDecrypt = new BigInteger(inputText.toString().getBytes());


        BigInteger decryptedMessage = gen.decrypt(toDecrypt);

        String decryptedString = new String(decryptedMessage.toByteArray());

        File decryptOut = new File("./src/main/resources/decrypted.txt");

        FileWriter fw = new FileWriter(decryptOut);
        fw.write(decryptedString);
        fw.flush();
    }

}
