# Lab 4.4 - Temática Livre

Para  este exercício decidi utilizar um Dataset do Yelp, que consiste em Reviews de utilizadores a 
Empresas, consoante os seus serviços. Infelizmente como os Dados originais do Yelp são de tamanho consideravelmente elevado, utilizei um subset: https://www.kaggle.com/omkarsabnis/yelp-reviews-dataset, que não apresenta tantos  atributos para as entidades modeladas quanto o que seria desejável. Este csv, foi "cortado" pois apresentava um número bastante elevado de nós, pelo que será **recomendável** usar o ficheiro que se encontra na pasta `dataset`, denominado **yelp-1.csv**

Assim modelei a base de Dados da Seguinte Forma:

**User**

 user_id

**Company**:

Company_id

**Review**

 review_id,

text,

date,

stars,

funny,

useful,

cool,

Review

**Writes**(User->Review)

**Evaluates**(Review -> Company)

## Carregamento dos dados do ficheiro csv

#### Criação das Entidades

```
LOAD CSV WITH HEADERS FROM "file:///yelp-1.csv" AS ROW
MERGE (u:User {id:ROW.user_id})
MERGE (c:Company{id:ROW.business_id})
MERGE (r:Review{id:ROW.review_id})
SET r.text = ROW.text, r.date=date(ROW.date),r.stars=toInteger(ROW.stars),
r.funny=toInteger(ROW.funny), r.useful = toInteger(ROW.useful), r.cool = toInteger(ROW.cool)
```

##### Criação das Relações

```
LOAD CSV WITH HEADERS FROM "file:///yelp-1.csv" AS Row
MATCH (u:User {id:Row.user_id}),(c:Company {id:Row.business_id}), (r:Review{id:Row.review_id}) 
create (u)-[:WRITES]->(r)
create (r)-[:EVALUATES]->(c)
```



