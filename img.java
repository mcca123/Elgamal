import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


// import org.apache.commons.codec.binary.Base64;

public class img {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //inputName
        System.out.print("input img file name : ");
        String imgName = sc.nextLine();
        String base64Img = imgToBase64(imgName);
        System.out.println(base64Img);

        // String encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");


    }
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
}
