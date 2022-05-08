import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static String filename;
    public static int bit;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        getInput();
        System.out.println("===Result===");
        String content = FindPrime.readFile(filename);
        System.out.println("Content is -> " + content);

        // file binary
        String binaryResult = FindPrime.convertStringToBinary(content);
        System.out.println("binary : " + binaryResult);
        char[] charBinary = FindPrime.convertStringToArray(binaryResult);
        // convert selected bit to decimal
        long decimal = FindPrime.convertStringBinaryToDecimal(FindPrime.selectBit(charBinary));

        System.out.println("==Find Prime==");
        long safePrime = FindPrime.findSafePrime(decimal);
        System.out.println("Safe Prime : " + safePrime);

        System.out.println("==Key Generator==");
        long keygen = KeyGen.KeyGenerator(safePrime);
        System.out.println("key Generator is " + keygen);
        Map<String, Long> key = KeyGen.GenKey(keygen, safePrime);

        System.out.println("==Public Key==");
        System.out.println("g :" + key.get("g"));
        System.out.println("p :" + key.get("p"));
        System.out.println("y :" + key.get("y"));
        System.out.println("==Private Key==");
        System.out.println("g :" + key.get("g"));
        System.out.println("p :" + key.get("p"));
        System.out.println("u :" + key.get("u"));

        KeyGen.TextFliePublicKey(key);
        KeyGen.TextFliePrivateKey(key);

        System.out.println("==Block Split==");
        
        int blockSize = (int)(Math.log(safePrime) / Math.log(2));
        System.out.println("Block Size :" + blockSize);
        /// 111 111 000 11
        String[] plaintextArray = Encryption.printPrettyBinary(binaryResult, blockSize);
        System.out.println("Plaintext Array : " + Arrays.toString(plaintextArray));

        ///111 111 000 110
        System.out.println(
                "Plaintext Array After padding : " + Arrays.toString(Encryption.padding(plaintextArray, blockSize)));

        long[] plaintextDecimal = new long[plaintextArray.length];

        // Convert string Binary To Decimal
        for (int i = 0; i < plaintextArray.length; i++) {

            plaintextDecimal[i] = Long.parseLong(plaintextArray[i], 2);

        }
        System.out.println("Plaintext Decimal Array : " + Arrays.toString(plaintextDecimal));

        System.out.println("==Encryption==");
        String cipherText =Encryption.encryption(plaintextDecimal, "publicKey.txt");
        System.out.println("Cipher Text : "+cipherText);

        System.out.println("==Decryption==");
        String [] plain = Encryption.decryption("cipherText.txt", "privateKey.txt");
        System.out.println("Binary text :"+Arrays.toString(plain));
    }

    public static void getInput() {
        System.out.print("input filename : ");
        filename = sc.nextLine() + ".txt";
    }
}