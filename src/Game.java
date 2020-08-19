import biuoop.DialogManager;
import biuoop.GUI;

public class Game {

    protected static GUI gui = new GUI("The Coins Game", 800, 600);


    public Game() {

    }

    public static void play() {
        Game game;
        DialogManager d = gui.getDialogManager();
        if (d.showYesNoDialog("The Coins Game", "Do you have friends?")) {
            game = new Game2Players();
        } else {
            game = new Game2Players(new ComputerPlayer());
        }
        game.run();
    }

    public static void main(String[] args) {
        play();

    }

    public void run() {

    }

    public int getSizeFromUser() {
        DialogManager d = gui.getDialogManager();
        String s = d.showQuestionDialog("Amount Of Coins", "How many coins do you want to play with?",
                "Enter a number between 2 and 44");
        int num = -1;
        while (num > 44 || num < 2) {
            try {
                num = Integer.parseInt(s);
                if (num > 44 || num < 2) {
                    s = d.showQuestionDialog("Amount Of Coins", "How many coins do you want to play with?",
                            "between 2 and 44! Are you trying to make me angry on purpose??");
                    num = Integer.parseInt(s);
                }
            } catch (Exception ignored) {
                s = "-1";
            }
        }
        return num;
    }

}
