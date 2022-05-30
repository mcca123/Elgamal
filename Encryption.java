import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileWriter;
import java.io.IOException;

public class Encryption {
    public static void main(String[] args) {
        //Encryption
        System.out.println("==Encryption==");
        String cipherText = Encryption.encrypt("text.txt", "publicKey.txt","fileName");
        System.out.println("Cipher Text : " + cipherText);

        // // //Decryption
        // System.out.println("==Decryption==");
        // String plain = Encryption.decrypt("cipherText.txt", "privateKey.txt");
        // System.out.println("PlainText :"+ (plain));

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
    public static String encrypt(String plaintextFile, String keyFile, String fileNameFile) {

        // read Key File
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        System.out.print("keyString"+Arrays.toString(keyString));
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        // long[] key = Stream.of(FindPrime.readFile(keyFile).split("
        // ")).mapToLong(Long::parseLong).toArray();

        System.out.println("==Block Split==");
        // find block size
        int blockSize = (int) (Math.log(key[0]-1) / Math.log(2));
        System.out.println("Block Size :" + blockSize);
        // read Plaintext File
        String plaintext = FindPrime.readFile(plaintextFile);
        String binaryResult = FindPrime.convertStringToBinary(plaintext);
        System.out.println("Plaintext binary : " + binaryResult);
        // plaintext length
        int plainLength = binaryResult.length();

        /// 111 111 000 11
        String[] plaintextArray = BlockDivide(binaryResult, blockSize);
        System.out.println("Plaintext Array : " + Arrays.toString(plaintextArray));

        /// 111 111 000 110
        System.out.println("Plaintext Array After padding : " + Arrays.toString(padding(plaintextArray, blockSize)));

        // Convert string Binary To Decimal 0010 => 2
        long[] plaintextDecimal = new long[plaintextArray.length];
        for (int i = 0; i < plaintextArray.length; i++) {

            plaintextDecimal[i] = Long.parseLong(plaintextArray[i], 2);

        }

        System.out.println("==Encryption==");
        String CipherText = "";
        String CipherFileName = fileNameFile;
        PrintWriter writerPublic = null;
        try {
            writerPublic = new PrintWriter(CipherFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Plaintext Decimal : " + Arrays.toString(plaintextDecimal));
        System.out.println("public key : " + Arrays.toString(key));

        // Cipher Text A,B
        for (int i = 0; i < plaintextDecimal.length; i++) {
            long k = 0;
            // random K , gcd(k,safeprime = 1)
            while (FindPrime.gcd(k, key[0] - 1) != 1) {
                k = 1 + (long) (Math.random() * (key[0]));
                // System.out.println("k: " + k);
            }

            // fine A = (g^k) % p
            long a = FindPrime.fastExponent(key[1], k, key[0]);
            // fine B = ((y^k) % p * plainText(x)) % p
            long b = (FindPrime.fastExponent(key[2], k, key[0]) * (plaintextDecimal[i] % key[0])) % key[0];
            // long[] cipher = { a, b };
            CipherText += a + " " + b + " ";
            writerPublic.flush();
            writerPublic.append(Long.toString(a) + " ");
            writerPublic.append(Long.toString(b) + " ");
        }
        CipherText += Long.toString(plainLength);
        writerPublic.append(Long.toString(plainLength));
        writerPublic.close();

        return CipherText;

    }

    // input is file
    public static String encryptString(String plaintext, String keyFile,String fileNameString) {

        // read Key File
        String[] keyString = FindPrime.readFile(keyFile).split(" ");
        // [] string => [] Long , for calculate
        System.out.print("keyString"+Arrays.toString(keyString));
        long[] key = Stream.of(keyString).mapToLong(Long::parseLong).toArray();
        // long[] key = Stream.of(FindPrime.readFile(keyFile).split("
        // ")).mapToLong(Long::parseLong).toArray();

        System.out.println("==Block Split==");
        // find block size
        int blockSize = (int) (Math.log(key[0]-1) / Math.log(2));
        System.out.println("Block Size :" + blockSize);
    
        String binaryResult = FindPrime.convertStringToBinary(plaintext);
        System.out.println("Plaintext binary : " + binaryResult);
        // plaintext length
        int plainLength = binaryResult.length();

        /// 111 111 000 11
        String[] plaintextArray = BlockDivide(binaryResult, blockSize);
        System.out.println("Plaintext Array : " + Arrays.toString(plaintextArray));

        /// 111 111 000 110
        System.out.println("Plaintext Array After padding : " + Arrays.toString(padding(plaintextArray, blockSize)));

        // Convert string Binary To Decimal 0010 => 2
        long[] plaintextDecimal = new long[plaintextArray.length];
        for (int i = 0; i < plaintextArray.length; i++) {
            plaintextDecimal[i] = Long.parseLong(plaintextArray[i], 2);
        }

        System.out.println("==Encryption==");
        String CipherText = "";
        String CipherFileName = fileNameString;
        PrintWriter writerPublic = null;
        try {
            writerPublic = new PrintWriter(CipherFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Plaintext Decimal : " + Arrays.toString(plaintextDecimal));
        System.out.println("public key : " + Arrays.toString(key));

        // Cipher Text A,B
        for (int i = 0; i < plaintextDecimal.length; i++) {
            long k = 0;
            // random K , gcd(k,safeprime = 1)
            while (FindPrime.gcd(k, key[0] - 1) != 1) {
                k = 1 + (long) (Math.random() * (key[0]));
                // System.out.println("k: " + k);
            }

            // fine A = (g^k) % p
            long a = FindPrime.fastExponent(key[1], k, key[0]);
            // fine B = ((y^k) % p * plainText(x)) % p
            long b = (FindPrime.fastExponent(key[2], k, key[0]) * (plaintextDecimal[i] % key[0])) % key[0];
            // long[] cipher = { a, b };
            CipherText += a + " " + b + " ";
            writerPublic.flush();
            writerPublic.append(Long.toString(a) + " ");
            writerPublic.append(Long.toString(b) + " ");
        }
        CipherText += Long.toString(plainLength);
        writerPublic.append(Long.toString(plainLength));
        writerPublic.close();

        return CipherText;

    }

    public static String decrypt(String cipherText, String keyfile) {
        // String[] keyString = FindPrime.readFile(keyFile).split(" ");
        String[] cipherArray = FindPrime.readFile(cipherText).split(" ");
        // []String => []Long
        long[] cipherArrayL = Stream.of(cipherArray).mapToLong(Long::parseLong).toArray();
        System.out.println("cipher text : " + Arrays.toString(cipherArrayL));
        //find binary text length
        int plaintLength = (int) cipherArrayL[cipherArrayL.length - 1];
        long[] cipherLongArray = new long[cipherArrayL.length-1];
        for (int i = 0; i < cipherLongArray.length; i++) {
            cipherLongArray[i] = cipherArrayL[i];
        }
        System.out.println("cipherLongArray: "+Arrays.toString(cipherLongArray));

        // string [] key = read keyflie
        String[] key = FindPrime.readFile(keyfile).split(" ");
        // [] string => []long
        long[] keyLong = Stream.of(key).mapToLong(Long::parseLong).toArray();
        System.out.println("private key : " + Arrays.toString(key));

        // create [] for waiting input
        long[] plaintext = new long[cipherArray.length / 2];
        String[] BinaryArray = new String[cipherArray.length / 2];

        int blockSize = (int) ((Math.log(keyLong[0]-1)) / Math.log(2));
        System.out.println("Block size : " + blockSize);

        // find plain text
        for (int i = 0, j = 0; j < plaintext.length; i += 2, j++) {
            // Plaintext[base10] = (B / (A ^ U)) % P => (B * (A ^ (P - 1 - U))) % P
            plaintext[j] = (FindPrime.fastExponent(cipherLongArray[i], (keyLong[0] - 1 - keyLong[2]), keyLong[0])
                    * cipherLongArray[i + 1]) % keyLong[0];
            //long[base10] --> String[base2]
            //delete 0 by itSelf
            BinaryArray[j] = Long.toBinaryString(plaintext[j]);
        }

        System.out.println("Plain Text : " + Arrays.toString(plaintext));

        //add 0 to block Binary 
        //if we don't add 0, unpadding will error
        for (int i = 0; i < BinaryArray.length; i++) {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < blockSize - BinaryArray[i].length()) {
                sb.append('0');
            }
            sb.append(BinaryArray[i]);
            BinaryArray[i] = sb.toString();
        }
        System.out.println("Binary before delete padding: " + Arrays.toString(BinaryArray));
        
        //plaintext length is last value in cipher
        //unpadding
        //blockSize = 5
        //block = 5
        //blockSize * black = 25
        //plaintLength = 23
        
        int lengthPadding = (BinaryArray.length * blockSize) - plaintLength;
        int lastBlock = BinaryArray.length - 1;
        BinaryArray[lastBlock] = BinaryArray[lastBlock].substring(0, blockSize - lengthPadding);
        System.out.println("after delete padding : " + Arrays.toString(BinaryArray));
        String word = binaryToWord(BinaryArray);
        System.out.println("word= "+word);
        return word;
    }

    public static void stringToFile(String filename,String text){
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(text);
            myWriter.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public static String binaryToWord(String BinaryArray[]){
        String tempForJoin = "";
        //10101, 11011, 10101, 11011 => 10101110111010111011
        String resultWord = String.join(tempForJoin,BinaryArray);
        System.out.println("Binary text without separator : " + resultWord);
        //10101110111010111011 => 1010111011 1010111011
        // resultWord = SubStringByBlock(resultWord,8," ");
        // System.out.println("Binary text with separ : " + resultWord);
        //01101101 => m
        String plainText = "";
        while(resultWord.length()%8!=0){resultWord +="0";}
        System.out.println("resultWord: "+resultWord);
            for(int index = 0; index < resultWord.length(); index+=8) {
                String temp = resultWord.substring(index, index+8);
                int num = Integer.parseInt(temp,2);
                char letter = (char) num;
                plainText = plainText+letter;
            }
        return plainText;
    }

    // divide by blocksize
    public static String[] BlockDivide(String text, int blockSize) {
        String k = SubStringByBlock(text, blockSize, " ");
        // split string by " " fot convert to Array
        String[] plaintextString = k.split(" ");
        return plaintextString;
    }

    public static String SubStringByBlock(String binary, int blockSize, String separator) {
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