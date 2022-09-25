import java.awt.*;

public class Shot extends Player {
    boolean goUp;
    boolean goDown;
    public Shot() {
        super();
    }

    public Shot(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
        goUp = false;
        goDown = false;
    }

    public void move() {
        if (y < -20) {
            goUp = false;
        }
        if (y > 920) {
            goDown = false;
        }
        if (goUp) {
            y = y - speed;
        }
        if (goDown) {
            y = y + speed;
        }
    }

    public void Draw(Graphics g1, Image img) {

        g1.drawImage(img, super.x, super.y, super.width, super.height, null);

    }
}
