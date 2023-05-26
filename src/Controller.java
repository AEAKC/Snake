import java.util.ArrayList;

public class Controller {
    private final int FRUITS_GROUP = 3;
    private int addTail=0;
    private SnakeHead head = SnakeHead.getInstance();
    private ArrayList<BaseFruit> fruits = new ArrayList<BaseFruit>();
    private FruitFactory fruitFactory;
    Controller(FruitFactory factory){
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
            fruitFactory.createFruits(FRUITS_GROUP);
        }
        return isOver;
    }
}
