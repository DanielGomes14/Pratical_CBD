package com.cbd.app;
import com.datastax.driver.core.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect();
        session.execute("USE cbd_video");


        ResultSet results = session.execute("select * from comment_user where author= 'DanielGomes14'");
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r) );
        results = session.execute("select count(val_rat), avg(val_rat)  from rating  where video_id=2");
        System.out.println(results.getColumnDefinitions());
        results.forEach(r-> System.out.println(r));
        results=session.execute("select * from video where author='DanielGomes14' and timestamp_upload<='2020-12-03' ALLOW FILTERING");
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r));        results=session.execute("SELECT  * from comment_video where video_id=3 LIMIT 3");
        System.out.println(results.getColumnDefinitions());
        results.forEach(r -> System.out.println(r));

    }
}
