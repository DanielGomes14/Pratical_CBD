# Lab 3.4 - Base de Dados com Temática Livre

Para a realização deste exercício escolhi como temática para a base de dados, um sistema de Gestão de Venda de videojogos, cujas entidade são:

**User**: Um utilizador comum da plataforma que pode efetuar compras de jogos

**Game**: Guarda toda a informação associada a um videojogo, como nome, descrição,preço,generos de jogo e o rating do jogo(média do rating por review)

Sale: Representa a venda de uma video jogo, contendo o utilizador que efetuou a compra,a data de compra, a lista de jogos comprados e o mapeamento **entre** cada jogo comprado e o seu preço

**Developer**: Entidade que efetua o desenvolvimento dos videojogos

**Review**: Entidade que representa as reviews feitas a cada videojogo contendo o autor da review(um user), o jogo em causa,o comentário e o rating atribuido.



**User:**

username text,

name text,

email text,

register_timestamp timestamp

**pk**(username)

```
CREATE TABLE User(username text, name text, email text,register_timestamp  timestamp, PRIMARY KEY (username));
```



**Game**:

name text,

description text,

launchdate date,

price float,

genres set<text>,

developer text

avg_rat double

**pk**(name)

```
CREATE TABLE Game(name text, description text, launchdate date, price float, 
                  genres set<text>,developer text, avg_rat double, PRIMARY KEY(name));
```



Game_per_Developer:

name text,

description text,

launchdate date,

price float,

genres set<text>,

developer text

avg_rat double

pk(developer,name)

**index** > genres

**index** -> avg_rat

```
CREATE TABLE Game_Per_Developer(name text, description text, launchdate date, price float, genres set<text>,developer text, avg_rat double, PRIMARY KEY(developer,name)) WITH CLUSTERING ORDER BY(name ASC);

CREATE INDEX genres_ind ON game_per_developer(genres);
CREATE INDEX avg_rat_ind ON game_per_developer(avg_rat);                  
```



**Sale**:

sale_id int,

games_list list<text>,

client text,

price_per_game map<text,float>

total_price float

purchase_timestamp timestamp,

**pk**(client,timestamp)

**index** -> total_price

**index** -> games_list

```
CREATE TABLE Sale(sale_id int,games_list<text>,client text, price_per_game map<text,float>, total_price float, purchase_timestamp timestamp, PRIMARY KEY ( client, purchase_timestamp)) WITH CLUSTERING ORDER BY (purchase_timestamp DESC);
CREATE INDEX totprice_ind ON sale(total_price);
CREATE INDEX games_list_ind ON sale(games_list);
```





**Developer**

name text,

foundation_date date,

city text,

country text,

games_developed set<text>,

**pk**(name)

```
CREATE TABLE Developer(name text, foundation_date date, city text, country text, games_developed set<text>, PRIMARY KEY (name));
```



**Review**

game_name text,

author text,

rev_text text,

review_timestamp  timestamp,

rating float,

**pk**(game_name,review_timestamp,author)

```
CREATE TABLE Review(game_name text, author text, rev_text text, review_timestamp timestamp, rating float, PRIMARY KEY (game_name,review_timestamp,author )) WITH CLUSTERING ORDER BY(review_timestamp DESC, author ASC);
```



