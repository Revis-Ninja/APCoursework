import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer implements Runnable {
    private final Socket socket;
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
                os.write(line+'\n');
                os.flush();
                if (line.equals("A")){
                    //If A, ready to receive extra card.
                    Thread readCardThread = new Thread(new ReadCard(socket,p));
                    System.out.println("This is your extra card: ");
                    readCardThread.start();
                    try {
                        readCardThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    int playerTempScore = p.checkPoint();
                    if (p.bust(playerTempScore)){
                        System.out.println("Your points exceed 21 points! BUST OUT!!");
                        os.write("bust"+'\n');
                        os.flush();
                        break;
                    }


                    System.out.println("A: Add one more card");
                    System.out.println("B: No more card");
                    System.out.println("C: Add card with raising the bet");
                    os.flush();

                }

                if (line.equals("B")){
                        p.checkPoint();
                        break;
                }

                if (line.equals("C")){
                    System.out.println("Plz bet");
                    Scanner sc2 = new Scanner(System.in);
                    int line2 = sc2.nextInt();
                    p.stakes.Bet(line2);

                    //If C, ready to receive extra card.
                    Thread readCardThread = new Thread(new ReadCard(socket,p));
                    System.out.println("This is your extra card: ");
                    readCardThread.start();
                    try {
                        readCardThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    int playerTempScore = p.checkPoint();
                    if (p.bust(playerTempScore)){
                        System.out.println("Your points exceed 21 points! BUST OUT!!");
                        os.write("bust"+'\n');
                        os.flush();
                        break;
                    }


                    System.out.println("A: Add one more card");
                    System.out.println("B: No more card");
                    System.out.println("C: Add card with raising the bet");
                    os.flush();
                }
            }

            //if all finished, send player and his final score to banker
            Thread writePlayer = new Thread(new writePlayer(p,socket));
            writePlayer.start();
            writePlayer.join();

        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
