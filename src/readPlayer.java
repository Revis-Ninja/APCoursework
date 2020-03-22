import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class readPlayer implements Runnable{
    Socket socket;
    banker banker;

    public readPlayer(banker banker, Socket socket){
        this.banker = banker;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream sc = new ObjectInputStream(this.socket.getInputStream());
            player p = (player) sc.readObject();

            banker.putFinishedPlayer(p,p.FinalScore);
            System.out.println(banker.PointStorage);
        }catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
