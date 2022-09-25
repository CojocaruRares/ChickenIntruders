import javax.swing.*;
import java.awt.*;

public class Player extends Character {
    boolean MoveRight;
    boolean MoveLeft;
    boolean isVisible;

    public Player() {
    }

    public Player(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
        MoveRight = false;
        MoveLeft = true;
        isVisible = true;
    }

    public void Draw(Graphics g1, Image img) {

        g1.drawImage(img, super.x, super.y, super.width, super.height, null);

    }
}