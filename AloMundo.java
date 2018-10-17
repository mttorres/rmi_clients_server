package src;
import java.rmi.Remote;
import java.rmi.RemoteException;

//temos a "interface" a qual o cliente pode invocar os seguintes metodos:
public interface AloMundo extends Remote
{
	String digaAloMundo() throws java.rmi.RemoteException;	
}