```
INSERT INTO  User(username,name,email,register_timestamp) VALUES('DanielGomes14', 'daniel', 'dagomes@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('AceDestiny', 'chico', 'chicoace@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('naosei1', 'naosei', 'naosei@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('naosei2', 'leonardo', 'leonardo@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('naosei3', 'lionel', 'lionel@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('naosei4', 'bot', 'bot@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('naosei5', 'bot_1', 'bot_1@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('yauyau', 'yau', 'yau@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('umacena', 'cena', 'cena@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('umacena2', 'cena2', 'cena2@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('umacena3', 'cena3', 'cena3@ua.pt', toTimestamp(now()));
INSERT INTO  User(username,name,email,register_timestamp) VALUES('Mzuckerberg', 'Mark', 'mark@fb.pt', toTimestamp(now()));

##################GAME#################
INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Super Mario 64','Super Mario 64 is a 1996 platform video game for the Nintendo 64.','1996-06-23',3.99,{'Platformer', 'Side-Scrolling'},'Nintendo',4 );

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Super Mario Bros','Super Mario Bros. is a side-scrolling video game.','2006-05-01',2.99,{'Platformer','Adventure'},'Nintendo',3.5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Pokemon Red','Pokemon Red Version is role-playing video game','1996-03-27',5.99,{'Role-Playing', 'Adventure'},'Nintendo',3);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Grand Theft Auto V','Grand Theft Auto V is a 2013 action-adventure game','2013-09-17',59.99,{'Action', 'Adventure','Shooter'} ,'Rockstar Games',5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('FIFA 20','FIFA 20 is a football simulation video game','2019-07-19',39.99,{'Simulation','Sports'} ,'EA Sports',4);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Call of Duty : Black Ops 2','Call of Duty: Black Ops II is a 2012 first-person shooter','2012-11-12',11.99,{'Shooter','Action'} ,'Activision',5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Left 4 Dead 2','Left 4 Dead 2 is a 2009 multiplayer survival horror game','2009-11-17',6.99,{'Shooter','Action','Survival'} ,'Valve',2.5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Need For Speed : Most Wanted','Need for Speed: Most Wanted is an open world racing game','2012-10-30',19.99,{'Racing'} ,'EA',3);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Assassins Creed IV: Black Flag','Assassins Creed IV: Black Flag is an action-adventure video game','2013-10-29',39.99, {'Adventure','Action'},'Ubisoft',4.5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Far Cry 5','Far Cry 5 is a first-person shooter game','2018-03-27',69.99,{'Adventure','Action'} ,'Ubisoft',5);
INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('Pro Evolution Soccer 2019','Pro Evolution Soccer 2019 is a football simulation video game','2019-08-08',29.99, {'Simulation','Sports'} ,'Konami',5);

INSERT INTO Game(name,description,launchdate,price,genres,developer,avg_rat)
VALUES ('The Elder Scrolls V: Skyrim','The Elder Scrolls V: Skyrim is an action role-playing video game','2011-11-11',29.99, {'Adventure','MMORPG','Strategy'},'Bethesda Softworks',5);



############### GAME PER DEVELOPER ########################


    INSERT INTO Game_Per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Super Mario 64','Super Mario 64 is a 1996 platform video game for the Nintendo 64.','1996-06-23',3.99,{'Platformer', 'Side-Scrolling'},'Nintendo',4 );

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Super Mario Bros','Super Mario Bros. is a side-scrolling video game.','2006-05-01',2.99,{'Platformer','Adventure'},'Nintendo',3.5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Pokemon Red','Pokemon Red Version is role-playing video game','1996-03-27',5.99,{'Role-Playing', 'Adventure'},'Nintendo',3);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Grand Theft Auto V','Grand Theft Auto V is a 2013 action-adventure game','2013-09-17',59.99,{'Action', 'Adventure','Shooter'} ,'Rockstar Games',5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('FIFA 20','FIFA 20 is a football simulation video game','2019-07-19',39.99,{'Simulation','Sports'} ,'EA Sports',4);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Call of Duty : Black Ops 2','Call of Duty: Black Ops II is a 2012 first-person shooter','2012-11-12',11.99,{'Shooter','Action'} ,'Activision',5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Left 4 Dead 2','Left 4 Dead 2 is a 2009 multiplayer survival horror game','2009-11-17',6.99,{'Shooter','Action','Survival'} ,'Valve',2.5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Need For Speed : Most Wanted','Need for Speed: Most Wanted is an open world racing game','2012-10-30',19.99,{'Racing'} ,'EA',3);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Assassins Creed IV: Black Flag','Assassins Creed IV: Black Flag is an action-adventure video game','2013-10-29',39.99, {'Adventure','Action'},'Ubisoft',4.5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Far Cry 5','Far Cry 5 is a first-person shooter game','2018-03-27',69.99,{'Adventure','Action'} ,'Ubisoft',5);
    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('Pro Evolution Soccer 2019','Pro Evolution Soccer 2019 is a football simulation video game','2019-08-08',29.99, {'Simulation','Sports'} ,'Konami',5);

    INSERT INTO Game_per_Developer(name,description,launchdate,price,genres,developer,avg_rat)
    VALUES ('The Elder Scrolls V: Skyrim','The Elder Scrolls V: Skyrim is an action role-playing video game','2011-11-11',29.99, {'Adventure','MMORPG','Strategy'},'Bethesda Softworks',5);


#### DEVELOPER #######

INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Bethesda Softworks','1986-06-28','Rockville, Maryland','United States',{'The Elder Scrolls V: Skyrim'});

INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('EA','1982-05-27','Redwood City,California','United States',
{'Need For Speed : Most Wanted'});
INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Rockstar Games','1998-12-01','New York City, New York','United States',{'Grand Theft Auto V'});

INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Nintendo','1889-09-23','Kyoto','Japan',{'Super Mario 64','Super Mario Bros','Pokemon Red'});

INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Konami','1969-03-21','Tokyo','Japan',{'Pro Evolution Soccer 2019'});

INSERT INTO Developer(name,foundation_date,city,country)
VALUES ('Sony','1993-11-16','San Mateo, California','United States');
INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Ubisoft','1986-03-12','Montreuil','France',{'Far Cry 5','Assassins Creed IV: Black Flag'});
INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('EA Sports','1991-01-01','Redwood City,California','United States',{'FIFA 20'});
INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Valve','1996-09-24','Belleveue, Washington','United States',{'Left 4 Dead 2'});
INSERT INTO Developer(name,foundation_date,city,country,games_developed)
VALUES ('Activision','1979-10-01','Santa Monica, California','United States',{'Call of Duty : Black Ops 2'});
INSERT INTO Developer(name,foundation_date,city,country)
VALUES ('Blizzard Entertainment','1991-02-08','Irving, California','United States');
INSERT INTO Developer(name,foundation_date,city,country)
VALUES ('Xbox Game Studios','2002-01-01','Redmond, Washington','United States');

######## SALE ########

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (1,['Super Mario 64','Left 4 Dead 2'],'DanielGomes14',{'Super Mario 64': 3.99,'Left 4 Dead 2': 6.99},10.98,toTimestamp(now()));


INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (2,['Far Cry 5','FIFA 20'],'AceDestiny',{'Far Cry 5': 69.99,'FIFA 20': 39.99},109.98,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (3,['Grand Theft Auto V','Pokemon Red'],'naosei1',{'Grand Theft Auto V': 59.99,'Pokemon Red': 5.99},65.98,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (4,['Assassins Creed IV: Black Flag','Super Mario Bros'],'naosei1',{'Assassins Creed IV: Black Flag': 39.99,'Super Mario Bros': 2.99},42.98,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (5,['Pro Evolution Soccer 2019'],'naosei2',{'Pro Evolution Soccer 2019': 29.99},29.99,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (6,['FIFA 20'],'naosei3',{'FIFA 20': 39.99},39.99,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (7,['The Elder Scrolls V: Skyrim','Call of Duty : Black Ops 2'],'yauyau',{'Grand Theft Auto V': 59.99,'Call of Duty : Black Ops 2': 11.99},71.98,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (8,['Call of Duty : Black Ops 2'],'naosei4',{'Call of Duty : Black Ops 2': 11.99},11.99,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (9,['Need For Speed : Most Wanted','Pokemon Red'],'naosei5',{'Need For Speed : Most Wanted': 19.99,'Pokemon Red': 5.99},31.98,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (10,['Super Mario Bros'],'umacena3',{'Super Mario Bros': 2.99},2.99,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (11,['Far Cry 5','FIFA 20','Left 4 Dead 2'],'umacena',{'Far Cry 5': 69.99,'FIFA 20': 39.99,'Left 4 Dead 2': 6.99  },116.97,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (12,['Call of Duty : Black Ops 2'],'umacena2',{'Call of Duty : Black Ops 2': 11.99},11.99,toTimestamp(now()));

INSERT INTO Sale(sale_id,games_list,client,price_per_game,total_price,purchase_timestamp)
VALUES (13,['Assassins Creed IV: Black Flag'],'Mzuckerberg',{'Assassins Creed IV: Black Flag': 39.99},39.99,toTimestamp(now()));


#### REVIEWS ########

INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Super Mario 64','DanielGomes14','Great Game. A classic!',toTimestamp(now()),4);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Left 4 Dead 2','DanielGomes14','Could be better. Not bad tho.',toTimestamp(now()),3);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Left 4 Dead 2','umacena','Awful Game.',toTimestamp(now()),2);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Far Cry 5','AceDestiny','One of the best games ive played',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('FIFA 20','naosei3','A copy of FIFA 19.',toTimestamp(now()),3);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('FIFA 20','umacena','Love This Game!',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Grand Theft Auto V','naosei1','Cant stop playing',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Pokemon Red','naosei5','Average Game',toTimestamp(now()),3);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Need For Speed : Most Wanted','naosei5','Another Average Game',toTimestamp(now()),3);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Call of Duty : Black Ops 2','yauyau','One of the all time best games ever!',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Call of Duty : Black Ops 2','umacena2','worth every penny!',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Assassins Creed IV: Black Flag','Mzuckerberg','Top Tier game!',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Assassins Creed IV: Black Flag','naosei1','Has some bugs, but good game',toTimestamp(now()),4);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Pro Evolution Soccer 2019','naosei2','Better than FIFA',toTimestamp(now()),5);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Super Mario Bros','naosei1','Overrated',toTimestamp(now()),3);
INSERT INTO Review(game_name, author,rev_text,review_timestamp,rating)
VALUES('Super Mario Bros','umacena3','Funny to play',toTimestamp(now()),4);

```

