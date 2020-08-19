import biuoop.DrawSurface;

import java.awt.*;
import java.util.Random;

public class MoneyList implements Sprite {
    public static int[] valuesArray = new int[]{1, 5, 10, 20, 50, 70, 100};
    final Money[] gameCoins;
    public Money[] gameCoinsCopy;
    private int size;

    public MoneyList(int size) {
        this.size = size;
        this.gameCoins = new Money[size];
        this.gameCoinsCopy = new Money[size];
        Random rand = new Random();
        Point position = new Point(0, 100);
        for (int i = 0; i < this.size; i++) {
            if (i == 34) {
                position.y = 400;
                position.x = Money.SIZE * 2 + 5;
            } else if (i == 23) {
                position.y = 300;
                position.x = Money.SIZE * 2 + 5;
            } else if (i == 12) {
                position.y = 200;
                position.x = Money.SIZE * 2 + 5;
            }
            int randVal = rand.nextInt(valuesArray.length);
            //position.x += Money.SIZE * 2 + 5;
            position = new Point(position.x + Money.SIZE * 2 + 5, position.y);
            //System.out.println(position.toString());
            this.gameCoins[i] = new Money(valuesArray[randVal], position);
            this.gameCoinsCopy[i] = new Money(valuesArray[randVal], position);
        }
    }

    public MoneyList(Money[] moneyListCopy) {
        this.size = moneyListCopy.length;
        this.gameCoins = new Money[size];
        this.gameCoinsCopy = new Money[size];
        for (int i = 0; i < this.size; i++) {
            this.gameCoins[i] = new Money(moneyListCopy[i].getValue(), moneyListCopy[i].getCenter());
            this.gameCoinsCopy[i] = new Money(moneyListCopy[i].getValue(), moneyListCopy[i].getCenter());
        }
    }

    public void drawOn(DrawSurface d) {
        for (int i = 0; i < size; i++) {
            //System.out.println(this.gameCoins[i].getValue());
            this.gameCoins[i].drawOn(d);
        }
    }

    public void removeFirst() {
        if (size - 1 >= 0) System.arraycopy(this.gameCoins, 1, this.gameCoins, 0, size - 1);
        this.size--;
    }

    public void removeLast() {
        this.size--;
    }

    public Money getFirst() {
        return this.gameCoins[0];
    }

    public Money getLast() {
        return this.gameCoins[size - 1];
    }

    public int getSize() {
        return size;
    }
}
