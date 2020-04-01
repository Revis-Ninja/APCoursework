import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public Server() {
    }



    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(6666);
        Socket socket = server.accept();

        Deck deck = new Deck();
        banker bk = new banker(socket,deck);

        //send an object of banker to client.main.
        //By this way, it could call the same banker`s method in client.main
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(bk);


        System.out.println("How many players do you want to have? ");
        System.out.println("Input numbers:  ");
        Scanner s = new Scanner(System.in);
        int playersNum = s.nextInt();
        //input an integer of how many players join the game
        // and then sent the integer to client.main.
        os.write(playersNum);
        os.flush();

        bk.setTotalPlayer(playersNum);

        bk.start();




    }


}
