package com.cbd.app;

import com.datastax.driver.core.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        CassandraHelper c = new CassandraHelper();
        String table="User";
        String cols= "(username,name,email,timestamp_register)";
        String values="('JavaUser', 'bot', 'java@ua.pt', toTimestamp(now()))";
        c.insertUser(cols,values);
        c.selectAllVideos();
        c.selectVideobyAuthor("'AceDestiny'");
        c.selectVideoByid("4");
        c.editVideoName("'My First video Edited'","1" , "'DanielGomes14'");

    }
    }

