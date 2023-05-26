public class SnakeTail implements SnakeComponent {
    private Point position;
    private Direction direction;
    private SnakeTail tail;
    SnakeTail(Point position, Direction direction){
        this.position = new Point(position);
        this.direction = direction;
    }
    @Override
    public void move(Direction newDirection){
        switch (direction){
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
     * Checks collision with head
     * @return true if no collision found
     */
    @Override
    public boolean checkCollision() {
        return checkCollision(SnakeHead.getInstance().getPosition());
    }


    /**
     * Checks collision against point
     * @param point point to check collision with
     * @return true if no collision found
     */
    @Override
    public boolean checkCollision(Point point) {
        if (point.equals(position)) return false;
        if (tail==null) return true;
        return tail.checkCollision();
    }

    /**
     * Add new tail in the end of snake (MOVE REQUIRED before spawning new tail)
     */
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
    public int getSize() {
        if (tail!=null) return tail.getSize()+1;
        return 1;
    }
    @Override
    public SnakeComponent getTail() {
        return tail;
    }

    public Point getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
