import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteCard implements Runnable {

    private Socket socket;
    private banker b;

    public WriteCard(Socket socket, banker b){
        this.b = b;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("one card is dealt");

            os.writeObject(b.dealOneCard());
            os.writeObject(null);
            os.flush();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
