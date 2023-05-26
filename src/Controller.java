import java.util.ArrayList;

public class Controller {
    private final int FRUITS_GROUP = 3;
    private int addTail=3;
    private SnakeHead head = SnakeHead.getInstance();
    private ArrayList<BaseFruit> fruits = new ArrayList<>();
    private static FruitFactory fruitFactory;
    private static Controller controller;
    private Controller(){
        if (fruitFactory==null)
            throw new NullPointerException("fruitFactory is not initialize, maybe you forgot to Contoller.setFruitFactory");
    }
    public static Controller getInstance() {
        if (controller==null)
            controller=new Controller();
        return controller;
    }
    public static void setFruitFactory(FruitFactory factory){
        fruitFactory=factory;
    }
    public boolean update(){
        head.move(); // Move snake
        for (int i=0;i<fruits.size();) {
            if (fruits.get(i).checkCollision()) {
                addTail+=fruits.get(i).score;
                fruits.remove(i);
            }
            else i++;
        }
        if (addTail>0){
            head.addTail();
            addTail--;
        }
        boolean notOver = head.checkCollision();
        if (notOver && fruits.isEmpty()){
            fruits.addAll(fruitFactory.createFruits(FRUITS_GROUP));
        }
        return notOver;
    }

    public ArrayList<BaseFruit> getFruits() {
        return fruits;
    }
}
