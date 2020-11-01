# Lab 1 CBD
# Bases de Dados de Documentos - MongoDB

## Instalação e Configurações

**Download e iniciação do servidor**

    1. $ curl -fsSL https://www.mongodb.org/static/pgp/server-4.4.asc | sudo apt-key add -
    2. $ echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list
    3. $ sudo apt update
    4. $ sudo apt install mongodb-org
    5. $ sudo systemctl start mongod.service
    6. $ sudo systemctl enable mongod
**Utilizar o serviço via Command Line**

    $ mongo

**Gerir o Serviço MongoDB**

    Verificar estado do serviço:
        $ sudo systemctl status mongod
    Parar este mesmo:
        $ sudo systemctl stop mongod
    Começar serviço, após a a paragem deste:
        $ sudo systemctl start mongod
    Reinicializar:
        $ sudo systemctl restart mongod

## Databases and Collections

Fonte: https://www.tutorialspoint.com/mongodb/index.htm (Basicamente Todos os exemplos retirados daqui)

"MongoDB stores data records as documents (specifically BSON documents) which are gathered together in collections. A database stores one or more collections of documents."

### Criar Base de Dados

O commando `use DATABASE_NAME` permite a criação de uma base de Dados com o nome que pretendemos.Caso esta já exista, simplesmente é aleterada a BD em utilização para essa.

    >use mydb
    switched to db mydb

Para ver a BD em utilização, utilizar o seguinte comando:

    >db
    mydb

### Listar BD's existentes    

O comando `show dbs`, mostra todas as BD's existentes.

    >show dbs
    local     0.78125GB
    test      0.23012GB

NOTA: Caso tenhamos criado uma base de dados que não possua Dados, esta não irá aparecer!

### Eliminar BD
Utilizar o seguinte comando: 
    
    >db.dropDatabase()
Se não tivermos criado nenhuma BD ainda, este comando irá eliminar a base de dados `test`


### Criar uma Collection
O comando parar criar uma coleção tem a seguinte sintaxe `db.createCollection(name, options)`:

    >db.createCollection("mycollection")
    { "ok" : 1 }
    
    >db.createCollection("mycol", { capped : true, autoIndexID : true, size : 6142800, max : 10000 } ){
    "ok" : 0,
    "errmsg" : "BSON field 'create.autoIndexID' is an unknown field.",
    "code" : 40415,
    "codeName" : "Location40415"
    }

Onde `name` é o nome da coleção e `options` é um documento que é usado para configuração da colleção em causa:


### Listar Collections Existentes
O seguinte comando mostra todas as coleções criadas na BD em que estamos a trabalhar:

    >show collections
    mycollection
    system.indexes
### Eliminar Collection

A sintaxe do comando para eliminar uma coleção é bastante semelhante aquele que é usado pra eliminar uma BD:

```
>db.mycollection.drop()
true
```

## Documents

Como já vimos o que é um Documento em MongoDB, vamos agora ver todos os comandos associados a estes.

### Inserir Documento

Para inserir um documento numa coleção, podemos tanto recorrer ao comando **insert()**, como **save()**. "If you don't specify **_id** in the document then **save()** method will work same as **insert()** method. If you specify _id then it will replace whole data of document containing _id as specified in save() method."  A sintaxe do comando insert é a seguinte : **>db.COLLECTION_NAME.insert(document)**

```
> db.users.insert({
... _id : ObjectId("507f191e810c19729de860ea"),
... title: "MongoDB Overview",
... description: "MongoDB is no sql database",
... by: "tutorials point",
... url: "http://www.tutorialspoint.com",
... tags: ['mongodb', 'database', 'NoSQL'],
... likes: 100
... })
WriteResult({ "nInserted" : 1 })
>
```

 Podemos também inserir vários documentos atraveś de  um array de documentos no método `insert()` :

```
> db.createCollection("post")
> db.post.insert([
	{
		title: "MongoDB Overview",
		description: "MongoDB is no SQL database",
		by: "tutorials point",
		url: "http://www.tutorialspoint.com",
		tags: ["mongodb", "database", "NoSQL"],
		likes: 100
	},
	{
	title: "NoSQL Database",
	description: "NoSQL database doesn't have tables",
	by: "tutorials point",
	url: "http://www.tutorialspoint.com",
	tags: ["mongodb", "database", "NoSQL"],
	likes: 20,
	comments: [
		{
			user:"user1",
			message: "My first comment",
			dateCreated: new Date(2013,11,10,2,35),
			like: 0
		}
	]
}
])
BulkWriteResult({
	"writeErrors" : [ ],
	"writeConcernErrors" : [ ],
	"nInserted" : 2,
	"nUpserted" : 0,
	"nMatched" : 0,
	"nModified" : 0,
	"nRemoved" : 0,
	"upserted" : [ ]
})
>
```

