import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Signature {
    // public static void main(String[] args) {
    // long[] signature = signature("text.txt", "privateKey.txt");
    // System.out.println("Signature" + Arrays.toString(signature));

    // boolean verify = verifying("text.txt", "publicKey.txt");
    // if (verify)
    // System.out.println("Corrected");
    // else
    // System.out.println("Incorrected");

    // }

    public static long[] signature(String plaintextFile, String keyFile) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==Signature==");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        System.out.println("Key : " + Arrays.toString(key));

        long p = key[0];
        long g = key[1];
        long x = key[2];

        int blockSize = (int) (Math.log(p - 1) / Math.log(2));

        String textString = FindPrime.readFile(plaintextFile);
        System.out.println("Message : " + textString);
        System.out.println("hash value:" + Hash.hashThisString(textString));
        long[] longHashArray = Signature.BinaryToDecimalArray(Hash.hashThisString(textString), blockSize);
        long HashValue = 0;
        for (int i = 0; i < longHashArray.length; i++) {
            HashValue = (HashValue + longHashArray[i]);
        }
        System.out.println("Hash Value: " + HashValue);

        long k = 0;
        // random K , gcd(k,safeprime = 1)
        while (FindPrime.gcd(k, p - 1) != 1) {
            k = 1 + (long) (Math.random() * (p - 1));
            // System.out.println("k: " + k);
        }

        long r = FindPrime.fastExponent(g, k, p);
        long s = (FindPrime.inverse(p - 1, k) * (HashValue - ((x * r) % (p - 1)))) % (p - 1);
        if (s < 0) {
            s = (p - 1) + (s % (p - 1));
        }
        // System.out.println("k: " + k);
        // System.out.println("g: " + g);
        // System.out.println("x: " + x);
        // System.out.println("p: " + p);
        // System.out.println("m: " + HashValue);
        // System.out.println("r: " + r);
        // System.out.println("s: " + s);
        // System.out.println("k-1: " + FindPrime.inverse(p - 1, k));

        long[] signature = { r, s };

        String signedMessage = textString + " " + r + " " + s;

        System.out.print("input signed Message file name : ");
        String fileNameSigned = sc.nextLine();
        Encryption.stringToFile(fileNameSigned, signedMessage);
        return signature;
    }

    public static boolean verifying(String plaintextFile, String keyFile) {
        System.out.println("===Verify===");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        long p = key[0];
        long g = key[1];
        long y = key[2];

        int blockSize = (int) (Math.log(p - 1) / Math.log(2));

        // read signed message file
        String textString = FindPrime.readFile(plaintextFile);
        String message = textString.substring(0, textString.lastIndexOf(' '));
        System.out.println("tet:" + message);
        long r = Long.parseLong(message.substring(message.lastIndexOf(' ') + 1));
        message = message.substring(0, message.lastIndexOf(' '));
        long s = Long.parseLong(textString.substring(textString.lastIndexOf(' ') + 1));
        System.out.println("r: " + r);
        System.out.println("s: " + s);

        System.out.println("signature: " + r + " " + s);
        System.out.println("Message: " + message);

        long[] longHashArray = Signature.BinaryToDecimalArray(Hash.hashThisString(message), blockSize);

        long HashValue = 0;
        for (int i = 0; i < longHashArray.length; i++) {
            HashValue = (HashValue + longHashArray[i]);
        }
        System.out.println("Hash Value: " + HashValue);

        System.out.println("g^x: " + FindPrime.fastExponent(g, HashValue, p));
        System.out.println("y^r*r^s : " + (FindPrime.fastExponent(y, r, p)
                * FindPrime.fastExponent(r, s, p) % p));

        if (FindPrime.fastExponent(g, HashValue, p) == (FindPrime.fastExponent(y, r, p)
                * FindPrime.fastExponent(r, s, p) % p)) {
            System.out.println("Correct");
            return true;
        } else {
            System.out.println("Incorrect");
            return false;
        }
    }


    public static boolean verifyingDecypt(String plaintextFile, String keyFile) {
        System.out.println("===Verify===");
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        long p = key[0];
        long g = key[1];
        long y = key[2];

        int blockSize = (int) (Math.log(p - 1) / Math.log(2));

        // read signed message file
        String textString = FindPrime.readFile(plaintextFile);
        String message = textString.substring(0, textString.lastIndexOf(' '));
        System.out.println("tet:" + message);
        long r = Long.parseLong(message.substring(message.lastIndexOf(' ') + 1));
        message = message.substring(0, message.lastIndexOf(' '));
        long s = Long.parseLong(textString.substring(textString.lastIndexOf(' ') + 1));
        System.out.println("r: " + r);
        System.out.println("s: " + s);

        System.out.println("signature: " + r + " " + s);
        System.out.println("Message: " + message);
        Scanner sc = new Scanner(System.in);
        System.out.print("input key file name for decypt: ");
        String keyfile = sc.nextLine();
        String plaintext =Encryption.decryptString(message, keyfile);
        System.out.print("input plainText file name : ");
                        String DecryText = sc.nextLine();
                        Encryption.stringToFile(DecryText, plaintext);

        long[] longHashArray = Signature.BinaryToDecimalArray(Hash.hashThisString(message), blockSize);

        long HashValue = 0;
        for (int i = 0; i < longHashArray.length; i++) {
            HashValue = (HashValue + longHashArray[i]);
        }
        System.out.println("Hash Value: " + HashValue);

        System.out.println("g^x: " + FindPrime.fastExponent(g, HashValue, p));
        System.out.println("y^r*r^s : " + (FindPrime.fastExponent(y, r, p)
                * FindPrime.fastExponent(r, s, p) % p));

                
        if (FindPrime.fastExponent(g, HashValue, p) == (FindPrime.fastExponent(y, r, p)
                * FindPrime.fastExponent(r, s, p) % p)) {
            System.out.println("Correct");
            return true;
        } else {
            System.out.println("Incorrect");
            return false;
        }
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
        String resultWord = Encryption.SubStringByBlock(BinaryMessage, (int) blockSize, " ");
        System.out.println("resultWord : " + resultWord);

        // 11110000 11110000
        // temp = 11110000
        // 11110000 11110000 11110000 11110000 => 240, 240, 240, 240
        // 240, 240, 240, 240 => [240, 240, 240, 240]

        String[] ArrayString = resultWord.split(" ");
        System.out.println("ArrayString : " + Arrays.toString(ArrayString));

        long[] longDicimalArray = new long[ArrayString.length];
        for (int i = 0; i < ArrayString.length; i++) {
            longDicimalArray[i] = Long.parseLong(ArrayString[i], 2);
        }
        System.out.println("Decimal is : " + Arrays.toString(longDicimalArray));
        return longDicimalArray;
    }
}
