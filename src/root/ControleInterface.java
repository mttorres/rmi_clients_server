package root;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControleInterface extends Remote {
	public void readFile(char file) throws java.rmi.RemoteException;
	public void writeFile(char file) throws java.rmi.RemoteException;
}
