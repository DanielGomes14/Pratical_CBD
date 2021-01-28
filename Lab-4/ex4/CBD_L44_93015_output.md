### Utilizadores existentes:

#### Query

```
"MATCH (u:User) return u"
```

#### Resultado

```
User: rLtl8ZkDX5vH5nAx9C3q5Q
User: 0a2KyEL0d3Yb1V6aivbIuQ
User: 0hT2KtfLiobPvh6cDC8JQg
User: uZetl9T0NcROGOyFfughhg
User: vYmM4KTsC8ZfQBg-j5MWkw
User: sqYN3lNgvPbPCTRsMFu27g
User: wFweIWhv2fREZV_dYkz_1g
User: 1ieuYcKS7zeAv_U15AB13A
User: Vh_DlizgGhSqQh4qfZ2h6A
User: sUNkXg8-KFtCMQDV6zRzQg
User: -OMlS6yWkYjVldNhC31wYg
User: C1rHp3dmepNea7XiouwB6Q
User: UPtysDF6cUDUxq2KY-6Dcg
User: Xm8HXE1JHqscXe5BKf0GFQ
User: JOG-4G4e8ae3lx_szHtR8g
(...)
```





### 10 Reviews com o maior numero de frases no texto, juntamente com id desta.

#### Query

```
MATCH (r:Review)
return r.id,size(split(r.text,'.')) as num_phrases 
ORDER BY num_phrases DESC
LIMIT 10
```

#### Resultado

```
Review ID: RUe0yOda890ngDaz32MZ8g, Number of Phrases: 94
Review ID: 39kwDyJI29y3poToHdCcHQ, Number of Phrases: 78
Review ID: _2FCV5-xP7TdHmRS3mmV1g, Number of Phrases: 72
Review ID: SuHlTdX-4q-mXkXD_H0m7g, Number of Phrases: 72
Review ID: twjAkn8Z7OlAemQfZexVrQ, Number of Phrases: 72
Review ID: -0d1TTyKmgknAYVEDLaqgQ, Number of Phrases: 63
Review ID: 6jRs2P6zTYMn36fVnCu1Zw, Number of Phrases: 61
Review ID: eSOmx01vY6hEgNdlRzw0FA, Number of Phrases: 59
Review ID: amf0jmbwOrCRQte4ev0miw, Number of Phrases: 52
```



### 10 utilizadores que escreveram mais Reviews

#### Query

```
MATCH (u:User)-[w:WRITES]->(r:Review)
return u as user, count(w) as total_reviews 
ORDER BY total_reviews DESC LIMIT 10
```

#### Resultado

```
User: fczQCSmaWF78toLEmb0Zsw, Total Number of Reviews Written: 6
User: 0CMz8YaO3f8xu4KqQgKb9Q, Total Number of Reviews Written: 6
User: 90a6z--_CUrl84aCzZyPsg, Total Number of Reviews Written: 5
User: nDBly08j5URmrHQ2JCbyiw, Total Number of Reviews Written: 5
User: l53FUDHRHLg7BQ89KgAtxQ, Total Number of Reviews Written: 4
User: nM3vfxr6fcnN_nXdzwbVCw, Total Number of Reviews Written: 4
User: 17PPxx8RxjOUD_nQZ1aHEw, Total Number of Reviews Written: 4
User: dQO0tQISZyb9L4d5ASnXyQ, Total Number of Reviews Written: 4
User: rLtl8ZkDX5vH5nAx9C3q5Q, Total Number of Reviews Written: 4
```



### O utilizador que fez a review mais antiga

#### Query

```
MATCH (u:User)-[:WRITES]->(r:Review)
return u,r.date
ORDER BY r.date 
LIMIT 1
```

#### Resultado

```
User: 9Z05mtXrXJ-22PElwp1ahA, Date of Review: 2006-01-11
```





### Empresa que obteu o maior número de Reviews

#### Query

```
MATCH (r:Review)-[e:EVALUATES]->(c:Company)
return count(r) as num_reviews, c as company ORDER BY  num_reviews DESC LIMIT 1
```

#### Resultado

```
Company : rZbHg4ACfN3iShdsT47WKQ, number of Reviews: 8
```



### Id das Reviews com efetuadas com rating de 4 depois de 1 de janeiro de 2011

#### Query

```
match (r:Review{stars:4})
WHERE r.date > date('2011-01-01')
return r.date as date ,r.id as id,r.stars as stars 
```

#### Resultado

