import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Menu {
    final int x = 600;
    final int y = 200;

    int choice = 1;

    Image image1;
    Image image2;
    Image image3;
    Image image4;

    public Menu(String path, String path2, String path3, String path4) {
        BufferedImage sourceImage = null;
        BufferedImage sourceImage2 = null;
        BufferedImage sourceImage3 = null;
        BufferedImage sourceImage4 = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
            URL url2 = this.getClass().getClassLoader().getResource(path2);
            sourceImage2 = ImageIO.read(url2);
            URL url3 = this.getClass().getClassLoader().getResource(path3);
            sourceImage3 = ImageIO.read(url3);
            URL url4 = this.getClass().getClassLoader().getResource(path4);
            sourceImage4 = ImageIO.read(url4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image1 = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
        image2 = Toolkit.getDefaultToolkit().createImage(sourceImage2.getSource());
        image3 = Toolkit.getDefaultToolkit().createImage(sourceImage3.getSource());
        image4 = Toolkit.getDefaultToolkit().createImage(sourceImage4.getSource());
    }

    public void Draw(Graphics g) {
        if (choice == 1) {
            g.drawImage(image2, x, y, null);
            g.drawImage(image3, x, y + 200, null);
        }
        else if (choice == 0) {
            g.drawImage(image1, x, y, null);
            g.drawImage(image4, x, y + 200, null);
        }
        else {
            g.drawImage(image1, x, y, null);
            g.drawImage(image3, x, y + 200, null);
        }
    }
    public int Choose(boolean upPressed, boolean downPressed, boolean enterPressed, Graphics g) {
        if (upPressed) {
            choice = 1;
        }
        if (downPressed) {
            choice = 0;
        }
        if (enterPressed) {
            return choice;
        }
        else return -1;
    }
}
