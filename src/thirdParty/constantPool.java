package thirdParty;

public class constantPool {
    // Sockets
    public static final String hostName = "localhost";
    public static final int port = 4747;
    public static final String firstPlayer = "firstPlayer";
    public static final String secondPlayer = "secondPlayer";
    public static final String startGame = "startGame";

    // Game window
    public static final int windowWidth = 476;
    public static final int windowHeight = 525;

    // Параметры игрового поля
    public static final int fieldOffset = 10;
    public static final int FIELD_WIDTH = windowWidth - 2 * fieldOffset;
    public static final int fieldSize = 19;
    public static final int countToWin = 3;
    public static final int cellSize = FIELD_WIDTH / fieldSize;


}
