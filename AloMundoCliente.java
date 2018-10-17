package src;
import java.io.Console;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AloMundoCliente
{
	private AloMundoCliente() {}
	public static void main(String[] args) 
	{
		String host = (args.length < 1) ? null : args[0];
		try 
		{
			Registry registry = LocateRegistry.getRegistry();
			AloMundo stub = (AloMundo) registry.lookup("AloMundo");
			String resposta = stub.digaAloMundo();
			System.out.println("resposta: " + resposta);
			Console c = System.console();
			c.format("\nPress ENTER to proceed.\n");
        	c.readLine();

		} catch (Exception e) 
			{
				System.err.println("Capturando a exceção no Cliente: " + e.toString());
				e.printStackTrace();
			}
	}

}