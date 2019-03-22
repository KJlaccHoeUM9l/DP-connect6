package client;

import thirdParty.constantPool;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class JGameWindow extends JFrame {
    private JGameField gameField;
    private JLabel playerNumber;
    private JLabel whosMove;
    private JLabel waitingPlayer;
    private GameLogic game = new GameLogic();

    public JGameWindow(PrintWriter messageFromClient) {
        setTitle("Connect6");
        setBounds(300, 300, constantPool.windowWidth, constantPool.windowHeight);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameField = new JGameField(game, messageFromClient);
        add(gameField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout());
        add(panel, BorderLayout.SOUTH);

        playerNumber = new JLabel("You are Player #");
        whosMove = new JLabel("");
        panel.add(playerNumber);
        panel.add(whosMove);

        waitingPlayer = new JLabel("Waiting another Player...");
        panel.add(waitingPlayer);

        setVisible(true);
    }

    public void setPlayerFirst(boolean first) {
        if (first) {
            game.setPlayerNum(0);
            playerNumber.setText("You are Player #" + Integer.toString(1));
            whosMove.setText("Your makeMove!");
        } else {
            game.setPlayerNum(1);
            playerNumber.setText("You are Player #" + Integer.toString(2));
            whosMove.setText("Opponent's makeMove");
        }
    }

    public void startGame() {
        game.startNewGame();

        gameField.setVisible(true);
        waitingPlayer.setVisible(false);
    }

    public void makeMove(int x, int y, int playerNum) {
        game.makeShot(x, y, playerNum);
        gameField.repaint();

        if (game.getPlayerNum() == 0 && game.hasPlayerShots() != 0)
            whosMove.setText("Your move!");
        if (game.getPlayerNum() == 1 && game.hasPlayerShots() == 0)
            whosMove.setText("Opponent's move!");

        if (game.getPlayerNum() == 1 && game.hasPlayerShots() != 0)
            whosMove.setText("Your move!");
        if (game.getPlayerNum() == 0 && game.hasPlayerShots() == 0)
            whosMove.setText("Opponent's move");
    }
}
