import biuoop.DrawSurface;

import java.awt.*;

public class Money {
    static final int SIZE = 30;
    private final int value;
    private final Point center;
    private Color color;

    public Money(int value, Point center) {
        this.center = center;
        this.value = value;
        color = Color.ORANGE;
    }

    public int getValue() {
        return value;
    }

    public void defaultColor() {
        this.color = Color.ORANGE;
    }

    public void brighterColor() {
        this.color = Color.YELLOW;
    }

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.center.x, this.center.y, SIZE);
        d.setColor(Color.BLACK);
        d.drawCircle(this.center.x, this.center.y, SIZE);
        d.drawText(this.center.x, this.center.y, Integer.toString(this.value), 15);
    }

    public Point getCenter() {
        return center;
    }
}
