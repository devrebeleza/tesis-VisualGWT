package visual.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.visualization.client.DataTable;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface AccesoDatosService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

//	en archivo tengo el string para guardar en ArchivoDatos.txt
	void procesarArchivo(String archivo)throws IllegalArgumentException;
	
//	con el archivo creado, inserto los datos en la base de datos 
	String setDatosTodos(Integer maxDivision)throws IllegalArgumentException;
	
	
	void setDatosArchivo(String lugarAdquisicion, int deadband1, int deadband2, int deadband3,
			 String sobreescritura, int altura1, int altura2, int altura3, int intervalo, int cantidadPaquetes)
		     throws IllegalArgumentException;
	
	void setTablaEnXML(String nombreTabla)throws IllegalArgumentException;
	
	void setTodasTablaEnXML()throws IllegalArgumentException;
	
	List getDatosTablaParaGrafico(String nombreTabla) throws IllegalArgumentException;
	
	List getDatosTablaParaGraficoRosa(String nombreTabla,int division) throws IllegalArgumentException;
	
	String[] getDatosAlturas() throws IllegalArgumentException;
	
	Double[][] getDatosFunciones() throws IllegalArgumentException;
	
	List getDatosTablaParaGraficoHistograma(String nombreTabla) throws IllegalArgumentException;
	
}
