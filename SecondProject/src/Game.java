import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {
    private boolean running;
    Thread thread;

    private Moving moving = new Moving();

    public static int WIDTH = 1500;
    public static int HEIGHT = 700;
    public static int choice = -1;
    public static int startingFlag = 0;
    public static String NAME = "Plain vs Pterodactyls";

    JFrame frame = new JFrame(NAME);

    public static Plain plain;
    public static Lives lives;
    public static ArrayList<Enemy> enemy = new ArrayList<>();
    public static Menu menu;

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (running) {
            render();
            if (choice == 1) {
                plain.Move(moving.leftPressed, moving.rightPressed, moving.upPressed, moving.downPressed);
                Enemy.CreateEnemy();
                for (int i = 0; i < enemy.size(); i++) {
                    enemy.get(i).movingCounter++;
                    enemy.get(i).movingCounter2++;
                    enemy.get(i).Move();
                }
                plain.movingCounter++;
                Enemy.creationCounter++;
                Enemy.flyingCounter++;
                Enemy.acceleration++;
                plain.Breaking();
                if (Lives.lives == 0) choice = 0;
            }
            else if (choice == 0) break;
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        frame.dispose();
    }

    public void init() {
        addKeyListener(moving);
        plain = new Plain("plain.png");
        enemy.add(new Enemy("Bird.png", "Bird2.png"));
        lives = new Lives("heart.png");
        menu = new Menu("start1.png", "start2.png", "close1.png", "close2.png");
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0, 190, 220));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (choice == 1) {
            plain.Draw(g);
            for (int i = 0; i < enemy.size(); i++) {
                enemy.get(i).Draw(g);
            }
            lives.Draw(g);
        }
        else {
            if (startingFlag == 1) Reset();
            menu.Draw(g);
            choice = menu.Choose(moving.upPressed, moving.downPressed, moving.enterPressed, g);
            if (choice == 1) startingFlag = 1;
        }
        g.dispose();
        bs.show();
    }

    void Reset() {
        Lives.lives = 3;
        Enemy.timeForCreation = Enemy.firstTimeForCreation;
        Enemy.speed = Enemy.firstSpeed;
        Plain.x = 100;
        enemy.clear();
    }

    void Frame() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
