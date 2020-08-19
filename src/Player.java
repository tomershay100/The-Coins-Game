import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;


public class Player implements Sprite {
    private final String name;
    protected boolean isFirstPressed;
    private int sum;
    private boolean isMyTurn;
    private boolean isLeftPlayer;
    private int victories;
    private boolean enterAlreadyPressed;

    public Player(String name) {
        this.name = name;
        this.sum = 0;
        this.isMyTurn = false;
        this.victories = 0;

        enterAlreadyPressed = true;
        isFirstPressed = true;
    }

    public void setMyTurn() {
        this.isMyTurn = !this.isMyTurn;
    }

    public void setToLeftPlayer() {
        this.isLeftPlayer = true;
    }

    public void setToRightPlayer() {
        this.isLeftPlayer = false;
    }

    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        if (isLeftPlayer) {
            d.fillRectangle(20, 600 - 100 - 20, 200, 100);
            d.setColor(Color.BLACK);
            if (isMyTurn) {
                d.drawRectangle(20, 600 - 100 - 20, 200, 100);
            }
            d.drawText(20 + 15, 600 - 100 - 20 + 15, this.name, 15);
            d.drawText(20 + 15 + 30, 600 - 100 - 20 + 15 + 40, Integer.toString(this.sum), 15);
        } else {
            d.fillRectangle(800 - 200 - 20, 600 - 100 - 20, 200, 100);
            d.setColor(Color.BLACK);
            if (isMyTurn) {
                d.drawRectangle(800 - 200 - 20, 600 - 100 - 20, 200, 100);
            }
            d.drawText(800 - 200 - 20 + 15, 600 - 100 - 20 + 15, this.name, 15);
            d.drawText(800 - 200 - 20 + 15 + 30, 600 - 100 - 20 + 15 + 40, Integer.toString(this.sum), 15);
        }
    }

    public void addSum(int x) {
        this.sum += x;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public void addVic() {
        victories++;
    }

    public int getVictories() {
        return victories;
    }

    public void turn(GUI gui, MoneyList moneyList, List<Sprite> spriteList) {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        DrawSurface d = gui.getDrawSurface();
        isFirstPressed = true;
        moneyList.getFirst().brighterColor();
        moneyList.getLast().defaultColor();

        for (Sprite sprite : spriteList) {
            sprite.drawOn(d);
        }

        gui.show(d);

        while (true) {
            //System.out.println(moneyList.getSize());
            long startTime = System.currentTimeMillis(); // timing
            d = gui.getDrawSurface();

            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.ENTER_KEY) && !this.enterAlreadyPressed) {
                try {
                    URL dupBlock = getClass().getClassLoader().getResource("healingSound.wav");
                    if (dupBlock != null) {
                        AudioInputStream sound = AudioSystem.getAudioInputStream(dupBlock);
                        Clip clip = AudioSystem.getClip();
                        clip.open(sound);
                        clip.setFramePosition(0);
                        clip.start();
                    } else {
                        String filePath = "src\\extra\\healingSound.wav";
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                                new File(filePath).getAbsoluteFile());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInput);
                        clip.start();
                    }
                } catch (Exception ignored) {
                    System.out.print("");
                }
                enterAlreadyPressed = true;

                if (this.isFirstPressed) {
                    this.addSum(moneyList.getFirst().getValue());
                    moneyList.removeFirst();
                } else {
                    this.addSum(moneyList.getLast().getValue());
                    moneyList.removeLast();
                }
                isFirstPressed = true;
                moneyList.getFirst().brighterColor();
                moneyList.getLast().defaultColor();
                return;
            } else if (!gui.getKeyboardSensor().isPressed(KeyboardSensor.ENTER_KEY)) {
                this.enterAlreadyPressed = false;
            }
            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.RIGHT_KEY)) {
                isFirstPressed = false;
                moneyList.getFirst().defaultColor();
                moneyList.getLast().brighterColor();
            }
            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.LEFT_KEY)) {
                isFirstPressed = true;
                moneyList.getFirst().brighterColor();
                moneyList.getLast().defaultColor();
            }

            for (Sprite sprite : spriteList) {
                sprite.drawOn(d);
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
