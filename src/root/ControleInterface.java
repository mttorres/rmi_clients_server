package root;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControleInterface extends Remote {
	public void readFile(int file, int cliente) throws RemoteException;
	public void writeFile(int file, int cliente) throws RemoteException;

	//Debugging
	public void updateFila(int arquivo, int leitura) throws RemoteException;
	public void getState() throws RemoteException;
}
