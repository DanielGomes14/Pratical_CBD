package com.cbd.mongo;


import com.mongodb.client.model.Projections;
import org.bson.Document;

import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.fields;
import static java.util.Arrays.asList;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;

import java.util.*;
import java.util.function.Consumer;

public class RestHandler {
    private MongoClient mg;
    private MongoDatabase mdb;
    private MongoCollection<Document> mcol;
    public RestHandler(){
        this.mg = MongoClients.create();
        this.mdb = this.mg.getDatabase("cbd");
        this.mcol = this.mdb.getCollection("rest");
    }

    public  int countLocalidades(){
       //Using the distinct method of mongo i get every single diferente city.
       MongoCursor<String> diffcities = this.mcol.distinct("localidade",String.class).iterator();
       int counter=0;
       //as iterator do not have a size method, i need to iterate through it in order to count the number of different cities
       while ((diffcities.hasNext())){
           counter++;
           diffcities.next();
       }
       return counter ;
    }
   public Map<String, Integer> countRestByLocalidade(){
       Map<String, Integer> cityCountMap = new HashMap<>();
       //to count the number of restaurants that every city has, i did an aggregate query where i group by city ("localidade") , and return the counter of times that city appeared
       //due to the fact that every restaurant has only one city associated.
       AggregateIterable<Document> diffcities =this.mcol.aggregate(
               asList(
               Aggregates
               .group("$localidade",Accumulators.sum("count",1))
       ));
        //As we needed to return an Map, with the City name as string, and the number of restaurants as value, i decided to do a forEach statement with a lambda function
       //for each document of the aggregate Iterable result, i get the "_id" field from the document and the "count" field,  and put them  in a HashMap where:
       // - "_id" corresponds to the city name  and "count" the number of restaurants
       diffcities.forEach(document -> cityCountMap.put((String) document.get("_id"), (Integer) document.get("count")));

    return cityCountMap;
   }
    public Map<String, Integer> countRestByLocalidadeByGastronomia(){
        Map<String, Integer> cityandFoodMap = new HashMap<>();
        //Following the same logic as the previous method, i created an aggregate query very similar to the previous one, but in this case instead of grouping only by
        //city ("localidade"), i group by both city and then by gastronomy ("gastronomia")
        AggregateIterable<Document> diffcities =this.mcol.aggregate(asList(
                Aggregates.group(asList("$localidade","$gastronomia"),Accumulators.sum("count",1))
        ));
        /*
          in order to make a different type of for each(without lambdas) i decided to use the Consumer class, that is a functional Interface that
          represents an operation that accepts a single input argument and returns no result.
          In practice, using a lambda function would have the same effect
         */
        diffcities.forEach(new Consumer<Document>() {
            @Override
            public void accept(final Document document) {
                List key = (ArrayList) document.get("_id");
                //to put the print exactly like the it was specified, i save in the map a key of kind  "localidade | gastronomia "
                // and a value the number of restaurants with that city and gastronomy
                cityandFoodMap.put((String)key.get(0) + "|" + (String) key.get(1) , (Integer) document.get("count"));
            }
        });

        return cityandFoodMap;
    }
    public List<String> getRestWithNameCloserTo(String name){
        List<String> closer_rests = new ArrayList<>();
        //do a find() operation by regex, in which finds any document where the field "nome" as the substring "name"
        List <Document> f =this.mcol
                .find(regex("nome",".*"+ name + ".*"))
                .projection(fields(Projections.include("nome")))
                .into(new ArrayList<Document>());

        f.forEach(document -> closer_rests.add(document.getString("nome")));
        return closer_rests;
    }
}
