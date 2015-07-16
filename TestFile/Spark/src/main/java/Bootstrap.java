import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by dshively on 6/11/15.
 */
public class Bootstrap {
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
        port(PORT);
        new TodoResource(mongo());
    }

    private static MongoDatabase mongo() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost");
        return mongoClient.getDatabase("demo");
    }
}
