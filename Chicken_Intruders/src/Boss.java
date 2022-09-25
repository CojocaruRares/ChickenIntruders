import java.awt.*;
import java.util.Random;

public class Boss extends Character {
    static int k = 0;
    boolean moveRight;
    boolean moveLeft;
    boolean isVisible;
    int Health;

    public Boss(int x, int y, int width, int height, int speed, int Health) {
        super(x, y, width, height, speed);
        moveLeft = false;
        moveRight = true;
        isVisible = false;
        this.Health = Health;
    }

    public void Draw(Graphics g1, Image img) {

        g1.drawImage(img, super.x, super.y, super.width, super.height, null);

    }

    public void Move() {
        if (this.moveLeft) {
            this.x -= this.speed;
        }
        if (this.moveRight) {
            this.x += this.speed;
        }
        if (this.x > 1200 - this.width) {
            this.moveLeft = true;
            this.moveRight = false;
        }

        if (this.x < 0) {
            this.moveRight = true;
            this.moveLeft = false;
        }
    }

    public void FlyingChicken(Graphics g, Image[] chicken_img) {
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

    public void DropEgg(Graphics g, Shot BigEgg, Random r) {
        if (r.nextInt(50) < 40 && !BigEgg.goDown) {
            BigEgg.x = this.x + this.width / 2;
            BigEgg.y = this.y + this.height;
            BigEgg.goDown = true;
        }
    }

    public Boolean isDead() {
        return this.Health <= 0;
    }
}
