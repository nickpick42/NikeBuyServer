import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class main {


    public static boolean isSandbox = true;

    public static void sandboxStack (BotCommands botCommands) throws Exception {

        botCommands.sendCredentials();
        Thread.sleep(1000);

        botCommands.setUrl();
        Thread.sleep(1000);

        botCommands.assignProxies();
        Thread.sleep(1000);


        botCommands.initialize();

    }

    public static void main(String[] args) throws Exception{
        final BotCommands botCommands = new BotCommands(connectedBots,isSandbox);

        ServerSocket server = new ServerSocket(3929);
        System.out.println("Server accepting connections on port " + 3929);
        Semaphore semaphore = new Semaphore(1);
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try{
                        System.out.println("Please enter command:");
                        Scanner input = new Scanner(System.in);
                        String command = input.nextLine();
                        if ( command.equals("assign_proxy")){
                            botCommands.assignProxies();
                        }else if ( command.equals("send_credentials")){
                            botCommands.sendCredentials();
                        }else if (command.equals("initialize")){
                            botCommands.initialize();
                        }else if ( command.equals("set_url") ){
                            botCommands.setUrl();
                        }else if ( command.equals("quick_buy") ){
                            botCommands.quickBuy();
                        }else if (command.equals("sandbox") ){
                            sandboxStack(botCommands);
                        }
                    }catch (Exception e){

                    }
                }
            }
        }).start();
        while ( true ){
            semaphore.acquire();
            Socket bot = server.accept();
            connectedBots.add(new Bot(bot));
            semaphore.release();
        }






    }

    private static ArrayList<Bot> connectedBots = new ArrayList<Bot>();

}
