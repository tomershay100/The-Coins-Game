import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;

public class EndScreen {
    Player p1;
    Player p2;
    MoneyList moneyList;
    boolean isQPressed;
    boolean isCPressed;
    boolean isNPressed;
    boolean isRPressed;

    public EndScreen(Player p1, Player p2, MoneyList moneyListCopy) {
        this.p1 = p1;
        this.p2 = p2;
        isQPressed = true;
        isCPressed = true;
        isNPressed = true;
        isRPressed = true;
        moneyList = moneyListCopy;
    }

    public void run(GUI gui) {
        Sleeper sleeper = new Sleeper();

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        DrawSurface d = gui.getDrawSurface();


        gui.show(d);

        while (true) {

            long startTime = System.currentTimeMillis(); // timing
            d = gui.getDrawSurface();
            new Clouds().drawOn(d);
            d.setColor(Color.BLACK);

            if (p1.getSum() > p2.getSum()) {
                d.drawText(100, 100, p1.getName() + " is the winner with " + p1.getSum() + "$", 20);
            } else if (p1.getSum() < p2.getSum()) {
                d.drawText(100, 100, p2.getName() + " is the winner with " + p2.getSum() + "$", 20);
            } else if (p1.getSum() == p2.getSum()) {
                d.drawText(100, 100, "It's a draw!", 20);
            }

            d.drawText(100, 150, "To continue playing press C", 20);
            d.drawText(100, 200, "To play new game press N", 20);
            d.drawText(100, 250, "To retry with same coins press R", 20);
            d.drawText(100, 300, "To quit press Q", 20);

            d.drawText(500, 350, "Victories:", 20);
            d.drawText(500, 400, p1.getName() + ": " + p1.getVictories(), 20);
            d.drawText(500, 450, p2.getName() + ": " + p2.getVictories(), 20);

            if ((gui.getKeyboardSensor().isPressed("Q") || gui.getKeyboardSensor().isPressed("q")) && !isQPressed) {
                System.exit(0);
            } else if (!gui.getKeyboardSensor().isPressed("Q") && !gui.getKeyboardSensor().isPressed("q")) {
                isQPressed = false;
            }
            if ((gui.getKeyboardSensor().isPressed("N") || gui.getKeyboardSensor().isPressed("n")) && !isNPressed) {
                Game2Players.play();
                System.exit(0);
            } else if (!gui.getKeyboardSensor().isPressed("N") && !gui.getKeyboardSensor().isPressed("n")) {
                isNPressed = false;
            }
            if ((gui.getKeyboardSensor().isPressed("C") || gui.getKeyboardSensor().isPressed("c")) && !isCPressed) {
                p1.addSum(-p1.getSum());
                p2.addSum(-p2.getSum());
                Game2Players game = new Game2Players(p1, p2);
                game.run();
            } else if (!gui.getKeyboardSensor().isPressed("C") && !gui.getKeyboardSensor().isPressed("c")) {
                isCPressed = false;
            }
            if ((gui.getKeyboardSensor().isPressed("R") || gui.getKeyboardSensor().isPressed("r")) && !isRPressed) {
                p1.addSum(-p1.getSum());
                p2.addSum(-p2.getSum());
                Game2Players game = new Game2Players(p1, p2, moneyList);
                game.run();
            } else if (!gui.getKeyboardSensor().isPressed("R") && !gui.getKeyboardSensor().isPressed("r")) {
                isRPressed = false;
            }
            gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

    }
}
