import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {



    public Server() {
    }


    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(6666);
        Socket socket = server.accept();

        Deck deck = new Deck();
        banker bk = new banker(socket,deck);

        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(bk);
        System.out.println("How many players do you want to have? ");
        System.out.println("Input numbers:  ");
        Scanner s = new Scanner(System.in);
        int playersNum = s.nextInt();
        os.write(playersNum);
        os.flush();

        bk.setTotalPlayer(playersNum);
        bk.start();




    }
}
