package redis;
import redis.clients.jedis.Jedis;
/**
 * Hello world!
 *
 */
public class Forum
{
    private Jedis jedis;
    public Forum(){
        this.jedis = new Jedis("localhost");
        System.out.println(jedis.info());}
    public static void main(String[] args) {
        new Forum();
    }
  
}
