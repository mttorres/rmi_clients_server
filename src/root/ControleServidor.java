package root;

import java.util.concurrent.Semaphore;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.Console;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ControleServidor implements ControleInterface {
	
	private int leituras[];
	private Semaphore mutex[];
	private Semaphore write[];

	public ControleServidor() {
		//Semáforo de exclusão mútua para as regiões críticas
		//de quantidade de leitores na fila.
		this.mutex = new Semaphore[Helper.NUMBER_OF_FILES];

		//Poderia ser feito com um for, mas aqui é programação arte.
		this.mutex[0] = new Semaphore(1, true);
		this.mutex[1] = new Semaphore(1, true);
		this.mutex[2] = new Semaphore(1, true);


		//Semáforo que permite escrita no arquivo.
		//Caso um leitor pegue, ele só permitirá escrita se ele for o último.
		//Também poderia ter sido feito com um for. Ou um while true com break ;).
		this.write = new Semaphore[Helper.NUMBER_OF_FILES];
		this.write[0] = new Semaphore(1, false);
		this.write[1] = new Semaphore(1, true);
		this.write[2] = new Semaphore(1, true);

		//Contador de quantos leitores há e quantos processos há na fila.
		this.leituras = new int[3];
	}
	

	//Starta o servidor
	public static void main(String args[]) {
		registerStub();
		System.out.println("Servidor pronto!");
		System.out.println("Aperte ENTER para executar uma instrução.");
	}
	
	//Método de leitura de arquivo.
	public byte[] readFile(int file) throws RemoteException, InterruptedException{
		int fileIndex = file - 1;
		
		//Entra na região crítica. Só um leitor atualiza o contador por vez para não dar merda.
		mutex[file-1].acquire(1);
		
		//Se ele é o primeiro leitor, nega o acesso do arquivo aos escritores.
		//Se já houver algúem escrevendo, bloqueia a thread.
		//Aumenta o número de leitores na fila do arquivo.
		this.leituras[fileIndex] += 1;
		if(this.leituras[fileIndex] == 1){	
			write[fileIndex].acquire(1);
		}
		
		//Sai da região crítica.
		mutex[fileIndex].release();

		//Lê o arquivo e espera 1 segundo.
		byte[] byteArrayToReturn = IOHelper.readFile(file);
		Console c = System.console();
    	c.readLine();

		//Entra na região crítica.
		mutex[fileIndex].acquire(1);
		this.leituras[fileIndex] -= 1;		
		
		//Último leitor terminou de usar o arquivo, libera para o escritor(se houver)
		if(this.leituras[fileIndex] == 0){
			write[fileIndex].release();
		}
		
		//Sai da região crítica
		mutex[fileIndex].release();
	
		return byteArrayToReturn;
	}
	
	public void writeFile(int file, String text) throws RemoteException, InterruptedException{
		int fileIndex = file - 1;
		
		//Pega permissão de escrita, ou bloqueia a thread se não houver.
		// Se tiver alguém na fila para leitura, dá preferência.
		while(true) {
			write[fileIndex].acquire();
			
			if (this.leituras[fileIndex] > 0) {
				write[fileIndex].release();
			}else {
				break;
			}
		}
		
		
		//Escreve e espera 5 segundos.
		IOHelper.writeFile(file, text);
		Console c = System.console();
    	c.readLine();		

		//Acabou de escrever, libera o arquivo para quem pediu anteriormente.
		write[fileIndex].release();
		
	}
	
	//Registra o servidor no RMI Registry para que os clientes possam acessar os métodos.
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