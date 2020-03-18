import java.net.Socket;

public class player extends Thread implements java.io.Serializable{
    card card1 = new card("");
    card card2 = new card("");
    card card3 = new card("");
    card card4 = new card("");
    card card5 = new card("");
    card[] handcards = new card[3];
    static int handCount = 0;

    private final transient Socket socket;

    public player(Socket socket){
        this.socket = socket;
        handcards[0]=card3;
        handcards[1]=card4;
        handcards[2]=card5;
    }

    public void getHandCard(card card1, card card2){
        this.card1 = card1;
        this.card2 = card2;
    }

    public void getOneMore(card cardExtra){
        handcards[handCount] = cardExtra;
        handCount++;
    }

    public void showCard(){
        card1.print();
        card2.print();
        handcards[0].print();
        handcards[1].print();
        handcards[2].print();
    }

    @Override
    public void run() {
        System.out.println(socket);
        System.out.println("Game starts. These are your hand cards");
        this.showCard();
        System.out.println("A: Add one more card");
        System.out.println("B: No more card");
        Thread writeThread = new Thread(new Writer(this.socket,this));
        writeThread.start();
        try {
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