#### Update Game Genre

Para atualizar os géneros de um jogo temos de efetuar update tanto a tabela `Game` como `Game_per_developer`. São então aqui efetuados 2 updates.

```
cqlsh:game_shop> Update Table Game SET categories = categories + {'Adventure'} WHERE game_name='Call of Duty : Black Ops 2';
cqlsh:game_shop> select name,genres from Game Where name ='Call of Duty : Black Ops 2' ;

 name                       | genres
----------------------------+------------------------------------
 Call of Duty : Black Ops 2 | {'Action', 'Adventure', 'Shooter'}

(1 rows)


cqlsh:game_shop> UPDATE game_per_developer  SET genres = genres + {'Adventure'} WHERE developer='Activision' AND name='Call of Duty : Black Ops 2';
cqlsh:game_shop> SELECT developer,name,genres FROM game_per_developer WHERE developer='Activision' AND name='Call of Duty : Black Ops 2';
 developer  | name                       | genres
------------+----------------------------+------------------------------------
 Activision | Call of Duty : Black Ops 2 | {'Action', 'Adventure', 'Shooter'}
(1 rows)

```

#### Update Developer Games

Como é possível observar na query a seguir, há developers que não possuem qualquer jogo, pelo que será adicionado um jogo a tabela `Game` e, posteriormente atualizar o set de jogos, atributos `games_developed`, a  um destes developers na tabela `Developer`.

