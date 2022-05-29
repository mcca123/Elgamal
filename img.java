import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayInputStream;
import sun.misc.BASE64Decoder;
import java.util.Arrays;

public class Img {
    public static String imgToBase64(String imgName){
        String resultantimage = "";

        try {
            BufferedImage sourceimage = ImageIO.read(new File(imgName));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(sourceimage, "png", bytes);
            resultantimage = Base64.encode(bytes.toByteArray());
          } catch (Exception e) {
            e.printStackTrace();
          }

        return resultantimage;
    }

    public static void decodeToImage(String base64String,String fileName ) {
          //read Flie
          String base64 = FindPrime.readFile(base64String);
      try {
          BASE64Decoder decoder = new BASE64Decoder();
          byte[] decodedBytes = decoder.decodeBuffer(base64);
          System.out.println(Arrays.toString(decodedBytes));
          
          BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
          File f = new File(fileName);
          ImageIO.write(image, "png", f);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
