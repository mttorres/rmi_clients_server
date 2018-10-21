package root;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControleServidor implements ControleInterface {
	
	private Semaphore semaforos[];
	
	public ControleServidor() {
		this.semaforos    = new Semaphore[Helper.NUMBER_OF_FILES];
		this.semaforos[0] = new Semaphore(semaforos.length, true);
		this.semaforos[1] = new Semaphore(semaforos.length, true);
		this.semaforos[2] = new Semaphore(semaforos.length, true);
	}
	
	public static void main(String args[]) {
			registerStub();
			System.out.println("Servidor pronto!");
	}
	
	public byte[] readFile(int file) throws RemoteException{
		Helper.acquireReadPermission(semaforos[file - 1]);
		byte[] byteArrayToReturn = IOHelper.readFile(file);
		Helper.sleepSeconds(5);
		Helper.releaseReadPermission(semaforos[file - 1]);
		return byteArrayToReturn;
	}
	
	public void writeFile(int file) throws RemoteException{
		Helper.acquireWritePermission(semaforos[file - 1]);
		// TODO: implementar a escrita aqui
		Helper.sleepSeconds(5);
		Helper.releaseWritePermission(semaforos[file - 1]);
	}
	
	private static void registerStub() {
		try {
			ControleServidor obj = new ControleServidor();
			ControleInterface stub = (ControleInterface) UnicastRemoteObject.exportObject(obj, 0); 
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Controle", stub);	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}