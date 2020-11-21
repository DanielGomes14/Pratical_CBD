package com.cbd.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;

import java.util.Iterator;
import java.util.function.Consumer;

public class RestaurantHandler {
    private MongoClient mc;
    private MongoDatabase mdb;
    private MongoCollection <Document> mcol;
    public RestaurantHandler(MongoClient mc){
        this.mc=mc;
        this.mdb = mc.getDatabase("cbd");
        this.mcol = mdb.getCollection("rest");
    }

    public void insertRest(Document rest){

        this.mcol.insertOne(rest);
    }
    public void updateRestByCity(String citytoFind, String citytoUpdate){
        this.mcol.updateMany(eq("localidade", citytoFind),set("localidade",citytoUpdate));
    }
    public void searchRest(String nome,String localidade){
        BasicDBObject query = new BasicDBObject();
        if (nome !=null) query.put("nome",nome);
        if(localidade != null) query.put("localidade", localidade);
        this.mcol.find(query).forEach(rest -> System.out.println(rest.toJson()));

    }
}
