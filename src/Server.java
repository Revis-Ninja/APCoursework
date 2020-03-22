import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {



    public Server() {
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(6666);
        Socket socket = server.accept();
        Deck deck = new Deck();
        banker bk = new banker(socket,deck);

        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(bk);

        os.flush();

        bk.start();




    }
}
