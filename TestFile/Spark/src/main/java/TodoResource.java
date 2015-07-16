import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static spark.Spark.*;

/**
 * Created by dshively on 6/11/15.
 */
public class TodoResource {

    private final MongoDatabase db;
    private final MongoCollection<Document> collection;

    public TodoResource(MongoDatabase mongoDatabase) {
        this.db = mongoDatabase;
        this.collection = db.getCollection("networks");
        setupEndpoints();
    }

    private void setupEndpoints() {
        before((request, response) -> response.type("application/json"));

        get("/:network", (request, response) ->
            collection.find(new Document("ssid", request.params(":network"))).first().toJson()
        );
    }
}
