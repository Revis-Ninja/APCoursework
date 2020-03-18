import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Thread {
    public static Socket socket;

    static {
        try {
            socket = new Socket("localhost",6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    public Client(){
       // this.socket = socket;
    }

    @Override
    public void run() {
        //.getDealer().start();
    }

    public static void main(String[] args) throws IOException {

        player p1 = new player(socket);
        p1.start();

    }
}
