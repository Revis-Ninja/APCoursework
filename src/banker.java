import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class banker extends Thread implements Serializable{
    public transient Socket socket;

    card card1 = new card("");
    card card2 = new card("");
    card card3 = new card("");
    card card4 = new card("");
    card card5 = new card("");
    card[] handcards = new card[3];
    static int handCount = 0;



    Deck dealer;

    public banker(Socket socket, Deck deck){
        this.dealer = deck;
        this.socket = socket;
        handcards[0]=card3;
        handcards[1]=card4;
        handcards[2]=card5;
        dealerHand();
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
        System.out.println("------------Dealer`s Action------------");

        while (true){
            System.out.println("A: Add one more card");
            System.out.println("B: No more card");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            if (line.equals("A")){
               card oneMore = dealOneCard();
               getOneMore(oneMore);
               showHandCard();
            }
            if (line.equals("B")){
                System.out.println("------------Dealer`s Hand Cards------------");
                showHandCard();
                sc.close();
                break;
            }
        }

    }
    public void dealCard(player punter){
        Collections.shuffle(dealer.deck);
        card c1 = dealer.deck.pop();
        card c2 = dealer.deck.pop();
        punter.getHandCard(c1,c2);
        dealer.cardCount += 2;
        dealer.burnCard.add(c1);
        dealer.burnCard.add(c2);

    }
    public card dealOneCard(){
        dealer.cardCount++;
        card c = dealer.deck.pop();
        dealer.burnCard.add(c);
        return c;
    }
    public void getOneMore(card cardExtra){
        handcards[handCount] = cardExtra;
        handCount++;
    }
    public void dealerHand(){
        card1 = dealer.deck.pop();
        card2 = dealer.deck.pop();
        dealer.cardCount += 2;
        dealer.burnCard.add(card1);
        dealer.burnCard.add(card2);
    }
    public void showHandCard(){
        card1.print();
        card2.print();
        handcards[0].print();
        handcards[1].print();
        handcards[2].print();
    }
    public void showDealerDeck(){
        dealer.showDeck();
    }
}
