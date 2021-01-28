package com.cbd.app;
import com.datastax.driver.core.*;

import java.util.List;
import java.util.Set;

public class CassandraHelper{
    private  Cluster cluster;
    private  Session session;
    private  String keyspace;
    private  String table;
    CassandraHelper(){
        this.cluster=Cluster.builder().addContactPoint("127.0.0.1").build();
        this.session=cluster.connect();
        this.keyspace="cbd_video";
        this.session.execute("USE " + keyspace);
        this.table="video";
    }

    //Insert in video table
    public void insertUser(String cols,String value){
        try{
            String insert="INSERT INTO USER " + cols+ "VALUES " +value;
            this.session.execute(insert);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error Inserting new User");
            return;
        }finally {
            System.out.println("Sucess Inserting new User");
        }

    }
    //Select all info from all videos
    public void selectAllVideos(){
        System.out.println("\n-------- ALL VIDEOS ------------------------------ ");
        String  query="SELECT * FROM " + this.table + ";";
        ResultSet results = this.session.execute(query);
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r));
        System.out.println("--------------------------------------------------");
    }

    //select a specific video by its id
    public  void selectVideoByid(String id){
        System.out.println("\n---------VIDEOS FILTERED BY ID---------------------");
        String  query="SELECT * FROM " + this.table
                     + " WHERE video_id=" + id+ ";";
        ResultSet results = this.session.execute(query);
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r));
        System.out.println("--------------------------------------------------");
    }

    //select a specific video by its author
    public  void selectVideobyAuthor(String author){
        System.out.println("\n---------VIDEOS FILTERED BY AUTHOR-----------------");
        String  query="SELECT * FROM " + this.table
                + " WHERE author=" + author
                + " ALLOW FILTERING;";
        ResultSet results = this.session.execute(query);
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r));
        System.out.println("--------------------------------------------------");

    }

    public  String getTimeStampDate(String id){
        String query =" SELECT timestamp_upload FROM " + this.table
                     + " WHERE video_id = " + id;
        String timestamp = null;
        ResultSet result = this.session.execute(query);
        for(Row r : result){
            timestamp=r.getTimestamp("timestamp_upload").toInstant().toString();
        }
        return  timestamp;
    }

        /*
        As i need to select all clustering keys of this  table in order to edit
         an entry from the table, i created the above method, that allows to retrive
         the timestamp of upload of a video. With this i manage to find the timestamp of the
         video i want to select without "hard coding"
         */
    public void editVideoName(String newName, String id,String author){
    System.out.println("\n---------UPDATE VIDEO NAME---------------------");
    String timestamp=getTimeStampDate(id);
    //As timestamp is a clustering it must be always different of null. Therefore if the method returns null, then the video does not exist.
    if(timestamp==null){
        System.out.println("Cannot perform this query!Video id not found!");
        return;
    }
    String query ="UPDATE " + this.table
                 + " SET video_name ="
                 + newName +" WHERE video_id = " + id
                 +  " AND author = " +author + " AND timestamp_upload = '" + timestamp  +"' ;";
    try { this.session.execute(query); }
    catch (Exception e){
        System.out.println("Error Updating data");
        e.printStackTrace();
        return;
    }
    finally {
        System.out.println("Success Updating Data"); }
        System.out.println("--------------------------------------------------");

    }

}