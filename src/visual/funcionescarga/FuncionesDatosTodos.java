package visual.funcionescarga;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import visual.client.basededatos.DatosTodos;


public class FuncionesDatosTodos {

	public static String limpiarTablaDatosTodos(){
		
		Session session = null;
		Transaction tx = null;
		Logger log = Logger.getLogger("Eliminando datos de la tabla DatosTodos");
		log.info("Eliminando datos de la tabla DatosTodos");
		String terminoOk;
		try{ 
			session = HibernateUtil.getSessionFactory().getCurrentSession();			
			tx = session.beginTransaction();
			
			// necesito sql nativo por que hql no contiene truncate y debo reiniciar el campo clave que es auto_increment
		    Query query = session.createSQLQuery("TRUNCATE TABLE DatosTodos");
		    query.executeUpdate();

		    tx.commit();
		    terminoOk = "OK";
			// cerrar la sesion
			//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al intentar limpiar la tabla DatosTodos");
		   	terminoOk = "No se pudo vaciar la tabla PRINCIPAL";
		   // cuando ocurre un error hace rollback
		   	if (tx != null)
		   		try {
		   			tx.rollback();
		   		} catch (HibernateException e1) {
		   			System.out.println("El rollback no fue exitoso");
		   			log.warn("El rollback no fue exitoso");
		   		}

         if (session != null)
         	try {
         		session.close();
         	} catch (HibernateException e2) {
         		System.out.println("El cierre de sesion no fue exitoso");
					log.warn("El cierre de sesion no fue exitoso");
         	}
		}		
	   return terminoOk;
	}
	
	public String insertarLineaDatosTodos(String[] linea) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		//frmAltaAplicacion.Cerrarse();
		Logger log = Logger.getLogger("Insertar Linea en tabla DatosTodos");
		log.info("Insertar Linea en tabla DatosTodos");
		
