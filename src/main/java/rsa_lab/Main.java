package rsa_lab;

public class Main {
    public static void main(String[] args) {
        RSAGenerator gen = new RSAGenerator(512);

        System.out.println(gen.getE());
        System.out.println(gen.getN());
        System.out.println(gen.getD());
    }
}
