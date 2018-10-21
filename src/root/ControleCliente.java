package root;

import java.util.concurrent.TimeUnit;
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
			{"W1", "W1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 1
			{"W1", "W1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 2
			{"W1", "W1", "R1", "R1", "R1", "R1", "R1", "R1", "R1", "R1"}, // tarefas do cliente 3
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
			
			//cliente2.start();
			
			//Debugging:
			//Cliente 1 inicia e atualiza a fila A
			//Cliente 1 pede o estado das filas
			//Cliente 3 atualiza a fila A
			//Cliente 1 pede o estado das filas
			cliente1.start();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Após cliente 1 atualizar");
			cliente1.cliente.stub.getState();
			
			cliente3.start();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Após cliente 1 e 3 atualizarem");
			
			cliente3.cliente.stub.getState();
			
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			cliente1.cliente.stub.getState();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public void readFile(char file) throws java.rmi.RemoteException {
		int fileInt = Integer.parseUnsignedInt(String.valueOf(file));
		this.stub.readFile(fileInt, this.numeroCliente);
	}
	
	public void writeFile(char file) throws java.rmi.RemoteException {
		int fileInt = Integer.parseUnsignedInt(String.valueOf(file));
		this.stub.writeFile(fileInt, this.numeroCliente);
	}
}
