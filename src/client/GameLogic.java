package client;

import thirdParty.constantPool;
import thirdParty.playerTags;

public class GameLogic {
    private int myPlayerNum;
    private Player[] players;
    private playerTags[][] cells;

    private boolean gameOver = false;
    private String gameOverMessage = "";

    public GameLogic(){
        cells = new playerTags[constantPool.fieldSize][constantPool.fieldSize];

        players = new Player[2];
        players[0] = new Player(this, playerTags.first);
        players[1] = new Player(this, playerTags.second);
        players[0].setRemainingShots(1);
    }

    // Getters & setters
    public void setPlayerNum(int myPlayerNum) { this.myPlayerNum = myPlayerNum; }

    public void markCell(int x, int y, playerTags playerTag){ cells[x][y] = playerTag; }

    public int getPlayerNum() { return myPlayerNum; }

    public playerTags getCellMarker(int x, int y){ return cells[x][y]; }

    public boolean getGameOver(){ return gameOver; }

    public String getGameOverMessage(){ return gameOverMessage; }

    // Game logic
    public void startNewGame() {
        for (int x = 0; x < constantPool.fieldSize; x++) {
            for (int y = 0; y < constantPool.fieldSize; y++) {
                cells[x][y] = playerTags.no_one;
            }
        }
    }

    public void makeShot(int x, int y, int currentPlayerNum) {
        int anotherPlayerNum = 2;
        switch (currentPlayerNum) {
            case 0:
                anotherPlayerNum = 1;
                break;
            case 1:
                anotherPlayerNum = 0;
                break;
        }

        if (players[currentPlayerNum].getRemainingShots() > 0 && players[anotherPlayerNum].getRemainingShots() == 0)
            if (!gameOver && isValidCell(x, y) && isCellEmpty(x, y)) {
                players[currentPlayerNum].shot(x, y);
                players[currentPlayerNum].setRemainingShots(players[currentPlayerNum].getRemainingShots() - 1);

                if (players[currentPlayerNum].getRemainingShots() == 0)
                    players[anotherPlayerNum].setRemainingShots(2);
            }

        if (players[currentPlayerNum].win()) {
            gameOver = true;
            gameOverMessage = "Player #" + (currentPlayerNum + 1) + " WIN!";
        }
    }

    public boolean checkWin(playerTags playerTag) {
        // строки
        for (int x = 0; x < constantPool.fieldSize; x++) {
            for (int y = 0; y <= constantPool.fieldSize - constantPool.countToWin; y++) {
                if (checkLine(x, y, 0, 1, playerTag))
                    return true;
            }
        }
        // столбцы
        for (int x = 0; x <= constantPool.fieldSize - constantPool.countToWin; x++) {
            for (int y = 0; y < constantPool.fieldSize; y++) {
                if (checkLine(x, y, 1, 0, playerTag))
                    return true;
            }
        }
        // y = x
        for (int x = 0; x <= constantPool.fieldSize - constantPool.countToWin; x++) {
            for (int y = 0; y <= constantPool.fieldSize - constantPool.countToWin; y++) {
                if (checkLine(x, y, 1, 1, playerTag))
                    return true;
            }
        }
        // y = -x
        for (int x = constantPool.countToWin - 1; x < constantPool.fieldSize; x++) {
            for (int y = 0; y <= constantPool.fieldSize - constantPool.countToWin; y++) {
                if (checkLine(x, y, -1, 1, playerTag))
                    return true;
            }
        }

        return false;
    }

    // Helpers
    public int hasPlayerShots() {
        return players[myPlayerNum].getRemainingShots();
    }

    private boolean isCellEmpty(int x, int y) {
        return cells[x][y].equals(playerTags.no_one);
    }

    private boolean isValidCell(int x, int y){
        return x >= 0 && y >= 0 && x < constantPool.fieldSize && y < constantPool.fieldSize;
    }

    private boolean checkLine(int start_x, int start_y, int dx, int dy, playerTags playerTag) {
        int count = 0;
        for (int offset = 0; offset < constantPool.countToWin; offset++)
            if (cells[start_x + offset * dx][start_y + offset * dy].equals(playerTag)) {
                count++;
                if (count == constantPool.countToWin)
                    return true;
            }
        return false;
    }
}
