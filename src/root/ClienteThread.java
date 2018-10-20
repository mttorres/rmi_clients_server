package root;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClienteThread extends Thread {
	
	public ControleCliente cliente;
	
	public ClienteThread(int numero_cliente) throws RemoteException, NotBoundException {
		this.cliente = new ControleCliente(numero_cliente);
	}
	
	public void run() {
		System.out.println("Thread " + cliente.numeroCliente + " runnning");
	}
}
