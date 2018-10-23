package root;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOHelper {
	public static int BUFFER_SIZE = 100;
	
	private IOHelper () {}
	
	public static byte[] readFile(int file) {
		byte[] byteArrayToReturn = new byte[BUFFER_SIZE];
		
		try {
			String fileName = getFileName(file);
			readFileIntoByteArray(byteArrayToReturn, fileName);
		} catch (IOException e) {
			System.out.println("Erro tratando arquivo na leitura.");
		}
		
		return byteArrayToReturn;
	}
	
	public static void writeFile(int file, String textToWrite) {
		try {
			String fileName = getFileName(file);
			writeFileWithByteArray(textToWrite.getBytes(), fileName);
		} catch (IOException e) {
			System.out.println("Erro tratando arquivo na leitura.");
		}
	}
	
	private static void readFileIntoByteArray(byte[] byteArray, String fileName) throws IOException{
		FileInputStream fileInput = new FileInputStream(fileName);
		BufferedInputStream bufferedFileInput = new BufferedInputStream(fileInput);		
		bufferedFileInput.read(byteArray);
		bufferedFileInput.close(); 
	}
	
	private static void writeFileWithByteArray(byte[] byteArray, String fileName) throws IOException{
		FileOutputStream fileOutput = new FileOutputStream(fileName);
		BufferedOutputStream bufferedFileOutput = new BufferedOutputStream(fileOutput);	
		bufferedFileOutput.write(byteArray);
		bufferedFileOutput.close(); 
	}
	
	private static String getFileName(int file) {
		return "file" + file + ".txt";
	}
}
