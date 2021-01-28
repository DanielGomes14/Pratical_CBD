**User**

username  text

name text

email text

timestamp_register timestamp

pk(username)

```
CREATE TABLE User( username text, name text, email text, timestamp_register timestamp, PRIMARY KEY (username));
```

**Video**

video_id int

author  text

video_name text

description text

tags  set<text>

followers set<text>

timestamp_upload timestamp 

pk(id,author,timestamp_upload)

```
CREATE TABLE Video( video_id int, author text, video_name text, description text, tags set <text>, followers set<text>, timestamp_upload timestamp, PRIMARY KEY (video_id,author,timestamp_upload));
```

**Video_autor**

video_id int

author  text

video_name text

description text

tags  set<text>

followers set<text>

timestamp_upload timestamp 

pk(id,author)



**Comment_User**

author text

video_id int

comment_text text	

timestamp_comment timestamp

pk(author,timestamp ) 

clusterpk(timestamp_comment)

```
CREATE TABLE Comment_User ( author text, video_id int, comment_text text, timestamp_comment timestamp, PRIMARY KEY (author,timestamp_comment)) with CLUSTERING ORDER BY (timestamp_comment DESC) ;
```

**Comment_video**

author text

video_id 

comment_text 

timestamp_comment 

pk(video_id,timestamp,timestamp_comment  ) 

```
CREATE TABLE Comment_video ( author text, video_id int , comment_text text, timestamp_comment timestamp, PRIMARY KEY ( video_id, timestamp_comment)) WITH CLUSTERING ORDER BY ( timestamp_comment DESC); 
```

**Comment_video_follower** 

video_id int,

follower text,

comments list<text> 

pk(follower,video)



**Event**

video_id int

author text

type text

event_timestamp timestamp

moment int

pk(video_id,author,moment,event_timestamp)

```
CREATE TABLE Event ( video_id int, author text, type text, event_timestamp timestamp, moment int, PRIMARY KEY (video_id, author,moment,event_timestamp));
```

**Rating**

id_rat int

video_id int 

val_rat int

pk(video_id,id)

```
CREATE TABLE RATING ( id_rat int , video_id int , val_rat int, PRIMARY KEY (video_id,id_rat));
```



**Tags_videos**

tag text 

video set<int>

pk(tag)

```
CREATE TABLE Tags_videos ( tag text, video set<int>, PRIMARY KEY (tag));
```



**Database Population**