Para inserir exatamente um documento ou vários documentos, podemos recorrer aos comandos **insertOne()** e **insertMany()**.  Ver mais no [tutorialspoint](https://www.tutorialspoint.com/mongodb/mongodb_insert_document.htm).

###  Query Document

### Devolver todos os Documentos

Para realizar queries no Mongo temos de recorrer ao método `find()`, **db.COLLECTION_NAME.find()**. Irá fazer display dos documentos numa forma não estruturada. Mais a frente iremos ver como filt

```
> db.post.find()
{ "_id" : ObjectId("5dd4e2cc0821d3b44607534c"), "title" : "MongoDB Overview", "description" : "MongoDB is no SQL database", "by" : "tutorials point", "url" : "http://www.tutorialspoint.com", "tags" : [ "mongodb", "database", "NoSQL" ], "likes" : 100 }
{ "_id" : ObjectId("5dd4e2cc0821d3b44607534d"), "title" : "NoSQL Database", "description" : "NoSQL database doesn't have tables", "by" : "tutorials point", "url" : "http://www.tutorialspoint.com", "tags" : [ "mongodb", "database", "NoSQL" ], "likes" : 20, "comments" : [ { "user" : "user1", "message" : "My first comment", "dateCreated" : ISODate("2013-12-09T21:05:00Z"), "like" : 0 } ] }
>
```

Contudo, podemos imprimir os dados de uma forma "apresentável", através do método **pretty()**, quando associado ao comando **find()**:

```
db.post.find().pretty()
{
	"_id" : ObjectId("5dd4e2cc0821d3b44607534c"),
	"title" : "MongoDB Overview",
	"description" : "MongoDB is no SQL database",
	"by" : "tutorials point",
	"url" : "http://www.tutorialspoint.com",
	"tags" : [
		"mongodb",
		"database",
		"NoSQL"
	],
	"likes" : 100
}
{
	"_id" : ObjectId("5dd4e2cc0821d3b44607534d"),
	"title" : "NoSQL Database",
	"description" : "NoSQL database doesn't have tables",
	"by" : "tutorials point",
	"url" : "http://www.tutorialspoint.com",
	"tags" : [
		"mongodb",
		"database",
		"NoSQL"
	],
	"likes" : 20,
	"comments" : [
		{
			"user" : "user1",
			"message" : "My first comment",
			"dateCreated" : ISODate("2013-12-09T21:05:00Z"),
			"like" : 0
		}
	]
}
```

### Devolver um único Documento

Através da execução de **db.COLLECTIONNAME.findOne()**, podemos devolver apenas um único documento que corresponda aos argumentos passados no método findOne(), por exemplo, no caso a seguir, devolvemos apenas um documentos que possua um `title` igual a `"MongoDB Overview"`:

```
> db.post.findOne({title: "MongoDB Overview"})
{
	"_id" : ObjectId("5dd6542170fb13eec3963bf0"),
	"title" : "MongoDB Overview",
	"description" : "MongoDB is no SQL database",
	"by" : "tutorials point",
	"url" : "http://www.tutorialspoint.com",
	"tags" : [
		"mongodb",
		"database",
		"NoSQL"
	],
	"likes" : 100
}
```

**Clásulas de SGBD Equivalentes no MongoDB**

A tabela a seguir permite nos perceber como realizar queries de pesquisa de forma ligeiramente mais "complexa":

|        Operação        |                    Sintaxe                    |                           Exemplo                            |                         Equivalente                          |
| :--------------------: | :-------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|        Equality        |             {<key>:{$eg;<value>}}             |       db.post.find({"by":"tutorials point"}).pretty()        |                 where by = 'tutorials point'                 |
|       Less Than        |             {<key>:{$lt:<value>}}             |          db.post.find({"likes":{$lt:50}}).pretty()           |                       where likes < 50                       |
|    Less Than Equals    |            {<key>:{$lte:<value>}}             |          db.post.find({"likes":{$lte:50}}).pretty()          |                      where likes <= 50                       |
|      Greater Than      |             {<key>:{$gt:<value>}}             |          db.post.find({"likes":{$gt:50}}).pretty()           |                       where likes > 50                       |
|  Greater Than Equals   |            {<key>:{$gte:<value>}}             |          db.post.find({"likes":{$gte:50}}).pretty()          |                      where likes >= 50                       |
|       Not Equals       |             {<key>:{$ne:<value>}}             |          db.post.find({"likes":{$ne:50}}).pretty()           |                      where likes != 50                       |
|   Values in an array   | {<key>:{$in:[<value1>, <value2>,……<valueN>]}} | db.post.find({"name":{$in:["Raj", "Ram", "Raghu"]}}).pretty() | Where name matches any of the value in :["Raj", "Ram", "Raghu"] |
| Values not in an array |            {<key>:{$nin:<value>}}             |  db.post.find({"name":{$nin:["Ramu", "Raghav"]}}).pretty()   | Where name values is not in the array :["Ramu", "Raghav"] or, doesn’t exist at all |

### Atualizar Documento

Em MongoDB, os métodos **update()** e **save()**, são utilizados para  atualizar documentos numa coleção. Enquanto o primeiro atualiza os valores existentes num documento, o último( **save()**), substituti o documento existente por aquele que é passado por argumento do método.

**db.COLLECTION_NAME.update(SELECTION_CRITERIA, UPDATED_DATA)**

Considerando uma coleção `mycol` com os dados seguintes...

```
{ "_id" : ObjectId(5983548781331adf45ec5), "title":"MongoDB Overview"}
{ "_id" : ObjectId(5983548781331adf45ec6), "title":"NoSQL Overview"}
{ "_id" : ObjectId(5983548781331adf45ec7), "title":"Tutorials Point Overview"}
```

... podemos atualizar, por exemplo, o valor de  `title` :

```
>db.mycol.update({'title':'MongoDB Overview'},{$set:{'title':'New MongoDB Tutorial'}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

***db.COLLECTION_NAME.save({_id:ObjectId(),NEW_DATA})***

```
>db.mycol.save(
   {
      "_id" : ObjectId("507f191e810c19729de860ea"), 
		"title":"Tutorials Point New Topic",
      "by":"Tutorials Point"
   }
)
WriteResult({
	"nMatched" : 0,
	"nUpserted" : 1,
	"nModified" : 0,
	"_id" : ObjectId("507f191e810c19729de860ea")
})
```

Além destes dois métodos, podemos também especificar  a nossa atualização dos dados através de **findOneAndUpdate()**, **updateOne()** ou **updateMany()** :

- **db.COLLECTION_NAME.findOneAndUpdate(SELECTION_CRITERIA, UPDATED_DATA)**

  ```
  > db.empDetails.findOneAndUpdate(
  	{First_Name: 'Radhika'},
  	{ $set: { Age: '30',e_mail: 'radhika_newemail@gmail.com'}}
  )
  {
  	"_id" : ObjectId("5dd6636870fb13eec3963bf5"),
  	"First_Name" : "Radhika",
  	"Last_Name" : "Sharma",
  	"Age" : "30",
  	"e_mail" : "radhika_newemail@gmail.com",
  	"phone" : "9000012345"
  }
  ```

- **db.COLLECTION_NAME.updateOne(<filter>, <update>)**	

  ```
  > db.empDetails.updateOne(
  	{First_Name: 'Radhika'},
  	{ $set: { Age: '30',e_mail: 'radhika_newemail@gmail.com'}}
  )
  { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 0 }
  
  ```

- **db.COLLECTION_NAME.updateMany(<filter>, <update>)**

  ```
  > db.empDetails.updateMany(
  	{Age:{ $gt: "25" }},
  	{ $set: { Age: '00'}}
  )
  { "acknowledged" : true, "matchedCount" : 2, "modifiedCount" : 2 }
  ```

### Remoção de Documentos

O método **remove()** é utilizado para retirar um documento de um coleção. A execução deste apresenta a sintaxe **db.COLLECTION_NAME.remove(DELLETION_CRITTERIA)**, onde :

- **deletion criteria** − (Opcional) critério que prentendemos impor para remover um documento.
- **justOne** − (Opcional), caso o valor deste seja 1, elimina apenas 1 documento.

```
> db.mycol.remove({'title':'MongoDB Overview'})
WriteResult({"nRemoved" : 1})
```

Caso pretendamos eliminar todos os records de uma coleção, podemos proceder à execução do método remove() da seguinte  forma:

```
> db.mycol.remove({})
WriteResult({ "nRemoved" : 2 })
> db.mycol.find()
```

### Ordenação de Documentos

- Ordem ascendente, `db.COLLECTION_NAME.find().sort({KEY:1}`:

  ```
  >db.mycol.find({},{"title":1,_id:0}).sort({"title":1})
  {"title":"MongoDB Overview"}
  {"title":"Tutorials Point Overview"}
  {"title":"NoSQL Overview"}
  ```

- Ordem Descendente, `db.COLLECTION_NAME.find().sort({KEY:-1})` :

  ```
  db.mycol.find({},{"title":1,_id:0}).sort({"title":-1})
  {"title":"Tutorials Point Overview"}
  {"title":"NoSQL Overview"}
  {"title":"MongoDB Overview"}
  >
  ```

## Índices

Tal como SQL, MongoDB também suporta a existência de índices nos documentos, o que é algo que pode vir a ser ótimo pois a utilização correta destes permite uma resolução eficiente de queries, sem ter que fazer scan a todos os documentos para selecionar todos aqueles que satisfazem as condições da query em causa.

### Criação de Índices

A criação de um índice pode ser feita com o método createIndex(), cuja sintaxe é **db.COLLECTION_NAME.createIndex({KEY:1})**, onde o valor de `KEY` deverá ser:

- 1, para ordem ascendente;
- -1, para ordem descendente.

```
>db.mycol.createIndex({"title":1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
```

Podemos passar diversos campos com vista a criar múltiplos índices:

```
>db.mycol.createIndex({"title":1,"description":-1})
```

Existe uma lista de opções (opcionais), que pode ser vista com mais detalhe em https://www.tutorialspoint.com/mongodb/mongodb_indexing.htm.

### Remoção de Índices:

**Remover um Índice:**

Para remover um índice em MongoDB, podemos recorrer ao método dropIndex(), cuja sintaxe é bastante parecida ao método createIndex(), **db.COLLECTION_NAME.dropIndex({KEY:1})**:

Valor para `KEY` igual a `createIndex()`.

```
> db.mycol.dropIndex({"title":1})
{
	"ok" : 0,
	"errmsg" : "can't find index with key: { title: 1.0 }",
	"code" : 27,
	"codeName" : "IndexNotFound"
}
```

**Remover múltiplos Índices:**

```
>db.mycol.dropIndexes({"title":1,"description":-1})
{ "nIndexesWas" : 2, "ok" : 1 }
>
```

### Listar Índices:

Para retornar todos índices numa collection usar **db.COLLECTION_NAME.getIndexes()** :

```
> db.mycol.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "test.mycol"
	},
	{
		"v" : 2,
		"key" : {
			"title" : 1,
			"description" : -1
		},
		"name" : "title_1_description_-1",
		"ns" : "test.mycol"
	}
]
>
```

## Agregações

"Aggregations operations process data records and return computed  results. Aggregation operations group values from multiple documents  together, and can perform a variety of operations on the grouped data to return a single result. In SQL count(*) and with group by is an  equivalent of MongoDB aggregation."

### db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)

Considerando uma coleção onde:

```
{
   _id: ObjectId(7df78ad8902c)
   title: 'MongoDB Overview', 
   description: 'MongoDB is no sql database',
   by_user: 'tutorials point',
   url: 'http://www.tutorialspoint.com',
   tags: ['mongodb', 'database', 'NoSQL'],
   likes: 100
},
{
   _id: ObjectId(7df78ad8902d)
   title: 'NoSQL Overview', 
   description: 'No sql database is very fast',
   by_user: 'tutorials point',
   url: 'http://www.tutorialspoint.com',
   tags: ['mongodb', 'database', 'NoSQL'],
   likes: 10
},
{
   _id: ObjectId(7df78ad8902e)
   title: 'Neo4j Overview', 
   description: 'Neo4j is no sql database',
   by_user: 'Neo4j',
   url: 'http://www.neo4j.com',
   tags: ['neo4j', 'database', 'NoSQL'],
   likes: 750
},
```

Podemos realizar um agregado com o intuito de saber quantos tutoriais estão escritos por utilizador através do método aggregate():

```
> db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : 1}}}])
{ "_id" : "tutorials point", "num_tutorial" : 2 }
{ "_id" : "Neo4j", "num_tutorial" : 1 }
```

No link a seguir, encontramos uma tabela com mais informações sobre outro tipo de agregações possiveis de realizar: https://www.tutorialspoint.com/mongodb/mongodb_aggregation.htm