import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class player extends Thread implements java.io.Serializable{
    card card1 = new card("");
    card card2 = new card("");
    card card3 = new card("");
    card card4 = new card("");
    card card5 = new card("");

    //except initial two hand cards, all of extra hand cards are stored in an array
    card[] extraHandcards = new card[3];
    //helper integer to position the extra hand card
    int handCount = -1;
    //finally, 5 hand cards are put together to calculate the points.
    ArrayList<card> check;
    //player`s position, which is used for banker to compare in order.
    int position = 0;
    //to synchronize run method
    private final Lock lock;
    //store player`s final score
    int FinalScore = 0;

    stakes stakes;

    banker dealer = null;

    private final transient Socket socket;

    public player(Socket socket, stakes stakes, Lock lock, int position){

        this.position = position;
        this.lock = lock;
        this.stakes = stakes;
        this.socket = socket;
        extraHandcards[0]=card3;
        extraHandcards[1]=card4;
        extraHandcards[2]=card5;
        check = new ArrayList<>();
    }

    public void setBanker(banker dealer){
        this.dealer = dealer;
    }

    public void getHandCard(card card1, card card2){
        this.card1 = card1;
        this.card2 = card2;
        check.add(card1);
        check.add(card2);
    }

    public void getOneMore(card cardExtra){
        handCount++;
        extraHandcards[handCount] = cardExtra;
        check.add(extraHandcards[handCount]);

    }

    public void showCard(){
        card1.print();
        card2.print();
        extraHandcards[0].print();
        extraHandcards[1].print();
        extraHandcards[2].print();
    }
    //calculate points of hand cards excluding Ace
    public int checkPoint(){
        int points = 0;
        for (card c: check
             ) {
            if ((c.num.trim().equals("J"))||(c.num.trim().equals("Q"))||(c.num.trim().equals("K"))){
                points = points+10;
            }
            else if (c.num.trim().equals("")||(c.num.trim().equals("A"))){
                continue;
            }
            else{
                int temp = Integer.parseInt(c.num.trim());
                points = points+temp;
            }
        }
        int result = checkA(points);

        System.out.println("------------Your current points are------------");
        System.out.println(result);

        if (result==21){
            showCard();
        }

        FinalScore = result;
        return result;
    }
    //Calculate Ace
    public int checkA(int points){
        int count = -1;
        HashMap<Integer,String> countA = new HashMap<>();
        countA.put(0,"the first A:");
        countA.put(1,"the second A:");
        countA.put(2,"the third A:");
        countA.put(3,"the fourth A:");

        for (card c: check){
            if (c.num.trim().equals("A")){
                count++;
                int temp = points;
                System.out.println("There are two options for your "+countA.get(count));
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
                        break;
                    case "2":
                        points = points+11;
                        break;
                }
            }
        }
        return points;
    }

    //bust out if exceed 21
    public boolean bust(int result){
        boolean flag = false;
        if (result>21){
            flag = true;
        }
        return flag;
    }



    @Override
    public void run() {

        synchronized (lock) {

            System.out.println("PLAYER "+position+" is playing: ");
//            System.out.println(socket);

            System.out.println("-----------Your current stakes are " + stakes.Stakes + "--------------");

            System.out.println("Plz bet");
            Scanner sc = new Scanner(System.in);
            int line = sc.nextInt();

            stakes.Bet(line);


            System.out.println("Game starts.");
            this.showCard();
            System.out.println("A: Add one more card");
            System.out.println("B: No more card");
            System.out.println("C: Add card with raising the bet");
            Thread writeThread = new Thread(new Writer(this.socket, this));
            writeThread.start();
            try {
                writeThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
