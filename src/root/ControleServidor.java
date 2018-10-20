package root;
import java.io.Console;
import java.util.Scanner;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControleServidor implements ControleInterface {
	
	public ControleServidor() {}
	
	public static void main(String args[]) {
		try
		{
			ControleServidor obj = new ControleServidor();
			ControleInterface stub = (ControleInterface) UnicastRemoteObject.exportObject(obj, 0); 
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Controle", stub);
			System.out.println("Servidor pronto!");
			Scanner input = new Scanner(System.in);
			String pausa = input.next();
		}catch(Exception e)
		{
			System.err.println("Capturando exceção no Servidor: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void readFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("readFile Servidor");
	}
	
	public void writeFile(char file) throws java.rmi.RemoteException, NotBoundException{
		System.out.println("writeFile Servidor");
	}
}
