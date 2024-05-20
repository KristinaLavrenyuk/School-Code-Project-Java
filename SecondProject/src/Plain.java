import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Plain {
    static int x = 100;
    static int y = 400;

    int movingCounter = 0;
    int touchingWithBird = -1;

    Image image;

    public Plain(String path) {
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
        g.drawImage(image,x,y,null);
    }

    public void Move(boolean leftPressed, boolean rightPressed, boolean upPressed, boolean downPressed) {
        if (movingCounter == 3) {
            if (leftPressed) if (x > 0) x -= 3;
            if (rightPressed) if (x < Game.WIDTH - 240) x += 3;
            if (upPressed) if (y > 0) y -= 3;
            if (downPressed) if (y < Game.HEIGHT - 120) y += 3;
            movingCounter = 0;
        }
    }

    public void Breaking() {
        for (int i = 0; i < Game.enemy.size(); i++) {
            if (Math.abs(Game.enemy.get(i).y - y) >= 0 && Math.abs(Game.enemy.get(i).y - y) <= 110
                    && Math.abs(Game.enemy.get(i).x - x) >= 0 && Math.abs(Game.enemy.get(i).x - x) <= 180) {
                if (touchingWithBird != i) {
                    if (Lives.lives > 0) Lives.lives--;
                    Lives.blinkingFlag = 1;
                }
                touchingWithBird = i;
            }
            if (Lives.blinkingFlag == 1) {
                Lives.Disappear();
                Lives.blinkingCounter++;
            }
        }
    }
}
