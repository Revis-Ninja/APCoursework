import java.io.*;
import java.net.Socket;
import java.util.*;


public class banker extends player implements Serializable{
    public final transient Socket socket;
    int totalPlayer = 0;
    Deck dealer;
    HashMap<player,Integer> PointStorage = new HashMap<>();
    public banker(Socket socket, Deck deck){
        super(socket, new stakes(), new Lock(), 0);
        this.dealer = deck;
        this.socket = socket;

        dealerHand();
    }

    @Override
    public void run() {
            System.out.println(socket);
            this.showCard();
            System.out.println("------------Player`s Action------------");
            Thread readThread = new Thread(new Reader(socket, this));
            readThread.start();
            try {
                readThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------------Dealer`s Action------------");
//            int result = checkPoint();

            while (true) {
                System.out.println("A: Add one more card");
                System.out.println("B: No more card");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if (line.equals("A")) {
                    System.out.println("This is your extra card: ");
                    card oneMore = dealOneCard();
                    getOneMore(oneMore);
                    oneMore.print();
                    System.out.println("------------Dealer`s Hand Cards------------");
                    showCard();
                    if (bust(checkPoint())) {
                        System.out.println("Your points exceed 21 points! BUST OUT!!");
                        break;
                    }
                }
                if (line.equals("B")) {
                    System.out.println("------------Dealer`s Hand Cards------------");
                    showCard();
                    int resultWithoutA = this.checkPoint();
                    int resultWithA = checkA(resultWithoutA);
                    FinalScore = resultWithA;
                    System.out.println("------------Your current points are------------");
                    System.out.println(resultWithA);

                    if (resultWithA == 21) {
                        System.out.println("BLACKJACK !!! You earn double!!");
                    }
                    break;
                }
            }


        Compare(FinalScore);

        // autoPlay(result);
    }

    public void Compare(int FinalScore){
        int playerPosition=0;
        for (player p: this.PointStorage.keySet()
        ) {
            playerPosition++;
            p.stakes.isGreater(FinalScore, p.FinalScore, playerPosition);
        }
    }

    public void putFinishedPlayer(player p, int score){
        PointStorage.put(p,score);
    }

    public void autoPlay(int points){
        showCard();
        if (points<17){
            getOneMore(this.dealOneCard());
            autoPlay(this.checkPoint());
        }
    }


    @Override
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
        FinalScore = points;
        return points;
    }
    @Override
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

    public void dealerHand(){
        card1 = dealer.deck.pop();
        card2 = dealer.deck.pop();
        dealer.cardCount += 2;

        check.add(card1);
        check.add(card2);
        check.add(extraHandcards[0]);
        check.add(extraHandcards[1]);
        check.add(extraHandcards[2]);

        dealer.burnCard.add(card1);
        dealer.burnCard.add(card2);
    }
    public void setTotalPlayer(int totalPlayer){
        this.totalPlayer = totalPlayer;
    }

    public void showDealerDeck(){
        dealer.showDeck();
    }
}
