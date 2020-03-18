import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Reader implements Runnable {
    private Socket socket;

    public Reader(Socket socket){
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
                if (line.equals("END")){
                    break;
                }
            }

            //sc.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
