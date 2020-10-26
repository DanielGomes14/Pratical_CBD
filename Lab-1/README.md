# Lab 1 CBD
# Bases de Dados Chave-Valor

## Configurações do Redis Server
### Inicializar o Redis Server
    
    $ redis-server

### Utilizar Consola do Redis(outro terminal)
    $ redis-cli

## Estruturas e tipos de Dados no Redis
Fonte:
- http://intro2libsys.info/introduction-to-redis/redis-data-types (Praticamente Todos os exemplos foram retirados deste website)
- https://redis.io/commands

## Keys
"Redis keys are binary safe -any binary stream can be used as a key- although the most common (and recommended) stream to use is a string key, like "Person", other file formats and binary streams like images, mp3, or other file formats, can be used. "

### Comandos Críticos para Keys
Mais a frente iremos ver como definir keys consoante os diversos data types e structures

- **EXISTS key:**
 Verificar se uma certa key existe
- **TYPE key:**
 Verificar Tipo associado a uma key
- **DEL key:**
 Eliminar uma key
- **KEYS pattern:**
 Retornar um conjunto de keys segundo um padrão (estilo regex)
- **SCAN pattern cursor:**
 Iteração baseada em cursor(Começa quando o cursor está a 0 e acaba quando estiver com este valor)    


## String
   " In Redis, a string is not merely alphanumeric characters as strings are normally understood to be"(...) "Integers are stored in Redis as a string." Ou seja, números são tratados como Strings
### SET key value [EX seconds|PX milliseconds|KEEPTTL] [NX|XX] [GET] 
Permite nos definir uma key com uma String como valor associado, a lista seguinte possui a lista de argumentos opcionais:

- EX seconds -- Set the specified expire time, in seconds.
- PX milliseconds -- Set the specified expire time, in milliseconds.
- NX -- Only set the key if it does not already exist.
- XX -- Only set the key if it already exist.
- KEEPTTL -- Retain the time to live associated with the key.
- GET -- Return the old value stored at key, or nil when key did not exist."

