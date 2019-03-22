package server;

import thirdParty.constantPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(constantPool.port);
            System.out.println("Server ready");

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);

                if (clients.size() == 2) {
                    new Thread(clients.get(0)).start();
                    clients.get(0).sendMessageToClient(constantPool.firstPlayer);
                    new Thread(clients.get(1)).start();
                    clients.get(1).sendMessageToClient(constantPool.secondPlayer);

                    sendMessageToAllClients(constantPool.startGame);
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен!");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler c: clients)
            c.sendMessageToClient(msg);
    }

    public static void main(String[] args) {
        Server s = new Server();
    }
}