```
cqlsh:game_shop> SELECT name,games_developed from Developer;

 name                   | games_developed
------------------------+-------------------------------------------------------
             Activision |                        {'Call of Duty : Black Ops 2'}
                     EA |                      {'Need For Speed : Most Wanted'}
              EA Sports |                                           {'FIFA 20'}
      Xbox Game Studios |                                                  null
 Blizzard Entertainment |                                                  null
               Nintendo | {'Pokemon Red', 'Super Mario 64', 'Super Mario Bros'}
         Rockstar Games |                                {'Grand Theft Auto V'}
                   Sony |                                                  null
     Bethesda Softworks |                       {'The Elder Scrolls V: Skyrim'}
                 Konami |                         {'Pro Evolution Soccer 2019'}
                Ubisoft |       {'Assassins Creed IV: Black Flag', 'Far Cry 5'}
                  Valve |                                     {'Left 4 Dead 2'}

(12 rows)
cqlsh:game_shop> INSERT INTO Game(name,description,launchdate,price,genres,developer) VALUES ('Halo 5 : Guardian','Halo 5: Guardians is a first-person shooter video game','2015-10-17',39.99,{'Action','Strategy','Shooter'},'Xbox Game Studios');
cqlsh:game_shop> INSERT INTO Game_Per_Developer(name,description,launchdate,price,genres,developer) VALUES ('Halo 5 : Guardian','Halo 5: Guardians is a first-person shooter video game','2015-10-17',39.99,{'Action','Strategy','Shooter'},'Xbox Game Studios');
cqlsh:game_shop> UPDATE Developer SET games_developed={'Halo 5 : Guardian'} WHERE name='Xbox Game Studios';
```

