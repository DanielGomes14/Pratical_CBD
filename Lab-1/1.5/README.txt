# Lab1.5- Sistema de Mensagens com Redis

## Estruturas de Dados

Com vista à realização deste exercício, procedi à criação de 3 Estruturas de Dados para cada utilizador:

- ### Hash
    Esta tinha como único objetivo armazenar as informações dos utilizadores, onde a `key` possuia como "template" a seguinte 
    estrutura `UserData:<UserName>`, e os `fields` seguintes :
    - `username:<UserName>` 
    - `password:<UserPassword>`    

    Um exemplo poder ser o seguinte:
    - `UserData:daniel  username "daniel" password "aminhapassword"`
- ### Set
    Para guardar as associações entre os utilizadores, decidi utilizar um `Set` visto que permite que não siga a mesma pessoas 2 ou mais vezes ( singulararidade de elementos).
    Assim, o template para a `key`  desta Estrutura de Dados é o seguinte :   `UserSubscriptions:<Username>`

- ### List
    Tem como intuito guardar as mensagens trocadas entre utilizadores.
    A `key` para esta estrutura tem o formato : `UserMessages:<Receiver>:<Sender>`
    Assim, consigo facilmente obter para quem foi enviada a mensagem, `<Receiver>`, e quem a enviou,`<Sender>`. 

## Detalhes do Sistema a Salientar
- Username é único
- Se um utilizador enviar uma mensagem para outro que nao segue o remetente, o destinatário nao irá receber a mensagem.
- A ordem das mensagens de um utilizador são estilo FILO (First in Last Out).
- Após se abrir a caixa de mensagens recebidas e o utilizador pressionar `ENTER` para continuar para o menu principal (a interface indica isto ao utilizador), as mensagens lidas serão apagadas.
- O histórico de mensagens enviadas mantém-se sempre.

## Menu

O Menu de opções neste sistema tem a capacidade de fazer :
- Registro de contas
- Login
- Logout
- Enviar Mensagens
- Ver mensagens por ler
- Ver histórico de mensagens enviadas
- Seguir um Utilizador
- Deixar de Seguir um Utilizador