```
Date Review: 2012-06-14, ID: IESLBzqUCLdSzSqm0eCSxQ, stars: 4
Date Review: 2012-07-12, ID: JL7GXJ9u4YMx7Rzs05NfiQ, stars: 4
Date Review: 2012-08-17, ID: XtnfnYmnJYi71yIuGsXIUA, stars: 4
Date Review: 2011-12-23, ID: FvEEw1_OsrYdvwLV5Hrliw, stars: 4
Date Review: 2011-11-17, ID: a0lCu-j2Sk_kHQsZi_eNgw, stars: 4
Date Review: 2011-03-30, ID: Y_ERKao0J5WsRiCtlKSNSA, stars: 4
Date Review: 2012-07-12, ID: hre97jjSwon4bn1muHKOJg, stars: 4
Date Review: 2012-10-16, ID: gNm48xplqt42p-NvCkzUBg, stars: 4
Date Review: 2011-01-02, ID: 5V9lfjjlY7tkXgkcDUWYyg, stars: 4
Date Review: 2011-11-09, ID: 2sG6LKzRz91l10stVHl8uQ, stars: 4
Date Review: 2012-06-30, ID: UTWI0z7nV5zNJLWnOz9Z5A, stars: 4
Date Review: 2012-04-12, ID: 7uzj6rJ71toRmdsXyo2suA, stars: 4
Date Review: 2012-01-03, ID: V75NCU3EhqTDwPj5In94DQ, stars: 4
Date Review: 2011-10-03, ID: GxaYFCprt-wyqO--vB4PHQ, stars: 4
Date Review: 2011-12-13, ID: hHwQaL3QN2xgTBSCQ26wIA, stars: 4
(....)
```



### Média de avaliação de uma Empresa consoante o número de "stars" em cada Review

#### Query

```
match(r:Review)-[:EVALUATES]->(c:Company)
with c as company, avg(r.stars) as avg_rating
return company, round(avg_rating*100)/100 as avg_rating
```

#### Resultado

```
Company : 9yKzy9PApeiPPOUJEtnvkg, Average Rating: 5.0
Company : ZRJwVLyzEJq1VAihDhYiow, Average Rating: 5.0
Company : 6oRAC4uyJCsJl1X0WZpVSA, Average Rating: 4.6
Company : _1QQZuf4zZOyFCvXc0o6Vg, Average Rating: 4.5
Company : 6ozycU1RpktNG2-1BroVtw, Average Rating: 5.0
Company : -yxfBYGB6SEqszmxJxd97A, Average Rating: 4.0
Company : zp713qNhx8d9KCJJnrw1xA, Average Rating: 5.0
Company : hW0Ne_HTHEAgGF1rAdmR-g, Average Rating: 2.71
Company : wNUea3IXZWD63bbOQaOH-g, Average Rating: 3.0
Company : nMHhuYan8e3cONo3PornJA, Average Rating: 5.0
Company : AsSCv0q_BWqIe3mX2JqsOQ, Average Rating: 5.0
Company : e9nN4XxjdHj4qtKCOPq_vg, Average Rating: 5.0
Company : h53YuCiIDfEFSJCQpk8v1g, Average Rating: 4.5
Company : WGNIYMeXPyoWav1APUq7jA, Average Rating: 4.0
Company : yc5AH9H71xJidA_J2mChLA, Average Rating: 4.0
Company : Vb9FPCEL6Ly24PNxLBaAFw, Average Rating: 3.5
(....)
```



### Média de "stars" atribuida por utilizador nas suas reviews

#### Query

```
Match(u:User)-[:WRITES]->(r:Review)
with u, avg(r.stars) as avg_rat
return u, round(avg_rat*100)/100 as avg_rat
```

#### Resultado

