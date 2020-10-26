# Lab 1 CBD
# Bases de Dados Chave-Valor

## Configurações do Redis Server
### Inicializar o Redis Server
    
    $ redis-server

### Utilizar Consola do Redis(outro terminal)
    $ redis-cli

## Estruturas e tipos de Dados no Redis
Fonte:http://intro2libsys.info/introduction-to-redis/redis-data-types (Todos os exemplos foram retirados deste website)

## Keys
"Redis keys are binary safe -any binary stream can be used as a key- although the most common (and recommended) stream to use is a string key, like "Person", other file formats and binary streams like images, mp3, or other file formats, can be used. "

    Criar uma key
    127.0.0.1:6379> SET testkey hello
    OK

    Verificar se Existe
    127.0.0.1:6379> EXISTS testkey
    (integer) 1

    Verificar Tipo
    127.0.0.1:6379> TYPE testkey
    string

    Retornar um conjunto de keys segundo um padrão
    127.0.0.1:6379> KEYS *
    1) "key1"

    Eliminar uma key
    127.0.0.1:6379> DEL key1
    (integer) 1

    Iteração baseada em cursor(Começa quando o cursor está a 0 e acaba quando estiver com este valor)    
    127.0.0.1:6379> SCAN 0
    1) "0"
    2) 1) "testkey"
    127.0.0.1:6379> SCAN 0 MATCH "hell*"
    1) "0"
    2) (empty list or set)

## String
   " In Redis, a string is not merely alphanumeric characters as strings are normally understood to be"(...) "Integers are stored in Redis as a string."
    

    Já referido no Ponto Anterior(Keys)
    127.0.0.1:6379> SET Book:1 "Infinite Jest"
    OK

    Ir buscar o valor associado a um set
    127.0.0.1:6379> GET Book:1
    "Infinite Jest"

    Tambem definimos como valor da key uma string, contudo apenas numérica
    127.0.0.1:6379> SET Book:1:ReadAction 1
    OK

    Incrementar por 1  valor  associado a uma key
    127.0.0.1:6379> INCR Book:1:ReadAction
    (integer) 2

    Incrementar por um valor específico
    127.0.0.1:6379> INCRBY Book:1:ReadAction 20
    (integer) 22

    Decrementar o valor associado a uma key
    127.0.0.1:6379> DECR Book:1:ReadAction
    (integer) 21

    Decrementar por um valor específico
    127.0.0.1:6379> DECRBY Book:1:ReadAction 5
    (integer) 16

 Nota: `INCR`, `INCRBY`, `DECR`, and `DECRBY` são atómicos de forma a que outros clientes não possam alterar o valor enquanto o comando é executado.


## List
"Lists in Redis are ordered collections of Redis strings that allows for duplicates values. Because Redis lists are implemented as linked-lists, adding an item to the front of the list with  LPUSH or to the end of the list with RPUSH are relatively inexpensive operations that are performed in constant time of O(1)."                                                                 
Basicamente `LPUSH` permite nos utilizar a lista como First In Last Out e `RPUSH` como First in First Out, contudo podemos usar simulteamente qualquer um dos dois comandos para introduzir na lista, pois como já foi dito listas no redis funcionam como Linked Lists.


    127.0.0.1:6379> LPUSH Book:1:comment "This was a fun read"
    (integer) 1
    127.0.0.1:6379> LPUSH Book:1:comment "Way too long!"
    (integer) 2
    127.0.0.1:6379> RPUSH Book:1:comment "Tennis anyone?"
    (integer) 3
    Devolver Lista segundo um intervalo (neste caso toda a lista) 
    127.0.0.1:6379> LRANGE Book:1:comment 0 -1
    1) "Way too long!"
    2) "This was a fun read"
    3) "Tennis anyone?"

    Eliminar o elemento na "cabeça" da lista
    127.0.0.1:6379> LPOP Book:1:comment
    "Way too long!"

    Eliminar o elemento na "cauda" da lista
    127.0.0.1:6379> RPOP Book:1:comment
    "Tennis anyone?"

    Verificar conteudo da lista outra vez
    127.0.0.1:6379> LRANGE Book:1:comment 0 -1
    1) "This was a fun read"

    Podemos tambem adicionar multiplos elementos a uma lista de uma só vez
    127.0.0.1:6379> RPUSH Organization:1:members Person:1 Person:2 Person:3 Person:4
    (integer) 4
    
    Basicamente permite-nos 'cortar' uma lista segundo um determinado intervalo
    127.0.0.1:6379> LTRIM Organization:1:members 0 2
    OK
   
    Similar a RPOP mas bloqueia o envio de uma lista vazia para o cliente.
    Devolve a key da lista e um elemento.
    127.0.0.1:6379> BRPOP Organization:1:members 5
    1) "Organization:1:members"
    2) "Person:3"

## Hash

