package root;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControleServidor implements ControleInterface {

	public Semaphore semaforos[];
	
	public ControleServidor() {
		semaforos = new Semaphore[3];
		semaforos[0] = new Semaphore(1, true);
		semaforos[1] = new Semaphore(1, true);
		semaforos[2] = new Semaphore(1, true);
	}
	
	public static void main(String args[]) {
		try
		{
			ControleServidor obj = new ControleServidor();
			ControleInterface stub = (ControleInterface) UnicastRemoteObject.exportObject(obj, 0); 
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Controle", stub);
			System.out.println("Servidor pronto!");
			Scanner input = new Scanner(System.in);
			input.next();  // pausa
			input.close();
		}catch(Exception e)
		{
			System.err.println("Capturando exceção no Servidor: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void readFile(int file, int cliente) throws RemoteException{
		System.out.println("readFile " + file + " Servidor");
		file -= 1;
		
		//TODO: implementar semaforo aqui
		
		Helper.sleepSeconds(5);
	}
	
	public void writeFile(int file, int cliente) throws RemoteException{
		System.out.println("writeFile " + file + " Servidor");
		file -= 1;
		
		Helper.acquireSemaphore(semaforos[file]);
		System.out.println("Executando " + cliente);
		Helper.sleepSeconds(5);
		Helper.releaseSemaphore(semaforos[file]);
	}
}