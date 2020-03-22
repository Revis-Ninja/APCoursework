import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Reader implements Runnable {
    private Socket socket;
    private banker b;
    public Reader(Socket socket, banker b){
        this.b = b;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            int count = 0;

            while(true){
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(), StandardCharsets.UTF_8));

                String line = br.readLine();
                System.out.println(line);
                if (line.equals("A")||line.equals("C")){
                    Thread writeCardThread = new Thread(new WriteCard(socket, b));
                    writeCardThread.start();
                    try {
                        writeCardThread.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (line.equals("B")||line.equals("bust")){
                    count++;
                    System.out.println("The player "+count+"`s turn is over");

                    Thread readPlayer = new Thread(new readPlayer(b,socket));
                    readPlayer.start();
                    try {
                        readPlayer.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count == b.totalPlayer){
                        break;
                    }
                    //break;
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
