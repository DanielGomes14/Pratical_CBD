package com.messaging;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private Jedis jedi;
    private User LoginUser; //user that is logged in
    //All the prefixes of the keys that will be used to store in the respective Data Structure
    private static String USERDATA="UserData:";
    private static String USERMSG="UserMessages:";
    private static String USERSUB="UserSubscriptions:";
    public App(){
        this.jedi= new Jedis("localhost");
        //jedi.flushDB();

    }
    //only created this function in order to not repeat a try catch block of code too many time in the main menu
    public void continuetoMenu(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getCurrentUser(){
        return LoginUser.getUsername();
    }

    public boolean sign_Up(String username, String password){
        String key = USERDATA+username;
        //check if key already exists, because it would overwrite the existent user Data
        if(jedi.hexists(key,"username"))return false;
        Map <String,String> userinfo= new HashMap<>();
        userinfo.put("username",username);
        userinfo.put("password",password);
        jedi.hmset(key,userinfo);
        return true;
    }
    public boolean login(String username,String password){
        String key=USERDATA+username;
        if(jedi.keys(USERDATA +"*").size()==0) return false;
        if (jedi.hget(key,"username") != null && (jedi.hget(key,"username").equals(username) && jedi.hget(key,"password").equals(password))){
            this.LoginUser= new User(username,password);
            return true;
        }
        return false;
    }

    //Send Message, I only verify if the user that we want to send the Message Exists, so that the message is not sent to no one.
    public boolean storeMessage(String user,String message){
        Set<String> l = jedi.keys(USERDATA+"*");


        //i only add the message to the Receiver Inbox
        if(l.contains(USERDATA + user) && !user.equals(LoginUser.getUsername())){
            //Basically the key is composed by who received the message and  to who  the message  was sent
            jedi.lpush(USERMSG+user+":" + LoginUser.getUsername() ,message);
            return true;
        }
        return false;

    }

    public  void checkInbox(){
        Set<String> senders = jedi.keys(USERMSG+LoginUser.getUsername() + ":*");
        Set<String> following= jedi.smembers(USERSUB+ LoginUser.getUsername());
        Set<String>temp = new HashSet<String>();

        //user will only see the messages from the people he follows.
        for(String a : senders) temp.add(a.split(":")[2]);
        temp.retainAll(following);
        if(temp.size()==0){
            System.out.println("You have no new Messages");
            return;
        }

        System.out.println("\n---New Messages");
        for(String from : temp){
            System.out.println("\tMessages From " + from + ":" );
            for(String message : jedi.lrange(USERMSG  + LoginUser.getUsername() + ":" +  from,0,-1)){
                System.out.println("\t  " + message);
            }
        }
        //Remove All read Message
        for(String sender : senders) jedi.del(sender);


    }

    public void checkMessagesSent(){
        Set<String> receivers = jedi.keys(USERMSG+  "*:" + LoginUser.getUsername());
        if(receivers.size()==0){
            System.out.println("You have not sent any Message!");
            return;
        }
        System.out.println("\n---All Messages Sent");
        for(String receiver : receivers){
            String to = receiver.split(":")[1];
            System.out.println("\tMessages to " + to + ":"  );
            for(String message : jedi.lrange(receiver,0,-1)){
                System.out.println("\t  " + message);
            }
        }

 }


    /*
    The Processing of Data in the following functions required to put a Scanner as an argument,
    so that the Main method only goal is to show an Interface to the User. There's probably a better way..
     */
    public void Follow_user(Scanner sc){
        Set<String> l = jedi.keys(USERDATA+"*");
        l.remove(USERDATA+ LoginUser.getUsername());
        System.out.println(("----Users to Follow"));
        boolean anyusertofollow=false;
        for(String s : l){
            if(!jedi.sismember(USERSUB+LoginUser.getUsername(),s.substring(9,s.length()))){
                System.out.println("\t" + s.substring(9,s.length()));
                anyusertofollow=true;
            }
        }
        if(!anyusertofollow){
            System.out.println("\tThere's not any  user left to Follow!");
            return;
        }
        String input =sc.nextLine();
        if(!l.contains(USERDATA + input)){
            System.out.println("User not Found!");
            return;
        }
        if(!jedi.sismember(USERSUB + LoginUser.getUsername(),input)) {
            jedi.sadd(USERSUB + LoginUser.getUsername(),input);
            System.out.println("User Followed with Success!");
            return;
        }
        System.out.println("Cannot Follow the Same User Twice");

    }

    public void UnFollowUser(Scanner sc){
        Set<String> l = jedi.smembers(USERSUB+ LoginUser.getUsername());
        if(l.size()==0){
            System.out.println("You dont Follow anyone!");
            return;
        }
        System.out.println(("----Users to UnFollow"));
        for(String s : l) {
            System.out.println("\t" + s);
        }
        String input = sc.nextLine();
        if(!l.contains(input)){
            System.out.println("User not Found!");
            return;
        }
        jedi.srem(USERSUB+LoginUser.getUsername(),input);
        System.out.println("User Removed with Success!");
    }



    public static void main( String[] args )
    {
        App app = new App();
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        boolean logflag=false;
        boolean exit=false;
        sb.append("Select an Option\n");
        sb.append("\nA- Login");
        sb.append("\nB- Sign in");
        sb.append("\nC- Exit");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\nSelect an Option: \nA - Send a Message\nB - Check Inbox\nC - Check Messages Sent\nD - Follow Someone\nE - Unfollow a User\nF- Logout");
        // Cycle
        while(true){
            if(!logflag&&!exit) {
                System.out.println(sb.toString());
                String opt =sc.nextLine();
                switch (opt.toUpperCase()) {
                    case "A":
                        System.out.println("Log In into The App\nEnter your Name: ");
                        String name = sc.nextLine();
                        //sc.nextLine();
                        System.out.println("Enter your Password: ");
                        String password = sc.nextLine();
                        while (!app.login(name, password)) {
                            System.out.println("Not a valid Username or Password. Please Try Again.");
                            System.out.println("Enter your Name: ");
                            name = sc.next();
                            sc.nextLine();
                            System.out.println("Enter your Password: ");
                            password = sc.next();
                        }
                        System.out.println(app.getCurrentUser());
                        System.out.println("Welcome!");
                        logflag = true;
                        break;
                    case "B":
                        System.out.println("Enter your Name: ");
                        String regname = sc.nextLine();
                        //sc.nextLine();
                        System.out.println("Enter your Password: ");
                        String regpassword = sc.nextLine();
                        while (!app.sign_Up(regname, regpassword)) {
                            System.out.println("UserName Already Taken.Please Try Again.");
                            System.out.println("Enter your Name: ");
                            regname = sc.next();
                            sc.nextLine();
                            System.out.println("Enter your Password: ");
                            regpassword = sc.next();
                        }
                        System.out.println("Registered with Sucess");
                        continue;
                    case "C":
                        exit=true;
                        break;
                    default:
                        continue;
                }
            }
            else if (logflag && !exit){
                System.out.println(sb2.toString());
                String opt =sc.nextLine();
                int input;
                switch (opt.toUpperCase()){
                    case "A":
                        System.out.println("Enter the Username of the Person you want to send a message: ");
                        String name = sc.nextLine();
                        //sc.nextLine();
                        System.out.println("Enter a Message: ");
                        String message = sc.nextLine();
                        if(!app.storeMessage(name,message)) System.out.println("Invalid Username!");
                        else System.out.println("\nMessage Sent!");
                        break;
                    case "B":
                        app.checkInbox();
                        break;
                    case "C":
                        app.checkMessagesSent();

                        break;
                    case "D":
                        app.Follow_user(sc);

                        break;
                    case "E":
                        app.UnFollowUser(sc);
                        break;
                    case "F":
                        logflag=false;
                        continue;
                    default:
                        continue;
                }
                app.continuetoMenu();
            }
            else break;
        }



    }
}
