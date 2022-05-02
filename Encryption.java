import java.util.Random;

public class Encryption {
    public static void main(String[] args) {
        // long generatedLong = new Random().nextLong();
        // System.out.println("ran: " + generatedLong);
        // long r = Math.abs(generatedLong);
        // System.out.println("ran po: " + r);
        long x = 47857;
        long p = 2189725403L;// 11
        long g = 619226901;// 2
        long y = 1017512232;// 2
        long u = 1027894598;// 2

        System.out.println("Plain text:" + x);
        System.out.println("==Encryption==");
        long[] cipher = encryption(p, g, y, x);
        System.out.println("a :" + cipher[0]);
        System.out.println("b :" + cipher[1]);
        System.out.println("==Decryption==");
        long plaintext = decryption(p, g, u, cipher[0], cipher[1]);
        System.out.println("plain text: " + plaintext);

    }

    public static long[] encryption(long p, long g, long y, long x) {
        long k = 0;
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p));
            System.out.println("k: " + k);
        }

        long a = FindPrime.fastExponent(g, k, p);
        long b = (FindPrime.fastExponent(y, k, p) * (x % p)) % p;
        long[] cipher = { a, b };
        return cipher;

    }

    public static long decryption(long p, long g, long u, long a, long b) {
        System.out.println("a:" + a);
        System.out.println("b:" + b);

        long plaintext = (b * FindPrime.fastExponent(a, (p - 1 - u), p)) % p;
        // long plaintext = (b / FindPrime.fastExponent(a, u, p))%p;
        return plaintext;

    }

}