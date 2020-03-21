import java.io.Serializable;

public class stakes implements Serializable {
    int Stakes = 100;
    int bet = 0;

    public stakes(){
    }

    public int Bet(int money){
//        Stakes = Stakes - money;
        bet = bet + money;
        return bet;
    }

    public void lose(){
        Stakes = Stakes - bet;
    }

    public void win(){
        Stakes = Stakes - bet;
    }

    public void blackJack(){
        Stakes = Stakes + bet*2;
    }

    public boolean isGreater(int dealer, int player){
        boolean result = false;
        if (dealer < player) {
            if (player==21){
                System.out.println("BLACKJACK !!! You earn double!!");
                blackJack();
            }else {
                System.out.println("---------------You win!--------------");
                win();
            }
            result = true;
        }else {
            System.out.println("---------------You lose!----------------");
            lose();
        }
        showStakes();
        return result;
    }

    public void clearBet(){
        this.bet = 0;
    }

    public void showStakes(){
        System.out.println("-----------Your current stakes are "+Stakes+"--------------");
    }

}
