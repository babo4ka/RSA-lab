package rsa_lab;

import millerRabinTask.PrimeNumbers;
import quickExpTask.QuickExp.QuickBigMath;
import random.Random;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSAGenerator {
    private final int length;

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger d;

    private BigInteger Fn;

    private BigInteger e;

    PrimeNumbers pn = new PrimeNumbers();

    public BigInteger getN() {
        return n;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getE() {
        return e;
    }

    public RSAGenerator(int length){
        this.length = length;
        generateP();
        generateQ();
        countN();
        countFN();
        generateE();
        generateD();
    }

    private void generateP(){
        this.p = pn.generatePrime(this.length);
    }

    private void generateQ(){
        this.q = pn.generatePrime(this.length);
    }

    private void countN(){
        this.n = p.multiply(q);
    }

    private void countFN(){
        this.Fn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    private void generateE(){
        Random rand = new Random();
        BigInteger tempE = rand.rand(BigInteger.ONE, this.Fn);

        while(QuickBigMath.nod(tempE, this.Fn).compareTo(BigInteger.ONE) != 0){
            tempE = rand.rand(BigInteger.ONE, this.Fn);
        }

        this.e = tempE;
    }

    private void generateD(){
        this.d = this.e.modInverse(this.Fn);
//        Random rand = new Random();
//        BigInteger tempD = rand.rand(this.length);
//
//
//
//        System.out.println(this.e.mod(this.Fn));
//        while((tempD.multiply(this.e)).mod(this.Fn).compareTo(BigInteger.ONE) != 0){
//            tempD = rand.rand(this.length);
////            System.out.println((tempD.multiply(this.e)).mod(this.Fn));
//        }
//
//        this.d = tempD;

    }

    public List<BigInteger> encrypt(String message){
        String [] symbols = message.split("");

        List<BigInteger> results = new ArrayList<>();

        Arrays.stream(symbols).forEach(s -> results.add(QuickBigMath.quickExpMod(new BigInteger(s.getBytes()), this.e, this.n)));

        return results;
//        return QuickBigMath.quickExpMod(message, this.e, this.n);
    }

    public String decrypt(String encryptedMsg){
        String [] numsAsString = encryptedMsg.split(" ");
        List<BigInteger> nums = new ArrayList<>();
        Arrays.stream(numsAsString).forEach(nas -> nums.add(new BigInteger(nas)));

        List<BigInteger> results = new ArrayList<>();
        nums.forEach(n -> results.add(QuickBigMath.quickExpMod(n, this.d, this.n)));

        StringBuilder sb = new StringBuilder();

        results.forEach(r -> sb.append(new String(r.toByteArray())));

        return sb.toString();
    }
}
