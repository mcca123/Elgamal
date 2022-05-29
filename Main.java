import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    // public static String filename;
    public static int bit;
    public static String cipherText;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("Select 1, 2, 3, 4, 5 or 6");
                System.out.println("1: Generate Key");
                System.out.println("2: Encryption File");
                System.out.println("3: Encryption String");
                System.out.println("4: Decryption");
                System.out.println("5: Signature");
                System.out.println("6: Verify");
                System.out.println("7: Exit program");
                System.out.println("--------");
                System.out.printf("input number: ");
                int i = sc.nextInt();
                sc.nextLine();

                switch (i) {
                    case 1:
                        // KeyGen.main(args);
                        System.out.printf("input filename : ");
                        String filename = sc.nextLine();

                        String content = FindPrime.readFile(filename);
                        System.out.println("===Data in file===");

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
                        KeyGen.TextFliePublicKey(key);
                        KeyGen.TextFliePrivateKey(key);
                        break;
                    case 2:
                        System.out.println("==Encryption==");
                        System.out.print("input Plaintext filename : ");
                        String textfilename = sc.nextLine();
                        System.out.print("input Key filename : ");
                        String keyfilename = sc.nextLine();
                        cipherText = Encryption.encrypt(textfilename, keyfilename);
                        System.out.println("Cipher Text : " + cipherText);
                        break;
                    case 3:
                        System.out.println("==Encryption==");
                        System.out.print("input Plaintext String : ");
                        String textString = sc.nextLine();
                        System.out.print("input Key filename : ");
                         keyfilename = sc.nextLine();
                        cipherText = Encryption.encryptString(textString, keyfilename);
                        System.out.println("Cipher Text : " + cipherText);
                        break;
                    case 4:
                        System.out.println("==Decryption==");
                        System.out.print("input Ciphertext filename : ");
                        textfilename = sc.nextLine();
                        System.out.print("input Key filename : ");
                        keyfilename = sc.nextLine();
                        String plain = Encryption.decrypt(textfilename, keyfilename);
                        // create plaintext
                        System.out.println("PlainText :" + (plain));
                    case 7:
                        System.out.println("--------");
                        System.out.println("Goodbye");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("--------");
                        System.out.println("Wrong input try again");
                        System.out.println("--------");
                        Main.main(args);
                        break;
                }
            }
            // getInput();
            // System.out.println("===Result===");
            // String content = FindPrime.readFile(filename);
            // System.out.println("Content is -> " + content);
        }

        // // file binary
        // String binaryResult = FindPrime.convertStringToBinary(content);
        // System.out.println("binary : " + binaryResult);
        // char[] charBinary = FindPrime.convertStringToArray(binaryResult);
        // // convert selected bit to decimal
        // long decimal =
        // FindPrime.convertStringBinaryToDecimal(FindPrime.selectBit(charBinary));

        // System.out.println("==Find Prime==");
        // long safePrime = FindPrime.findSafePrime(decimal);
        // System.out.println("Safe Prime : " + safePrime);

        // System.out.println("==Key Generator==");
        // long keygen = KeyGen.KeyGenerator(safePrime);
        // System.out.println("key Generator is " + keygen);
        // Map<String, Long> key = KeyGen.GenKey(keygen, safePrime);

        // System.out.println("==Public Key==");
        // System.out.println("p :" + key.get("p"));
        // System.out.println("g :" + key.get("g"));
        // System.out.println("y :" + key.get("y"));
        // System.out.println("==Private Key==");
        // System.out.println("p :" + key.get("p"));
        // System.out.println("g :" + key.get("g"));
        // System.out.println("u :" + key.get("u"));

        // KeyGen.TextFliePublicKey(key);
        // KeyGen.TextFliePrivateKey(key);

        // // System.out.println("==Encryption==");
        // cipherText = Encryption.encrypt(filename, "publicKey.txt");
        // System.out.println("Cipher Text : " + cipherText);

        // System.out.println("==Decryption==");
        // String plain = Encryption.decrypt("cipherText.txt", "privateKey.txt");
        // System.out.println("PlainText :" + (plain));

        // String textString = FindPrime.readFile("text.txt");

        // String textBinary = FindPrime.convertStringToBinary(textString);
        // long[] longDicimalArray =
        // Signature.BinaryToDecimalArray(Hash.hashThisString(textBinary),
        // (int) (Math.log(key.get("p") - 1) / Math.log(2)));
        // // System.out.println("hash : " + Arrays.toString(longDicimalArray));
        // boolean isVerifying = true;
        // //System.out.println("==Signature==");
        // for (int i = 0; i < longDicimalArray.length; i++) {
        // long[] signature = Signature.signature(longDicimalArray[i],
        // "privateKey.txt");
        // System.out.println("signature: " + Arrays.toString(signature));
        // if (Signature.verifying(signature, "publicKey.txt")) {
        // System.out.println(" Correct");
        // } else {
        // isVerifying = false;
        // System.out.println("Wrong");
        // //break;
        // }

        // }
        // System.out.println("Verify is :" + isVerifying);

    }

    // public static void getInput() {
    // System.out.print("input filename : ");
    // filename = sc.nextLine() + ".txt";
    // }

}