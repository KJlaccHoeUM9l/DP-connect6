package client;

import thirdParty.playerTags;

public class Player {
    private playerTags playerTag;
    private GameLogic gameField;
    private int remainingShots = 0;

    public Player(GameLogic gameField, playerTags playerTag) {
        this.playerTag = playerTag;
        this.gameField = gameField;
    }

    public int getRemainingShots() {
        return remainingShots;
    }

    public void setRemainingShots(int remainingShots) {
        this.remainingShots = remainingShots;
    }

    void shot(int x, int y) {
        gameField.markCell(x, y, playerTag);
    }

    boolean win() {
        return gameField.checkWin(playerTag);
    }
}
