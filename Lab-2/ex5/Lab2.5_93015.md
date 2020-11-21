# Lab 2.5  - Bases de Dados com Temática Livre

## Dataset Utilizado

Para realizar este exercicio utilizei  a base de dados presente no ficheiro `sales.bson` no seguinte repositório  https://github.com/huynhsamha/quick-mongo-atlas-datasets/tree/master/dump/sample_supplies .  Como este ficheiro vem em formato bson, para importar utilizei o comando :

`mongorestore  -d cbd -c sales sales.bson` 

Importante referir que este dateset está presente nos datasets pré-definidos do  Mongo Atlas. 

## Queries Realizadas 

**Encontrar os emails dos vendedores com  na qual a loja esteja localizada em "Denver"** 

db.sales.find({"storeLocation": "Denver"}, {"customer.email":1, "_id": 0 })

**encontrar 5 vendas na qual todos os items comprados nao possuam qualquer item com a tag "school" e na qual a venda foi feita fisicamente em loja ("In store")**

```
db.sales.find(   { "items.tags" : {$nin: ["school"] }}   ).limit(5)
```

**encontrar as vendas  feitas online por vendedores do sexo feminino e cuja idade seja inferior a 25 anos**

```
db.sales.find({$and :[{"customer.gender":  {"$eq" : "F"}  }, {"customer.age": {"$lt" : 25 }} , {"purchaseMethod" : {$eq: "Online" }} ]})
```

**numero de vendas feitas utilizando um cupão de desconto, e que incluam pelo menos um item do tags do tipo "eletronics", "general" e "office"** 

```
db.sales.find({$and: [  {"couponUsed" : {$eq :  true } },  {"items.tags": { $all :  ["school", "electronics" , "general"  ] }  }  ]  } ).count()
```



**vendas que incluam pelo menos um item de preço superior a 50.0 ( NumberDecimal("50.0"))** 

```
db.sales.find( {$and : [   {"items.price" : {"$gt" : NumberDecimal("50.0") }}  ]  })
```

**vendas feitas por vendedores tenham um nivel de satisfazeção inferior a dois ou superior a 4**

```
db.sales.find( {$or : [   {"customer.satisfaction"  : {"$gt" : 4 }}, {"customer.satisfaction" : { "$lt" : 2}  }  ]  })
```

**vendas online feitas exatamente no momento ISODate("2017-12-29T22:39:41.487Z")**

```
db.sales.find(  {"saleDate" : { "$eq" : ISODate("2017-12-29T22:39:41.487Z" ) }  })
```

**ordenar as vendas pelo email do vendedores decrescentemente**

```
db.sales.find().sort({"customer.email" : -1})
```

**encontrar as 10 vendas que possuiram o maior numero de items incluidos, e onde a loja esteja localizada em "New York":**

```
db.sales.aggregate( [  { $unwind: "$items" }, {$match : {"storeLocation": "New York" }}, { $group: {  _id: '$_id', sum: { $sum: '$items.quantity' } } } , {$limit : 10 }, {$sort: {"sum" : -1}  } ] );
```


**contar o numero de vendas efetuadas por cidade e ordenar decrescentemente**

```
db.sales.aggregate([ {$group : {_id : "$storeLocation", total_sales: {$sum:1}  } }, {$sort : {"total_sales" : -1}} ])
```

**vendas cuja média da quantidade de items  pela qual esta  é constituida  é superior a 9 ( por exemplo se uma venda for constituida por 2 items na qual um foi comprado na quantidade de 6 e outro na quantidade de 3 , a média seria 4.5 )** 

```
db.sales.aggregate([ {$project : {average_items : {$avg:"$items.quantity"}  } },{$match:{average_items:{$gt:9}}} ])
```

**o método de pagamento(online, fisicamente na loja, ou por telemovel ) por onde se  realizou mais vendas**

```
db.sales.aggregate(  [ {$group : {_id: "$purchaseMethod"  ,total_sales : {$sum : 1}}     }, {$sort:{total_sales:-1}},{$limit : 1} ])
```

**os nomes dos items com mais de 4000 presenças em  todas as vendas.**

```
db.sales.aggregate([ {$unwind:"$items"}, {$group:{"_id":"$items.name", "count":{"$sum":1}}}, {$sort:{count:-1}},{$match : {count: {$gt:4000}}} ])
```

**o email dos vendedores que obtiveram a média   de "satisfaction" mais baixa**

```
db.sales.aggregate([ {$group : {_id: "$customer.email" ,average_satisfact : {$avg:"$customer.satisfaction"}  } }, {$sort:{average_satisfact:1}} ])
```

**obter as vendas localizadas em "London" que obtiveram um total de valor  (sem considerar a quantidade de items em cada venda e sem utilizar o field "total_score" ) inferior a 30  numa venda** 

```
db.rest.aggregate([{$addFields : {total_value : {$sum : "$items.price"}}},{$match : {total_value: {$lt: 30}, storeLocation: {$eq:"London"} }}])
```

**obter o valor total para cada uma das vendas**

```
db.sales.aggregate([  {$unwind: "$items"}, { "$project": { "prices": { "$multiply": ["$items.price" ,"$items.quantity" ]},  "tempId": "$_id"} }, { "$group": { "_id": "$tempId", "totalValue": { "$sum": "$prices" } }  }   ])
```