```

------USER----------------------------
INSERT INTO  User(username,name,email,timestamp_register) VALUES('DanielGomes14', 'daniel', 'dagomes@ua.pt', toTimestamp(now()))
INSERT INTO  User(username,name,email,timestamp_register) VALUES('AceDestiny', 'chico', 'chicoace@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('naosei1', 'naosei', 'naosei@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('naosei2', 'leonardo', 'leonardo@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('naosei3', 'lionel', 'lionel@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('naosei4', 'bot', 'bot@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('naosei5', 'bot_1', 'bot_1@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('yauyau', 'yau', 'yau@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('umacena', 'cena', 'cena@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,timestamp_register) VALUES('Mzuckerberg', 'Mark', 'mark@fb.pt', toTimestamp(now()));

---VIDEO---------------------

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (1, 'DanielGomes14', 'My first video', 'I dont know, just a video', {'vlog','lifestyle'}, {'AceDestiny','naosei1','naosei2'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (2, 'AceDestiny', 'Valorant Livestream', 'watch me doing aces in all games', {'gaming','livestream'}, {'naosei3','naosei4','Mzuckerberg'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (3, 'naosei1', 'Gordon Ramsey would be proud', 'a normal food video', {'food','lifestyle','tvshow'}, {'naosei1','naosei5'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (4, 'naosei2', 'Reviewing new J.Cole Song', 'a music review', {'music','review'}, {'naosei3','yauyau','umacena'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (5, 'naosei3', 'Razer mouse review', ' review of the new Razer mouse', {'gaming','review','tech'}, {'DanielGomes14','AceDestiny','umacena'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload) VALUES (6, 'naosei4', 'My  style video', ' new video guys', {'fashion','vlog'}, {'naosei2','umacena'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (7, 'naosei5', 'A cool video', ' idk just watch', {'gaming','tech'}, {'naosei2','Mzuckerberg'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (8, 'yauyau', 'music livestream', ' music!!', {'music','livestream'}, {'naosei3'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (9, 'umacena', 'nice video ', ' gg man ', {'food','vlog'}, {'yauyau','AceDestiny'},toTimestamp(now()));

INSERT INTO Video(video_id,author,video_name,description ,tags,followers,timestamp_upload)
VALUES (10, 'Mzuckerberg', 'New FB purchase ', 'another app bought ', {'tvshow','tech'}, {'yauyau','AceDestiny','DanielGomes14', 'naosei1', 'naosei2'},toTimestamp(now()));



-----COMENT USER------------------------

INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('Mzuckerberg',1, 'where could i buy the video?',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('umacena',2, 'wow, you play really well,nice video',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('yauyau',3, 'meh, i didnt like .',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('naosei5',4, 'awesome dude',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('naosei4',5, 'awful video',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('naosei3',6, 'you could improve next video, still good tho',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('naosei2',7, 'wasted my time watching this',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('naosei1',8, 'i really enjoyed this live',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('AceDestiny',9, 'great, looking forward to see next video',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('DanielGomes14',10, 'wow, one more purchase, nice',toTimestamp(now())); 
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('Mzuckerberg',3, 'spamming comments',toTimestamp(now()));
INSERT INTO Comment_User (author,video_id,comment_text,timestamp_comment) VALUES ('AceDestiny',3, 'spamming comments',toTimestamp(now()));



---COMENT VIDEO----------------------

INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('Mzuckerberg',1, 'where could i buy the video?',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('umacena',2, 'wow, you play really well,nice video',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('yauyau',3, 'meh, i didnt like .',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('naosei5',4, 'awesome dude',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('naosei4',5, 'awful video',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('naosei3',6, 'you could improve next video, still good tho',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('naosei2',7, 'wasted my time watching this',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('naosei1',8, 'i really enjoyed this live',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('AceDestiny',9, 'great, looking forward to see next video',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('DanielGomes14',10, 'wow, one more purchase, nice',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('Mzuckerberg',3, 'spamming comments',toTimestamp(now()));
INSERT INTO Comment_Video (author,video_id,comment_text,timestamp_comment) VALUES ('AceDestiny',3, 'spamming comments',toTimestamp(now()));

-----EVENT---------------------

INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'AceDestiny' , 'PLAY' , toTimestamp(now()),0 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'AceDestiny' , 'PAUSE' , toTimestamp(now()),100 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'AceDestiny' , 'PLAY' , toTimestamp(now()),100 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'AceDestiny' , 'STOP' , toTimestamp(now()),300 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'Mzuckerberg' , 'PLAY' , toTimestamp(now()),0 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'Mzuckerberg' , 'PAUSE' , toTimestamp(now()),56 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'Mzuckerberg' , 'PLAY' , toTimestamp(now()),56 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'Mzuckerberg' , 'PAUSE' , toTimestamp(now()),296 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'naosei1' , 'PLAY' , toTimestamp(now()),0 );
INSERT INTO Event(video_id,author,type,event_timestamp,moment) VALUES (1,'naosei2' , 'PLAY' , toTimestamp(now()),0 );

----RATING---------------

INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (1,1,4);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (2,1,5);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (3,2,5);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (4,2,2);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (5,2,3);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (6,2,4);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (7,3,4);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (8,3,1);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (9,3,3);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (10,4,4);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (11,4,3);
INSERT INTO Rating(id_rat,video_id,val_rat) VALUES (12,4,5);

----TAGS VIDEOS----
INSERT INTO tags_videos ( tag,video) VALUES('vlog' ,{1,6,9});
INSERT INTO tags_videos ( tag,video) VALUES('lifestyle' ,{1,3});
INSERT INTO tags_videos ( tag,video) VALUES('gaming' ,{2,5,7});
INSERT INTO tags_videos ( tag,video) VALUES('livestream' ,{2,8});
INSERT INTO tags_videos ( tag,video) VALUES('food' ,{3,9});
INSERT INTO tags_videos ( tag,video) VALUES('tvshow', {3,10});
INSERT INTO tags_videos ( tag,video) VALUES('music', {4,8});
INSERT INTO tags_videos ( tag,video) VALUES('review', {4,5});
INSERT INTO tags_videos ( tag,video) VALUES('tech', {5,7,10});
INSERT INTO tags_videos ( tag,video) VALUES('fashion', {6});

---- COMENT VIDEO_FOLLOWER----

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('AceDestiny',1,['where could i buy the video?']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei1',1,['where could i buy the video?']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei2',1,['where could i buy the video?']); 

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei3',2,['wow, you play really well,nice video']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei4',2,['wow, you play really well,nice video']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('Mzuckerberg',2,['wow, you play really well,nice video']); 



INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei1',3,['meh, i didnt like .']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei5',3,['meh, i didnt like .']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei1',3,['spamming comments']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei5',3,['spamming comments']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei1',3,['spamming comments']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei5',3,['spamming comments']);

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei3',4,['awesome dude']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('yauyau',4,['awesome dude']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('umacena',4,['awesome dude']); 


INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('DanielGomes14',5,['awful video']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('AceDestiny',5,['awful video']); 
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('umacena',5,['awful video']); 

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei2',6,['you could improve next video, still good tho']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('umacena',6,['you could improve next video, still good tho']);

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei2',7,['wasted my time watching this']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('Mzuckerberg',7,['wasted my time watching this']);

INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei3',8,['i really enjoyed this live']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('AceDestiny',9,['great, looking forward to see next video']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('yauyau',9,['great, looking forward to see next video']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('AceDestiny',10,['wow, one more purchase, nice']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('DanielGomes14',10,['wow, one more purchase, nice']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei1',10,['wow, one more purchase, nice']);
INSERT INTO comment_video_follower ( follower,video_id, comments) VALUES ('naosei2',10,['wow, one more purchase, nice']);




```





