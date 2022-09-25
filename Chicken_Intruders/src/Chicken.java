import javax.swing.*;
import java.awt.*;

public class Chicken extends Character {
    static int k = 0;
    boolean moveRight;
    boolean moveLeft;
    boolean isVisible;

    public Chicken(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
        moveLeft = false;
        moveRight = true;
        isVisible = true;
    }

    public void Draw(Graphics g1, Image img) {

        g1.drawImage(img, super.x, super.y, super.width, super.height, null);

    }

    void FlyingChicken(Graphics g, Image[] chicken_img) {
        long chicken_loop = System.currentTimeMillis();
        if (chicken_loop % 2 == 0) {
            if (k < 3)
                k++;
            else
                k = 0;
        }

        switch (k) {
            case 0:
                this.Draw(g, chicken_img[0]);
                break;
            case 1:
                this.Draw(g, chicken_img[1]);
                break;
            case 2:
                this.Draw(g, chicken_img[2]);
                break;
            case 3:
                this.Draw(g, chicken_img[3]);
                break;
            default:
                this.Draw(g, chicken_img[0]);
        }
    }

}



