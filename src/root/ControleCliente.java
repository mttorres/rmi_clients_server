package root;

import java.io.Console;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControleCliente {	

	public Registry registry;
	public ControleInterface stub;
	
	private ControleCliente() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry();
		this.stub = (ControleInterface) this.registry.lookup("Controle");
	}
	
	public static void main(String[] args) 
	{
		try {
			ControleCliente obj = new ControleCliente();
			obj.readFile('a');
			Console c = System.console();
			c.format("\nPress ENTER to proceed.\n");
	    	c.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void readFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("readFile Cliente");
		this.stub.readFile(file);
		
	}
	
	public void writeFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("writeFile Ciente");
		this.stub.writeFile(file);
	}
}
