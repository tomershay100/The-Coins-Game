import biuoop.DialogManager;
import biuoop.Sleeper;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Game2Players extends Game {
    private final MoneyList moneyList;
    private final int moneyListSize;
    private final Player p1;
    private final Player p2;
    //private static GUI gui = new GUI("The Coins Game", 800, 600);
    private final List<Sprite> spriteList;

    public Game2Players() {
        moneyListSize = getSizeFromUser();
        moneyList = new MoneyList(moneyListSize);

        DialogManager d = gui.getDialogManager();
        String name1 = d.showQuestionDialog("Players Name", "Enter first player name:", "player 1");
        while (name1.length() > 22 || name1.equals("computer")) {
            if (name1.equals("computer")) {
                name1 = d.showQuestionDialog("Players Name",
                        "Enter first player name: (The player's name can not be \"computer\")", "player 1");
            } else {
                name1 = d.showQuestionDialog("Players Name",
                        "Enter first player name: (The player's name must be up to 22 characters)", "player 1");
            }
        }
        p1 = new Player(name1);

        String name2 = d.showQuestionDialog("Players Name", "Enter second player name:", "player 2");
        while (name2.length() > 22 || name2.equals("computer")) {
            if (name2.equals("computer")) {
                name2 = d.showQuestionDialog("Players Name",
                        "Enter second player name: (The player's name can not be \"computer\")", "player 2");
            } else {
                name2 = d.showQuestionDialog("Players Name",
                        "Enter second player name: (The player's name must be up to 22 characters)", "player 2");
            }
        }
        p2 = new Player(name2);
        //p1 = new ComputerPlayer();

        p1.setToLeftPlayer();
        p2.setToRightPlayer();
        p1.setMyTurn();
        spriteList = new ArrayList<>();
        spriteList.add(new Clouds());
        spriteList.add(moneyList);
        spriteList.add(p1);
        spriteList.add(p2);
        //this.enterAlreadyPressed = true;
        //this.isFirstPressed = true;
    }

    public Game2Players(Player player1, Player player2) {
        moneyListSize = getSizeFromUser();
        moneyList = new MoneyList(moneyListSize);

        p1 = player1;
        p2 = player2;

        if (!p1.isMyTurn()) {
            p1.setMyTurn();
            p2.setMyTurn();
        }
        spriteList = new ArrayList<>();
        spriteList.add(new Clouds());
        spriteList.add(moneyList);
        spriteList.add(p1);
        spriteList.add(p2);
        //this.enterAlreadyPressed = true;
        //this.isFirstPressed = true;
    }

    public Game2Players(ComputerPlayer computerPlayer) {
        moneyListSize = getSizeFromUser();
        moneyList = new MoneyList(moneyListSize);

        String name1 = "you";

        if (Math.random() > 0.5) {
            p1 = new Player(name1);
            p2 = computerPlayer;
        } else {
            p2 = new Player(name1);
            p1 = computerPlayer;
        }

        p1.setToLeftPlayer();
        p2.setToRightPlayer();

        p1.setMyTurn();
        spriteList = new ArrayList<>();
        spriteList.add(new Clouds());
        spriteList.add(moneyList);
        spriteList.add(p1);
        spriteList.add(p2);
    }

    public Game2Players(Player player1, Player player2, MoneyList moneyListCopy) {
        moneyListSize = moneyListCopy.getSize();
        this.moneyList = new MoneyList(moneyListCopy.gameCoinsCopy);
        //moneyListCopy = new MoneyList(moneyListSize);

        p1 = player1;
        p2 = player2;

        if (!p1.isMyTurn()) {
            p1.setMyTurn();
            p2.setMyTurn();
        }
        spriteList = new ArrayList<>();
        spriteList.add(new Clouds());
        spriteList.add(moneyList);
        spriteList.add(p1);
        spriteList.add(p2);
        //this.enterAlreadyPressed = true;
        //this.isFirstPressed = true;
    }

    public void run() {

        while (moneyList.getSize() > 0) {

            if (p1.isMyTurn()) {
                p1.turn(gui, this.moneyList, spriteList);
            } else {
                p2.turn(gui, this.moneyList, spriteList);
            }
            p1.setMyTurn();
            p2.setMyTurn();

            if (moneyList.getSize() == 1) {
                if (p1.isMyTurn()) {
                    p1.addSum(moneyList.getFirst().getValue());
                } else {
                    p2.addSum(moneyList.getFirst().getValue());
                }
                //EndScreen endScreen;
                if (p1.getSum() > p2.getSum()) {
                    if (p2.getName().equals("computer")) {
                        playWinSound();
                    } else if (p1.getName().equals("computer")) {
                        playLostSound();
                    }
                    p1.addVic();
                } else if (p1.getSum() < p2.getSum()) {
                    if (p1.getName().equals("computer")) {
                        playWinSound();
                    } else if (p2.getName().equals("computer")) {
                        playLostSound();
                    }
                    p2.addVic();
                    //System.out.println(p1.getSum() + ", " + p2.getSum());
                }
                EndScreen endScreen = new EndScreen(p1, p2, moneyList);
                endScreen.run(gui);
                return;
            }

        }

    }

    private void playLostSound() {
        try {
            //Sleeper sleeper = new Sleeper();
            URL gameOver = getClass().getClassLoader().getResource("pacmanGameOver.wav");
            if (gameOver != null) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(gameOver);
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.setFramePosition(0);
                clip.start();
                //sleeper.sleepFor(1800);
            } else {
                String filePath = "src\\extra\\pacmanGameOver.wav";
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                        new File(filePath).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                //sleeper.sleepFor(1750);
            }
        } catch (Exception ignored) {
            //ignored.printStackTrace();
            System.out.print("");
        }
    }

    private void playWinSound() {
        try {
            Sleeper sleeper = new Sleeper();
            URL gameWin = getClass().getClassLoader().getResource("winSound.wav");
            if (gameWin != null) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(gameWin);
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.setFramePosition(0);
                clip.start();
                sleeper.sleepFor(5200 - 2000);
            } else {
                String filePath = "src\\extra\\winSound.wav";
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                        new File(filePath).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                sleeper.sleepFor(5500 - 2000);
            }
        } catch (Exception ignored) {
            System.out.print("");
        }
    }
}