"Simple string reply: 
-  `OK` if`SET was executed correctly(...)
-  Null reply: a Null Bulk Reply is returned if the SET operation was not performed because the user specified the NX or XX option but the condition was not met or if user specified the NX and GET options that do not met." Retorna basicamente `(nil)`

**Exemplo**

    127.0.0.1:6379> SET Book:1 "Infinite Jest"
    OK

### GET Key
Devolve o valor associado a um Set. Caso ocorra um erro é retornado, mais uma vez, `nil`

**Exemplo**

    127.0.0.1:6379> GET Book:1
    "Infinite Jest"

### INCR key
Incrementa por 1 o valor associado a uma key. Caso não exista, é colocada a zero antes de efetuar a operação.

**Exemplo**

    Incrementar por 1  valor  associado a uma key
    127.0.0.1:6379> INCR Book:1:ReadAction
    (integer) 2

### INCRBY key integer
Incrementa por um valor específico (`integer`) o valor da key.

**Exemplo**

    127.0.0.1:6379> INCRBY Book:1:ReadAction 20
    (integer) 22


### DECR key
Decrementa por 1 o valor associado a uma key. Caso não exista a key, é colocada a zero antes de efetuar a operação.

**Exemplo**

     127.0.0.1:6379> DECR Book:1:ReadAction
    (integer) 21

### DECRBY key integer
Decrementa por um valor específico (`integer`) o valor da key.  

**Exemplo**

    127.0.0.1:6379> DECRBY Book:1:ReadAction 5
    (integer) 16



Nota: `INCR`, `INCRBY`, `DECR`, and `DECRBY` são atómicos de forma a que outros clientes não possam alterar o valor enquanto o comando é executado.
### Outros Comandos
- **MSET key value [key value ...]:**
 Define vários pares key, value (uma String)
- **MGET key value [key value ...]:**
 Devolve vários pares key, value (uma String)
- **APPEND key value:**
 Concatena a string associada a uma key com o `value`


## List
"Lists in Redis are ordered collections of Redis strings that allows for duplicates values. Because Redis lists are implemented as linked-lists, adding an item to the front of the list with  LPUSH or to the end of the list with RPUSH are relatively inexpensive operations that are performed in constant time of O(1)." Basicamente `LPUSH` permite nos utilizar a lista como First In Last Out e `RPUSH` como First in First Out, contudo podemos usar simulteamente qualquer um dos dois comandos para introduzir na lista, pois como já foi dito listas no Redis funcionam como Linked Lists.

### LPUSH key value [value ...]                                                              
Insere um ou mais `values` à cabeça da lista tal como já foi enunciado indiretamente no ponto anterior. Cada lista é identificada por uma `key`.

**Exemplo**

    127.0.0.1:6379> LPUSH Book:1:comment "This was a fun read"
    (integer) 1
    127.0.0.1:6379> LPUSH Book:1:comment "Way too long!"
    (integer) 2

### RPUSH key value [value ...]
Insere um ou mais `values` à cauda da lista tal como já foi enunciado indiretamente no ponto anterior. Cada lista é identificada por uma `key`.

**Exemplo** 

    127.0.0.1:6379> RPUSH Book:1:comment "Tennis anyone?"
    (integer) 3

### LRANGE key start stop    

Devolve uma certa Lista (representada por uma key) segundo um intervalo, onde `start` representa o índice de partida e `stop` o index onde pretendemos parar, por outra palavras estes índices de começo e fim são offsets. 
"These offsets can also be negative numbers indicating offsets starting at the end of the list. For example, -1 is the last element of the list, -2 the penultimate, and so on."

**Exemplo** 

Neste caso, devolvemos a lista toda:

    127.0.0.1:6379> LRANGE Book:1:comment 0 -1
    1) "Way too long!"
    2) "This was a fun read"
    3) "Tennis anyone?"
Outro exemplo, devolvendo o 1 elemento: 

    127.0.0.1:6379>  RPUSH mylist "one"
    (integer) 1
    127.0.0.1:6379>  RPUSH mylist "two"
    (integer) 2
    127.0.0.1:6379>  RPUSH mylist "three"
    (integer) 3
    127.0.0.1:6379>  LRANGE mylist 0 0
    1) "one"
### LPOP key
Elimina e devolve  o primeiro elemento da lista.

    127.0.0.1:6379> LPOP Book:1:comment
    "Way too long!"

### RPOP key
Eliminar o elemento no fim da lista

    127.0.0.1:6379> RPOP Book:1:comment
    "Tennis anyone?"

### Outros Comandos
- **LTRIM key start stop:**
  Permite-nos 'cortar' uma lista segundo um determinado intervalo.
- **BRPOP key [key ...] timeout:**
 Similar a `RPOP` mas bloqueia o envio de uma lista vazia para o cliente.Devolve a key da lista e um elemento.
- **LINDEX key index:**
 Devolve o elemento  de uma lista no índice `index`.


## Hash
É uma estrutura de dados que mapeia um ou mais campos aos correspondentes pares valores. Basicamente é um dicionário de campos e valores.

### HSET key field value [field value ...] 
Define um (ou mais) campo(s) `field` à qual correspondem os seus respetivos pares valores `value`. A `key` identifica a Hash. Se a key nao existir, é criada, e se já existir um campo associado a uma key é overwritten.
Valor de retorno: o número de campos adicionados

**Exemplo**

    127.0.0.1:6379> HSET Book:3 name "Cats Cradle"
    (integer) 1
    
###  HGET key field  
Devolve o valor de um campo `field` guardado numa `key` de uma Hash.

**Exemplo**

    127.0.0.1:6379> HGET Book:3 name
    "Cats Cradle"
### HMSET key field value [field value ...]
A única diferença para o comando `HSET`, é que que em vez de se retornar o número de campos adicionados, é retorna `"OK"` caso o campo seja definido com sucesso.

**Exemplo**

    127.0.0.1:6379> HMSET Book:4 name "Slaughterhouse-Five" author "Kurt Vonnegut" copyrightYear 1969 ISBN 29960763
    OK


###  HMGET key field [field ...] 
Devolve os valor de múltiplos `fields` específicos guardados numa `key`.
"For every field that does not exist in the hash, a nil value is returned. Because non-existing keys are treated as empty hashes, running HMGET against a non-existing key will return a list of nil values."

**Exemplo** 

    127.0.0.1:6379> HMGET Book:4 author ISBN
    1) "Kurt Vonnegut"
    2) "29960763"


### HEXISTS key field
Devolve um valor booleano (0 ou 1 ), consoante a existência, ou não, de um campo guardado numa key (0 caso não exista).

**Exemplo** 

    127.0.0.1:6379> HEXISTS Book:4 copyrightYear
    (integer) 1
    127.0.0.1:6379> HEXISTS Book:4 barcode
    (integer) 0


### HDEL key field [field ...]

Elimina um,ou mais, campos de um hash. Se o campo não estiver guardado dentro de uma key, o Redis simplesmente ignora.

    127.0.0.1:6379> HDEL Book:4 copyrightYear
    (integer) 1

### Outros Comandos

- **HLEN key:**
 Retorna o número de campos guardados numa key.
- **HKEYS key**
 Retorna os nomes dos campos
- **HINCRBY key field increment**
 Aumenta o valor mapeado a um `field` por `increment`, guardado numa key de uma Hash.
    


## Set
Coleção de valores de string onde a singularidade de cada membro é assegurada, contudo importante salientar que não é oferecida ordenação  dos membros."Redis sets also implement union, intersection, and difference set semantics along with the ability to store the results of those set operations as a new Redis. "

### SADD key member [member ...]
Adiciona um membro específico a uma key de um Set. Caso um membro ja exista é ignorado
 
 **Exemplo**

    127.0.0.1:6379> SADD Organization:5:member Paul John George Ringo
    (integer) 4


### SMEMBERS key
Lista os membros de um Set em específicos

 **Exemplo**

    127.0.0.1:6379> SMEMBERS Organization:5:member
    1) "Ringo"
    2) "John"
    3) "Paul"
    4) "George"

### SISMEMBER key member
Devolve um valor booleano, 1  se um `member` estiver guardado numa `key`, e 0 em caso contrário.

 **Exemplo**

    127.0.0.1:6379> SISMEMBER Organization:5:member "John"
    (integer) 1

Devolver número de elementos num set
127.0.0.1:6379> SCARD Organization:5:member
(integer) 4


### SUNION key [key ...]
Retorna  a união de dois ou mais sets, representados por uma `key`.

 **Exemplo**

    127.0.0.1:6379> SADD Organization:6:member Paul Linda Denny
    (integer) 3
    127.0.0.1:6379> SADD Organization:7:member Bob George Jeff Roy Tom
    (integer) 5
    127.0.0.1:6379> SUNION Organization:5:member Organization:6:member
    1) "Ringo"
    2) "John"
    3) "Paul"
    4) "George"
    5) "Denny"
    6) "Linda"

### SINTER key [key ...]

Retorna  a união de dois ou mais sets, representados por uma `key`.

 **Exemplo**

    127.0.0.1:6379> SINTER Organization:5:member Organization:6:member
    1) "Paul"
    127.0.0.1:6379> SINTER Organization:6:member Organization:7:member
    (empty list or set)

### SDIFF key [key ...]
Substrair Sets (Elementos do primeiro set, que não existem naqueles a qual compararmos).

 **Exemplo**

    127.0.0.1:6379> SDIFF Organization:5:member Organization:6:member
    1) "John"
    2) "Ringo"
    3) "George"



## Sorted Set
"A sorted set combines characteristics of both Redis lists and sets. Like a Redis list, a sorted set's values are ordered and like a set, each value is assured to be unique." Basicamente é um set, contudo sempre ordenado segundo um `SCORE`, fornecendo nos propriedades extremamente importantes. Caso, o `SCORE` seja igual para dois membros, o critério de ordenação é a lexicografia.

### ZADD key [NX|XX] [CH] [INCR] score member [score member ...]
Adiciona todos os membros específicos com os respetivos scores (também específicos).Se um membro já estiver guardado numa key, entao o `score` é atualizado, e colocado na posição correta consoante este.

- `XX`: Only update elements that already exist. Never add elements.
- `NX`: Don't update already existing elements. Always add new elements.
- `LT`: Only update existing elements if the new score is less than the current score. This flag doesn't prevent adding new elements.
- `GT`: Only update existing elements if the new score is greater than the current score. This flag doesn't prevent adding new elements.
- `CH`: Modify the return value from the number of new elements added, to the total number of elements changed (CH is an abbreviation of changed). Changed elements are new elements added and elements already existing for which the score was updated. So elements specified in the command line having the same score as they had in the past are not counted. Note: normally the return value of ZADD only counts the number of new elements added.
- `NCR`: When this option is specified ZADD acts like ZINCRBY. Only one score-element pair can be specified in this mode.

Note: The `GT`, `LT` and `NX` options are mutually exclusive.

**Exemplo**
    127.0.0.1:6379> ZADD copyrightYear 1996 Book:1 2014 Book:2 1963 Book:3
(   integer) 3
    127.0.0.1:6379> ZADD copyrightYear 1969 Book:4
    (integer) 1

### ZRANGE key start stop [WITHSCORES]
Lista os membros pela ordem na qual estão guardados no SortedSet, segundo um intervalo, a comecar em `start` e acabar no indice `stop`. Opcionalmente podemos também devolver o `Score` associado a cada membro através de `WITHSCORES`.

**Exemplo** 

    127.0.0.1:6379> ZRANGE copyrightYear 0 -1
    1) "Book:3"
    2) "Book:4"
    3) "Book:1"
    4) "Book:2"
    127.0.0.1:6379> ZRANGE copyrightYear 0 -1 WITHSCORES
    1) "Book:3"
    2) "1963"
    3) "Book:4"
    4) "1969"
    5) "Book:1"
    6) "1996"
    7) "Book:2"
    8) "2014"
### ZREVRANGE key start stop [WITHSCORES]
Mesmo que `ZRANGE` contudo, os membros são listados pela ordem inversa à qual são guardados. Opcionalmente podemos também devolver o `Score` associado a cada membro através de `WITHSCORES`.

**Exemplo**

    1) "Book:2"
    2) "Book:1"
    3) "Book:4"
    4) "Book:3"
    1) "Book:2"
    2) "2014"
    3) "Book:1"
    4) "1996"
    5) "Book:4"
    6) "1969"
    7) "Book:3"
    8) "1963"


### ZRANK key member
Devolve o índice (rank) de um membro em específico de um SortedSet. Os ranks são de ordem crescente.

**Exemplo**

    127.0.0.1:6379> ZRANK copyrightYear Book:1
    (integer) 2

### ZREM key member [member ...]
Remove membros em específico que estão associados a uma key. Caso não existam esses membros, o Redis ignora.

**Exemplo**

    redis>  ZADD myzset 1 "one"
    (integer) 1
    redis>  ZADD myzset 2 "two"
    (integer) 1
    redis>  ZREM myzset "two"
    (integer) 1

### ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
Lista todos os membros num intervalo de `Score` (de `min` a `max`)."The optional LIMIT argument can be used to only get a range of the matching elements (similar to SELECT LIMIT offset, count in SQL). A negative count returns all elements from the offset. Keep in mind that if offset is large, the sorted set needs to be traversed for offset elements before getting to the elements to return, which can add up to O(N) time complexity. The optional `WITHSCORES` argument makes the command return both the element and its score, instead of the element alone."
"`min` and `max` can be -inf and +inf, so that you are not required to know the highest or lowest score in the sorted set to get all elements from or up to a certain score."

**Exemplo**

    127.0.0.1:6379> ZRANGEBYSCORE copyrightYear 1900 1970 
    1) "Book:3"
    3) "Book:4"
    127.0.0.1:6379> ZRANGEBYSCORE copyrightYear -inf 2000 WITHSCORES
    1) "Book:3"
    2) "1963"
    3) "Book:4"
    4) "1969"
    5) "Book:1"
    6) "1996"
    7) "Book:5"
    8) "1996"
    9) "Book:6"
    10) "1996"


**Outros Comandos**
    - **ZCOUNT key min max:**
 Retorna o numero de elementos com um `SCORE` entre `min` e `max`
    - **ZCARD key:**
 Devolve a cardinalidade de um set, ou seja o número de elementos guardados em `key`.



    


