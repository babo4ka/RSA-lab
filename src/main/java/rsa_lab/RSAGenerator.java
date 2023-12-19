package rsa_lab;

import millerRabinTask.PrimeNumbers;
import quickExpTask.QuickExp.QuickBigMath;
import random.Random;

import java.math.BigInteger;

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
//        System.out.println(this.p);
    }

    private void generateQ(){
        this.q = pn.generatePrime(this.length);
//        System.out.println(this.q);
    }

    private void countN(){
        this.n = p.multiply(q);
//        System.out.println(this.n);
    }

    private void countFN(){
        this.Fn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
//        System.out.println("fn " + this.Fn);
    }

    private void generateE(){
        Random rand = new Random();
        BigInteger tempE = rand.rand(BigInteger.ONE, this.Fn);

        while(QuickBigMath.nod(tempE, this.Fn).compareTo(BigInteger.ONE) != 0){
            tempE = rand.rand(BigInteger.ONE, this.Fn);
        }

        this.e = tempE;

//        System.out.println(this.e);
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

    public BigInteger encrypt(BigInteger message){
        return QuickBigMath.quickExpMod(message, this.e, this.n);
    }

    public BigInteger decrypt(BigInteger encryptedMsg){
        return QuickBigMath.quickExpMod(encryptedMsg, this.d, this.n);
    }
}
