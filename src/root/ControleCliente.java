package root;

import java.io.Console;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControleCliente implements ControleInterface {	

	private ControleCliente() {}
	
	public static void main(String[] args) throws RemoteException, NotBoundException 
	{
		ControleCliente obj = new ControleCliente();
		obj.readFile('a');
		Console c = System.console();
		c.format("\nPress ENTER to proceed.\n");
    	c.readLine();
	}
	
	public void readFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("readFile Cliente");
		Registry registry = LocateRegistry.getRegistry();
		ControleInterface stub = (ControleInterface) registry.lookup("Controle");
		stub.readFile('a');	
	}
	
	public void writeFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("writeFile Ciente");
	}
}
