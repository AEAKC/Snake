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
        for (BaseFruit fruit: // Check if any apples are eaten
             fruits) {
            if (fruit.checkCollision()) {
                fruits.remove(fruit);
                addTail+=fruit.score;
            }
        }
        if (addTail>0){
            head.addTail();
            addTail--;
        }
        boolean isOver = head.checkCollision();
        if (!isOver && fruits.isEmpty()){
            fruits.addAll(fruitFactory.createFruits(FRUITS_GROUP));
        }
        return isOver;
    }

    public ArrayList<BaseFruit> getFruits() {
        return fruits;
    }
}
