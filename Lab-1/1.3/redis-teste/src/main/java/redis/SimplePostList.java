package redis;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class SimplePostList {
    private Jedis jedis;
    private static String USERSLIST ="list:users";

    public SimplePostList(Jedis jedis) {
        this.jedis=jedis;
    }
    public void SimplePostList() {
        this.jedis = new Jedis("localhost");
    }
    //methods
    public void saveUserLeft(String username) {
      jedis.lpush(   USERSLIST,username);

    }
    public void saveUserRight(String username) {
         jedis.rpush(USERSLIST,username);
    }

    public List<String> getUser(){
        return jedis.lrange(USERSLIST,0,-1);
    }
    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }
}
