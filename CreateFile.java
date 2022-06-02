import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {
    public static void main(String[] args) {
        String filename = "b.txt";
        String text = FindPrime.readFile("plaintext.txt");
        //String text = "mcca\ndasda\ndas\n*-*";
        stringToFile(filename, text);
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
}
