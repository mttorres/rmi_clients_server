package root;

import java.util.concurrent.Semaphore;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
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
	}
	
	//Método de leitura de arquivo.
	public byte[] readFile(int file) throws RemoteException, InterruptedException{

		//Entra na região crítica. Só um leitor atualiza o contador por vez para não dar merda.
		mutex[file-1].acquire(1);
		
		//Se ele é o primeiro leitor, nega o acesso do arquivo aos escritores.
		//Se já houver algúem escrevendo, bloqueia a thread.
		//Aumenta o número de leitores na fila do arquivo.
		this.leituras[file-1] += 1;
		if(this.leituras[file-1] == 1){	
			write[file-1].acquire(1);
		}
		
		//Sai da região crítica.
		mutex[file-1].release();
		
		//Debugging:
		//System.out.println("Fila antes = " + this.leituras[file-1]);

		//Lê o arquivo e espera 1 segundo.
		byte[] byteArrayToReturn = IOHelper.readFile(file);
		Thread.sleep(1000);

		//Entra na região crítica.
		mutex[file-1].acquire(1);
		this.leituras[file-1] -= 1;		
		//Último leitor terminou de usar o arquivo, libera para o escritor(se houver)
		if(this.leituras[file-1] == 0){
			write[file-1].release();
		}
		//Sai da região crítica
		mutex[file-1].release();

		//Debugging:
		/* System.out.println("Fila depois = " + this.leituras[file-1] + " *** " + this.write[file-1].availablePermits()
							+ " *** " + this.mutex[file-1].availablePermits()
							); */
	
		return byteArrayToReturn;
	}
	
	public void writeFile(int file, String text) throws RemoteException, InterruptedException{
		int fileIndex = file - 1;
		
		//Pega permissão de escrita, ou bloqueia a thread se não houver.
		// Se tiver alguém na fila para leitura, dá preferência.
		while(true) {
			write[fileIndex].acquire();
			
			if (this.leituras[fileIndex] > 0) {
				write[fileIndex].release(1);
			}else {
				break;
			}
		}
		
		
		//Debbuging:
		//System.out.println("Permissão de escrita antes =" + this.write[file-1].availablePermits());
		//System.out.println("Pegou write. Vai escrever.");
		
		//Escreve e espera 5 segundos.
		IOHelper.writeFile(file, text);
		Thread.sleep(5000);		

		//Acabou de escrever, libera o arquivo para quem pediu anteriormente.
		write[fileIndex].release();

		//Debugging:
		//System.out.println("Permissão de escrita depois =" + this.write[file-1].availablePermits());
		
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


//Deprecated

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