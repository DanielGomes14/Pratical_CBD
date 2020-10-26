package com.autocomplete;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.HashSet;
import java.util.Set;

public class InsertionHelperB {
    private Jedis jedi;
    private static String PRT_NAME_KEY="ScoredNames";
    public InsertionHelperB(Jedis jedi){
        this.jedi=jedi;
    }
    public void insertData(String path){
        InputStream is;
        try{
            is= getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String currentLine = reader.readLine();


            while(currentLine!=null){
                String [] sep = currentLine.split((";"));
                jedi.zadd(PRT_NAME_KEY,Integer.parseInt(sep[2]),sep[0]);
                currentLine=reader.readLine();
            }
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}
