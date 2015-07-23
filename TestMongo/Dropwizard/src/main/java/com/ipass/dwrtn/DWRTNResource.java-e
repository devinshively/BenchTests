package com.ipass.dwfile;

import co.paralleluniverse.fibers.Suspendable;
import com.allanbank.mongodb.MongoCollection;
import com.allanbank.mongodb.MongoIterator;
import com.allanbank.mongodb.bson.Document;
import com.codahale.metrics.annotation.Timed;
import static com.allanbank.mongodb.builder.QueryBuilder.where;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by dshively on 7/2/15.
 */
@Path("/{network}")
@Produces(MediaType.APPLICATION_JSON)
public class DWFileResource {

    private MongoCollection collection;

    public DWFileResource(MongoCollection networks) {
        this.collection = networks;
    }

    @Suspendable
    @GET
    @Timed
    public String getNetwork(@PathParam("network") String network) {
        MongoIterator<Document> iter = collection.find(where("ssid").equals(network));
        while (iter.hasNext()) {
            return iter.next().toString();
        }
        return "";
    }
}