## JSON 

###  [User]

    cqlsh:cbd_video> select json * from User;
    
    [json]
    -----------------------------------------------------------------------------------------------------------------------------
    {"username": "yauyau", "email": "yau@ua.pt", "name": "yau", "timestamp_register": "2020-12-02 10:19:05.510Z"}
    {"username": "naosei5", "email": "bot_1@ua.pt", "name": "bot_1", "timestamp_register": "2020-12-02 10:18:02.260Z"}
    {"username": "naosei2", "email": "leonardo@ua.pt", "name": "leonardo", "timestamp_register": "2020-12-02 10:15:09.827Z"}
    {"username": "AceDestiny", "email": "chicoace@ua.pt", "name": "chico", "timestamp_register": "2020-12-02 10:14:06.069Z"}
    {"username": "Mzuckerberg", "email": "mark@fb.pt", "name": "Mark", "timestamp_register": "2020-12-02 10:20:45.943Z"}
    {"username": "naosei1", "email": "naosei@ua.pt", "name": "naosei", "timestamp_register": "2020-12-02 10:14:10.662Z"}
    {"username": "naosei4", "email": "bot@ua.pt", "name": "bot", "timestamp_register": "2020-12-02 10:17:27.604Z"}
    {"username": "DanielGomes14", "email": "dagomes@ua.pt", "name": "daniel", "timestamp_register": "2020-12-02 10:13:59.467Z"}
    {"username": "naosei3", "email": "lionel@ua.pt", "name": "lionel", "timestamp_register": "2020-12-02 10:16:00.810Z"}
    {"username": "umacena", "email": "cena@ua.pt", "name": "cena", "timestamp_register": "2020-12-02 10:19:25.086Z"}
    
    (10 rows)

