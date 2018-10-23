package root;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.lang.InterruptedException;

public class ClienteThread extends Thread {
	
	public ControleCliente cliente;
	public String[] tarefas;
	
	public ClienteThread(int numero_cliente) throws RemoteException, NotBoundException {
		this.cliente = new ControleCliente(numero_cliente);
		this.tarefas = ControleCliente.tarefas[numero_cliente - 1];
	}
	
	public void run() {
		try {
			char tipoTarefa;
			char arquivoTarefa;
			
			for (String tarefa : this.tarefas) {
				tipoTarefa = tarefa.charAt(0);
				arquivoTarefa = tarefa.charAt(1);
				this.handleTarefa(tipoTarefa, arquivoTarefa);
			}
			
			System.out.println("==> \tCliente " + cliente.numeroCliente + " finalizou.");
		}catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	private void handleTarefa(char tipoTarefa, char arquivoTarefa) throws RemoteException, InterruptedException {
		System.out.println("Cliente " + cliente.numeroCliente + " executando " + tipoTarefa + arquivoTarefa);
		if(tipoTarefa == 'W'){
			this.cliente.writeFile(arquivoTarefa);
		} 
		else if(tipoTarefa == 'R'){
			this.cliente.readFile(arquivoTarefa); 
			
		} 
		else System.out.println("Tarefa inválida!");
		System.out.println("Cliente " + cliente.numeroCliente + " finalizou " + tipoTarefa + arquivoTarefa);
	}
}
