public interface SnakeComponent {
    /**
     * Moves whole snake to direction given
     * @param newDirection Direction to move towards
     */
    void move(Direction newDirection);
    boolean checkCollision();
    boolean checkCollision(Point point);
    /**
     * Add new tail in the end of snake (MOVE REQUIRED before spawning new tail)
     */
    void addTail();
    /**
     * @return SnakeComponent or null if ended
     */
    SnakeComponent getTail();
    int getSize();
    Point getPosition();
}
