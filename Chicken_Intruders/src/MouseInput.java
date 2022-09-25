import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MouseInput implements Commons, MouseListener, MouseWheelListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        /*
         public Rectangle newButton = new Rectangle(BOARD_WIDTH/2-150 ,300,300,100);
    public Rectangle saveButton = new Rectangle(BOARD_WIDTH/2-150 ,400,300,100);
    public Rectangle loadButton = new Rectangle(BOARD_WIDTH/2-150 ,500,300,100);
    public Rectangle quitButton = new Rectangle(BOARD_WIDTH/2-150 ,600,300,100);
         */
        if (Game.state == STATE.MENU) {
            if (mx >= BOARD_WIDTH / 2 - 150 && mx <= BOARD_WIDTH / 2 + 150) {
                if (my >= 300 && my < 400) {
                    Game.state = Game.STATE.GAME;
                }
                if (my >= 400 && my < 500) {
                    Game.SaveData = true;
                }
                if (my >= 500 && my < 600) {
                    Game.LoadData = true;
                    Game.state = STATE.GAME;
                }
                if (my >= 600 && my <= 700) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        /*
         public Rectangle newButton = new Rectangle(BOARD_WIDTH/2-150 ,300,300,100);
    public Rectangle saveButton = new Rectangle(BOARD_WIDTH/2-150 ,400,300,100);
    public Rectangle loadButton = new Rectangle(BOARD_WIDTH/2-150 ,500,300,100);
    public Rectangle quitButton = new Rectangle(BOARD_WIDTH/2-150 ,600,300,100);
         */
        if (Game.state == STATE.MENU) {
            if (mx >= BOARD_WIDTH / 2 - 150 && mx <= BOARD_WIDTH / 2 + 150) {
                if (my >= 300 && my < 400) {
                    Game.MouseEntered = 1;
                } else if (my >= 400 && my < 500) {
                    Game.MouseEntered = 2;
                } else if (my >= 500 && my < 600) {
                    Game.MouseEntered = 3;
                } else if (my >= 600 && my <= 700) {
                    Game.MouseEntered = 4;
                } else
                    Game.MouseEntered = 0;
            } else Game.MouseEntered = 0;
        }

    }
}
