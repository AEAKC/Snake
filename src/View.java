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
    int FPS = 1;


    public View(Point gridSize) {
        this.gridSize = gridSize;
        this.setPreferredSize(new Dimension(gridSize.x * tileSize, gridSize.y * tileSize));
        this.setBackground(Color.decode("#fd79a8"));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        this.addKeyListener(keyH);

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
        g2.setColor(Color.black);
        g2.drawImage(img, SnakeHead.getInstance().getPosition().x * tileSize, SnakeHead.getInstance().getPosition().y * tileSize, tileSize, tileSize, null);
        g2.dispose();
    }


    private void drawTail() {

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHead(g);
    }
}
