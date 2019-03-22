package client;

import thirdParty.constantPool;
import thirdParty.playerTags;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

public class JGameField extends JPanel {
    private GameLogic game;
    private PrintWriter messageFromClient;

    private void sendMessageToServer(String msg) {
        try {
            messageFromClient.println(msg);
            messageFromClient.flush();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JGameField(GameLogic game, PrintWriter messageFromClient) {
        setVisible(false);

        this.game = game;
        this.messageFromClient = messageFromClient;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x, y;
                x = (e.getX() - constantPool.fieldOffset) / constantPool.cellSize;
                y = (e.getY() - constantPool.fieldOffset) / constantPool.cellSize;

                // Send message from client to server
                sendMessageToServer(x + "/" + y + "/" + game.getPlayerNum());
            }
        });
    }

    // Swing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(100, 52, 40));
        g.fillRect(constantPool.fieldOffset, constantPool.fieldOffset, constantPool.FIELD_WIDTH, constantPool.FIELD_WIDTH);

        // Рисование разделительных линий
        for (int i = 0; i <= constantPool.fieldSize; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(constantPool.fieldOffset, constantPool.fieldOffset + i * constantPool.cellSize,
                    constantPool.fieldOffset + constantPool.FIELD_WIDTH,
                    constantPool.fieldOffset + i * constantPool.cellSize);
            g.drawLine(constantPool.fieldOffset + i * constantPool.cellSize, constantPool.fieldOffset,
                    constantPool.fieldOffset + i * constantPool.cellSize,
                    constantPool.fieldOffset + constantPool.FIELD_WIDTH);
        }

        // Отображение ходов
        for (int x = 0; x < constantPool.fieldSize; x++) {
            for (int y = 0; y < constantPool.fieldSize; y++) {
                if (!game.getCellMarker(x,y).equals(playerTags.no_one)) {
                    if (game.getCellMarker(x,y).equals(playerTags.first)) {
                        g.setColor(Color.BLACK);
                        g.fillOval(constantPool.fieldOffset + x * constantPool.cellSize,
                                   constantPool.fieldOffset + y * constantPool.cellSize,
                                      constantPool.cellSize, constantPool.cellSize);
                    }

                    if (game.getCellMarker(x,y).equals(playerTags.second)) {
                        g.setColor(Color.WHITE);
                        g.fillOval(constantPool.fieldOffset + x * constantPool.cellSize,
                                constantPool.fieldOffset + y * constantPool.cellSize,
                                constantPool.cellSize, constantPool.cellSize);
                    }
                }
            }
        }

        // Баннер победы
        if (game.getGameOver()) {
            g.setColor(new Color(100, 95, 0));
            int lineHeight = constantPool.FIELD_WIDTH / 5;
            g.fillRect(constantPool.fieldOffset, constantPool.fieldOffset + constantPool.FIELD_WIDTH / 2 - lineHeight / 2,
                    constantPool.FIELD_WIDTH, lineHeight);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            String gom = game.getGameOverMessage();
            g.drawString(gom, constantPool.fieldOffset + constantPool.FIELD_WIDTH / 2 + 30,
                    constantPool.fieldOffset + constantPool.FIELD_WIDTH / 2 + gom.length() / 2 + 10);
        }
    }
}
