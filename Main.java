import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static String filename;
    public static int bit;
    public static String cipherText;
    
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

        //System.out.println("==Encryption==");
        cipherText = Encryption.encrypt(filename, "publicKey.txt");
        System.out.println("Cipher Text : " + cipherText);

        System.out.println("==Decryption==");
        String plain = Encryption.decrypt("cipherText.txt", "privateKey.txt");
        System.out.println("PlainText :"+ (plain));

        Signature.run();
        
    }

    public static void getInput() {
        System.out.print("input filename : ");
        filename = sc.nextLine() + ".txt";
    }
    
}