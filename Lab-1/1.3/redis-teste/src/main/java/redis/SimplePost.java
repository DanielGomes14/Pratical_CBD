package redis;

import java.util.Set;
import redis.clients.jedis.Jedis;





public class SimplePost {
 
	private Jedis jedis;
	public static String USERS = "set:users:";
	
	public SimplePost(Jedis jedis){this.jedis=jedis;
	}
 
	public void saveUser(String username) {
		jedis.sadd(USERS, username);
	}
	public Set<String> getUser() {
		return jedis.smembers(USERS);
	}
	
	public Set<String> getAllKeys() {
		return jedis.keys("*");
	}
 
	public static void main(String[] args) {
		Jedis jedi = new Jedis("localhost");
		SimplePost board = new SimplePost(jedi);
		SimplePostList board_list = new SimplePostList(jedi);
		SimplePostHash board_hashmap = new SimplePostHash(jedi);
		// set some users
		String[] users = { "Ana", "Pedro", "Maria", "Luis" };
		for (String user: users) {
			board.saveUser(user);
			board_list.saveUserRight(user);
			board_hashmap.saveUser(user);
		}
		System.out.println("---Keys:");
		board.getAllKeys().stream().forEach(System.out::println);
		System.out.println("\n---Values:");
		System.out.println("--Set:");
		board.getUser().stream().forEach(System.out::println);
		System.out.println("\n--List:");
		board_list.getUser().stream().forEach(System.out::println);
		System.out.println("\n--Hash:");
		for (String user: users) board_hashmap.saveUser(user);
		board_hashmap.getUser().forEach((k, v) -> System.out.println((k + ":" + v)));

	}
}



