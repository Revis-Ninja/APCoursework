import java.io.Serializable;

public class card implements Serializable {
    //String suits;
    String num;

    public card(String num){
        this.num = num;
    }

    public void print(){
        System.out.println(num);
    }
}
