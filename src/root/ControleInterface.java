package root;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControleInterface extends Remote {
	public byte[] readFile(int len, int file) throws RemoteException;
	public void writeFile(int file) throws RemoteException;
}
