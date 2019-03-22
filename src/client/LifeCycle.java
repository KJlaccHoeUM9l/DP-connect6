package client;

import thirdParty.constantPool;

import java.util.Scanner;

public class LifeCycle {
    private JGameWindow gameWindow;
    private Scanner messageFromServer;

    public LifeCycle(JGameWindow gameWindow, Scanner messageFromServer) {
        this.gameWindow = gameWindow;
        this.messageFromServer = messageFromServer;
    }

    public void start() {
        while (true) {
            if (messageFromServer.hasNext()) {
                String commandFromServer = messageFromServer.nextLine();
                switch (commandFromServer) {
                    case constantPool.firstPlayer:
                        gameWindow.setPlayerFirst(true);
                        break;
                    case constantPool.secondPlayer:
                        gameWindow.setPlayerFirst(false);
                        break;
                    case constantPool.startGame:
                        gameWindow.startGame();
                        break;
                    default:
                        // Message format: x/y/playerNum
                        String[] data = commandFromServer.split("/");
                        int x = Integer.parseInt(data[0]);
                        int y = Integer.parseInt(data[1]);
                        int playerNum = Integer.parseInt(data[2]);

                        gameWindow.makeMove(x, y, playerNum);
                        break;
                }
            }
        }
    }
}
