import java.util.Random;

public class Encryption {
    public static void main(String[] args) {
        // long generatedLong = new Random().nextLong();
        // System.out.println("ran: " + generatedLong);
        // long r = Math.abs(generatedLong);
        // System.out.println("ran po: " + r);
        long x = 150;
        long[] cipher = encryption(563, 128, 389, x);
        System.out.println("a :" + cipher[0]);
        System.out.println("b :" + cipher[1]);

        long plaintext = decryption(563, 128, 65, cipher[0], cipher[1]);
        System.out.println("plain: " + plaintext);

    }

    public static long[] encryption(long p, long g, long y, long x) {
        // long k = 1 + (long) (Math.random() * (rightLimit - 1));
        long k = 0;
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = Math.abs(new Random().nextLong());
        }

        long a = FindPrime.fastExponent(g, k, p);
        long b = (FindPrime.fastExponent(y, k, p) * (x % p)) % p;
        long[] cipher = { a, b };
        return cipher;

    }

    public static long decryption(long p, long g, long u, long a, long b) {
        System.out.println("a:" + a);
        System.out.println("b:" + b);
        // long k = 1 + (long) (Math.random() * (rightLimit - 1));
        long plaintext = (b * FindPrime.fastExponent(a, (p - 1 - u), p))%p;
        return plaintext;

    }

}