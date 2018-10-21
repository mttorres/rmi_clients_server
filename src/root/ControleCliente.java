package root;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControleCliente {	

	public Registry registry;
	public ControleInterface stub;
	public int numeroCliente;
	
	// "RA" -> Read File  'A'
	// "WC" -> Write File 'C'
	public static String[][] tarefas = {
			{"W1", "W1", "W1", "W1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 1
			{"R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 2
			{"R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 3
	}; 
	
	public ControleCliente(int numeroCliente) throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry();
		this.stub = (ControleInterface) this.registry.lookup("Controle");
		this.numeroCliente = numeroCliente;
	}
	
	public static void main(String[] args) 
	{
		try {
			ClienteThread cliente1 = new ClienteThread(1);
			ClienteThread cliente2 = new ClienteThread(2);
			ClienteThread cliente3 = new ClienteThread(3);
			
			cliente1.start();
			cliente2.start();
			cliente3.start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void readFile(char file) throws java.rmi.RemoteException {
		int fileInt = Integer.parseUnsignedInt(String.valueOf(file));
		byte[] inputByteArray = this.stub.readFile(fileInt);
		String teste = new String(inputByteArray);
		System.out.println(teste);
	}
	
	public void writeFile(char file) throws java.rmi.RemoteException {
		int fileInt = Integer.parseUnsignedInt(String.valueOf(file));
		this.stub.writeFile(fileInt);
	}
}
