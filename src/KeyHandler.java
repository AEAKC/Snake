import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_DOWN) {
            SnakeHead.getInstance().setDirection(Direction.UP);
        }
        if (code == KeyEvent.VK_UP) {
            SnakeHead.getInstance().setDirection(Direction.DOWN);

        }
        if (code == KeyEvent.VK_LEFT) {
            SnakeHead.getInstance().setDirection(Direction.LEFT);

        }
        if (code == KeyEvent.VK_RIGHT) {
            SnakeHead.getInstance().setDirection(Direction.RIGHT);

        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
