import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class SignVeri {
    public static void main(String[] args) {
        long[] signature = Signature(9, "privateKey.txt");
        System.out.println("signature: " + Arrays.toString(signature));
        if (verifying(signature, "publicKey.txt")) {
            System.out.println(" Correct");
        } else
            System.out.println("Wrong ISAS");

    }

    public static long[] Signature(String message, String keyFile) {
        System.out.println("==Signature==");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();

        long p = key[0];
        long g = key[1];
        long x = key[2];

        long m = 10;

        long k = 0;
        // random K , gcd(k,safeprime = 1)
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p - 1));
            // System.out.println("k: " + k);
        }

        long r = FindPrime.fastExponent(g, k, p);
        long s = FindPrime.inverse(p, k) * (m - (x * r)) % p - 1;
        long[] signature = { m, r, s };
        return signature;
    }

    public static long[] Signature(long message, String keyFile) {
        System.out.println("==Signature==");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();

        long p = key[0];
        long g = key[1];
        long x = key[2];

        long m = message;
        System.out.println("p" + p);

        long k = 0;
        // random K , gcd(k,safeprime = 1)
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p - 1));
            // System.out.println("k: " + k);
        }
        k = 7;
        System.out.println("k" + k);

        long r = FindPrime.fastExponent(g, k, p);
        long s = (FindPrime.inverse(p - 1, k) * (m - (x * r))) % (p - 1);
        if (s < 0) {
            s = (p - 1) + (s % (p - 1));
        }
        System.out.println("inv: " + FindPrime.inverse(p - 1, k));
        long[] signature = { m, r, s };
        return signature;
    }

    public static boolean verifying(long[] signature, String keyFile) {

        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();

        System.out.println("g^x: " + FindPrime.fastExponent(key[1], signature[0], key[0]));
        System.out.println("y^r*r^s : " + FindPrime.fastExponent(key[2], signature[1], key[0])
                * FindPrime.fastExponent(signature[1], signature[2], key[0]));

        if (FindPrime.fastExponent(key[1], signature[0], key[0]) == FindPrime.fastExponent(key[2], signature[1], key[0])
                * FindPrime.fastExponent(signature[1], signature[2], key[0]))
            return true;
        else
            return false;

    }
}
