package root;

import java.util.concurrent.Semaphore;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//import java.util.LinkedList;
//import java.util.Queue;

public class ControleServidor implements ControleInterface {
	
	private Semaphore semaforos[];
	private int filas[];
	private int leituras[];
	/*static int filaA, filaB, filaC;
	static int leituraA, leituraB, leituraC;
	*/

	public ControleServidor() {
		this.semaforos    = new Semaphore[Helper.NUMBER_OF_FILES];
		this.semaforos[0] = new Semaphore(semaforos.length, true);
		this.semaforos[1] = new Semaphore(semaforos.length, true);
		this.semaforos[2] = new Semaphore(semaforos.length, true);
		this.leituras = new int[3];
		this.filas = new int[3];
		/*
		this.filaA = this.filaB = this.filaC = 0;
		this.leituraA = this.leituraB = this.leituraC = 0;
		*/
	}
	
	public static void main(String args[]) {
		registerStub();
		System.out.println("Servidor pronto!");
	}
	
	public byte[] readFile(int file) throws RemoteException{
		
		//entraFila(file,true,0);
		this.filas[file-1] += 1;
		this.leituras[file-1] += 1;
		Helper.acquireReadPermission(semaforos[file - 1]);
		byte[] byteArrayToReturn = IOHelper.readFile(file);
		Helper.sleepSeconds(5);
		//saiFila(file,true);
		Helper.releaseReadPermission(semaforos[file - 1]);
		this.filas[file-1] -= 1;
		this.leituras[file-1] -= 1;
		notifyAll();
		return byteArrayToReturn;
	}
	
	public void writeFile(int file, String text) throws RemoteException, InterruptedException{
		
		//int filaleitura = 0;
		//entraFila(file,false,filaleitura);
		this.filas[file-1] += 1;
		while(this.leituras[file-1] > 0 )
		{
			System.out.println("Debug: bloqueado por leitura ao arquivo estar em curso");
			wait();
			System.out.println("Debug: desbloqueado");
		}
		//Helper.acquireWritePermission(semaforos[file - 1],filaleitura);
		Helper.acquireWritePermission(semaforos[file - 1]);
		IOHelper.writeFile(file, text);
		Helper.sleepSeconds(5);
		
		Helper.releaseWritePermission(semaforos[file - 1]);
		this.filas[file-1] -= 1;
		
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
	/*
	public void getState(){
		System.out.println("Filas do servidor");
		System.out.println("Estado das filas: \nA = " + filaA + "\nB = " + filaB + "\nC = " + filaC);
		System.out.println("Estado das filas de leitura: \nA = " + leituraA + "\nB = " + leituraB + "\nC = " + leituraC);
		System.out.println("");
	}

	// atualiza as variaveis de fila  e fornece se o arquivo atual tem leituras em curso usando a variavel auxiliar filaleitura
	public void entraFila(int fnum, boolean leitura,int filaleitura)
	{
		switch (fnum) {
			case 1:
				this.filaA++;
				if(leitura)
				{
					this.leituraA++;
				}
				else
				{
					filaleitura = this.leituraA;
				}
				break;
			case 2:
				this.filaB++;
				if(leitura)
				{
					this.leituraB++;
				}
				else
				{
					filaleitura = this.leituraB;
				}
				break;
			case 3:
				this.filaC++;
				if(leitura)
				{
					this.leituraC++;
				}
				else
				{
					filaleitura = this.leituraC;
				}
				break;

			
		}


		
	}

	// apenas atualiza as variaveis de fila
	public void saiFila(int fnum, boolean leitura)
	{
		switch (fnum) {
			case 1:
				this.filaA--;
				if(leitura)
				{
					this.leituraA--;
				}
				break;
			case 2:
				this.filaB--;
				if(leitura)
				{
					this.leituraB--;
				}
				break;
			case 3:
				this.filaC--;
				if(leitura)
				{
					this.leituraC--;
				}
				break;

			
		}
	}
	*/
}