### Video

    cqlsh:cbd_video> select json * from Video;
    
    [json]
    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    {"video_id": 5, "author": "naosei3", "timestamp_upload": "2020-12-02 10:43:15.411Z", "description": " review of the new Razer mouse", "followers": ["AceDestiny", "DanielGomes14", "umacena"], "tags": ["gaming", "review", "tech"], "video_name": "Razer mouse review"}
    {"video_id": 10, "author": "Mzuckerberg", "timestamp_upload": "2020-12-02 10:57:40.912Z", "description": "another app bought ", "followers": ["AceDestiny", "DanielGomes14", "naosei1", "naosei2", "yauyau"], "tags": ["tech", "tvshow"], "video_name": "New FB purchase "}
    {"video_id": 1, "author": "DanielGomes14", "timestamp_upload": "2020-12-02 10:30:30.048Z", "description": "I dont know, just a video", "followers": ["AceDestiny", "naosei1", "naosei2"], "tags": ["lifestyle", "vlog"], "video_name": "My First video Edited"}
    {"video_id": 8, "author": "yauyau", "timestamp_upload": "2020-12-02 10:53:41.648Z", "description": " music!!", "followers": ["naosei3"], "tags": ["livestream", "music"], "video_name": "music livestream"}
    {"video_id": 2, "author": "AceDestiny", "timestamp_upload": "2020-12-02 10:37:00.301Z", "description": "watch me doing aces in all games", "followers": ["Mzuckerberg", "naosei3", "naosei4"], "tags": ["gaming", "livestream"], "video_name": "Valorant Livestream"}
    {"video_id": 4, "author": "naosei2", "timestamp_upload": "2020-12-02 10:40:46.141Z", "description": "a music review", "followers": ["naosei3", "umacena", "yauyau"], "tags": ["music", "review"], "video_name": "Reviewing new J.Cole Song"}
    {"video_id": 7, "author": "naosei5", "timestamp_upload": "2020-12-02 10:51:11.459Z", "description": " idk just watch", "followers": ["Mzuckerberg", "naosei2"], "tags": ["gaming", "tech"], "video_name": "A cool video"}
    {"video_id": 6, "author": "naosei4", "timestamp_upload": "2020-12-02 10:50:57.705Z", "description": " new video guys", "followers": ["naosei2", "umacena"], "tags": ["fashion", "vlog"], "video_name": "My  style video"}
    {"video_id": 9, "author": "umacena", "timestamp_upload": "2020-12-02 10:55:57.303Z", "description": " gg man ", "followers": ["AceDestiny", "yauyau"], "tags": ["food", "vlog"], "video_name": "nice video "}
    {"video_id": 3, "author": "naosei1", "timestamp_upload": "2020-12-02 10:37:04.888Z", "description": "a normal food video", "followers": ["naosei1", "naosei5"], "tags": ["food", "lifestyle", "tvshow"], "video_name": "Gordon Ramsey would be proud"}
    
    (10 rows)
    



###  comment_video

    cqlsh:cbd_video> select json * from comment_video ;
    
    [json]
    -------------------------------------------------------------------------------------------------------------------------------------------------------
    {"video_id": 5, "timestamp_comment": "2020-12-02 11:22:46.736Z", "author": "naosei4", "comment_text": "awful video"}
    {"video_id": 10, "timestamp_comment": "2020-12-02 11:22:49.306Z", "author": "DanielGomes14", "comment_text": "wow, one more purchase, nice"}
    {"video_id": 1, "timestamp_comment": "2020-12-02 11:22:46.704Z", "author": "MZuckerberg", "comment_text": "where could i buy the video?"}
    {"video_id": 8, "timestamp_comment": "2020-12-02 11:22:46.747Z", "author": "naosei1", "comment_text": "i really enjoyed this live"}
    {"video_id": 2, "timestamp_comment": "2020-12-02 11:22:46.714Z", "author": "umacena", "comment_text": "wow, you play really well,nice video"}
    {"video_id": 4, "timestamp_comment": "2020-12-02 11:22:46.727Z", "author": "naosei5", "comment_text": "awesome dude"}
    {"video_id": 7, "timestamp_comment": "2020-12-02 11:22:46.744Z", "author": "naosei2", "comment_text": "wasted my time watching this"}
    {"video_id": 6, "timestamp_comment": "2020-12-02 11:22:46.741Z", "author": "naosei3", "comment_text": "you could improve next video, still good tho"}
    {"video_id": 9, "timestamp_comment": "2020-12-02 11:22:46.750Z", "author": "AceDestiny", "comment_text": "great, looking forward to see next video"}
    {"video_id": 3, "timestamp_comment": "2020-12-03 09:51:32.469Z", "author": "AceDestiny", "comment_text": "spamming comments"}
    {"video_id": 3, "timestamp_comment": "2020-12-03 09:51:19.678Z", "author": "Mzuckerberg", "comment_text": "spamming comments"}
    {"video_id": 3, "timestamp_comment": "2020-12-03 09:42:14.008Z", "author": "DanielGomes14", "comment_text": "continue the good work"}
    {"video_id": 3, "timestamp_comment": "2020-12-02 11:22:46.721Z", "author": "yauyau", "comment_text": "meh, i didnt like ."}
    
    (13 rows)

