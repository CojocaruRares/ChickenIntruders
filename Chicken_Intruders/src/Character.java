import javax.swing.*;
import java.awt.*;

public abstract class Character {
    int x;
    int y;
    int speed;
    int width;
    int height;

    public Character() {
    }

    public Character(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public abstract void Draw(Graphics g1, Image img);


}