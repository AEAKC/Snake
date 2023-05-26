public interface SnakeComponent {
    void move(Direction direction);
    boolean checkCollision();
    boolean checkCollision(Point point);
    void addTail();
    int getSize();
}
