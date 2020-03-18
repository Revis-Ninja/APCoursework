import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer implements Runnable {
    private Socket socket;

    public Writer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream());

            while(true){
                String line = sc.nextLine();
                os.write(line + '\n');
                os.flush();
                if (line.equals("END")){
                    break;
                }
            }
            sc.close();
            os.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
