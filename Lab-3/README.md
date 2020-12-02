# Lab3 - Apache Cassandra

## Introdução

O Apache Cassandra é um sistema de Base de Dados não relacional,  open source,  distribuido/descentralizado , geralmente utilizado para gerir vastas quantidades de dados estruturados espalhadas pelo mundo inteiro. Fornece . "It  provides highly available service with no single point of failure."

Listed below are some of the notable points of Apache Cassandra −

- It is scalable, fault-tolerant, and consistent.
- It is a **column-oriented database**.
- Cassandra implements a **Dynamo-style replication model** with no  single point of failure, but adds a more powerful “column family” data  model.
- **Elastic scalability** − it allows to add more hardware to accommodate more customers and more data as per requirement.
- **Always on architecture** − Cassandra has no single point of  failure and it is continuously available for business-critical  applications that cannot afford a failure.
- **Fast linear-scale performance** − Cassandra is linearly  scalable, i.e., it increases your throughput as you increase the number  of nodes in the cluster. Therefore it maintains a quick response time.
- **Flexible data storage** − Cassandra accommodates all  possible data formats including: structured, semi-structured, and  unstructured. It can dynamically accommodate changes to your data  structures according to your need.
- **Easy data distribution** − Cassandra provides the flexibility to distribute data where you need by replicating data across multiple data centers.
- **Transaction support** − Cassandra supports properties like Atomicity, Consistency, Isolation, and Durability (ACID).
- **Fast writes** − Cassandra was designed to run on cheap  commodity hardware. It performs blazingly fast writes and can store  hundreds of terabytes of data, without sacrificing the read efficiency. "

## Components of Cassandra

The key components of Cassandra are as follows −

- **Node** − It is the place where data is stored.
- **Data center** − It is a collection of related nodes.
- **Cluster** − A cluster is a component that contains one or more data centers.
- **Commit log** − The commit log is a crash-recovery mechanism in Cassandra. Every write operation is written to the commit log.
- **Mem-table** − A mem-table is a memory-resident data  structure. After commit log, the data will be written to the mem-table.  Sometimes, for a single-column family, there will be multiple  mem-tables.
- **SSTable** − It is a disk file to which the data is flushed from the mem-table when its contents reach a threshold value.
- **Bloom filter** − These are nothing but quick,  nondeterministic, algorithms for testing whether an element is a member  of a set. It is a special kind of cache. Bloom filters are accessed  after every query

## Arquitetura 

Como já foi referido o Cassandro é distríbuido, utilizando um sistema peer-to-peer em que que os dados são distribuídos por cada node pertencente a um cluster:

- Cada node é independente e ao mesmo tempo sincronizado temporalmente com os outros nodes

- Qualquer node pode aceitar qualquer pedido de leitura/escrita, independetemente dos dados estarem no próprio cluster ou não.

