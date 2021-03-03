package NikeCredentialsLoader;

import com.mongodb.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NikeCredentialsLoader {

    public NikeCredentialsLoader(boolean sandbox) throws Exception{
        this.sandbox = sandbox;

        MongoClient mongoClient = new MongoClient(new MongoClientURI(""));
        DB nikeCredsDatabase = mongoClient.getDB("NikeBot");
        Logger mongoLoger = Logger.getLogger("org.mongodb.driver");
        mongoLoger.setLevel(Level.SEVERE);
        DBCursor cursor = (DBCursor) nikeCredsDatabase.getCollection("nikecreds").find();
        while (cursor.hasNext()){
            JSONObject collection = (JSONObject) new JSONParser().parse(String.valueOf(cursor.next()));
            String email = (String) collection.get("email");
            if ( this.sandbox ) {
                if (!email.equals("nicholas.lopezk92@aol.com")) {
                    continue;
                }
            }
            NikeCredentials loadedCredentials = new NikeCredentials();
            JSONObject idObject = (JSONObject)new JSONParser().parse(String.valueOf(collection.get("_id")));
            loadedCredentials._id = String.valueOf(idObject.get("$oid"));
            credentials.push(loadedCredentials);
        }

    }
    public static void main(String[] args) throws Exception {
        NikeCredentialsLoader credsLoader = new NikeCredentialsLoader(true);
        Stack<NikeCredentials> creds = credsLoader.credentials;
        while (!creds.isEmpty()){
            NikeCredentials currentCreds = creds.pop();

        }
        System.out.println("Complete");
    }


    private boolean sandbox;
    public Stack<NikeCredentials> credentials = new Stack<NikeCredentials>();
}
//load credentials/ ping pong: test creds pop out
