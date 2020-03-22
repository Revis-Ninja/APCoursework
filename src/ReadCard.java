import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadCard implements Runnable {

    private Socket socket;
    private player p;

    public ReadCard(Socket socket, player p){
        this.socket = socket;
        this.p = p;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream sc = new ObjectInputStream(this.socket.getInputStream());
            card Card = (card) sc.readObject();
                Card.print();
                p.getOneMore(Card);
        }catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
