package projet.bananaspoker.metier;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public Client() throws IOException {
        Socket socket = new Socket("localhost", 22017);

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { socket.close();}
        }
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
