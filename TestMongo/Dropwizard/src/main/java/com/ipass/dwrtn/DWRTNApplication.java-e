package com.ipass.dwfile;

import co.paralleluniverse.fibers.dropwizard.FiberApplication;
import co.paralleluniverse.fibers.mongodb.FiberMongoFactory;
import com.allanbank.mongodb.MongoClient;
import com.allanbank.mongodb.MongoClientConfiguration;
import com.allanbank.mongodb.MongoCollection;
import com.allanbank.mongodb.MongoDatabase;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

/**
 * Created by dshively on 7/2/15.
 */
public class DWFileApplication extends FiberApplication<DWFileConfiguration> {
    public static void main(String[] args) throws Exception {
        new DWFileApplication().run(new String[]{"server", "src/main/resources/dwfile.yml"});
    }

    @Override
    public String getName() {
        return "dwfile";
    }

    @Override
    public void initialize(Bootstrap<DWFileConfiguration> bootstrap) {

    }

    @Override
    public void fiberRun(DWFileConfiguration configuration, Environment environment) throws UnknownHostException {
        MongoClientConfiguration config = new MongoClientConfiguration();
        config.addServer("127.0.0.1:27017");
        MongoClient mongoClient = FiberMongoFactory.createClient(config);
        MongoDatabase mongoDb = mongoClient.getDatabase("demo");
        MongoCollection collection = mongoDb.getCollection("networks");
//        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport);
//        DB db = mongo.getDB(configuration.mongodb);
//        JacksonDBCollection<Network, String> networks = JacksonDBCollection.wrap(db.getCollection("networks"), Network.class, String.class);
        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        environment.lifecycle().manage(mongoManaged);

        final DWFileResource resource = new DWFileResource(collection);
        environment.jersey().register(resource);
    }
}