```
User : rLtl8ZkDX5vH5nAx9C3q5Q, Average Rating Given : 4.25
User : 0a2KyEL0d3Yb1V6aivbIuQ, Average Rating Given : 5.0
User : 0hT2KtfLiobPvh6cDC8JQg, Average Rating Given : 4.0
User : uZetl9T0NcROGOyFfughhg, Average Rating Given : 5.0
User : vYmM4KTsC8ZfQBg-j5MWkw, Average Rating Given : 5.0
User : sqYN3lNgvPbPCTRsMFu27g, Average Rating Given : 4.0
User : wFweIWhv2fREZV_dYkz_1g, Average Rating Given : 5.0
User : 1ieuYcKS7zeAv_U15AB13A, Average Rating Given : 4.0
User : Vh_DlizgGhSqQh4qfZ2h6A, Average Rating Given : 4.0
User : sUNkXg8-KFtCMQDV6zRzQg, Average Rating Given : 5.0
User : -OMlS6yWkYjVldNhC31wYg, Average Rating Given : 4.5
User : C1rHp3dmepNea7XiouwB6Q, Average Rating Given : 5.0
User : UPtysDF6cUDUxq2KY-6Dcg, Average Rating Given : 5.0
User : Xm8HXE1JHqscXe5BKf0GFQ, Average Rating Given : 3.0
User : JOG-4G4e8ae3lx_szHtR8g, Average Rating Given : 4.0
User : ylWOj2y7TV2e3yYeWhu2QA, Average Rating Given : 2.0
User : SBbftLzfYYKItOMFwOTIJg, Average Rating Given : 3.0
User : u1KWcbPMvXFEEYkZZ0Yktg, Average Rating Given : 5.0
User : UsULgP4bKA8RMzs8dQzcsA, Average Rating Given : 4.0
User : nDBly08j5URmrHQ2JCbyiw, Average Rating Given : 3.4
(....)
```



### Caminho entre o Utilizador com id "fczQCSmaWF78toLEmb0Zsw" e cada Companhia 

#### Query

```
MATCH (u:User{id:"fczQCSmaWF78toLEmb0Zsw"}), (c:Company)
MATCH p=shortestPath((u)-[*]->(c))
where u <> c
RETURN p
```

#### Resultado

```
{'id': 'fczQCSmaWF78toLEmb0Zsw'}
WRITES
{'date': neo4j.time.Date(2009, 5, 23), 'cool': 7, 'text': "I've gotta say, Nordy's (as my aunt refers to Nordstom since it's her favorite department store ever) is always a safe bet if you're looking for quality selection, customer service, and a very pleasant shopping experience.  Whether you're looking for shoes, lingerie, cosmetics, apparel, etc... you'll likely find it at Nordy's.\n\nI'll have to take Samantha O's and Julia T's advice and remember to go to Nordstom's next time I need a new bra.\n\nIn the meantime, I've gotten lucky with shoes, all kinds of clothes, accessories, and perfume.  This weekend is a big sale.  I ran in earlier to return something and walked out with a new pair of shoes.  Oops.  Take that as a warning, Nordstrom can be dangerous for shop-a-holics.\n\nThe only times I have not gotten lucky at Nordstrom are when I've been on a mission for something specific - such as Converse Sneakers in Jared's size 7 or a certain shirt or bathing suit in my size.  But I'll overlook these times and return again and again... Keep up the good customer service and selection, Nordy's - and I'll be a customer for life.", 'stars': 4, 'id': 'LtjtyM0CcVWBNRJ-mfsC_Q', 'useful': 8, 'funny': 4}
EVALUATES
{'id': 'c7VgGP8xT25OSReok6fwcQ'}

 
{'id': 'fczQCSmaWF78toLEmb0Zsw'}
WRITES
{'date': neo4j.time.Date(2009, 2, 23), 'cool': 6, 'stars': 4, 'id': '5rzUPtAk7rR5EK8x_WszwA', 'text': "I really don't consider myself a Neiman's type of girl...\nAs a matter of fact, I don't really feel comfortable in Neiman's - it's extremely 'fancy' and high end for me.  But, I've gone in a handful of times and even bought a dress or two on sale at Neiman's in the past.  I admit, the quality associated with the designer labels at this store is pretty amazing.\n\nAnyways, I felt some major pressure to register at Neiman's from my mom, so I met my mother-in-law-to-be at this Neiman's location on Sunday.  The housewares / registry department is extremely small... and although the woman working was sweet, she botched the spelling of Jared's name in the process.  Needless to say, i was not so impressed.\n\nFancy sums this department store up in my eyes - I'm much more of a Nordstrom's or Macy's or Bloomingdales type of girl!", 'useful': 8, 'funny': 2}
EVALUATES
{'id': 'h8rqIokh6EkM4flR9CjxbA'}

(....)
```



### Companhia que teve a review com o maior número de reacoes (useful,funny e cool), e o respetivo total de reações

#### Query

```
MATCH (r:Review)-[:EVALUATES]->(c:Company)
with r,c,[r.funny,r.useful,r.cool] as reactions
return r.id,c,reduce(total=0, number in reactions | total + number) as total
ORDER BY total DESC
LIMIT 1;
```

#### Resultado

```
Review: RqwFPp_qPu-1h87pGBAM8w,  Company: pfTwzep_4hRTX_jXoi38cw, Total Number of Reactions : 95
```

