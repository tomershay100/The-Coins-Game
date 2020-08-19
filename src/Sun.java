//323082701
import biuoop.DrawSurface;

import java.awt.Color;
/**
 * Sun class.
 *
 * @author Tomer Shay <tomershay100@gmail.com>
 * @version 1.0
 * @since 2020-06-05
 */
public class Sun implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.CYAN);
        d.fillRectangle(0, 0, 800, 600);

        d.setColor(Color.YELLOW);
        for (int i = 0; i < 100; i++) {
            d.drawLine(200, 150, -500 + i * 20, 600);
        }

        d.setColor(Color.ORANGE.brighter());
        d.fillCircle(200, 150, 75);
    }

}