#### Update Review and Game Rating

Para atualizar o rating de uma review precisamos de atualizar tanto a tabela `Review` como `Game` e `Game_Per_Developer`. Isto porque estas últimas duas tabelas têm como atributo o rating médio de um jogo, que é dependente dos ratings dados nas reviews. Assim para atualizar o rating de uma review são efetuados 3 updates.

```
cqlsh:game_shop> select * from review WHERE  game_name='Assassins Creed IV: Black Flag' and review_timestamp='2020-12-26 10:54:01.943000+0000';

game_name                      | review_timestamp                | author  | rating | rev_text
--------------------------------+---------------------------------+---------+--------+------------------------------
Assassins Creed IV: Black Flag | 2020-12-26 10:54:01.943000+0000 | naosei1 |      4 | Has some bugs, but good game
(1 rows)


cqlsh:game_shop> UPDATE  review SET rating=3  WHERE  game_name='Assassins Creed IV: Black Flag' and review_timestamp='2020-12-26 10:54:01.943000+0000'and author = 'naosei1' ;

cqlsh:game_shop> select * from review WHERE  game_name='Assassins Creed IV: Black Flag' and review_timestamp='2020-12-26 10:54:01.943000+0000';

 game_name                      | review_timestamp                | author  | rating | rev_text
--------------------------------+---------------------------------+---------+--------+------------------------------
 Assassins Creed IV: Black Flag | 2020-12-26 10:54:01.943000+0000 | naosei1 |      3 | Has some bugs, but good game
(1 rows)

cqlsh:game_shop> UPDATE game set avg_rat = 4 WHERE name='Assassins Creed IV: Black Flag' ;
cqlsh:game_shop> UPDATE game_per_developer set avg_rat = 4 WHERE developer='Ubisoft' AND name='Assassins Creed IV: Black Flag';
cqlsh:game_shop> select developer,name,avg_rat from game_per_developer WHERE developer='Ubisoft' AND name='Assassins Creed IV: Black Flag';

 developer | name                           | avg_rat
-----------+--------------------------------+---------
   Ubisoft | Assassins Creed IV: Black Flag |     4

(1 rows)

```



#### Delete Developer

Para eliminar um developer da tabela correspondente ( `Developer` ), é necessário também eliminar das tabelas de jogos ( `Game` e `Game_Per_Developer`), visto que não faz sentido ter jogos disponiveis para comprar, sem ter um developer associado. 

```
cqlsh:game_shop> select name,developer from Game;

 name                           | developer
--------------------------------+--------------------
 Assassins Creed IV: Black Flag |            Ubisoft
                 Super Mario 64 |           Nintendo
                  Left 4 Dead 2 |              Valve
    The Elder Scrolls V: Skyrim | Bethesda Softworks
     Call of Duty : Black Ops 2 |         Activision
               Super Mario Bros |           Nintendo
                      Far Cry 5 |            Ubisoft
      Pro Evolution Soccer 2019 |             Konami
   Need For Speed : Most Wanted |                 EA
              Halo 5 : Guardian |  Xbox Game Studios
                    Pokemon Red |           Nintendo
                        FIFA 20 |          EA Sports
             Grand Theft Auto V |     Rockstar Games

(13 rows)
cqlsh:game_shop> DELETE FROM Game where name='Halo 5 : Guardian';
cqlsh:game_shop> DELETE FROM game_per_developer WHERE developer='Xbox Game Studios';
cqlsh:game_shop> select name,developer from Game;

 name                           | developer
--------------------------------+--------------------
 Assassins Creed IV: Black Flag |            Ubisoft
                 Super Mario 64 |           Nintendo
                  Left 4 Dead 2 |              Valve
    The Elder Scrolls V: Skyrim | Bethesda Softworks
     Call of Duty : Black Ops 2 |         Activision
               Super Mario Bros |           Nintendo
                      Far Cry 5 |            Ubisoft
      Pro Evolution Soccer 2019 |             Konami
   Need For Speed : Most Wanted |                 EA
                    Pokemon Red |           Nintendo
                        FIFA 20 |          EA Sports
             Grand Theft Auto V |     Rockstar Games

(12 rows)
```

