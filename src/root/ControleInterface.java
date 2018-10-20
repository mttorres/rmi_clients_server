package root;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControleInterface extends Remote {
	public void readFile(char file) throws RemoteException;
	public void writeFile(char file) throws RemoteException;
}
