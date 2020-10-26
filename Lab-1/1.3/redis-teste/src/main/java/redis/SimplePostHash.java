package redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimplePostHash {
    public static String USERS = "hash:users:"; // Key set for users' name
    private Jedis jedis;
    public SimplePostHash(Jedis jedis){this.jedis=jedis;
    }
    //Basically in this hash i store as fields the usernames' and as values of these fields the ammount of time they were stored
    public void saveUser(String username) {
            jedis.hincrBy(USERS,username,1);
    }
    public Map<String,String> getUser() {
         return jedis.hgetAll(USERS);

        }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
