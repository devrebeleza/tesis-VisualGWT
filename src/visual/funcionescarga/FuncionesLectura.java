package visual.funcionescarga;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.InputStreamReader;


public class FuncionesLectura {
	
	private static BufferedReader bf;
	
	public static boolean leerArchivo(String rutaArchivo) throws UnsupportedEncodingException{
		
		
		//FileReader fr= null;
		int tamaño = rutaArchivo.length();
		String extension = rutaArchivo.substring(tamaño-3,rutaArchivo.length());
		if (extension.equals("txt")){
			try {
				//fr = new FileReader(rutaArchivo);
				FileInputStream fis = new FileInputStream(rutaArchivo);
				InputStreamReader is = new InputStreamReader(fis,"ISO-8859-1");
				bf = new BufferedReader(is);
				//bf = new BufferedReader(fr);
			}catch (FileNotFoundException e) {
			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	public static String[] getNextLinea(String separador) throws IOException{
		
			String sCadena = bf.readLine();
			String[] lista = null;
			if (sCadena != null){
				lista = sCadena.split(separador);
//				System.out.println(sCadena);
//				int i =0; 
//				while (i< lista.length){
//					System.out.println("elemento " + i + ": "+ lista[i]);
//					i++;
//				}
			}
		return lista;
	}
}