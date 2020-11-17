package com.cbd.mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.Iterator;
import java.util.function.Consumer;

public class App 
{
    public static void main( String[] args ) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("rest");
        collection.createIndex(Indexes.ascending("gastronomia"), new IndexOptions().name("gastronomiaIndex"));
        collection.createIndex(Indexes.ascending("localidade"),new IndexOptions().name("localidadeIndex"));
        IndexOptions nm = new IndexOptions().name("nomeIndex");
        collection.createIndex(Indexes.text("nome"),nm);
        for (Document index : collection.listIndexes()) {
            System.out.println(index.toJson());
        }

    }
}
