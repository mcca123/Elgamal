import java.util.Arrays;
import java.util.stream.Stream;

public class Signature {
    public static void main(String[] args) {

    }

    public static long[] signature(String message, String keyFile) {
        System.out.println("==Signature==");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();

        long p = key[0];
        long g = key[1];
        long x = key[2];

        // message is String
        // String --> long m(base 10)
        long m = 10;

        long k = 0;
        // random K , gcd(k,safeprime-1) = 1
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p - 1));
            // System.out.println("k: " + k);
        }

        long r = FindPrime.fastExponent(g, k, p);
        long s = FindPrime.inverse(p, k) * (m - (x * r)) % p - 1;
        long[] signature = { m, r, s };
        return signature;
    }

    public static long[] signature(long message, String keyFile) {
        System.out.println("==Signature==");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        System.out.println("Key : " + Arrays.toString(key));

        long p = key[0];
        long g = key[1];
        long x = key[2];

        long m = message;

        long k = 0;
        // random K , gcd(k,safeprime = 1)
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p - 1));
            // System.out.println("k: " + k);
        }

        long r = FindPrime.fastExponent(g, k, p);
        long s = (FindPrime.inverse(p - 1, k) * (m - (x * r))) % (p - 1);
        if (s < 0) {
            s = (p - 1) + (s % (p - 1));
        }
        long[] signature = { m, r, s };
        return signature;
    }

    public static boolean verifying(long[] signature, String keyFile) {
        System.out.println("===Verify===");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();

        System.out.println("g^x: " + FindPrime.fastExponent(key[1], signature[0], key[0]));
        System.out.println("y^r*r^s : " + (FindPrime.fastExponent(key[2], signature[1], key[0])
        * FindPrime.fastExponent(signature[1], signature[2], key[0]) % key[0]));

        // System.out.println("a : " + FindPrime.fastExponent(key[1], signature[0], key[0]));
        // System.out.println("b : " + FindPrime.fastExponent(key[2], signature[1], key[0]));
        // System.out.println("c : " + FindPrime.fastExponent(signature[1], signature[2], key[0]));
        // System.out.println("d : " + (FindPrime.fastExponent(key[2], signature[1], key[0]) * FindPrime.fastExponent(signature[1], signature[2], key[0]) % 167));

        if (FindPrime.fastExponent(key[1], signature[0],
                key[0]) == (FindPrime.fastExponent(key[2], signature[1], key[0])
                        * FindPrime.fastExponent(signature[1], signature[2], key[0]) % key[0]))
            return true;
        else
            return false;

    }

    public static long[] BinaryToDecimalArray(String BinaryMessage, long blockSize) {
        // pad
        long pad = BinaryMessage.length() % blockSize;
        if (pad != 0) {
            long numPad = blockSize - pad;
            for (long i = 0; i < numPad; i++) {
                StringBuilder padIt = new StringBuilder(BinaryMessage);
                padIt.append("0");
                BinaryMessage = padIt.toString();
            }
        }
        System.out.println("after pad : " + BinaryMessage);

        // 1111000011110000111100001111 => 11110000 11110000 11110000
        String resultWord = Encryption.SubStringByBlock(BinaryMessage, (int) blockSize," ");
        System.out.println("resultWord : " + resultWord);

        // String[] BinaryMessageArray = new String[BinaryMessage.length() / (int) blockSize];
        // System.out.println("Bina:" + Arrays.toString(BinaryMessageArray));

        // 11110000 11110000
        // temp = 11110000
        // 11110000 11110000 11110000 11110000 => 240, 240, 240, 240
        // 240, 240, 240, 240 => [240, 240, 240, 240]

        String[] ArrayString = resultWord.split(" ");
        System.out.println("ArrayString : "+ Arrays.toString(ArrayString));

        // for(int index = 0,i = 0; index < BinaryMessage.length();
        // index+=(blockSize+1),i++) {
        // String temp = BinaryMessage.substring(index, (int)(index+blockSize));
        // BinaryMessageArray[i] = String.valueOf(Integer.parseInt(temp,2));
        // }
        long[] longDicimalArray = new long[ArrayString.length];
        for (int i = 0; i < ArrayString.length; i++) {
            longDicimalArray[i] = Long.parseLong(ArrayString[i], 2);
        }
        System.out.println("Decimal is : " + Arrays.toString(longDicimalArray));
        return longDicimalArray;
    }
}
