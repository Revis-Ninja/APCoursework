import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class player extends Thread implements java.io.Serializable{
    card card1 = new card("");
    card card2 = new card("");
    card card3 = new card("");
    card card4 = new card("");
    card card5 = new card("");
    card[] handcards = new card[3];
    static int handCount = 0;

    stakes stakes;

    private final transient Socket socket;

    public player(Socket socket, stakes stakes){
        this.stakes = stakes;
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



    public int checkPoint(){
        int points = 0;
        ArrayList<card> check = new ArrayList<>();
        check.add(card1);
        check.add(card2);
        check.add(handcards[0]);
        check.add(handcards[1]);
        check.add(handcards[2]);

        for (card c: check
             ) {
            if (c.num.trim().equals("A")){
                int temp = points;
                System.out.println("There are two options for your points: ");
                temp = temp+1;
                System.out.println("1: "+temp);
                temp = temp-1+11;
                System.out.println("2: "+temp);
                System.out.println("----------Plz select your options---------");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                switch (line){
                    case "1":
                        points = points+1;
                        System.out.println("your current points: "+points);
                        break;
                    case "2":
                        points = points+11;
                        System.out.println("your current points: "+points);
                        break;
                }
            }
            else if ((c.num.trim().equals("J"))||(c.num.trim().equals("Q"))||(c.num.trim().equals("K"))){
                points = points+10;
            }
            else if (c.num.trim().equals("")){
                continue;
            }
            else{
                int temp = Integer.parseInt(c.num.trim());
                points = points+temp;
            }
        }
        System.out.println("------------Your current points are------------");
        System.out.println(points);
        return points;
    }

    @Override
    public void run() {
        System.out.println(socket);

        System.out.print("your stakes are ");
        stakes.showStakes();

        System.out.println("Plz bet");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        stakes.Bet(line);
        System.out.println("Game starts. These are your hand cards");
        this.showCard();
        System.out.println("A: Add one more card");
        System.out.println("B: No more card");
        System.out.println("C: Add card with raising the bet");
        Thread writeThread = new Thread(new Writer(this.socket,this));
        writeThread.start();
        try {
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkPoint();

    }
}
