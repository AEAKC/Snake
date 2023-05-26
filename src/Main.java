import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        //Start window
        SnakeHead.setDimensions(18,16);
        FruitFactory.setDimensions(new Point(18,16));
        Controller.setFruitFactory(new FruitFactory());

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Snake-game");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        View view = new View(new Point(18,16));
        window.add(view);
        window.pack();
        view.startGameThread();



    }
}