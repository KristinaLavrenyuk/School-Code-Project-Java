import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Lives {
    static int lives = 3;
    static int blinkingFlag = 0;
    static int blinkingCounter = 499;

    int x = 0;
    int y = 0;

    Image image;

    public Lives(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        image = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public void Draw(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawImage(image,x + 70 * i, y,null);
        }
    }
    public static void Disappear() {
        if (blinkingCounter % 500 == 0) {
            lives++;
        }
        else if (blinkingCounter % 250 == 0) {
            lives--;
        }
        if (blinkingCounter == 1750) {
            blinkingCounter = 499;
            blinkingFlag = 0;
        }
    }
}
