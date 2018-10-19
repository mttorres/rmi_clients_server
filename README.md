# rmi_clients_server 
 Aplicação distribuída cliente-servidor, onde o servidor realiza operações de leituras e escritas em diferentes arquivos.

#Intruções

Read me!


A aplicação RMI é sempre composta de um cliente e um servidor , e uma "interface" RMI que deve ser implementada pelo servidor o servidor cria objetos remotos e faz referência a eles e então os armazena para os clientes invocarem métodos nesses objetos o modelo RMI fornece uma aplicação com objetos distribuídos para o programador , o mecanismo que o servidor e o servidor e o cliente usam  para se comunicarem e passarem informação entre si. Uma aplicação de objetos distribuídos tem que gerenciar as seguintes propriedades:

- Localizar os objetos remotos: o sistema deve obter as referências aos objetos remotos. Isso pode ser feito de duas formas tanto usando o "feature" do RMI de nomeação: o RMI registry, ou passando e retornando objetos remotos(talvez não seja bom por causa daquela questão de endereçamento?)

- comunicar com os objetos remotos: o programador não deve ter que lidar com a comunicação entre objetos remotos  já que essa é feita pelo sistema RMI, a comunicação ocorre como uma simples invocação de método feita pelo programador.

- carregar os bytecodes das classe para os objetos que são transferidos como argumentos ou resultados de valores

interfaces e classes: já que java rmi é um sistema individual de linguagem, a programação de aplicação distribuída em RMI é relativamente simples. Todas as interfaces e classes para o sistema RMI são definidas no java.RMI (pacote) a relação entre algumas das interfaces e das classes. A classe Remote Object implementa a interface Remota enquanto as outras classes estendem o Remote Object

 A interface Remota: a interface remota é definida estendendo a interface remota que está na java.RMI package. A interface que declara os  MÉTODOS QUE O CLIENTE PODE INVOCAR de uma máquina remota virtual.

 A RemoteObject class: as funções RMI server são fornecidas pela classe RemoteObject e suas subclasses RemoteServer, Activable e UniCastRemoteObject cada uma dessas fazem respectivamente: prove implementações dos métodos toString, equals, hashcode e outros na java.lang.Object class criam e exploram objetos remotos para os cliente.



 A classe remoteException é uma super classe de exceções que o sistema RMI dispara durante a invocação de um método remoto.
 Todos os os métodos remotos que são declarados em uma interface remota deve especificar uma RemoteException(ou uma de suas superclasses) na sua cláusula throws para garantir a “segurança” das aplicações no sistema RMI. Quando a invocação remota de um método falha a exceção RemoteException é dispersada... ERROS DE COMUNICAÇÃO, PROTOCOLOS, E FALHAS DURANTE a "ordenação/acompanhamento" de parâmetros ou valores de retorno são algumas razões para uma falha RMI, uma exceção desse tipo deve ser tratada por quem chama o método remoto. 


- passo 1: definir uma interface remota

- passo 2: implementar o servidor

- passo 3: implementar o cliente

- passo 4: compilar os arquivos fonte

javac -d destDir Hello.java Server.java Client.java

destDir: Diretório destinação para por os arquivos .class

- passo 5: Iniciar o JAVA RMI registry, server , e cliente

iniciando o JAVA RMI registry: 
For example, on the Solaris Operating System:

rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false &

Or, on Windows platforms:

start rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false


- Iniciando o Servidor:

rode o arquivo class do servidor :

no solaris:

java -classpath classDir -Djava.rmi.server.codebase=file:classDir/ packageName.className &


no windows :


start java -classpath classDir -Djava.rmi.server.codebase=file:classDir/ packageName.className


classDir : diretorio que contem o .class do servidor

se tudo deu certo você deve receber como output : Servidor pronto!



Iniciando o Cliente:

NOTA: Execute o servidor antes!

faça o mesmo processo feito com o servidor com o cliente.


se tudo deu certo você deve receber como output: Uma resposta =)
