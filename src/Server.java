import java.io.IOException;
import java.io.InputStream;
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
        banker bk = new banker(socket);
        bk.start();
        //banker.dealer.start();



    }
}
