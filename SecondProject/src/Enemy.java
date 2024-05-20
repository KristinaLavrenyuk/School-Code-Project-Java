import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Enemy {
    final static int firstSpeed = 300;
    final static int firstTimeForCreation = 1700;

    static int creationCounter = 0;
    static int timeForCreation = firstTimeForCreation;
    static int acceleration = 1;
    static int delay = 9;
    static int flyingCounter = 0;
    static int movingLength = 3;
    static int speed = firstSpeed;

    final int maxSpeed = 700;
    final int minTimeForCreation = 500;
    final int accelerationPeriod = 500;
    final int flyingPeriod = 400;
    final int flyingTime = 250;

    int x = Game.WIDTH;
    int y = 400;
    int movingCounter = 0;
    int movingCounter2 = 0;
    int flyingType = 0;
    int flyingType2 = 0;

    Image image1;
    Image image2;

    public Enemy(String path, String path2) {
        BufferedImage sourceImage = null;
        BufferedImage sourceImage2 = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
            URL url2 = this.getClass().getClassLoader().getResource(path2);
            sourceImage2 = ImageIO.read(url2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image1 = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
        image2 = Toolkit.getDefaultToolkit().createImage(sourceImage2.getSource());
    }

    public void Draw(Graphics g) {
        if (flyingType == 0) {
            if (flyingCounter <= (flyingPeriod / 2)) {
                g.drawImage(image2,x,y,null);
            }
            else if (flyingCounter < flyingPeriod){
                g.drawImage(image1,x,y,null);
            }
            else flyingCounter = 0;
        }
        else {
            if (flyingCounter <= (flyingPeriod / 2 - 50)) {
                g.drawImage(image1,x,y,null);
            }
            else if (flyingCounter < (flyingPeriod - 50)){
                g.drawImage(image2,x,y,null);
            }
            else flyingCounter = 0;
        }
    }

    public void SetSpeed() {
        speed += 3;
        float del = 1000f / speed;
        if (del - (int)del >= 0 || del - (int)del <= 0.1) {
            delay = (int)del;
            movingLength = 1;
        }
        else {
            for (int i = 1; i < 10; i++) {
                if (del * (float)i - (int)del * (float)i >= 0 && del * (float)i - (int)del * (float)i <= 0.1) {
                    delay = (int)del * i;
                    movingLength = i;
                    break;
                }
            }
        }
        if (timeForCreation > 1) timeForCreation = (int)((float)Game.WIDTH / speed / 3 * 1000.0f);
    }

    public void Move() {
        if (acceleration >= accelerationPeriod) {
            if (speed < maxSpeed) SetSpeed();
            if (speed >= maxSpeed - 180) timeForCreation = minTimeForCreation;
            acceleration = 0;
        }

        if (movingCounter >= delay) {
            x-=movingLength;
            movingCounter = 0;
        }

        if (movingCounter2 >= flyingTime / 2) {
            switch (flyingType2) {
                case 0:
                    if (y > 5) y -= (int)(Math.random() * ((2 - 1) + 1));
                    break;
                case 1:
                    if (y < Game.HEIGHT - 160) y += (int)(Math.random() * ((2 - 1) + 1));
                    break;
            }

            if (movingCounter2 == flyingTime) {
                flyingType2 = (int)(Math.random() * 2);
                movingCounter2 = 0;
            }
        }
    }

    public static void CreateEnemy() {
        int flag = 0;

        if (creationCounter > timeForCreation) creationCounter = timeForCreation;
        if (creationCounter == timeForCreation) {
            for (int i = 0; i < Game.enemy.size(); i++) {
                if (Game.enemy.get(i).x < -100) {
                    flag = 1;
                    Game.enemy.set(i, new Enemy("Bird.png", "Bird2.png"));
                    Game.enemy.get(i).y = (int)(Math.random() * ((Game.HEIGHT - 120) - 1) + 1);
                    if (Math.abs(Game.enemy.get(i).y - Plain.y) >= 200) {
                        if (Game.enemy.get(i).y - Plain.y < 0) Game.enemy.get(i).y += 150;
                        else Game.enemy.get(i).y -= 150;
                    }
                    Game.enemy.get(i).flyingType = (int)(Math.random() * 2);
                    break;
                }
            }
            if (flag == 0) {
                Game.enemy.add(new Enemy("Bird.png", "Bird2.png"));
                Game.enemy.getLast().y = (int)(Math.random() * ((Game.HEIGHT - 120) - 1) + 1);
                Game.enemy.getLast().flyingType = (int)(Math.random() * 2);
            }
            creationCounter = 0;
        }
    }
}
