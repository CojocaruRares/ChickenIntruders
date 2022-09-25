import javax.swing.*;
import java.awt.*;
import java.util.Random;

public interface Commons {


    boolean ingame = true;
    Random rand = new Random();
    int BOARD_WIDTH = 1200;
    int BOARD_HEIGHT = 900;
    Chicken[] chickens = new Chicken[30];

    Shot[] egg = new Shot[chickens.length];
    Image asteroid = new ImageIcon("SpriteSheet/asteroid.png").getImage();
    Image player_img = new ImageIcon("SpriteSheet/spacesheep.png").getImage();
    Image background_img = new ImageIcon("SpriteSheet/space1.png").getImage();
    Image[] chicken_img = {
            new ImageIcon("SpriteSheet/chicken0.png").getImage(),
            new ImageIcon("SpriteSheet/chicken1.png").getImage(),
            new ImageIcon("SpriteSheet/chicken2.png").getImage(),
            new ImageIcon("SpriteSheet/chicken3.png").getImage(),
    };
    Image shot_img = new ImageIcon("SpriteSheet/shot.png").getImage();
    Image egg_img = new ImageIcon("SpriteSheet/Egg.png").getImage();
    Image Win_msg = new ImageIcon("SpriteSheet/win.png").getImage();
    Image Game_Over = new ImageIcon("SpriteSheet/GameOver.png").getImage();
    Image[] boss_img = {
            new ImageIcon("SpriteSheet/king1.png").getImage(),
            new ImageIcon("SpriteSheet/king2.png").getImage(),
            new ImageIcon("SpriteSheet/king3.png").getImage(),
            new ImageIcon("SpriteSheet/king4.png").getImage(),
    };
    Image HealthBar = new ImageIcon("SpriteSheet/HealthBar.png").getImage();
    Image GigaShot_Img = new ImageIcon("SpriteSheet/GigaShot.png").getImage();
    Image Title = new ImageIcon("SpriteSheet/Title.png").getImage();
    Player player = new Player(BOARD_WIDTH / 2, BOARD_HEIGHT - 190, 90, 70, 4);
    Shot sh = new Shot(-10, -10, 6, 10, 1);
    Boss King_Chicken = new Boss(BOARD_WIDTH / 2, 80, 200, 200, 5, 40);
    Shot BigEgg = new Shot(-100, -100, 40, 60, 12);
    Shot GigaShot = new Shot(-20, -20, 8, 12, 1);
    enum STATE {
        GAME,
        MENU
    }

}
