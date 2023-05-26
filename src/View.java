import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.PublicKey;

public class View extends JPanel implements Runnable {
    final int originalTitleSize = 16; //snake size 16x16
    final int scale = 3;//scale snake
    final int tileSize = originalTitleSize * scale; //48x48
    final Point gridSize;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    int FPS = 2;


    public View(Point gridSize) {
        this.gridSize = gridSize;
        this.setPreferredSize(new Dimension(gridSize.x * tileSize, gridSize.y * tileSize));
        this.setBackground(Color.decode("#fd79a8"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();



    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            Controller.getInstance().update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void drawHead(Graphics g) {
        Image img = null;
        System.out.println(SnakeHead.getInstance().getPosition().y);
        try {
            switch (SnakeHead.getInstance().getDirection()){
                case DOWN->
                    img = ImageIO.read(new File("resources/headU.png"));
                case UP->
                    img = ImageIO.read(new File("resources/headD.png"));
                case LEFT->
                    img = ImageIO.read(new File("resources/headL.png"));
                case RIGHT->
                    img = ImageIO.read(new File("resources/headR.png"));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, SnakeHead.getInstance().getPosition().x * tileSize, SnakeHead.getInstance().getPosition().y * tileSize, tileSize, tileSize, null);

    }


    private void drawTail(Graphics g) {
        super.paintComponent(g);
        SnakeComponent snakeTail =  SnakeHead.getInstance().getTail();
        Graphics2D g2 = (Graphics2D) g;
        System.out.println(snakeTail.getPosition().y);
        while (snakeTail !=null) {
            Image img = null;
            try {
                switch (snakeTail.getDirection()) {
                    case DOWN -> img = ImageIO.read(new File("resources/bodyU.png"));
                    case UP -> img = ImageIO.read(new File("resources/bodyD.png"));
                    case LEFT -> img = ImageIO.read(new File("resources/bodyL.png"));
                    case RIGHT -> img = ImageIO.read(new File("resources/bodyR.png"));
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            g2.drawImage(img, snakeTail.getPosition().x * tileSize, snakeTail.getPosition().y * tileSize, tileSize, tileSize, null);
            snakeTail = snakeTail.getTail();
        }


    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHead(g);
        drawTail(g);
        System.out.println("------------------------------");
    }
}
