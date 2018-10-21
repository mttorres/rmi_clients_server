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
	static int filaA, filaB, filaC;
	static int leituraA, leituraB, leituraC;

	public ControleServidor() {
		this.filaA = this.filaB = this.filaC = 0;
		this.leituraA = this.leituraB = this.leituraC = 0;
	}
	
	public static void main(String args[]) {
		try
		{
			ControleServidor obj = new ControleServidor();
			ControleInterface stub = (ControleInterface) UnicastRemoteObject.exportObject(obj, 0); 
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Controle", stub);
			System.out.println("Servidor pronto!");
		}catch(Exception e)
		{
			System.err.println("Capturando exceção no Servidor: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void readFile(int file, int cliente) throws RemoteException{
//foobar
	}
	
	public void writeFile(int file, int cliente) throws RemoteException{
//foobar
	}
	//As funcoes abaixo verificam se os clientes pegam o mesmo valor da fila 

	public void updateFila(int arquivo, int leitura){
		if(leitura  == 0){
			if(arquivo == 0) filaA++;
			if(arquivo == 1) filaB++;
			if(arquivo == 2) filaC++;
		}
		else{
			if(arquivo == 0) leituraA++;
			if(arquivo == 1) leituraB++;
			if(arquivo == 2) leituraC++;
		}
		System.out.println("Terminou de atualizar as filas\n");
	}

	public void getState(){
		System.out.println("Filas do servidor");
		System.out.println("Estado das filas: \nA = " + filaA + "\nB = " + filaB + "\nC = " + filaC);
		System.out.println("Estado das filas de leitura: \nA = " + leituraA + "\nB = " + leituraB + "\nC = " + leituraC);
		System.out.println("");
	}

}