É uma estrutura de dados que mapeia um ou mais campos aos correspondentes pares valores. Basicamente é um dicionário de campos e valores.
    
    Definir um único campo
    127.0.0.1:6379> HSET Book:3 name "Cats Cradle"
    (integer) 1
    
    Ir buscar o valor de um campo
    127.0.0.1:6379> HGET Book:3 name
    "Cats Cradle"

    Definir múltiplos campos
    127.0.0.1:6379> HMSET Book:4 name "Slaughterhouse-Five" author "Kurt Vonnegut" copyrightYear 1969 ISBN 29960763
    OK

    Devolver valor de múltiplos campos
    127.0.0.1:6379> HMGET Book:4 author ISBN
    1) "Kurt Vonnegut"
    2) "29960763"

    Devolver todos os valores de todos os campos
    127.0.0.1:6379> HGETALL Book:4
    1) "name"
    2) "Slaughterhouse-Five"
    3) "author"
    4) "Kurt Vonnegut"
    5) "copyrightYear"
    6) "1969"
    7) "ISBN"
    8) "29960763"

    Verificar se um campo de um hash existe (devolve valor booleano)
    127.0.0.1:6379> HEXISTS Book:4 copyrightYear
    (integer) 1
    127.0.0.1:6379> HEXISTS Book:4 barcode
    (integer) 0

    Eliminar um campo de um hash 
    127.0.0.1:6379> HDEL Book:4 copyrightYear
    (integer) 1

    Incrementar o valor de um hash (caso este nao seja tipo string)
    127.0.0.1:6379> HINCRBY Book:4 copyrightYear 1



## Set
Coleção de valores de string onde a singularidade de cada membro é assegurada, contudo importante salientar que não é oferecida ordenação  dos membros."Redis sets also implement union, intersection, and difference set semantics along with the ability to store the results of those set operations as a new Redis. "

    Já visto na secção das Strings
    127.0.0.1:6379> SET Organization:5 "Beatles"
    Adicionar membros ao Set
    127.0.0.1:6379> SADD Organization:5:member Paul John George Ringo
    (integer) 4

    Listar os membros de um Set em específicos
    127.0.0.1:6379> SMEMBERS Organization:5:member
    1) "Ringo"
    2) "John"
    3) "Paul"
    4) "George"

    Verificar se um membro de um set especifico existe (valor booleano )
    127.0.0.1:6379> SISMEMBER Organization:5:member "John"
    (integer) 1

    Devolver número de elementos num set
    127.0.0.1:6379> SCARD Organization:5:member
    (integer) 4


    Adicionar apenas um membro a um set
    127.0.0.1:6379> SET Organization:6 "Wings"
    OK
    127.0.0.1:6379> SET Organization:7 "Traveling Wilburys"
    OK

    Já visto (apenas pra ajudar nos próximos exemplos)
    127.0.0.1:6379> SADD Organization:6:member Paul Linda Denny
    (integer) 3
    127.0.0.1:6379> SADD Organization:7:member Bob George Jeff Roy Tom
    (integer) 5


    União entre 2 sets
    127.0.0.1:6379> SUNION Organization:5:member Organization:6:member
    1) "Ringo"
    2) "John"
    3) "Paul"
    4) "George"
    5) "Denny"
    6) "Linda"
  
    Intersetar sets
    127.0.0.1:6379> SINTER Organization:5:member Organization:6:member
    1) "Paul"

    Caso a interseção devolva um set vazio
    127.0.0.1:6379> SINTER Organization:6:member Organization:7:member
    (empty list or set)

    Substrair Sets (Elementos do primeiro set, que não existem naqueles a qual compararmos)
    127.0.0.1:6379> SDIFF Organization:5:member Organization:6:member
    1) "John"
    2) "Ringo"
    3) "George"


## Sorted Set
"A sorted set combines characteristics of both Redis lists and sets. Like a Redis list, a sorted set's values are ordered and like a set, each value is assured to be unique." Basicamente é um set, contudo sempre ordenado segundo um `SCORE`, fornecendo nos propriedades extremamente importantes. Caso, o `SCORE`seja igual para dois membros, o critério de ordenação é a lexicografia.

        Adicionar a um sorted Set: chave seguida do Score e por fim o membro 
        127.0.0.1:6379> ZADD copyrightYear 1969 Book:4
        (integer) 1
        127.0.0.1:6379> ZADD copyrightYear 1996 Book:1 2014 Book:2 1963 Book:3
        (integer) 3

        Listar membros pela ordem na qual estão guardados no SortedSet, segundo um intervalo
        127.0.0.1:6379> ZRANGE copyrightYear 0 -1
        1) "Book:3"
        2) "Book:4"
        3) "Book:1"
        4) "Book:2"

        Mesmo que ZRANGE contudo devolvemos também o Score de cada membro
        127.0.0.1:6379> ZRANGE copyrightYear 0 -1 WITHSCORES
        1) "Book:3"
        2) "1963"
        3) "Book:4"
        4) "1969"
        5) "Book:1"
        6) "1996"
        7) "Book:2"
        8) "2014"

        Listar membros pela ordem inversa na qual estão guardados no SortedSet,segundo um intervalo
        127.0.0.1:6379> ZREVRANGE copyrightYear 0 -1
        1) "Book:2"
        2) "Book:1"
        3) "Book:4"
        4) "Book:3"

        Mesmo que ZREVRANGE mas devolvemos também o Score de cada membro
        127.0.0.1:6379> ZREVRANGE copyrightYear 0 -1 WITHSCORES
        1) "Book:2"
        2) "2014"
        3) "Book:1"
        4) "1996"
        5) "Book:4"
        6) "1969"
        7) "Book:3"
        8) "1963"
    
        Rank(Index) de um membro em específico de um SortedSet
        127.0.0.1:6379> ZRANK copyrightYear Book:1
        (integer) 2

        Score de um membro 
        127.0.0.1:6379> ZSCORE copyrightYear Book:1
        "1996"

        Contar número de elementos num intervalo de Score
        127.0.0.1:6379> ZCOUNT copyrightYear 1900 1970
        (integer) 2

        Listar todos os elementos num intervalo de Score
        Valor mínimo é -inf, máximo é +inf
        127.0.0.1:6379> ZRANGEBYSCORE copyrightYear 1900 1970 
        1) "Book:3"
        3) "Book:4"
    


