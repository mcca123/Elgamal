import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Map;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindPrime {
    static Scanner sc = new Scanner(System.in);
    public static String filename;
    public static int bit;

    public static void getInput() {
        System.out.print("input filename : ");
        filename = sc.nextLine() + ".txt";
    }

    public static void printResult(String text) {
        // print text
        System.out.println(text);
    }

    public static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();

        // append text in textfile to string birany
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar)) // char -> int, auto-cast
                            .replaceAll(" ", "0") // zero pads
            );
        }
        return result.toString();

    }

    public static String readFile(String filename) {
        String data = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            // we use While Loop because if textfile have nextLine while loop can fix it
            while (myReader.hasNextLine()) {
                System.out.println("read Line");
                data += myReader.nextLine();
                if (myReader.hasNextLine()) {
                    System.out.println("have next Line");
                    data += "\n";
                }
            }
            System.out.println("data : ");
            System.out.println(data);
            // System.out.println("Number of bits is : " + bit);
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, File is not found");
            e.printStackTrace();
        }
        return data;
    }

    public static char[] convertStringToArray(String Binary) {

        String str = Binary;

        // Creating array of string length
        char[] ch = new char[str.length()];

        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }

        return ch;
    }

    public static String selectBit(char[] ch) {
        int countZero = 0;

        // Check 0 in front until find 1
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '1') {
                break;
            } else if (ch[i] == '0') {
                countZero++;
            }
        }

        // accept Bit is bits without 0 in front
        int acceptBit = ch.length - countZero;

        // we accept Bit 0-31
        if (acceptBit > 31) {
            acceptBit = 31;
        }

        // bit input
        System.out.println("Accept Bit range is (1," + acceptBit + ")");
        System.out.print("input bit length : ");
        bit = sc.nextInt();

        // if bit input is error, try aggin unit it correct
        while (bit > acceptBit || bit <= 0 || bit > 31) {
            System.out.println("Accept Bit range is (1," + acceptBit + ")");
            System.out.print("input bit length : ");
            bit = sc.nextInt();
        }

        // binary that we select from Bit length without 0 in front
        String tempBit = "";
        for (int i = countZero; i <= bit; i++) {
            tempBit += ch[i];
        }
        System.out.println("Select Bit : " + tempBit);
        return tempBit;

    }

    public static long convertStringBinaryToDecimal(String Bit) {
        // Convert string Binary To Decimal
        long foo = Long.parseLong(Bit, 2);
        return foo;
    }

    private static boolean findPrime(long n) {
        // find prime
        if (n % 2 == 0)
            return false;
        boolean isPrime = false;
        long leftLimit = 1L;
        long rightLimit = n;
        long a;

        for (int i = 1; i <= 100; i++) {
            // random a
            a = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            // System.out.println("result"+fastExponent(a, (n - 1) / 2, n));
            if (n / a == 0) {
                isPrime = false;
                break;
            } else {
                if (gcd(a, n) > 1) {
                    isPrime = false;
                    break;
                } else if (fastExponent(a, (n - 1) / 2, n) == 1 || fastExponent(a, (n - 1) / 2, n) == n - 1) {
                    isPrime = true;
                } else {
                    isPrime = false;
                    break;
                }
            }
        }

        return isPrime;

    }

    public static long findSafePrime(long decimal) {
        while (true) {
            System.out.println("find prime of " + decimal);
            if (findPrime(decimal)) {
                System.out.print(decimal + " is prime");
                if (findPrime((decimal - 1) / 2)) {
                    System.out.println(" and safe prime");
                    break;
                } else
                    System.out.println(" but not safe prime");
            } else {
                System.out.println(decimal + " is not prime");
            }
            if (decimal % 2 == 0)
                decimal += 1;
            else
                decimal += 2;
        }
        return decimal;
    }

    public static long gcd(long n1, long n2) {
        // find gcd
        long gcd = 0;
        long q = n1 / n2;
        long r = n1 % n2;
        long a1 = 1, a2 = 0, b1 = 0, b2 = 1, t;

        while (r != 0) {
            n1 = n2;
            n2 = r;
            t = a2;
            a2 = a1 - q * a2;
            a1 = t;
            t = b2;
            b2 = b1 - q * b2;
            b1 = t;
            q = n1 / n2;
            r = n1 % n2;
        }
        gcd = n2;
        return gcd;

    }

    public static long fastExponent(long base, long expo, long mod) {
        // find power

        long result = 1;
        while (expo > 0) {
            // if last bit is 0
            if ((expo & 1) != 0) {
                result = (result * base) % mod;
            }

            // base = fastExponent(base % mod, 2, mod) % mod;
            base = /* (base*base) % mod; */((base % mod) * (base % mod)) % mod;
            expo = expo >> 1;

        }

        return result;
    }

    public static long inverse(long mod, long n) {
        long inverse = 0;
        long r1 = mod;
        long r2 = n;
        long t1 = 0;
        long t2 = 1;
        long q;
        long r;
        long t;

        while (r2 > 0) {
            q = r1 / r2;
            r = r1 - q * r2;
            r1 = r2;
            r2 = r;
            t = t1 - q * t2;
            t1 = t2;
            t2 = t;
        }
        if (r1 == 1) {
            inverse = t1;
        }
        if (inverse < 0) {
            inverse = mod + ((inverse) % mod);
        }

        return inverse;
    }
}