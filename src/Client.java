import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client{
    public static Socket socket;

    static {
        try {
            socket = new Socket("localhost",6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Client(){

    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {



        ObjectInputStream sc = new ObjectInputStream(socket.getInputStream());

        banker bk = (banker)sc.readObject();


        stakes stakes = new stakes();
        player p1 = new player(socket,stakes);
        p1.setBanker(bk);
        bk.dealCard(p1);


        p1.start();


    }
}
