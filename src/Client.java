import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    public static Socket socket;

    static {
        try {
            socket = new Socket("localhost",6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static JTextField clientText= new JTextField();;
//    static JTextArea clientContent= new JTextArea();
//    static String clientString;
//
//
//    public void launchFrame() {
//        this.setSize(300, 300);
//        this.setLocation(300, 300);
//        clientText.addActionListener(this);
//        this.add(clientText, "South");
//        this.add(clientContent, "North");
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//        this.pack();
//        clientText.setCaretPosition(clientText.getText().length());
//        this.setVisible(true);
//    }
//
//    public static void showMsg(String str){
//        clientContent.append(str+"\n");
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        clientString = clientText.getText().trim();
//        showMsg(clientString);
//        clientText.setText("");
//    }
    public Client(){

    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //receive the banker object from server.main
        ObjectInputStream sc = new ObjectInputStream(socket.getInputStream());
        banker bk = (banker)sc.readObject();
        //receive the number of players involved from server.main
        int playersNum = sc.read();
        //The lock is for player to synchronize the run method
        Lock lock = new Lock();

//        new Client().launchFrame();

        for (int i=1;i<playersNum+1;i++){

            stakes stakes = new stakes();
            player p = new player(socket,stakes, lock, i);
            p.setBanker(bk);
            bk.dealCard(p);
            p.start();
        }



    }

}
