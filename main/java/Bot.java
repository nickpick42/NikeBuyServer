import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Bot extends Thread{

    public Bot(Socket connection) throws Exception{
        this.connection = connection;
        this.start();
    }
    public void run(){
        try {

            writer = new PrintWriter(connection.getOutputStream());
            reader = new Scanner(connection.getInputStream());
            JSONObject initial_message = new JSONObject();
            initial_message.put("type", "server_initial_connection");
            writer.write(initial_message.toJSONString());
            writer.flush();
            botResponse();
        }catch (Exception e){
            if (e.toString().equals("java.util.NoSuchElementException: No line found")){
                System.out.println("Bot Disconnected");
            }else{
                System.out.println(e.toString());
            }
        }
    }
    public String getProxy() {
        return proxy;
    }


    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public String getNikeUser() {
        return nikeUser;
    }

    public void setNikeUser(String nikeUser) {
        this.nikeUser = nikeUser;
    }

    public String getNikePassword() {
        return nikePassword;
    }

    public void setNikePassword(String nikePassword) {
        this.nikePassword = nikePassword;
    }

    public JSONObject botResponse() throws Exception{

        while (true) {
            if ( reader.hasNext()){
                break;
            }

        }

        String bot_response = reader.nextLine();
        JSONObject botResponseObject = (JSONObject)(new JSONParser().parse(bot_response));
        System.out.println("FROM RESPONSE FUNCTION:" + botResponseObject.toJSONString());

        return botResponseObject;

    }
    public void sendCommand(String command) throws Exception {
        writer.write(command + "\n");
        writer.flush();

    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public Scanner getReader() {
        return reader;
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }

    private String proxy;
    private Socket connection;
    private String nikeUser;
    private String nikePassword;
    private PrintWriter writer;
    private Scanner reader;
}