#### Delete Review

Eliminar uma Review afeta, como já foi referido, a coluna de rating médio, `avg_rat`, tanto da tabela `Game` como `Game_Per_Developer`. Contudo caso eliminemos a única review feita a um jogo, teremos também de eliminar a coluna do rating médio do jogo visto que não houve nenhuma review a este jogo. Assim no caso a seguir, são efetuados 3 Deletes.

```
cqlsh:game_shop> select * from Review WHERE game_name='Pro Evolution Soccer 2019' AND review_timestamp='2020-12-26 10:54:01.946000+0000';

 game_name                 | review_timestamp                | author  | rating | rev_text
---------------------------+---------------------------------+---------+--------+------------------
 Pro Evolution Soccer 2019 | 2020-12-26 10:54:01.946000+0000 | naosei2 |      5 | Better than FIFA

(1 rows)
cqlsh:game_shop> delete from Review WHERE game_name='Pro Evolution Soccer 2019' AND review_timestamp='2020-12-26 10:54:01.946000+0000';
cqlsh:game_shop> select * from Review WHERE game_name='Pro Evolution Soccer 2019';

 game_name | review_timestamp | author | rating | rev_text
-----------+------------------+--------+--------+----------
(0 rows)

cqlsh:game_shop>select developer from game_per_developer WHERE name='Pro Evolution Soccer 2019' ALLOW FILTERING;

 developer
-----------
    Konami
(1 rows)
cqlsh:game_shop> DELETE avg_rat from game_per_developer  WHERE developer='Konami' and name='Pro Evolution Soccer 2019';
cqlsh:game_shop> DELETE avg_rat from Game WHERE name='Pro Evolution Soccer 2019';
```



### Queries

Selecionar todos os nomes do jogos que tenham o género 'Action', assim como o respetivo developer.

```
cqlsh:game_shop> select developer,name,genres from game_per_developer where genres CONTAINS 'Action';

 developer      | name                           | genres
----------------+--------------------------------+------------------------------------
     Activision |     Call of Duty : Black Ops 2 | {'Action', 'Adventure', 'Shooter'}
 Rockstar Games |             Grand Theft Auto V | {'Action', 'Adventure', 'Shooter'}
        Ubisoft |                      Far Cry 5 |            {'Action', 'Adventure'}
        Ubisoft | Assassins Creed IV: Black Flag |            {'Action', 'Adventure'}
          Valve |                  Left 4 Dead 2 |  {'Action', 'Shooter', 'Survival'}

(5 rows)

```

Selecionar Todas as Reviews feitas ao jogo 'FIFA 20',ordenadas de mais recente para mais antiga.

```
cqlsh:game_shop> select * from review  where game_name='FIFA 20';

 game_name | review_timestamp                | author  | rating | rev_text
-----------+---------------------------------+---------+--------+--------------------
   FIFA 20 | 2020-12-26 10:54:01.918000+0000 | umacena |      5 |    Love This Game!
   FIFA 20 | 2020-12-26 10:54:01.910000+0000 | naosei3 |      3 | A copy of FIFA 19.

(2 rows)

```

Selecionar o selo temporal da ultima compra feita pelo cliente com o username "naosei1".

```
cqlsh:game_shop> SELECT client,purchase_timestamp FROM SALE where client='naosei1' ORDER BY purchase_timestamp DESC LIMIT 1;

 client  | purchase_timestamp
---------+---------------------------------
 naosei1 | 2020-12-24 11:55:36.161000+0000

(1 rows)	
	
```

Selecionar as Compras feitas que incluiram o jogo "Call of Duty : Black Ops 2"

```
cqlsh:game_shop> select client,purchase_timestamp,games_list,total_price from Sale where games_list CONTAINS 'Call of Duty : Black Ops 2';

 client   | purchase_timestamp              | games_list                                                    | total_price
----------+---------------------------------+---------------------------------------------------------------+-------------
   yauyau | 2020-12-24 12:02:35.186000+0000 | ['The Elder Scrolls V: Skyrim', 'Call of Duty : Black Ops 2'] |       71.98
  naosei4 | 2020-12-24 12:04:11.644000+0000 |                                ['Call of Duty : Black Ops 2'] |       11.99
 umacena2 | 2020-12-24 12:11:09.036000+0000 |                                ['Call of Duty : Black Ops 2'] |       11.99

(3 rows)
```

