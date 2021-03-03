import NikeCredentialsLoader.NikeCredentialsLoader;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import NikeCredentialsLoader.NikeCredentials;

public class BotCommands {
    public BotCommands(ArrayList<Bot> bots,boolean isSandbox) throws Exception {
        this.bots = bots;
        File proxiesTxt = new File("src/proxies.txt");
        Scanner reader = new Scanner(proxiesTxt);
        while (reader.hasNext()){
            proxies.push(reader.nextLine());
        }
        credentialsLoader = new NikeCredentialsLoader(true);

    }
    public void assignProxies () throws Exception {
        JSONObject assignProxyObj = new JSONObject();
        assignProxyObj.put("type","assign_proxy");


        for ( int i  = 0 ; i  < bots.size(); i++){
            while ( true) {
                if ( proxies.isEmpty() ){
                    System.out.println("NO MORE PROXIES");
                }
                String currentProxy = proxies.pop();
                Bot currentBot = bots.get(i);
                assignProxyObj.put("proxy",currentProxy);
                currentBot.sendCommand(assignProxyObj.toJSONString());
                JSONObject botResponse = currentBot.botResponse();
                if ( botResponse.get("type").equals("proxy_check") && botResponse.get("valid").equals("true") ){
                    break;
                }else {
                    System.out.println("bad proxy retyting");
                }
            }
        }
        System.out.println("Assigned Proxies to all bots");
    }
    public void sendCredentials () throws Exception {
        Stack<NikeCredentials>  credentialsStack = credentialsLoader.credentials;
        for ( int i = 0; i < bots.size(); i++){
            Bot currentBot = bots.get(i);
            if ( credentialsStack.isEmpty() ){
                break;
            }
            NikeCredentials currentCredential = credentialsStack.pop();
            JSONObject credentials = new JSONObject();
            credentials.put("type","nike_credentials");
            credentials.put("_id", currentCredential._id);
            currentBot.sendCommand(credentials.toJSONString());
            JSONObject response = currentBot.botResponse();
            System.out.println(response);

        }

    }
    public void initialize() throws Exception {
        JSONObject initilization = new JSONObject();
        initilization.put("type","initialize");
        for ( int i = 0; i < bots.size(); i++){
            Bot currentBot = bots.get(i);
            currentBot.sendCommand(initilization.toJSONString());
            JSONObject response = currentBot.botResponse();
            System.out.println(response);

        }



    }
    public void setUrl() throws Exception{
        JSONObject url = new JSONObject();
        url.put("type","url_initilization");
        url.put("url","https://www.nike.com/launch/t/blazer-low-77-vintage-midnight-navy");
        for ( int i = 0 ; i < bots.size(); i++){
            bots.get(i).sendCommand(url.toJSONString());
        }

    }
    public void quickBuy() throws Exception{
        JSONObject quickBuy = new JSONObject();
        quickBuy.put("type","quick_buy");
        for ( int i = 0; i < bots.size(); i++){
            bots.get(i).sendCommand(quickBuy.toJSONString());

        }
    }

    private ArrayList<Bot> bots;
    private Stack<String> proxies = new Stack<String>();
    private NikeCredentialsLoader credentialsLoader;

}
