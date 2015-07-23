package com.ipass.dwfile;

import com.allanbank.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by dshively on 7/2/15.
 */
public class MongoManaged implements Managed {
    private MongoClient mongo;

    public MongoManaged(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongo.close();
    }
}
