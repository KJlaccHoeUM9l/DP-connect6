package client;

import thirdParty.constantPool;

import java.net.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class Client{
    private static Socket clientSocket;
    static Scanner messageFromServer;
    static PrintWriter messageFromClient;

    public Client() {
        try {
            clientSocket = new Socket(constantPool.hostName, constantPool.port);
            messageFromServer = new Scanner(clientSocket.getInputStream());
            messageFromClient = new PrintWriter(clientSocket.getOutputStream());

            new LifeCycle(new JGameWindow(messageFromClient), messageFromServer).start();
            System.out.println("Client has been connected");

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage()); // socket creation failed
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    public static void main(String[] args) { Client c = new Client(); }
}
