import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class writePlayer implements Runnable {
    Socket socket;
    player player;

    public writePlayer(player player, Socket socket){
        this.player = player;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("player final score has sent");

            os.writeObject(player);
            os.flush();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