###  comment_user

    cqlsh:cbd_video> select json * from comment_user  ;
    [json]
    -------------------------------------------------------------------------------------------------------------------------------------------------------
    {"author": "yauyau", "timestamp_comment": "2020-12-02 11:13:02.659Z", "comment_text": "meh, i didnt like .", "video_id": 3}
    {"author": "naosei5", "timestamp_comment": "2020-12-02 11:14:45.834Z", "comment_text": "awesome dude", "video_id": 4}
    {"author": "naosei2", "timestamp_comment": "2020-12-02 11:17:17.230Z", "comment_text": "wasted my time watching this", "video_id": 7}
    {"author": "AceDestiny", "timestamp_comment": "2020-12-03 09:51:06.078Z", "comment_text": "spamming comments", "video_id": 3}
    {"author": "AceDestiny", "timestamp_comment": "2020-12-02 11:19:16.173Z", "comment_text": "great, looking forward to see next video", "video_id": 9}
    {"author": "Mzuckerberg", "timestamp_comment": "2020-12-03 09:50:34.174Z", "comment_text": "spamming comments", "video_id": 3}
    {"author": "naosei1", "timestamp_comment": "2020-12-02 11:17:59.708Z", "comment_text": "i really enjoyed this live", "video_id": 8}
    {"author": "naosei4", "timestamp_comment": "2020-12-02 11:15:50.230Z", "comment_text": "awful video", "video_id": 5}
    {"author": "DanielGomes14", "timestamp_comment": "2020-12-03 09:41:14.679Z", "comment_text": "continue the good work", "video_id": 3}
    {"author": "DanielGomes14", "timestamp_comment": "2020-12-02 11:19:55.082Z", "comment_text": "wow, one more purchase, nice", "video_id": 10}
    {"author": "MZuckerberg", "timestamp_comment": "2020-12-02 11:09:41.294Z", "comment_text": "where could i buy the video?", "video_id": 1}
    {"author": "naosei3", "timestamp_comment": "2020-12-02 11:16:29.835Z", "comment_text": "you could improve next video, still good tho", "video_id": 6}
    {"author": "umacena", "timestamp_comment": "2020-12-02 11:10:31.186Z", "comment_text": "wow, you play really well,nice video", "video_id": 2}
    
    (13 rows)
    

 event
-------------------------------------------------------------------------------------------------------------------------

     cqlsh:cbd_video> select json * from event ;
    
    [json]
    -------------------------------------------------------------------------------------------------------------------------
    {"video_id": 1, "author": "AceDestiny", "moment": 0, "event_timestamp": "2020-12-02 11:28:48.313Z", "type": "PLAY"}
    {"video_id": 1, "author": "AceDestiny", "moment": 100, "event_timestamp": "2020-12-02 11:28:52.006Z", "type": "STOP"}
    {"video_id": 1, "author": "AceDestiny", "moment": 100, "event_timestamp": "2020-12-02 11:28:55.296Z", "type": "PLAY"}
    {"video_id": 1, "author": "AceDestiny", "moment": 300, "event_timestamp": "2020-12-02 11:28:58.784Z", "type": "STOP"}
    {"video_id": 1, "author": "Mzuckerberg", "moment": 0, "event_timestamp": "2020-12-02 11:33:00.591Z", "type": "PLAY"}
    {"video_id": 1, "author": "Mzuckerberg", "moment": 56, "event_timestamp": "2020-12-02 11:33:03.928Z", "type": "PAUSE"}
    {"video_id": 1, "author": "Mzuckerberg", "moment": 56, "event_timestamp": "2020-12-02 11:33:09.872Z", "type": "PLAY"}
    {"video_id": 1, "author": "Mzuckerberg", "moment": 296, "event_timestamp": "2020-12-02 11:33:13.724Z", "type": "PAUSE"}
    {"video_id": 1, "author": "naosei1", "moment": 0, "event_timestamp": "2020-12-02 11:33:17.108Z", "type": "PLAY"}
    {"video_id": 1, "author": "naosei2", "moment": 0, "event_timestamp": "2020-12-02 11:33:20.681Z", "type": "PLAY"}
    
    (10 rows)

