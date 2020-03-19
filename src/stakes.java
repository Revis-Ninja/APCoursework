import java.io.Serializable;

public class stakes implements Serializable {
    int bet = 100;

    public stakes(){
    }

    public int Bet(String money){
        int result = Integer.parseInt(money);
        bet = bet - result;
        return result;
    }

    public void showStakes(){
        System.out.println(bet);
    }

}
