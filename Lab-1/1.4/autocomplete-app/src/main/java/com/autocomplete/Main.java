package com.autocomplete;
import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        Jedis jedi = new Jedis("localhost");
        InsertionHelper ins = new InsertionHelper(jedi);
        ins.insertData("female-names.txt");
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Search for ('Enter' for quit): ");
            String input = sc.nextLine();
            System.out.println(input.length());
            if(input.length() ==0)break;
            Set<String> set = ins.getNames(input);
            for (String name: set) {
                System.out.println(name);
            }
        }
    }


}
