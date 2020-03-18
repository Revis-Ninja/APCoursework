import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class banker extends Thread{
    Socket socket;

    card card1;
    card card2;

    public static banker dealer = new banker(Client.socket);

    public static Stack<card> deck;
    static ArrayList<card> burnCard = new ArrayList<>(52);
    static int cardCount = 0;

    String[] cards = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    public banker(Socket socket){
        deck = new Stack<>();
        this.socket = socket;
        for(int i=0;i<4;i++){
            for (int j=0;j<13;j++){
                card poker = new card(cards[j]);
                deck.push(poker);
            }
        }
        Collections.shuffle(deck);
        dealerHand();
    }

    public banker getDealer(){
        return this;
    }

    @Override
    public void run() {
        System.out.println(socket);
        this.showHandCard();
        Thread readThread = new Thread(new Reader(socket));
        readThread.start();
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread writeCardThread = new Thread(new WriteCard(socket));
        writeCardThread.start();
        try {
            writeCardThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void dealCard(player punter){
        Collections.shuffle(deck);
        punter.getHandCard(deck.pop(),deck.pop());
        cardCount += 2;
    }
    public void dealOneCard(player punter){
            punter.getOneMore(deck.pop());
            cardCount++;
    }
    public void dealerHand(){
        card1 = deck.pop();
        card2 = deck.pop();
        cardCount += 2;
    }
    public void showHandCard(){
        card1.print();
        card2.print();
    }
}
