import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck implements Serializable {
    public  Stack<card> deck = new Stack<>();
    String[] cards = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    ArrayList<card> burnCard = new ArrayList<>(52);
     int cardCount = 0;


    public Deck(){
        for(int i=0;i<4;i++){
            for (int j=0;j<13;j++){
                card poker = new card(cards[j]);
                deck.push(poker);
            }
        }
        Collections.shuffle(deck);
    }
}
