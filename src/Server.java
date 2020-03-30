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


        //发送一个庄家的对象给client，方便client里的线程调用banker的方法
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(bk);


        System.out.println("How many players do you want to have? ");
        System.out.println("Input numbers:  ");
        Scanner s = new Scanner(System.in);
        int playersNum = s.nextInt();
        //想要多少个玩家玩这个游戏，然后在client的主方法里跑多少个player的线程
        os.write(playersNum);
        os.flush();

        bk.setTotalPlayer(playersNum);

        bk.start();




    }


}
