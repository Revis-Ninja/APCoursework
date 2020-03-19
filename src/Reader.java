import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

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
            //Scanner sc = new Scanner(this.socket.getInputStream());
            while (true) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(), "UTF-8"));

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
                if (line.equals("B")){
                    System.out.println("The player`s turn is over");
                    break;
                }
                if (line.equals("end")){
                    break;
                }

            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
