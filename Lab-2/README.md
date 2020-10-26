# Lab 1 CBD
# Bases de Dados de Documentos - MongoDB

## Instalação

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

