package root;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class IOHelper {
	
	private IOHelper () {}
	
	public static byte[] readFile(int len, int file) {
		byte[] byteArrayToReturn = new byte[len];
		
		try {
			String fileName = getFileName(file);
			readFileIntoByteArray(byteArrayToReturn, fileName);
		} catch (IOException e) {
			System.out.println("Erro tratando arquivo na leitura.");
		}
		
		return byteArrayToReturn;
	}
	
	private static void readFileIntoByteArray(byte[] byteArray, String fileName) throws IOException{
		FileInputStream fileInput = new FileInputStream(fileName);
		BufferedInputStream bufferedFileInput = new BufferedInputStream(fileInput);		
		bufferedFileInput.read(byteArray);
		bufferedFileInput.close(); 
	}
	
	private static String getFileName(int file) {
		return "file" + file + ".txt";
	}
}
