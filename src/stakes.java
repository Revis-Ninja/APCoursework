import java.io.Serializable;

public class stakes implements Serializable {
    int Stakes = 100;
    int bet = 0;

    public stakes(){
    }

    public int Bet(int money){
        bet = bet + money;
        return bet;
    }

    public void lose(){
        Stakes = Stakes - bet;
    }

    public void win(){
        Stakes = Stakes + bet;
    }


    public void blackJack(){
        Stakes = Stakes + bet*2;
    }
    public void loseBlackJack(){ Stakes = Stakes - bet*2;}

    public boolean isGreater(int dealer, int player, int position){
        boolean result = false;
        String PLAYER = "player"+position;
        if (dealer < player) {
            if (player==21){
                System.out.println("BLACKJACK !!!"+PLAYER+" earn double!!");
                blackJack();
            }else {
                System.out.println("---------------"+PLAYER+" win!--------------");
                win();
            }
            result = true;
        }else if (dealer > player){
            System.out.println("---------------"+PLAYER+" lose!----------------");
            if (dealer == 21){
                loseBlackJack();
            }else {
                lose();
            }
        }else {
            System.out.println("---------------Tie!----------------");
            clearBet();
        }
        System.out.println("-----------"+PLAYER+" current stakes are "+Stakes+"--------------");
        return result;
    }

    public void clearBet(){
        this.bet = 0;
    }

}
