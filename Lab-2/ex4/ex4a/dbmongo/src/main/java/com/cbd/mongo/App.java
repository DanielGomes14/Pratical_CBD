package com.cbd.mongo;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MongoClient mongoClient = MongoClients.create();
        //MongoDatabase database = mongoClient.getDatabase("cbd");
        //MongoCollection<Document> collection = database.getCollection("rest");
        RestaurantHandler handler = new RestaurantHandler((mongoClient));

        System.out.println(("\n\nSearch by field \"nome\" "));
        handler.searchRest("Nb. National Baker",null);
        Document d = new Document();
        List coords = new ArrayList<Double>();
        coords.add(-72.9000);
        coords.add(39.23);
        List grades = new ArrayList<Document>();
        grades.add(new Document("date", new Document("date", Long.valueOf("1409961600000"))).append("grade","A").append("score",3));
        grades.add(new Document("date", new Document("date", Long.valueOf("1404172800000"))).append("grade","A").append("score",23));
        grades.add(new Document("date", new Document("date", Long.valueOf("1404172800000"))).append("grade","A").append("score",18));
        d.append("address",new Document("building", "1007").append("coord", coords).append("rua", "Mário Sacramento").append("zipcode","10463"));
        d.append("localidade","Aveiro");
        d.append("gastronomia","American");
        d.append("grades",grades);
        d.append("nome", "A cool RESTaurant");
        d.append("restaurant_id","300754459");

        handler.insertRest(d);
        handler.searchRest("A cool RESTaurant",null);

        handler.updateRestByCity("Aveiro", "Águeda");
        handler.searchRest(null,"Águeda");


    }
}
