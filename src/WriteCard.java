import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteCard implements Runnable {

    private Socket socket;

    public WriteCard(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("This is your card: ");

                os.writeObject(banker.deck.pop());

            os.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
