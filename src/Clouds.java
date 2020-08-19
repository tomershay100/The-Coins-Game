//323082701
import biuoop.DrawSurface;

import java.awt.*;

/**
 * Clouds class.
 *
 * @author Tomer Shay <tomershay100@gmail.com>
 * @version 1.0
 * @since 2020-06-05
 */
public class Clouds implements Sprite {
    /**
     * draw One Cloud on given DrawSurface.
     * @param d the DrawSurface.
     * @param x the x axis.
     * @param y the y axis.
     */
    public void drawOneCloud(DrawSurface d, int x, int y) {
        d.setColor(Color.WHITE);
        d.fillRectangle(x, y, 100, 20);
        d.fillCircle(x, y, 20);
        d.fillCircle(x + 100, y, 20);
        d.fillCircle(x + 40, y - 20, 40);
        d.fillCircle(x + 80, y - 10, 25);
    }

    @Override
    public void drawOn(DrawSurface d) {
        new Background(new Rectangle(new Point(0, 0), 800, 600), Color.cyan).drawOn(d);
        new Sun().drawOn(d);
        drawOneCloud(d, 585, 100);
        drawOneCloud(d, 400, 400);
        drawOneCloud(d, 100, 100);
        drawOneCloud(d, 650, 350);
        drawOneCloud(d, 200, 490);
        drawOneCloud(d, 620, 500);
        int x = 50, y = 150;
    }
}
