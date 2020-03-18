import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadCard implements Runnable {

    private Socket socket;

    public ReadCard(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream sc = new ObjectInputStream(this.socket.getInputStream());
            card Card;
            while((Card = (card) sc.readObject())!=null) {
                Card.print();
            }
            sc.close();
        }catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
