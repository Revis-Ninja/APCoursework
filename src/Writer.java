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
                    p.checkPoint();
                    Thread readCardThread = new Thread(new ReadCard(socket,p));
                    System.out.println("This is your extra card: ");
                    readCardThread.start();
                    try {
                        readCardThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (p.bust(p.checkPoint())){
                        System.out.println("Your points exceed 21 points! BUST OUT!!");
                        os.write("end");
                        os.flush();
                        break;
                    }
                    System.out.println("A: Add one more card");
                    System.out.println("B: No more card");
                    System.out.println("C: Add card with raising the bet");
                    os.flush();

                }

                if (line.equals("B")){
                    System.out.println("Your turn is over");
                    break;
                }

                if (line.equals("C")){
                    System.out.println("Plz bet");
                    Scanner sc2 = new Scanner(System.in);
                    String line2 = sc2.nextLine();
                    p.stakes.Bet(line2);
                    Thread readCardThread = new Thread(new ReadCard(socket,p));
                    System.out.println("This is your extra card: ");
                    readCardThread.start();
                    try {
                        readCardThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (p.bust(p.checkPoint())){
                        System.out.println("Your points exceed 21 points! BUST OUT!!");
                        os.write("end");
                        os.flush();
                        break;
                    }
                    System.out.println("A: Add one more card");
                    System.out.println("B: No more card");
                    System.out.println("C: Add card with raising the bet");
                    os.flush();
                }

            }


        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