## rating

```
cqlsh:cbd_video> select json * from rating;

[json]
---------------------------------------------
{"video_id": 1, "id_rat": 1, "val_rat": 4}
{"video_id": 1, "id_rat": 2, "val_rat": 5}
{"video_id": 2, "id_rat": 3, "val_rat": 5}
{"video_id": 2, "id_rat": 4, "val_rat": 2}
{"video_id": 2, "id_rat": 5, "val_rat": 3}
{"video_id": 2, "id_rat": 6, "val_rat": 4}
{"video_id": 4, "id_rat": 10, "val_rat": 4}
{"video_id": 4, "id_rat": 11, "val_rat": 3}
{"video_id": 4, "id_rat": 12, "val_rat": 5}
{"video_id": 3, "id_rat": 7, "val_rat": 4}
{"video_id": 3, "id_rat": 8, "val_rat": 1}
{"video_id": 3, "id_rat": 9, "val_rat": 3}
```

### tags_videos

```
cqlsh:cbd_video> select json * from tags_videos;

[json]
----------------------------------------
{"tag": "food", "video": [3, 9]}
{"tag": "livestream", "video": [2, 8]}
{"tag": "vlog", "video": [1, 6, 9]}
{"tag": "lifestyle", "video": [1, 3]}
{"tag": "tech", "video": [5, 7, 10]}
{"tag": "fashion", "video": [6]}
{"tag": "review", "video": [4, 5]}
{"tag": "music", "video": [4, 8]}
{"tag": "gaming", "video": [2, 5, 7]}
{"tag": "tvshow", "video": [3, 10]}

(10 rows)
```

## Queries



**Videos de um determinado autor**

Como o atributo `timestamp_upload` é uma clustering key necessitei de usar o ALLOW FILTERING. Contudo replicando a tabela video mudando apenas a primary key (não incluindo o timestamp_upload ), seria possível efetuar esta query.

```

cqlsh:cbd_video> select *  from video where author='DanielGomes14' ALLOW FILTERING;
video_id  | author        | timestamp_upload                | description               | followers           | tags| video_name
----------+---------------+---------------------------------+---------------------------+--------------------------------------+-
1 | DanielGomes14 | 2020-12-02 10:30:30.048000+0000 | I dont know, just a video | {'AceDestiny', 'naosei1', 'naosei2'} |{'lifestyle', 'vlog'} | My first video

(1 rows)
cqlsh:cbd_video> select * from comment_author;
```



**Pesquisa de comentários por utilizador, ordenado inversamente pela data **

```
cqlsh:cbd_video> select * from comment_user where author= 'DanielGomes14';
 author        | timestamp_comment               | comment_text                 | video_id
---------------+---------------------------------+------------------------------+----------
 DanielGomes14 | 2020-12-03 09:41:14.679000+0000 |       continue the good work |        3
 DanielGomes14 | 2020-12-02 11:19:55.082000+0000 | wow, one more purchase, nice |       10


```



**Pesquisa de comentários por video, ordenado inversamente pela data**

```
cqlsh:cbd_video> select * from comment_video  where video_id=3;

 video_id | timestamp_comment               | author        | comment_text
----------+---------------------------------+---------------+------------------------
        3 | 2020-12-03 09:42:14.008000+0000 | DanielGomes14 | continue the good work
        3 | 2020-12-02 11:22:46.721000+0000 |        yauyau |    meh, i didnt like .

(2 rows)
```



**Pesquisa do rating médio de um vídeo e quantas vezes foi votado**

```
cqlsh:cbd_video> select count(val_rat), avg(val_rat)  from rating  where video_id=2;

 system.count(val_rat) | system.avg(val_rat)
-----------------------+---------------------
                     4 |                   3

(1 rows)
```

**Os últimos 3 comentários introduzidos para um vídeo;**

