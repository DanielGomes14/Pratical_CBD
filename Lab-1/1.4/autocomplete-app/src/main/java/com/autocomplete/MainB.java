package com.autocomplete;

import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;

public class MainB {
    public static void main(String [] args){
        Jedis jedi = new Jedis("localhost");
        InsertionHelperB ins = new InsertionHelperB(jedi);
        ins.insertData("nomes-registados-2020.csv");
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Search for ('Enter' for quit): ");
            String input = sc.nextLine();
            if(input.length()==0)break;
            for(String s : jedi.zrevrange("ScoredNames",0,-1)) {
                if(s.toLowerCase().matches(input + "(.*)" )) System.out.println(s);
            }

        }
    }
}
