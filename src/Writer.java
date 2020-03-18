import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer implements Runnable {
    private Socket socket;
    private player p;

    public Writer(Socket socket, player p){
        this.p = p;
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
                if (line.equals("A")){

                    Thread readCardThread = new Thread(new ReadCard(socket,p));
                    System.out.println("This is your extra card: ");
                    readCardThread.start();
                    try {
                        readCardThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("A: Add one more card");
                    System.out.println("B: No more card");
                    os.flush();

                }

                if (line.equals("B")){
                    System.out.println("Your turn is over");
                    break;
                }

            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
