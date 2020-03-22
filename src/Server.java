import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

//    public static ServerSocket server;
//    Socket socket = server.accept();
//    static {
//        try {
//            server = new ServerSocket(6666);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Server() throws IOException {
    }


    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException {
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
