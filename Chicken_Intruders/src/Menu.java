import javax.swing.*;
import java.awt.*;


public class Menu implements Commons {

    public Rectangle playButton = new Rectangle(BOARD_WIDTH / 2 - 150, 300, 300, 100);
    public Rectangle saveButton = new Rectangle(BOARD_WIDTH / 2 - 150, 400, 300, 100);
    public Rectangle loadButton = new Rectangle(BOARD_WIDTH / 2 - 150, 500, 300, 100);
    public Rectangle quitButton = new Rectangle(BOARD_WIDTH / 2 - 150, 600, 300, 100);


    public void render(Graphics g, int Value) {


        Point p = MouseInfo.getPointerInfo().getLocation();
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawImage(Title, BOARD_WIDTH / 2 - 400, 100, 800, 100, null);

        if (Value == 1)
            g.setColor(Color.GRAY);
        g.drawString("Play", playButton.x + 80, playButton.y + 70);
        g2d.draw(playButton);

        g.setColor(Color.white);
        if (Value == 2)
            g.setColor(Color.GRAY);
        g.drawString("Save", saveButton.x + 80, saveButton.y + 70);
        g2d.draw(saveButton);

        g.setColor(Color.white);
        if (Value == 3)
            g.setColor(Color.GRAY);
        g.drawString("Load", loadButton.x + 80, loadButton.y + 70);
        g2d.draw(loadButton);

        g.setColor(Color.white);
        if (Value == 4)
            g.setColor(Color.GRAY);
        g.drawString("Quit", quitButton.x + 80, quitButton.y + 70);
        g2d.draw(quitButton);

    }
}
