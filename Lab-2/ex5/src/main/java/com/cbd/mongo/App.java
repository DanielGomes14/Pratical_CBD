package com.cbd.mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.*;
import java.util.function.Consumer;
public class App 
{
    private MongoClient mg;
    private MongoDatabase mdb;
    private MongoCollection<Document> mcol;
    public App(){
        this.mg = MongoClients.create();
        this.mdb = this.mg.getDatabase("cbd");
        this.mcol = this.mdb.getCollection("sales");
    }
    //find all salesmen emails'  within a specific Store Location
    public  List <Document>   SalesinSpecificLocation(String location ){

        List<Document> l = this.mcol
                .find(eq("storeLocation",location))
                .projection(Projections.fields(Projections.excludeId(),Projections.include("customer")))
                .into(new ArrayList<Document>());
            l.forEach( d -> System.out.println("-> " + d.toJson() ));
            return l;
        }
    // find 10 items purchased  that only include school items
    public  List <Document> SalesSchoolItems(){
        /*
        List<Document> query = this.mcol
                .find(not(elemMatch("items.tags",ne("items.tags","school")))).limit(5)
                .into(new ArrayList<Document>() );
        query.forEach( d -> System.out.println("->" + d.toJson()));
        return  query;
        */
         */
    }

    public static void main( String[] args )
    {
        App app = new App();
        //List<Document> l = app.SalesinSpecificLocation("Austin");
        List<Document> l = app.SalesSchoolItems();
    }
}
