import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class KeyGen {
    public static void main(String[] args) {
        System.out.println("==KEY GENERATOR==");
        long p = 11L;
        long keygen = KeyGenerator(p);
        Map<String, Long> key = GenKey(keygen, p);
        Map<String, String> keyBinary = KeyTobinary(key);
        TextFliePublicKey(keyBinary);
        TextFliePrivateKey(keyBinary);

    }

    public static long KeyGenerator(long p) {
        System.out.println("Key Generator");
        long keyGen;
        long leftLimit = 2L;
        long rightLimit = p - 1;
        long a = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        System.out.println("a: " + a);

        
        System.out.println(a + "^" + (p - 1) / 2 + "%" + p + " = " + FindPrime.fastExponent(a, (p - 1) / 2, p));
        if (FindPrime.fastExponent(a, (p - 1) / 2, p) != 1 % p) {

            System.out.println( a + " is key generator");
        } else {
            System.out.println("-" + a + " % " + p + " is key generator");
            a = p + ((a * (-1)) % p);

            System.out.println(a + " is key generator");
        }
        keyGen = a;

        return keyGen;

    }

    public static Map<String, Long> GenKey(long g, long p) {
        System.out.println("Generate Key");
        long leftLimit = 1L;
        long rightLimit = p;
        long u = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        Map<String, Long> key = new HashMap<>();

        long y = FindPrime.fastExponent(g, u, p);

        key.put("p", p);
        key.put("g", g);
        key.put("y", y);
        key.put("u", u);

        System.out.println("p :" + key.get("p"));
        System.out.println("g :" + key.get("g"));
        System.out.println("y :" + key.get("y"));
        System.out.println("u :" + key.get("u"));

        return key;

    }

    //covent to text file public Key
    public static void TextFliePublicKey(Map<String, String> keyBinary){
        String fileNamePublic = "publicKey.txt";
        
        PrintWriter writerPublic = null;
        
        try{
            writerPublic = new PrintWriter(fileNamePublic);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        writerPublic.flush();
        writerPublic.append(keyBinary.get("p") + " ");
        writerPublic.append(keyBinary.get("g") + " ");
        writerPublic.append(keyBinary.get("y") + " ");
        writerPublic.close();
    }
    
    //covent to text file private Key
    public static void TextFliePrivateKey(Map<String, String> keyBinary){
        String fileNameSecret = "privateKey.txt";

        PrintWriter writerSecret = null;
        
        try{
            writerSecret = new PrintWriter(fileNameSecret);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        writerSecret.flush();
        writerSecret.append(keyBinary.get("p") + " ");
        writerSecret.append(keyBinary.get("g") + " ");
        writerSecret.append(keyBinary.get("u") + " ");
        writerSecret.close();
    }

    public static Map<String, String> KeyTobinary(Map<String, Long> key){
            
        Map<String, String> keyBinary = new HashMap<>();

        keyBinary.put("p", Long.toBinaryString(key.get("p")));
        keyBinary.put("g", Long.toBinaryString(key.get("g")));
        keyBinary.put("y", Long.toBinaryString(key.get("y")));
        keyBinary.put("u", Long.toBinaryString(key.get("u")));

        return keyBinary;
    }

}
