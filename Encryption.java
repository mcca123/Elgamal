import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encryption {
    public static void main(String[] args) {
        // long generatedLong = new Random().nextLong();
        // System.out.println("ran: " + generatedLong);
        // long r = Math.abs(generatedLong);
        // System.out.println("ran po: " + r);
        // long x = 47857;
        // long p = 2189725403L;// 11
        // long g = 619226901;// 2
        // long y = 1017512232;// 2
        // long u = 1027894598;// 2

        // System.out.println("Plain text:" + x);
        // System.out.println("==Encryption==");
        // long[] cipher = encryption(p, g, y, x);
        // System.out.println("a :" + cipher[0]);
        // System.out.println("b :" + cipher[1]);
        // System.out.println("==Decryption==");
        // long plaintext = decryption(p, g, u, cipher[0], cipher[1]);
        // System.out.println("plain text: " + plaintext);

        // long [] Bplain = {0001,1110,1010,1100};
        long[] plain = { 10, 23, 27, 16, 5 };
        String cipherText = encrypt(plain, "publicKey.txt");
        System.out.println("Cipher: " + cipherText);

        decrypt("cipherText.txt", "privateKey.txt");

    }

    // input is value
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

    // input is file
    public static String encrypt(long[] plaintext, String keyFile) {
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        String CipherText = "";
        String fileNamePublic = "CipherText.txt";
        PrintWriter writerPublic = null;
        try {
            writerPublic = new PrintWriter(fileNamePublic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(plaintext));

        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        // System.out.println(Arrays.toString(keyString));
        // System.out.println(Arrays.toString(key));

        for (int i = 0; i < plaintext.length; i++) {
            long k = 0;
            while (FindPrime.gcd(k, key[0] - 1) != 1) {
                k = 1 + (long) (Math.random() * (key[0]));
                // System.out.println("k: " + k);
            }

            long a = FindPrime.fastExponent(key[1], k, key[0]);
            long b = (FindPrime.fastExponent(key[2], k, key[0]) * (plaintext[i] % key[0])) % key[0];
            // long[] cipher = { a, b };
            CipherText += a + " " + b + " ";
            writerPublic.flush();
            writerPublic.append(Long.toString(a) + " ");
            writerPublic.append(Long.toString(b) + " ");

        }
        writerPublic.close();

        return CipherText;

    }

    public static String[] decrypt(String cipherText, String keyfile) {
        // String[] keyString = FindPrime.readFile(keyFile).split(" ");
        String[] cipherArray = FindPrime.readFile(cipherText).split(" ");
        long[] cipherArrayL = Stream.of(cipherArray).mapToLong(Long::parseLong).toArray();
        System.out.println("cipher text : " + Arrays.toString(cipherArray));

        String[] key = FindPrime.readFile(keyfile).split(" ");
        long[] keyLong = Stream.of(key).mapToLong(Long::parseLong).toArray();
        System.out.println("private key : " + Arrays.toString(key));

        long[] plaintext = new long[cipherArray.length / 2];
        String[] BinaryArray = new String[cipherArray.length / 2];

        int blockSize = (int) (Math.log(keyLong[0]) / Math.log(2));
        System.out.println("Block size : " + blockSize);

        for (int i = 0, j = 0; j < cipherArray.length / 2; i += 2, j++) {
            plaintext[j] = (FindPrime.fastExponent(cipherArrayL[i], (keyLong[0] - 1 - keyLong[2]), keyLong[0])
                    * cipherArrayL[i + 1]) % keyLong[0];

            BinaryArray[j] = Long.toBinaryString(plaintext[j]);
        }

        System.out.println("Plain Text : " + Arrays.toString(plaintext));
        System.out.println("Binary After delete padding: " + Arrays.toString(BinaryArray));
        for (int i = 0; i < BinaryArray.length; i++) {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < blockSize - BinaryArray[i].length()) {
                sb.append('0');
            }
            sb.append(BinaryArray[i]);
            BinaryArray[i] = sb.toString();
        }

        return BinaryArray;

    }

    // Print Result Binary divide by 8 for beautiful
    public static String[] printPrettyBinary(String text, int blockSize) {

        String k = prettyBinary(text, blockSize, " ");
        // split sting by " " fot covent to Array
        String[] plaintextString = k.split(" ");

        return plaintextString;
    }

    public static String prettyBinary(String binary, int blockSize, String separator) {
        List<String> result = new ArrayList<>();
        int index = 0;

        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }

    public static String[] padding(String plaintextString[], int blockSize) {

        for (int i = 0; i < plaintextString.length; i++) {
            if (plaintextString[i].length() < blockSize) {
                int temp = blockSize - plaintextString[i].length();
                for (int j = 0; j < temp; j++) {
                    StringBuilder pad = new StringBuilder(plaintextString[i]);
                    pad.append("0");
                    plaintextString[i] = pad.toString();
                }
            }
        }

        return plaintextString;
    }

}