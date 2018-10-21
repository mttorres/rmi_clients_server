package root;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControleServidor implements ControleInterface {
	
	private Queue <Integer> fila = new LinkedList <Integer>();

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
		
		//TODO: implementar fila aqui
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile(int file, int cliente) throws RemoteException{
		System.out.println("writeFile " + file + " Servidor, topo da fila = " + this.fila.peek());
		file -= 1;
		
		this.fila.add(cliente);

		// TODO: Trocar isso por um .wait() correto ou um callback 
		while(true) {
			if (this.fila.peek() != null) {
				if (this.fila.peek() == cliente) {
					break;
				}else {
					// Explicação da gambiarra:
					// antes de tudo, me desculpem por fazer isso e.e
					// se não utilizasse as variáveis/não fizesse nada dentro do while a thread para de atualizar o valor, sempre dando falso no if anterior
					String zuado_mas_funciona = "topo = " + this.fila.peek() + " cliente = " + cliente;
				}
			}
		}
			
		System.out.println("Executando " + cliente);
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.fila.poll();
	}
}