```

cqlsh:cbd_video> SELECT  * from comment_video where video_id=3 LIMIT 3;

 video_id | timestamp_comment               | author        | comment_text
----------+---------------------------------+---------------+------------------------
        3 | 2020-12-03 09:51:32.469000+0000 |    AceDestiny |      spamming comments
        3 | 2020-12-03 09:51:19.678000+0000 |   Mzuckerberg |      spamming comments
        3 | 2020-12-03 09:42:14.008000+0000 | DanielGomes14 | continue the good work

(3 rows)
```

**Lista das tags de determinado vídeo;**

```
cqlsh:cbd_video> select tags  from video where video_id = 5;

tags
------------------------------

{'gaming', 'review', 'tech'}

(1 rows)
```

**Todos os vídeos com a tag Aveiro;**

Como não possuo nenhum video com a tag 'Aveiro', o exemplo a seguir e demonstrado com a tag 'gaming'

```
cqlsh:cbd_video> select video from tags_videos where tag = 'gaming';

 video
-----------
 {2, 5, 7}

(1 rows)
```

**Os últimos 5 eventos de determinado vídeo realizados por um utilizador;**

```
cqlsh:cbd_video> select * from event where video_id = 1 and author= 'Mzuckerberg' LIMIT 5;

 video_id | author      | moment | event_timestamp                 | type
----------+-------------+--------+---------------------------------+-------
        1 | Mzuckerberg |      0 | 2020-12-02 11:33:00.591000+0000 |  PLAY
        1 | Mzuckerberg |     56 | 2020-12-02 11:33:03.928000+0000 | PAUSE
        1 | Mzuckerberg |     56 | 2020-12-02 11:33:09.872000+0000 |  PLAY
        1 | Mzuckerberg |    296 | 2020-12-02 11:33:13.724000+0000 | PAUSE

(4 rows)
```

**Vídeos partilhados por determinado utilizador num determinado período de tempo**

Não existe em Cassandra um operador  'LIKE' como em SQL Server, o mais semelhante que existe é utilizar '<=', dai ter sido usado.  A utilização de '<=' necessita da utilização de ALLOW FILTERING

```
cqlsh:cbd_video> select * from video where author='DanielGomes14' and timestamp_upload<='2020-12-03' ALLOW FILTERING;

 video_id | author        | timestamp_upload                | description               | followers                            | tags                  | video_name
----------+---------------+---------------------------------+---------------------------+--------------------------------------+-----------------------+----------------
1 | DanielGomes14 | 2020-12-02 10:30:30.048000+0000 | I dont know, just a video | {'AceDestiny', 'naosei1', 'naosei2'} | {'lifestyle', 'vlog'} | My first video

(1 rows)
```

**Os últimos 10 vídeos, ordenado inversamente pela data da partilha**

É impossível realizar esta query visto que para que os resultados aparecessem ordenados seria necessário especificar um autor desse vídeo. Contudo, mesmo assim teríamos de utilizar ALLOW FILTERING, visto que o selo_temporal é uma clustering key.

**Todos os seguidores (followers) de determinado vídeo**

```
cqlsh:cbd_video> select followers from video where video_id=2;

 followers
---------------------------------------

 {'Mzuckerberg', 'naosei3', 'naosei4'}

(1 rows)
```



**Todos os comentários (dos vídeos) que determinado utilizador está a seguir (following);**

```
cqlsh:cbd_video> select * from comment_video_follower  where follower='DanielGomes14';

follower      | video_id | comments
---------------+----------+----------------------------------
DanielGomes14 |        5 |                  ['awful video']
DanielGomes14 |       10 | ['wow, one more purchase, nice']

(2 rows)
```

**Os 5 vídeos com maior rating;**

Seria necessário incluir o rating como clustering key de forma a ordenar por rating. Mesmo assim teriamos de incluir a partition key, pelo que esta query não seria possível.

**Uma query que retorne todos os vídeos e que mostre claramente a forma pela qual estão ordenados;**

Não é possivel realizar esta query.

**Lista com as Tags existentes e o número de vídeos catalogados com cada uma delas;**

Seria necessário ter uma coluna  na tabela `tags_videos` que contabilizasse o numero de videos com a tag em causa, isto porque em cassandra nao existe nenhum método nativo que conte o número de elementos numa lista ou conjunto ( coluna `video` desta tabela).