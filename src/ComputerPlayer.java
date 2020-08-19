import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.util.List;

public class ComputerPlayer extends Player {
    public ComputerPlayer() {
        super("computer");
    }

    public int p(int i, int j, MoneyList moneyListCopy, int[] sumArray) {
        if (i == j) {
            return moneyListCopy.gameCoins[i].getValue();
        }
        int sumOfAll;

        if (i == 0) {
            sumOfAll = sumArray[j];
        } else {
            sumOfAll = sumArray[j] - sumArray[i - 1];
        }

        return sumOfAll - Math.min(p(i + 1, j, moneyListCopy, sumArray), p(i, j - 1, moneyListCopy, sumArray));
    }

    public boolean shouldIGoLeft(int[] sumArray, MoneyList moneyListCopy) {
        int[][] dynamicArray = new int[moneyListCopy.getSize()][moneyListCopy.getSize()];

        for (int i = 0; i < moneyListCopy.getSize(); i++) {
            dynamicArray[i][i] = moneyListCopy.gameCoins[i].getValue();
        }

        for (int l = 1; l < moneyListCopy.getSize(); l++) {
            for (int i = 0; i < moneyListCopy.getSize() - l; i++) {
                int sumOfAll;
                if (i != 0) {
                    sumOfAll = sumArray[i + l] - sumArray[i - 1];
                } else {
                    sumOfAll = sumArray[i + l];
                }
                dynamicArray[i][i + l] = sumOfAll - Math.min(dynamicArray[i + 1][i + l], dynamicArray[i][i + l - 1]);
            }
            //dynamicArray[j][j + i] = sumOfAll - Math.min(dynamicArray[j+1][j + i], dynamicArray[j+1][j + i - 1]);
            //System.out.println(temp);
        }
        System.out.println("The minimum value that the computer will achieve: " + dynamicArray[0][moneyListCopy.getSize() - 1]);
        return dynamicArray[0][moneyListCopy.getSize() - 2] > dynamicArray[1][moneyListCopy.getSize() - 1];
        /*
        System.out.println(p(0, moneyListCopy.getSize() - 2, moneyListCopy, sumArray) + moneyListCopy.gameCoins[moneyListCopy.getSize() - 1].getValue());
        System.out.println(p(1, moneyListCopy.getSize() - 1, moneyListCopy, sumArray) + moneyListCopy.gameCoins[0].getValue());
        return p(0, moneyListCopy.getSize() - 2, moneyListCopy, sumArray) + moneyListCopy.gameCoins[moneyListCopy.getSize() - 1].getValue() >
                p(1, moneyListCopy.getSize() - 1, moneyListCopy, sumArray) + moneyListCopy.gameCoins[0].getValue();

         */
    }

    @Override
    public void turn(GUI gui, MoneyList moneyList, List<Sprite> spriteList) {
        //System.out.println("6");
        //MoneyList moneyListCopy = moneyList.copy();
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

        int[] sumArray = new int[moneyList.getSize()];
        for (int i = 0; i < sumArray.length; i++) {
            if (i == 0) {
                sumArray[i] = moneyList.gameCoins[i].getValue();
            } else {
                sumArray[i] = sumArray[i - 1] + moneyList.gameCoins[i].getValue();
            }
        }

        boolean chooseLeft = shouldIGoLeft(sumArray, moneyList);

        d = gui.getDrawSurface();
        if (chooseLeft) {
            isFirstPressed = true;
            moneyList.getFirst().brighterColor();
            moneyList.getLast().defaultColor();
            for (Sprite sprite : spriteList) {
                sprite.drawOn(d);
            }
            sleeper.sleepFor(250);
            gui.show(d);
            this.addSum(moneyList.getFirst().getValue());
            moneyList.removeFirst();
        } else {
            isFirstPressed = false;
            moneyList.getFirst().defaultColor();
            moneyList.getLast().brighterColor();
            for (Sprite sprite : spriteList) {
                sprite.drawOn(d);
            }
            sleeper.sleepFor(250);
            gui.show(d);
            this.addSum(moneyList.getLast().getValue());
            moneyList.removeLast();
        }
    }
}