- Quando um node "vai abaixo",  como o sistema é **Fault Tolerant**, todos os pedidos de leitura e escrita são encaminhados para os outros nodes (visto que o Cassandra utiliza o protocolo **Gossip** em background para conseguir detetar pontos de falha num cluster)

  ![Data Replication](https://www.tutorialspoint.com/cassandra/images/data_replication.jpg)

Iniciar client shell : `cqlsh`

## Cassandra Keyspace Operations

No Cassandra, um keyspace é uma namespace que define replicação de data em nodes. Um cluster contém um keyspace por node

### Criação de um keyspace

```cassandra
CREATE KEYSPACE <identifier> WITH <properties>
```

Onde  <properties> inclui replication ( o número de réplicas)  e durable_writes.

| Nome da Estratégia                | Descrição                                                    |
| --------------------------------- | ------------------------------------------------------------ |
| **Simple Strategy**               | "Specifies a simple replication factor for the cluster".     |
| **Network Topology Strategy**     | "Using this option, you can set the replication factor for each data-center independently." |
| **Old Network Topology Strategy** | "This is a legacy replication strategy"                      |

No exemplo a seguir demonstra-se a utilização deste commando:

```cassandra
cqlsh.> CREATE KEYSPACE tutorialspoint
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};
```

Para verificar correr o commando `DESCRIBE keyspaces`:

```cassandra
cqlsh> describe keyspaces
cbd     system_auth         system_schema  system_views         
system  system_distributed  system_traces  system_virtual_schema
cqlsh> 
```

### Mudar de keyspace em Uso

Através da utilização do comando listado a seguir é possivel usar um keyspace que tenha sido já criado anteriormente.

```cassandra
cqlsh> USE cbd;
cqlsh:cbd>
```

### Alteração de Keyspace

Podemos alterar as propriedades definidas no passado para um keyspace através do comando `ALTER KEYSPACE`, cuja sintaxe é mostrada a seguir:

```cassandra
ALTER KEYSPACE <identifier> WITH <properties>
```

Alterando, por exemplo, o nome da estratégia de replicação : 

```cassandra
cqlsh:cbd_tutorial> ALTER KEYSPACE cbd WITH replication = {'class': 'NetworkTopology', 'replication_factor': 5};
```

### Eliminar um Keyspace

Para eliminar um keyspace existente utiliza-se o comando `DROP KEYSPACE`:

```cassandra
DROP KEYSPACE <identifier>
```

Exemplificando com a eliminação do keyspace criado antes:

```cassandra
cqlsh> DROP KEYSPACE cbd;
```

Recorrendo ao commando DESCRIBE, podemos verificar a concretização desta operação.

## Cassandra Table Operations

## Criação de Tabelas/ Familia de Colunas

Podemos criar uma  ColumnFamily utilizando o comando  cuja sintaxe consiste em :

```cassandra
CREATE (TABLE | COLUMNFAMILY) <tablename>
('<column-definition>' , '<column-definition>')
(WITH <option> AND <option>)
```

A definição das colunas, `<column-definition>` é feita da seguinte maneira:

```
column name1 data type,
column name2 data type,
...
```

Na definição destas colunas é sempre mandatório definirmos a nossa **Primary Key**, uma coluna que identifica unicamente uma linha. Em Cassandra, a primary key é feita de uma ou mais colunas de uma tabela. Podemos definir a chave primária da forma ilustrada a seguir:

```
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type.
   )
```

**ou** 

```
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type,
   PRIMARY KEY (column1)
   )
```

Exemplificando a criação desta, a seguir é criada uma ColumnFamily denominada `emp`, contendo em cada coluna os detalhes de um funcionário. Além disso foi criada,usando o keyspace `cbd`: 

```cassandra
cqlsh> USE cbd;
cqlsh:cbd>; CREATE TABLE emp(
   emp_id int PRIMARY KEY,
   emp_name text,
   emp_city text,
   emp_sal varint,
   emp_phone varint
   );
```

A verificação da criação da Tabela pode ser feita recorrendo ao commando **SELECT**:

```cassandra
cqlsh:tutorialspoint> select * from emp;

 emp_id | emp_city | emp_name | emp_phone | emp_sal
--------+----------+----------+-----------+---------

(0 rows)
```

Como a tabela ainda não foi populada, o número de "rows" é 0.

### Alteração de Tabelas/ColumnFamilies

Também é possível altera a estrutura de uma tabela para efetuar as seguintes operações:

- Adicionar uma coluna
- Eliminar uma coluna

A sintaxe do comando para tal é  : 

```
ALTER (TABLE | COLUMNFAMILY) <tablename> <instruction>
```

Quando pretendemos adicionar colunas, é preciso ter cuidados como o nome da coluna não pode estar em conflito com outras que possuam o mesmo nome, e que a tabela não esteja definida com "compact storage option". Para adicionar uma coluna podemos seguir o seguinte exemplo:

```cassandra
cqlsh:cbd> ALTER TABLE emp ADD emp_email text;
```

Mais uma vez a verificação das alterações à tabela pode ser efetuada recorrendo ao comando SELECT:

```cassandra
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_email | emp_name | emp_phone | emp_sal
--------+----------+-----------+----------+-----------+---------
```

Da mesma forma, podemos também eliminar uma coluna através do comando **ALTER**

```
cqlsh:cbd> ALTER TABLE emp DROP emp_email;
```

Para verificar a "deletion" de uma coluna:

```
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_name | emp_phone | emp_sal
--------+----------+----------+-----------+---------
(0 rows)
```

### Eliminar uma Tabela/ColumnFamily

Recorrendo ao comando **DROP**, eliminamos uma tabela/ColumnFamily que tenha sido criada:

```
DROP TABLE <tablename>
```

Eliminando a tabela/ColumnFamily criada anteriormenta denominada `emp`:

```
cqlsh:cbd> DROP TABLE emp;
```

Caso eliminemos uma tabela/ColumnFamily, esta não irá aparecer na lista de column families, pelo que podemos verificar o sucesso do comando anterior atraveś do uso do comando DESCRIBE:

```
cqlsh:cbd> DESCRIBE COLUMNFAMILIES;

```

### "Truncação" de uma Tabela

"You can truncate a table using the **TRUNCATE** command. When you  truncate a table, all the rows of the table are deleted permanently.  Given below is the syntax of this command."

```
TRUNCATE <tablename>
```

Assumindo a existência de uma tabela **student**, quando executamos o comando anterior, todas as rows da tabela são eliminadas de forma permanente:

```
cqlsh:cbd> TRUNCATE student;
cqlsh:cbd> select * from student;

 s_id | s_aggregate | s_branch | s_name
------+-------------+----------+--------

(0 rows)
```

### Criação de Índices

Podemos criar um índice em Cassandra usando o comando **CREATE INDEX.** A sintaxe é mostrada a seguir:

```
CREATE INDEX <identifier> ON <tablename>
```

Exemplificando a execução do comando na tabela `emp`:

```
cqlsh:cbd> CREATE INDEX name ON emp (emp_name);
```

### Eliminação de Índices

De forma semelhantes à criação de índices, podemos fazer drop de índice usando comando  **DROP INDEX**. A sua sintaxe é a seguinte:

```
DROP INDEX <identifier>
```

Exemplificando, com a eliminação do índice criado antes:

```
cqlsh:cbd> drop index name;
```

## Cassandra CURD Operations

### Criação de Dados

Utilizando o comando INSERT, podemos introduzir dados numa row de uma tabela previamente criada. A sintaxe de tal é mostrada a seguir:

```
INSERT INTO <tablename>
(<column1 name>, <column2 name>....)
VALUES (<value1>, <value2>....)
USING <option>
```

Inserindo 3 funcionários na tabela `emp` e verificando a sua inserção com o comando SELECT, são produzidos os seguintes resultados:

```cassandra
cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
   emp_phone, emp_sal) VALUES(1,'ram', 'Hyderabad', 9848022338, 50000);

cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
   emp_phone, emp_sal) VALUES(2,'robin', 'Hyderabad', 9848022339, 40000);

cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
   emp_phone, emp_sal) VALUES(3,'rahman', 'Chennai', 9848022330, 45000);
   
cqlsh:cbd> SELECT * FROM emp;

 emp_id |  emp_city | emp_name |  emp_phone | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 | 50000
      2 | Hyderabad |    robin | 9848022339 | 40000
      3 |   Chennai |   rahman | 9848022330 | 45000
 
(3 rows)
```

### Atualização de Dados

Com o comando UPDATE, é possivel atualizar dados, numa tabela:

```
UPDATE <tablename>
SET <column name> = <new value>
<column name> = <value>....
WHERE <condition>
```

Importante referir o significado da algumas keywords neste comando:

- **Where** − Cláusula usado para selecionar a row a ser atualizada.
- **Set** − Introduzir o novo valor
- **Must** − Inclui todas as colunas que compõe a Primary key

Atualizando o valor de `emp_city` para o funcionário com o `emp_id` igual a 2, na tabela `emp`:

```cassandra
cqlsh:cbd> UPDATE emp SET emp_city='Delhi',emp_sal=50000
   WHERE emp_id=2;
   
cqlsh:cbd> select * from emp;

 emp_id |  emp_city | emp_name |  emp_phone | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 | 50000
      2 |     Delhi |    robin | 9848022339 | 50000
      3 |   Chennai |   rahman | 9848022330 | 45000
      
(3 rows)
```

### Leitura de Dados

Como já foi visto, a cláusula select é utilizada para leitura de dados, contudo podemos selecionar apenas dados que pretendamos de uma tabela ( como apenas uma coluna ou uma célula particular):

```
SELECT FROM <tablename>
```

Selecionando apenas as colunas `emp_name` e `emp_sal` :

```
cqlsh:cbd> SELECT emp_name, emp_sal from emp;

 emp_name | emp_sal
----------+---------
      ram | 50000
    robin | 50000
   rahman | 45000 
	
(3 rows)
```

Através da junção com a cláusula WHERE é possivel colocar uma restrição, para a seleção de dados. Esta junção é feita da seguinte forma:

```
SELECT FROM <table name> WHERE <condition>;
```

NOTA- "A WHERE clause can be used only on the columns that are a part of primary key or have a secondary index on them".

Assim caso queiramos selecionar os funcionários cuja salário seja de 50000, necessitamos de criar um índice na coluna referente ao salário, `emp_sal`:

```
cqlsh:cbd> CREATE INDEX ON emp(emp_sal);
cqlsh:cbd> SELECT * FROM emp WHERE emp_sal=50000;

 emp_id |  emp_city | emp_name |  emp_phone | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 | 50000
      2 |     Delhi |    robin | 9848022339 | 50000
```

### Eliminação de Dados

Com recurso ao comando DELETE, podemos também eliminar dados de uma tabela:

```
DELETE FROM <identifier> WHERE <condition>;
```

Eliminando, por exemplo a coluna de salário do funcionário cujo `emp_id` seja igual a 3:

```
cqlsh:cbd> DELETE emp_sal FROM emp WHERE emp_id=3;
cqlsh:tutorialspoint> select * from emp;

 emp_id |  emp_city | emp_name |  emp_phone | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 | 50000
      2 |     Delhi |    robin | 9848022339 | 50000
      3 |   Chennai |   rahman | 9848022330 | null
(3 rows)
```

Podemos também eliminar uma row inteira:

```
cqlsh:tutorialspoint> DELETE FROM emp WHERE emp_id=3;
cqlsh:tutorialspoint> select * from emp;

 emp_id |  emp_city | emp_name |  emp_phone | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 | 50000
      2 |     Delhi |    robin | 9848022339 | 50000
 
(2 rows)
```

