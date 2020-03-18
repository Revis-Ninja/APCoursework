import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class banker extends Thread implements Serializable{
    public transient Socket socket;

    card card1;
    card card2;
    Deck deck;

//    public static Stack<card> deck = new Stack<>();
//    static ArrayList<card> burnCard = new ArrayList<>(52);
//    static int cardCount = 0;
//    String[] cards = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    public banker(Socket socket, Deck deck){
        this.deck = deck;
        this.socket = socket;
//        for(int i=0;i<4;i++){
//            for (int j=0;j<13;j++){
//                card poker = new card(cards[j]);
//                deck.push(poker);
//            }
//        }
//        Collections.shuffle(deck);
        dealerHand();
    }

    public banker getDealer(){
        return this;
    }

    @Override
    public void run() {

        System.out.println(socket);
        this.showHandCard();
        System.out.println("------------Player`s Action------------");
        Thread readThread = new Thread(new Reader(socket, this));
        readThread.start();
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void dealCard(player punter){
        Collections.shuffle(deck.deck);
        card c1 = deck.deck.pop();
        card c2 = deck.deck.pop();
        punter.getHandCard(c1,c2);
        deck.cardCount += 2;
        deck.burnCard.add(c1);
        deck.burnCard.add(c2);

    }
    public card dealOneCard(){
        deck.cardCount++;
        card c = deck.deck.pop();
        deck.burnCard.add(c);
        return c;
    }
    public void dealerHand(){
        card1 = deck.deck.pop();
        card2 = deck.deck.pop();
        deck.cardCount += 2;
        deck.burnCard.add(card1);
        deck.burnCard.add(card2);
    }
    public void showHandCard(){
        card1.print();
        card2.print();
    }
}