Selecionar todos os nomes dos jogos desenvolvidos pela 'Nintendo'.

```
cqlsh:game_shop> select developer,name from game_per_developer where developer='Nintendo';

 developer | name
-----------+------------------
  Nintendo | Super Mario Bros
  Nintendo |   Super Mario 64
  Nintendo |      Pokemon Red

(3 rows)
```

​	Selecionar 3 vendas que um preço superior a 60 euros.

```
cqlsh:game_shop> select client,purchase_timestamp,total_price from sale where total_price >60.00 LIMIT 3 ALLOW FILTERING; 

 client     | purchase_timestamp              | total_price
------------+---------------------------------+-------------
     yauyau | 2020-12-24 12:02:35.186000+0000 |       71.98
 AceDestiny | 2020-12-24 11:50:57.037000+0000 |      109.98
    naosei1 | 2020-12-24 11:52:54.020000+0000 |       65.98

(3 rows)
```

Selecionar os jogos com rating médio de 4:

```
cqlsh:game_shop> SELECT name,avg_rat from game_per_developer where avg_rat =4;

 name                           | avg_rat
--------------------------------+---------
                        FIFA 20 |       4
                 Super Mario 64 |       4
 Assassins Creed IV: Black Flag |       4

(3 rows)
```

Selecionar o preço de cada jogo de 5  compras, assim como o preço total.

```
cqlsh:game_shop> SELECT price_per_game,total_price  from sale LIMIT 5;

 price_per_game                                                     | total_price
--------------------------------------------------------------------+-------------
 {'Call of Duty : Black Ops 2': 11.99, 'Grand Theft Auto V': 59.99} |       71.98
       {'Need For Speed : Most Wanted': 19.99, 'Pokemon Red': 5.99} |       31.98
                               {'Pro Evolution Soccer 2019': 29.99} |       29.99
                             {'FIFA 20': 39.99, 'Far Cry 5': 69.99} |      109.98
                          {'Assassins Creed IV: Black Flag': 39.99} |       39.99

(5 rows)

```

Selecionar o conjunto de jogos desenvolvidos por cada developer.

```
cqlsh:game_shop> select name,games_developed from developer ;

 name                   | games_developed
------------------------+-------------------------------------------------------
             Activision |                        {'Call of Duty : Black Ops 2'}
                     EA |                      {'Need For Speed : Most Wanted'}
              EA Sports |                                           {'FIFA 20'}
 Blizzard Entertainment |                                                  null
               Nintendo | {'Pokemon Red', 'Super Mario 64', 'Super Mario Bros'}
         Rockstar Games |                                {'Grand Theft Auto V'}
                   Sony |                                                  null
     Bethesda Softworks |                       {'The Elder Scrolls V: Skyrim'}
                 Konami |                         {'Pro Evolution Soccer 2019'}
                Ubisoft |       {'Assassins Creed IV: Black Flag', 'Far Cry 5'}
                  Valve |                                     {'Left 4 Dead 2'}

(11 rows)
cqlsh:game_shop>
```



Selecionar todos os jogos cujo developer seja 'Ubisoft' e possua um rating médio de 4.

```
cqlsh:game_shop> select developer,name,avg_rat from game_per_developer where developer='Ubisoft' and avg_rat =4;

 developer | name                           | avg_rat
-----------+--------------------------------+---------
   Ubisoft | Assassins Creed IV: Black Flag |       4

(1 rows)
cqlsh:game_shop> 

```

Selecionar a ultima review feita ao jogo 'Left 4 Dead 2'.

```
cqlsh:game_shop> select * from review where game_name='Left 4 Dead 2' ORDER BY review_timestamp DESC  LIMIT 1;

 game_name     | review_timestamp                | author  | rating | rev_text
---------------+---------------------------------+---------+--------+-------------
 Left 4 Dead 2 | 2020-12-26 10:54:01.893000+0000 | umacena |      2 | Awful Game.

(1 rows)

```

