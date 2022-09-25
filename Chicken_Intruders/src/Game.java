import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

public class Game extends JPanel implements Runnable, Commons {
    public static STATE state = STATE.MENU;
    public static int MouseEntered = 0;
    public static Boolean LoadData = false;
    public static Boolean SaveData = false;
    static int points = 0;
    static int ShotCounter = 1;
    public Menu menu = new Menu();
    private Thread animator;

    public Game() {

        addKeyListener(new TAdapter());
        addMouseListener(new MouseInput());
        addMouseMotionListener(new MouseInput());
        setFocusable(true);
        player.MoveLeft = false;
        player.MoveRight = false;
        for (int i = 0; i < chickens.length; i++)
            egg[i] = new Shot(-20, -20, 15, 25, 2);
        int chicken_x = 10;
        int chicken_y = 10;
        for (int i = 0; i < chickens.length; i++) {
            chickens[i] = new Chicken(chicken_x, chicken_y, 50, 50, 2);
            chicken_x += 100;
            if (i == 9 || i == 19) {
                chicken_x = 10;
                chicken_y += 100;
            }
        }
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);

    }

    public void update() throws InterruptedException {
        if (SaveData == true) {
            File SaveFile = new File("SpriteSheet/SavedData.txt");
            try {
                FileWriter Write = new FileWriter("SpriteSheet/SavedData.txt");
                Write.write(String.valueOf(Game.points));
                for (int i = 0; i < chickens.length; i++) {
                    Write.write("\n" + String.valueOf(chickens[i].x));
                    Write.write("\n" + String.valueOf(chickens[i].y));
                    Write.write("\n" + String.valueOf(chickens[i].isVisible));
                }
                Write.write("\n" + String.valueOf(King_Chicken.Health));
                Write.write("\n" + String.valueOf(King_Chicken.isVisible));
                Write.write("\n" + String.valueOf(player.isVisible));
                Write.close();
            } catch (IOException er) {
                er.printStackTrace();
            }
            SaveData = false;
        }
        if (LoadData == true) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("SpriteSheet/SavedData.txt"));
                points = Integer.valueOf(br.readLine());
                for (int i = 0; i < chickens.length; i++) {
                    chickens[i].x = Integer.valueOf(br.readLine());
                    chickens[i].y = Integer.valueOf(br.readLine());
                    chickens[i].isVisible = Boolean.valueOf(br.readLine());
                }
                King_Chicken.Health = Integer.valueOf(br.readLine());
                King_Chicken.isVisible = Boolean.valueOf(br.readLine());
                player.isVisible = Boolean.valueOf(br.readLine());
            } catch (IOException er) {
                System.out.println("File doesn't exist");
                er.printStackTrace();
            }
            LoadData = false;
        }
        hitDetect();
        moveChickens();
    }

    public void paint(Graphics g) {
        super.paint(g);
        int x_point = 0;
        int y_point = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.drawImage(background_img, x_point, y_point, 300, 225, this);
                x_point += 300;
            }
            x_point = 0;
            y_point = y_point + 225;
        }
        //g.drawImage(asteroid, 0, 450, 300, 225, this);

        if (state == STATE.GAME) {
            if (player.isVisible)
                player.Draw(g, player_img);
            if (player.MoveRight) {
                if (player.x > BOARD_WIDTH - player.width)
                    player.MoveRight = false;
                else
                    player.x += player.speed;

            }
            if (player.MoveLeft) {
                if (player.x < 0)
                    player.MoveLeft = false;
                else
                    player.x -= player.speed;
            }

            for (int i = 0; i < chickens.length; i++) {
                if (chickens[i].isVisible) {

                    if (rand.nextInt(10000) < 8) {
                        egg[i].x = chickens[i].x + chickens[i].width / 2;
                        egg[i].y = chickens[i].y + chickens[i].height;
                        egg[i].goDown = true;

                    }
                    chickens[i].FlyingChicken(g, chicken_img);
                }
                egg[i].move();
                if (player.isVisible)
                    egg[i].Draw(g, egg_img);
                sh.move();
                sh.Draw(g, shot_img);
                GigaShot.move();
                GigaShot.Draw(g, GigaShot_Img);
            }

            if (King_Chicken.isVisible && player.isVisible) {
                King_Chicken.FlyingChicken(g, boss_img);
                King_Chicken.Move();
                King_Chicken.DropEgg(g, BigEgg, rand);
                BigEgg.move();
                if (player.isVisible)
                    BigEgg.Draw(g, egg_img);
                if (King_Chicken.isDead()) {
                    points += 1000;
                    King_Chicken.isVisible = false;
                }
                g.drawImage(HealthBar, 100, BOARD_HEIGHT - 100, King_Chicken.Health * 25, 20, null);
            }

            Font small = new Font("Helvetica", Font.BOLD, 20);
            g.setColor(Color.WHITE);
            g.setFont(small);
            g.drawString("Score: " + points, 1000, 70);

            if (points == 3000) {
                King_Chicken.isVisible = true;
            }
            if (points == 4000) {
                g.drawImage(Win_msg, BOARD_WIDTH / 2 - 200, BOARD_HEIGHT / 2 - 200, 400, 200, this);
                player.isVisible = false;
            }
            if (!player.isVisible && points != 4000) {
                g.drawImage(Game_Over, BOARD_WIDTH / 2 - 250, BOARD_HEIGHT / 2 - 200, 500, 200, this);
            }
            if (Collision_check()) {
                for (int i = 0; i < chickens.length; i++) {
                    chickens[i].isVisible = false;
                }
                player.isVisible = false;
            }
        }
        if (state == STATE.MENU)
            menu.render(g, MouseEntered);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    // Check collisions for:
    // chicken--player
    // egg--player
    // chicken--End of the Board
    // Game Over when it's true
    boolean Collision_check() {
        for (int i = 0; i < chickens.length; i++)
            if (chickens[i].isVisible && player.isVisible &&
                    player.x + player.width >= chickens[i].x && player.x <= chickens[i].x + chickens[i].width &&
                    player.y + player.height >= chickens[i].y && player.y <= chickens[i].y + chickens[i].height ||
                    (chickens[i].isVisible && chickens[i].y >= BOARD_HEIGHT - 100) ||
                    (player.x + player.height >= egg[i].x && player.x <= egg[i].x + egg[i].width &&
                            player.y + player.height >= egg[i].y && player.y <= egg[i].y + egg[i].height)
                    || (King_Chicken.isVisible && player.x + player.height >= BigEgg.x
                    && player.x <= BigEgg.x + BigEgg.width && player.y + player.height >= BigEgg.y &&
                    player.y <= BigEgg.y + BigEgg.height)) {
                return true;
            }
        return false;
    }

    //Collisions for: Chickens--Shot
    //Adds points if chicken is hit
    public void hitDetect() {

        for (int i = 0; i < chickens.length; i++) {
            if (chickens[i].isVisible && sh.x + sh.width >= chickens[i].x &&
                    sh.x <= chickens[i].x + chickens[i].width &&
                    sh.y + sh.height >= (chickens[i].y) &&
                    sh.y <= chickens[i].y + chickens[i].height) {
                points += 100;
                chickens[i].isVisible = false;
                sh.x = -30;
            }
            if (chickens[i].isVisible && GigaShot.x + GigaShot.width >= chickens[i].x &&
                    GigaShot.x <= chickens[i].x + chickens[i].width &&
                    GigaShot.y + GigaShot.height >= (chickens[i].y) &&
                    GigaShot.y <= chickens[i].y + chickens[i].height) {
                points += 100;
                chickens[i].isVisible = false;
            }
        }
        if (King_Chicken.isVisible && sh.x + sh.width >= King_Chicken.x &&
                sh.x <= King_Chicken.x + King_Chicken.width &&
                sh.y + sh.height >= (King_Chicken.y) &&
                sh.y <= King_Chicken.y + King_Chicken.height) {

            King_Chicken.Health--;
            sh.x = -30;
        }
        if (King_Chicken.isVisible && GigaShot.x + GigaShot.width >= King_Chicken.x &&
                GigaShot.x <= King_Chicken.x + King_Chicken.width &&
                GigaShot.y + GigaShot.height >= (King_Chicken.y) &&
                GigaShot.y <= King_Chicken.y + King_Chicken.height) {

            King_Chicken.Health = King_Chicken.Health - 2;
            GigaShot.x = -30;
        }

    }

    //Chickens move both Horizontally and Vertically
    public void moveChickens() {
        for (int i = 0; i < chickens.length; i++) {
            if (chickens[i].moveLeft) {
                chickens[i].x -= chickens[i].speed;
            }
            if (chickens[i].moveRight) {
                chickens[i].x += chickens[i].speed;
            }
        }
        for (int i = 0; i < chickens.length; i++) {
            if (chickens[i].x > BOARD_WIDTH - 70) {
                for (int j = 0; j < chickens.length; j++) {
                    chickens[j].moveLeft = true;
                    chickens[j].moveRight = false;
                    chickens[j].y += 10;
                }
            }
            if (chickens[i].x < 0) {
                for (int j = 0; j < chickens.length; j++) {
                    chickens[j].moveRight = true;
                    chickens[j].moveLeft = false;
                    chickens[j].y += 10;
                }
            }
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int animationDelay = 15;
        long time = System.currentTimeMillis();
        while (true) {//infinite loop
            //spriteManager.update();
            try {
                update();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop
    }//end of run

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == 39) {
                player.MoveRight = false;
            }
            //if left arrow is pressed, player move left
            if (key == 37) {
                player.MoveLeft = false;
            }


        }

        public void keyPressed(KeyEvent e) {

            boolean left = false, right = false, z = false, space = false;
            int key = e.getKeyCode();
            //if right arrow is pressed, player move right
            if (key == 39) {
                player.MoveRight = true;
            }
            //if left arrow is pressed, player move left
            if (key == 37) {
                player.MoveLeft = true;
            }
            //if space bar is pressed, player shoots
            if (key == 32 && !sh.goUp && ShotCounter % 5 == 0 && player.isVisible) {
                GigaShot.goUp = true;
                GigaShot.x = player.x + player.width / 2;
                GigaShot.y = player.y;
                ShotCounter++;
            } else if (key == 32 && !sh.goUp && player.isVisible) {
                sh.goUp = true;
                sh.x = player.x + player.width / 2;
                sh.y = player.y;
                ShotCounter++;
            }
            if (key == 27 && state == STATE.GAME)
                state = STATE.MENU;
            else if (key == 27 && state == STATE.MENU)
                state = STATE.GAME;
        }
    }
}//end of class