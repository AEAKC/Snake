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
    int FPS = 3;


    public View(Point gridSize) {
        this.gridSize = gridSize;
        this.setPreferredSize(new Dimension(gridSize.x * tileSize, gridSize.y * tileSize + 100));
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
            if(!Controller.getInstance().update())
            {
                break;
            }

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


    private void drawHead(Graphics2D g2) {
        Image img = null;
        System.out.println(SnakeHead.getInstance().getPosition().y);
        try {
            switch (SnakeHead.getInstance().getDirection()){
                case UP->
                    img = ImageIO.read(new File("resources/headU.png"));
                case DOWN->
                    img = ImageIO.read(new File("resources/headD.png"));
                case LEFT->
                    img = ImageIO.read(new File("resources/headL.png"));
                case RIGHT->
                    img = ImageIO.read(new File("resources/headR.png"));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        g2.drawImage(img, SnakeHead.getInstance().getPosition().x * tileSize, SnakeHead.getInstance().getPosition().y * tileSize, tileSize, tileSize, null);

    }


    private void drawTail(Graphics2D g2) {
        SnakeComponent snakeTail =  SnakeHead.getInstance().getTail();
        System.out.println(snakeTail.getPosition().y);
        while (snakeTail !=null) {
            Image img = null;
            try {
                switch (snakeTail.getDirection()) {
                    case UP -> img = ImageIO.read(new File("resources/bodyU.png"));
                    case DOWN -> img = ImageIO.read(new File("resources/bodyD.png"));
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


    public void drawFruits(Graphics g2)
    {
        Image img = null;
        for (BaseFruit fruit:
             Controller.getInstance().getFruits()) {
            try {
                img = ImageIO.read(new File(fruit.imagePath));
            }
            catch (Exception e){
                System.out.println(e);
            }
            System.out.println(fruit);
            g2.drawImage(img, fruit.getPosition().x * tileSize, fruit.getPosition().y * tileSize, tileSize,tileSize,null);
        }
    }


    public void addScore(Graphics2D g2)
    {
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g2.drawString("Score: " + SnakeHead.getInstance().getSize().toString(),0,gridSize.y * tileSize +60);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawHead(g2);
        drawTail(g2);
        drawFruits(g2);
        drawGrid(g2);
        addScore(g2);
        g2.dispose();
        System.out.println("------------------------------");
    }

    private void drawGrid(Graphics2D g2) {
        for(int i=0;i<gridSize.x;i++){
            for (int j=0;j<gridSize.y;j++)
                g2.drawRect(i*tileSize, j*tileSize, tileSize,tileSize);
        }
    }
}
