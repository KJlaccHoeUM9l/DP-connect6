import client.Client;
import server.Server;
import thirdParty.playerTags;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
    public static void main(String[] args) {

        /*ExecutorService exec = Executors.newFixedThreadPool(3);

        exec.execute(new Server());
        exec.execute(new Client());
        exec.execute(new Client());*/



       /* Thread t1 = new Thread(new Server());
        Client c1 = new Client();
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(new Client());

        t1.start();
        t2.start();
        t3.start();*/

        System.out.println("Hello world");
    }
}
