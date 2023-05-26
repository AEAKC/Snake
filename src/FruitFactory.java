import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class FruitFactory{
    private static Point DIMENSIONS;


    public static void setDimensions(Point dimensions) {
        DIMENSIONS = new Point(dimensions);
    }
    public Point findPosition(){
        int x = ThreadLocalRandom.current().nextInt(0, DIMENSIONS.x);
        int y = ThreadLocalRandom.current().nextInt(0, DIMENSIONS.y);
        Point position = new Point(x,y);
        while(!SnakeHead.getInstance().checkCollision(position)){
            x = ThreadLocalRandom.current().nextInt(0, DIMENSIONS.x);
            y = ThreadLocalRandom.current().nextInt(0, DIMENSIONS.y);
        }
        return position;
    }


    public BaseFruit createApple(Point pos){
        return new Apple(pos);
    }


    public BaseFruit createBanana(Point pos){
        return new Banana(pos);
    }

    public BaseFruit createOrange(Point pos){
        return new Orange(pos);
    }

    ArrayList<BaseFruit> createFruits(int count){
        if(DIMENSIONS.x* DIMENSIONS.y<SnakeHead.getInstance().getSize()+count){
            count=DIMENSIONS.x* DIMENSIONS.y-SnakeHead.getInstance().getSize();
        }
        ArrayList<Point> positions=new ArrayList<>();
        ArrayList<BaseFruit> fruits=new ArrayList<>();
        for(int i=0; i<count;i++){
            int cnt=0;
            Point newPoint=findPosition();
            while (cnt<positions.size()){
                if (newPoint.equals(positions.get(cnt))){
                    newPoint = findPosition();
                    cnt=0;
                } else cnt++;
            }
            positions.add(newPoint);
        }
        for (Point position:
                positions) {
            int rand = ThreadLocalRandom.current().nextInt(0, 10);
            if (rand<5){
                fruits.add(createApple(position));
            } else if (rand<8) {
                fruits.add(createOrange(position));
            }else fruits.add(createBanana(position));
        }
        return fruits;
    }

}