		String terminoOk = "OK";
		if ((linea.length != 0)){
			try {
								
				//iniciar la sesion con Hibernate
	            SessionFactory sess = HibernateUtil.getSessionFactory();
	            session = sess.getCurrentSession();
				//comenzar la transaccion
				tx = session.beginTransaction();

				// Creo el objeto para la tabla 
				DatosTodos obj_datosTodos = new DatosTodos();
								
				int i = 0;
				while ((i < linea.length) && (terminoOk == "OK")){
					terminoOk = insertarSiguienteColumnaDatosTodos(linea[i],i,obj_datosTodos);
					i++;
				}
				if (terminoOk == "OK"){					
					// Guardo en la BD el objeto
					session.save(obj_datosTodos);				

					// cometer la transaccion o sino no se escribe nada en la BD
					tx.commit();
				
					terminoOk = "OK";
				}
				// cerrar la sesion
				//session.close();
				
			} catch (HibernateException e) {
				
				System.out.println(e.getMessage());
				log.warn("Ocurrio un error al insertar el objeto");
				
				terminoOk = "No se pudo guardar correctamente por un error en la transaccion de la tabla que contiene todos los datos";
				// cuando ocurre un error hace rollback
				if (tx != null)
					try {
						tx.rollback();
					} catch (HibernateException e1) {
						System.out.println("El rollback no fue exitoso");
					    log.warn("El rollback no fue exitoso");
					}
				
				if (session != null)
					try {
						session.close();
					} catch (HibernateException e2) {
						System.out.println("Cerrar la sesion no fue exitoso");
						log.warn("Cerrar la sesion no fue exitoso");
					}
			}

		}else{
			
			terminoOk = "No hay datos para guardar";	
		}
		// TODO Auto-generated method stub
		return terminoOk;

}

	private String insertarSiguienteColumnaDatosTodos(String dato, int columna, DatosTodos objeto) {
		// TODO Auto-generated method stub
		String valorDevolucion = "OK";
		String valorColumna = null;
		try{
		switch(columna) { 
			case 0: //columna fecha
					valorColumna = "Fecha";
					//genero el formato de tipo de fecha
					SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yy");

					// Seteo a false para que chequee que la fecha sea valida. Larga una excepci�n si es invalida
					formatoDeFecha.setLenient(false);
				
					Date fecha = null;
					try {					
					
						fecha = formatoDeFecha.parse(dato);												

					} catch (Exception e) {

						e.printStackTrace();
						System.out.println(e.getMessage());		
						valorDevolucion = "Error al querer insertar datos en la columna Fecha";
					}
					// guardo la fecha 
					objeto.setFecha(fecha);	
					break;
			case 1: //columna hora
					valorColumna = "Hora";
					//genero el formato de tipo de fecha
					SimpleDateFormat formatoDeHora = new SimpleDateFormat("HH:mm:ss");

					// Seteo a false para que chequee que la fecha sea valida. Larga una excepci�n si es invalida
					formatoDeHora.setLenient(false);
				
					Date hora = null;
					try {					
					
						hora = formatoDeHora.parse(dato);												

					} catch (Exception e) {

						e.printStackTrace();
						System.out.println(e.getMessage());		
						valorDevolucion = "Error al querer insertar datos en la columna Hora";
					}
					// guardo la hora
					objeto.setHora(hora);
					break;
			case 2: //columna Temperatura Promedio
					valorColumna = "Temperatura Promedio";
					// guardo la temperatura promedio
					objeto.setTempProm( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 3: //columna Humedad
					valorColumna = "Humedad";
					//guardo el porcentaje de humedad
					objeto.setHumedad(Integer.parseInt(dato));
					break;
			case 4: //columna Presion
					valorColumna = "Presión";
					//guardo la temperatura máxima
					objeto.setPresion(Integer.parseInt(dato));
					break;
			case 5: //columna Temperatura Maxima
					valorColumna = "Temperatura Máxima";
					//guardo la temperatura máxima
					objeto.setTempMax( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 6: //columna Temperatura Minima
					valorColumna = "Temperatura Mínima";
					//guardo la temperatura máxima
					objeto.setTempMin( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
					
				//Datos Altura 1
					
			case 7: //columna Velocidad Promedio Alt1
					valorColumna = "Velocidad Promedio Alt1";
					//guardo la velocidad promedio
					objeto.setVelPromAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 8: //columna Direccion Promedio Alt1
					valorColumna = "Dirección Promedio Alt1";
					//guardo la direccion promedio
					objeto.setDirPromAlt1(Integer.parseInt(dato));
					break;
			case 9: //columna Temperatura Promedio Alt1
					valorColumna = "Temperatura Promedio Alt1";
					//guardo la temperatura promedio
					objeto.setTempPromAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 10: //columna Desvio Alt1
					valorColumna = "Desvio Alt1";
					//guardo el desvio
					objeto.setDesvioAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 11: //columna Velocidad Maxima Alt1
					valorColumna = "Velocidad Máxima Alt1";
					//guardo la velocidad maxima
					objeto.setVelMaxAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 12: //columna Direccion Maxima Alt1
					valorColumna = "Dirección Máxima Alt1";
					//guardo la direccion maxima
					objeto.setDirMaxAlt1(Integer.parseInt(dato));
					break;
			case 13: //columna Temperatura Maxima Alt1
					valorColumna = "Temperatura Máxima Alt1";
					//guardo la temperatura maxima
					objeto.setTempMaxAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 14: //columna Velocidad Minima Alt1
					valorColumna = "Velocidad Minima Alt1";
					//guardo la velocidad minima
					objeto.setVelMinAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 15: //columna Direccion Minima Alt1
					valorColumna = "Dirección Minima Alt1";
					//guardo la direccion minima
					objeto.setDirMinAlt1(Integer.parseInt(dato));
					break;
			case 16: //columna Temperatura Minima Alt1
					valorColumna = "Temperatura Minima Alt1";
					//guardo la temperatura minima
					objeto.setTempMinAlt1( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
					
				//Datos Altura 2
					
			case 17: //columna Velocidad Promedio Alt2
					valorColumna = "Velocidad Promedio Alt2";
					//guardo la velocidad promedio
					objeto.setVelPromAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 18: //columna Direccion Promedio Alt2
					valorColumna = "Dirección Promedio Alt2";
					//guardo la direccion promedio
					objeto.setDirPromAlt2(Integer.parseInt(dato));
					break;
			case 19: //columna Temperatura Promedio Alt2
					valorColumna = "Temperatura Promedio Alt2";
					//guardo la temperatura promedio
					objeto.setTempPromAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 20: //columna Desvio Alt2
					valorColumna = "Desvio Alt2";
					//guardo el desvio
					objeto.setDesvioAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 21: //columna Velocidad Maxima Alt2
					valorColumna = "Velocidad Máxima Alt2";
					//guardo la velocidad maxima
					objeto.setVelMaxAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 22: //columna Direccion Maxima Alt2
					valorColumna = "Dirección Máxima Alt2";
					//guardo la direccion maxima
					objeto.setDirMaxAlt2(Integer.parseInt(dato));
					break;
			case 23: //columna Temperatura Maxima Alt2
					valorColumna = "Temperatura Máxima Alt2";
					//guardo la temperatura maxima
					objeto.setTempMaxAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 24: //columna Velocidad Minima Alt2
					valorColumna = "Velocidad Minima Alt2";
					//guardo la velocidad minima
					objeto.setVelMinAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 25: //columna Direccion Minima Alt2
					valorColumna = "Dirección Minima Alt2";
					//guardo la direccion minima
					objeto.setDirMinAlt2(Integer.parseInt(dato));
					break;
			case 26: //columna Temperatura Minima Alt2
					valorColumna = "Temperatura Minima Alt2";
					//guardo la temperatura minima
					objeto.setTempMinAlt2( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
					
					
				//Datos Altura 3
			
			case 27: //columna Velocidad Promedio Alt3
					valorColumna = "Velocidad Promedio Alt3";
					//guardo la velocidad promedio
					objeto.setVelPromAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 28: //columna Direccion Promedio Alt3
					valorColumna = "Dirección Promedio Alt3";
					//guardo la direccion promedio
					objeto.setDirPromAlt3(Integer.parseInt(dato));
					break;
			case 29: //columna Temperatura Promedio Alt3
					valorColumna = "Temperatura Promedio Alt3";
					//guardo la temperatura promedio
					objeto.setTempPromAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 30: //columna Desvio Alt3
					valorColumna = "Desvio Alt3";
					//guardo el desvio
					objeto.setDesvioAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 31: //columna Velocidad Maxima Alt3
					valorColumna = "Velocidad Máxima Alt3";
					//guardo la velocidad maxima
					objeto.setVelMaxAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 32: //columna Direccion Maxima Alt3
					valorColumna = "Dirección Máxima Alt3";
					//guardo la direccion maxima
					objeto.setDirMaxAlt3(Integer.parseInt(dato));
					break;
			case 33: //columna Temperatura Maxima Alt3
					valorColumna = "Temperatura Máxima Alt3";
					//guardo la temperatura maxima
					objeto.setTempMaxAlt3(FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 34: //columna Velocidad Minima Alt3
					valorColumna = "Velocidad Minima Alt3";
					//guardo la velocidad minima
					objeto.setVelMinAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			case 35: //columna Direccion Minima Alt3
					valorColumna = "Dirección Minima Alt3";
					//guardo la direccion minima
					objeto.setDirMinAlt3(Integer.parseInt(dato));
					break;
			case 36: //columna Temperatura Minima Alt3
					valorColumna = "Temperatura Minima Alt3";
					//guardo la temperatura minima
					objeto.setTempMinAlt3( FuncionesComunes.convertirStrABigDecimal(dato));
					break;
			default: valorColumna = "(Fuera de rango de columnas)";
		}
		}catch (Exception e) {
			
			System.out.println(e.getMessage()); 
			valorDevolucion="Error al querer insertar " + dato + " en la columna " + valorColumna;
		}	
		return valorDevolucion;	
	}

	

	public String controlarColumnas(String[] linea) {
		// TODO Auto-generated method stub
		int i =0; 
		boolean continua = true;
		String str_match = null;
		String str_find;
		String strError = null;
		String terminoOk = "OK";
		
		while ((i< linea.length) && continua){
			str_match =null;
			strError = "La columna ' " + i + " ' no corresponde a la columna esperada";
			
			switch(i){
			case 0: str_match= linea[i].toString().toUpperCase();
					str_find = "Fecha";
					continua = str_match.equalsIgnoreCase(str_find);
					break;
			case 1:	str_match= linea[i].toString().toUpperCase();
					str_find = "Hora";
					continua = str_match.equalsIgnoreCase(str_find);
					break;
			case 2: str_match= linea[i].toString().toUpperCase();
					str_find = "Temp prom";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 3: str_match= linea[i].toString().toUpperCase();	
					str_find = "Humedad";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 4: str_match= linea[i].toString().toUpperCase();	
					str_find = "Presion";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 5: str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp max";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 6: str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp Min";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			
					//Datos altura 1
					
			case 7: str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel prom Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 8: str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir prom Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 9:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp prom Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 10:str_match= linea[i].toString().toUpperCase();	
					str_find = "Desvio Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 11:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel max Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 12:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir max Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 13:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp max Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 14:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel min Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 15:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir min Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 16:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp min Alt1";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			
					//columna para altura 2
					
			case 17: str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel prom Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;	
			case 18: str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir prom Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 19:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp prom Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 20:str_match= linea[i].toString().toUpperCase();	
					str_find = "Desvio Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 21:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel max Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 22:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir max Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 23:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp max Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 24:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel min Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 25:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir min Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 26:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp min Alt2";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
					
					//columna para altura 3
			case 27: str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel prom Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;	
			case 28: str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir prom Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 29:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp prom Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 30:str_match= linea[i].toString().toUpperCase();	
					str_find = "Desvio Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 31:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel max Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 32:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir max Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 33:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp max Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 34:str_match= linea[i].toString().toUpperCase();	
					str_find = "Vel min Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 35:str_match= linea[i].toString().toUpperCase();	
					str_find = "Dir min Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			case 36:str_match= linea[i].toString().toUpperCase();	
					str_find = "Temp min Alt3";
					continua = str_match.regionMatches( true, 0, str_find, 0, str_find.length());
					break;
			default:strError = "Demasiadas columnas en el archivo de entrada";
					continua = false;
			}
			//System.out.println("elemento " + i + ": "+ linea[i] + " tamaño linea = " + linea.length);
			i++;
		}
		if(!continua){
			terminoOk= strError + " - " + str_match;
		}else if( !(linea.length == 17 || linea.length == 27 || linea.length == 37) ){
						terminoOk = "La cantidad de columnas es inválida: " + linea.length;
			}
			
		return terminoOk;
	}

	
	public static int getCantidadDatos() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		
		// Iniciar la sesion con Hibernate
		SessionFactory sess = HibernateUtil.getSessionFactory();
    	session = sess.getCurrentSession();

    	// Comenzar la transaccion
    	tx = session.beginTransaction();
		
        
        Logger log = Logger.getLogger("Obtener la cantidad de registros de DatosTodos");
		log.info("Obtener la cantidad de registros de DatosTodos");
		
		//List listaDatosArchivo = null;
		int cantDatos = 0;
	    try {		

	    		//hago un query contando la cantidad de datos
		        StringBuilder queryObjeto= new StringBuilder();
		        queryObjeto.append("select count(dt.idDatosTodos)  from DatosTodos as dt");
		        
		        //lo ejecuto y lo guardo en un iterador.
		        List listaDatosArchivo = session.createQuery(queryObjeto.toString()).list();
		        
		        cantDatos = Integer.parseInt(listaDatosArchivo.get(0).toString());
				//cantDatos = Integer.parseInt(tupla[0].toString());
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar la cantidad de registros de DatosTodos");

		   // cuando ocurre un error hace rollback
		   	if (tx != null)
		   		try {
		   			tx.rollback();
		   		} catch (HibernateException e1) {
		   			System.out.println("El rollback no fue exitoso");
		   			log.warn("El rollback no fue exitoso");
		   		}

            if (session != null)
            	try {
            		session.close();
            	} catch (HibernateException e2) {
            		System.out.println("El cierre de sesion no fue exitoso");
					log.warn("El cierre de sesion no fue exitoso");
            	}
	   }	
	
	return cantDatos;
		
	}

	
}
