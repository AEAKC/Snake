/**
 * SNAKE USAGE:
 * 1. Set field dimensions for head (SnakeHead.setDimensions)
 * 2. Get snake instance
 * 3. Spawn and draw snake
 * 4. On each frame:
 *      4.1 Move snake
 *      4.2 Check if snake eaten apple (apple must check)
 *      4.3 Spawn tail if needed
 *      4.4 Check collision
 *      4.5 Spawn new apple if needed and check collision with snake before spawning (apple must check)
 */
public class SnakeHead implements SnakeComponent {
    private static SnakeHead instance;
    private Point position;
    private Direction direction;
    private SnakeComponent tail;
    private static Point DIMENSIONS;
    private SnakeHead(){
        if (DIMENSIONS == null){
            throw new NullPointerException("DIMENSIONS not provided. Maybe you forgot to SnakeHead.setDIMENSIONS");
        }
        position = new Point(DIMENSIONS.x/2, DIMENSIONS.y/2); // Spawn on center
        direction = Direction.DOWN; // Default direction is down(UP)
    }

    public static SnakeHead getInstance() {
        if (instance==null)
            instance = new SnakeHead();
        return instance;
    }

    static void setDimensions(int X, int Y){
        DIMENSIONS = new Point(X, Y);
    }


    @Override
    public void move(Direction newDirection) {
        switch (newDirection){
            case UP -> position.y++;
            case DOWN -> position.y--;
            case LEFT -> position.x--;
            case RIGHT -> position.x++;
        }
        if (tail!=null)
            tail.move(direction); // Tail must move in old direction first, and turn one step later
        direction = newDirection;
    }

    /**
     * Moves whole snake towards old direction
     */
    public void move() {
        move(direction);
    }

    public Point getPosition() {
        return position;
    }


    /**
     * Checks collision with walls and tail
     * @return true if no collision found
     */
    @Override
    public boolean checkCollision() {
        if (!checkWallsCollision()) return false;
        else if (tail!=null) return tail.checkCollision();
        return true;
    }


    @Override
    public boolean checkCollision(Point point) {
        if (!point.equals(position)){
            if (tail!=null) return tail.checkCollision(point);
            return true;
        }
        return false;
    }

    private boolean checkWallsCollision() {
        return position.x <= DIMENSIONS.x && position.x >= 0
                && position.y<= DIMENSIONS.y && position.y>=0;
    }


    @Override
    public void addTail() {
        if (tail == null){
            Point spawnPoint = new Point(position);
            // Spawn happens after move, so this previous position of the tail must be empty
            switch (direction){
                case UP -> spawnPoint.y--;
                case DOWN -> spawnPoint.y++;
                case LEFT -> spawnPoint.x++;
                case RIGHT -> spawnPoint.x--;
            }
            tail = new SnakeTail(spawnPoint, direction);
        }
        else tail.addTail();
    }


    @Override
    public SnakeComponent getTail() {
        return tail;
    }

    @Override
    public Integer getSize() {
        if (tail==null) return 1;
        return tail.getSize()+1;
    }

    public void setDirection(Direction direction) {
        if (this.direction.getVal()!=-direction.getVal())
            this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
