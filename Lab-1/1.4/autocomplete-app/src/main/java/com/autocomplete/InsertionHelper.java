package com.autocomplete;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;

public class InsertionHelper {
    private Jedis jedi;
    private static String NAME_KEY="females";
    public InsertionHelper(Jedis jedi){
        this.jedi=jedi;
    }
    public void insertData(String path){
        InputStream is;
        try{
            is= getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String currentLine = reader.readLine();
            while(currentLine!=null){
                jedi.zadd(NAME_KEY,1,currentLine);
                currentLine=reader.readLine();
            }
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public Set<String> getNames(String name){
    //couldn't get a better way to do this so..yeah
        byte[] prefixByte = ("[" + name).getBytes();
        byte[] prefixByteExtended = Arrays.copyOf(prefixByte, prefixByte.length + 1);
        prefixByteExtended[prefixByte.length] = (byte) 0xFF;
        return jedi.zrangeByLex(NAME_KEY,"["+ name , new String(prefixByteExtended));
    }
}
