package src;
import java.io.Console;
import java.util.Scanner;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


// a classe servidor que nesse contexto é a classe que contem um metodo main que cria a instancia da implementação do RemoteObject
//exporta o objeto remoto e então "binds" (associa) essa instancia a um nome em um JAVA RMI Registry. 
public class AloMundoServidor implements AloMundo
{
	public AloMundoServidor(){}

	public String digaAloMundo()
	{

		System.out.println("Chamada de aplicacao Cliente recebida!");
		return "Servidor diz: Surprise Mother Fucker.... Olá Mundo...";

	}

	public static void main(String args[])
	{
		try
		{
			AloMundoServidor obj = new AloMundoServidor();
			// o servidor deve criar um objeto remoto que prove o serviço a classe cliente 
			//esse deve ser exportado  para o RMI em tempo de execução para poder receber chamadas remotas
			AloMundo stub = (AloMundo) UnicastRemoteObject.exportObject(obj,0); // exporta o objeto para a porta 0 ou porta anonima
			// como resultado ele pode começar a escutar requisições em uma nova porta socket.
			// o stub retornado para o cliente implementa o mesmo conjunto de interfaces remotas que o objeto remoto 
			// esse deve ter um nome (host) e porta por onde devem ser contactados!
			//associa o stub do objeto remoto no registro
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.bind("AloMundo",stub);
			System.out.println("Servidor pronto!");
			Scanner input = new Scanner(System.in);
			String pausa = input.next();
		}catch(Exception e)
		{
			System.err.println("Capturando exceção no Servidor: " + e.toString());
			e.printStackTrace();
		}

	}

	// a implementação da classe servidor implementa a interface remota AloMundo, fornecendo uma implementação do metodo remoto DigaAloMundo
	//nota: a classe pode definir metodos que não estejam na interface a diferença é que estes nao podem ser invocados remotamente


} 
