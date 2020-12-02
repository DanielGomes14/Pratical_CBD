**User**

username  text

name text

email text

timestamp_register timestamp

pk(username)



**Video**

video_id int

author  text

video_name text

description text

tags  set<text>

followers set<text>

timestamp_upload timestamp 

pk(id,author,timestamp_upload)



**Comment_User**

author text

video_id int

comment_text text	

timestamp_comment text

pk(author,timestamp ) 

clusterpk(timestamp_comment)



**Comment_video**

author text

video_id 

comment_text 

timestamp_comment 

pk(video_id,timestamp  ) 

clusterpk(timestamp_comment)



**Event**

video_id int

author text

type text

event_timestamp timestamp

moment int

pk(video_id,author,moment,timestamp)



**Rating**

id_rat int

video_id int 

value text

pk(video_id,id)



**Tags_videos**

tag text 

video set<int>



**Follower**

