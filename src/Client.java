import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

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

        int playersNum = sc.read();

        Lock lock = new Lock();

        for (int i=1;i<playersNum+1;i++){

            stakes stakes = new stakes();
            player p = new player(socket,stakes, lock, i);
            p.setBanker(bk);
            bk.dealCard(p);
            p.start();
        }



    }
}
