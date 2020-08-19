//323082701

import biuoop.DrawSurface;

import java.awt.*;


/**
 * The background rectangle of the game.
 *
 * @author Tomer Shay <tomershay100@gmail.com>
 * @version 1.0
 * @since 2020-04-24
 */
public class Background implements Sprite {
    private final Rectangle rectangle;
    private final Color color;

    /**
     * Constructor.
     *
     * @param rectangle the rectangle.
     * @param color     the color of the background.
     */
    public Background(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(this.rectangle.getUpperLeft().x, this.rectangle.getUpperLeft().y,
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }


}
