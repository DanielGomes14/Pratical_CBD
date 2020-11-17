package com.cbd.mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RestHandler hd = new RestHandler();
        System.out.println(hd.countLocalidades());
        System.out.println("Numero de restaurantes por localidade");
        Map <String,Integer> mp = hd.countRestByLocalidade();
        mp.forEach((k,v) -> System.out.println("  -> " + k + " - " + v));
        Map<String,Integer> mp2 = hd.countRestByLocalidadeByGastronomia();

        System.out.println("Numero de restaurantes por localidade e gastronomia:");
        mp2.forEach((k,v) -> System.out.println("  -> " + k + " - " + v));

        System.out.println("Nome de restaurantes contendo 'Park' no nome:");
        List<String > l = hd.getRestWithNameCloserTo("Park");
        l.forEach( el -> System.out.println("  -> " + el));

    }


}
