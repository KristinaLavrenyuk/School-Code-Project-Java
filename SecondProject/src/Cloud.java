import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Cloud {
    static int x = 0;
    static int y = 400;

    Image image;

    public Cloud(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public void Draw(Graphics g, int x, int y) {
        g.drawImage(image,x,y,null);
    }
}
