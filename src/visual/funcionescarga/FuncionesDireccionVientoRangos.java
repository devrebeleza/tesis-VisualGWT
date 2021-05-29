package visual.funcionescarga;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import visual.client.basededatos.DireccionVientoRangos;

public class FuncionesDireccionVientoRangos {

	
	public static String limpiarTablaDireccionVientoRangos(){
		
		Session session = null;
		Transaction tx = null;
		Logger log = Logger.getLogger("Eliminando datos de la tabla DireccionVientoRangos");
		log.info("Eliminando datos de la tabla DatosTodos");
		String terminoOk;
		try{ 
			session = HibernateUtil.getSessionFactory().getCurrentSession();			
			tx = session.beginTransaction();
			
			// necesito sql nativo por que hql no contiene truncate y debo reiniciar el campo clave que es auto_increment
		    Query query = session.createSQLQuery("TRUNCATE TABLE DireccionVientoRangos");
		    query.executeUpdate();

		    tx.commit();
		    terminoOk = "OK";
			// cerrar la sesion
			//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al intentar limpiar la tabla DireccionVientoRangos");
		   	terminoOk = "No se pudo vaciar la tabla de datos para la dirección del viento";
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
	
	public static String addDatosDireccionVientoRangos(int maximaDivision) throws Exception{

		Session session = null;
		Transaction tx = null;
		
		Logger log = Logger.getLogger("Guardar los datos en la tabla DireccionVientoRangos");
		log.info("Guardar los datos en la tabla DireccionVientoRangos");
		
		String[] strAlturas = null;
		strAlturas = FuncionesDatosArchivo.getAlturasDatosArchivo(); 
		int cantDatos= FuncionesDatosTodos.getCantidadDatos();
		
		String terminoOk;
		boolean insertaOk = true;
		try {
	        
	        BigDecimal inicioRango;
			BigDecimal finRango;
			double cantidadX100;
			// deberia ir un savepoint acá
			
			//chequeo que la tabla DatosArchivo tenga datos en la primera altura
			if (strAlturas[0] != null) {
				terminoOk = "OK";
						        
				for(int division= 4; (division <= maximaDivision) && (insertaOk) ; division= division + 4){
				
					BigDecimal sumador = BigDecimal.valueOf(360 / division);
					BigDecimal mitadSumador = sumador.divide(BigDecimal.valueOf(2));
				
					inicioRango = BigDecimal.valueOf( (Double)360.0 - mitadSumador.doubleValue());
					finRango = mitadSumador;
				
					//System.out.println("division a llenar : " + division);
				
					for (int paso = 1; paso <= division; paso++){
					
						if (paso > 1){						
							inicioRango = finRango;
							finRango = finRango.add(sumador);
						}
					
						// Creo el objeto para la tabla 
						DireccionVientoRangos obj_DireccionVientos = new DireccionVientoRangos();
						/*
						 *calculo los datos usando la vista de la altura 1 
						*/
						String[] strPerfilTemporario1 = FuncionesViewDatosAltura1.getDatosDireccionVientoRangos(inicioRango, finRango);
				
						int cantDatosAlt1 = Integer.parseInt(strPerfilTemporario1[0]);
						double porcentajeAlt1 = 0;
						if(cantDatosAlt1 != 0){
							cantidadX100 = cantDatosAlt1*100;											
							porcentajeAlt1 = cantidadX100 / cantDatos;
						}
						
						obj_DireccionVientos.setDivision(division);
						obj_DireccionVientos.setInicioRango(inicioRango);
						obj_DireccionVientos.setFinRango(finRango);
						obj_DireccionVientos.setCantDatosAlt1(cantDatosAlt1);
						obj_DireccionVientos.setPorcentajeAlt1(porcentajeAlt1);						
															
						//chequeo que la tabla DatosArchivo tenga datos en la segunda altura y recupero la segunda altura
						if (strAlturas[1] != null) {				
					
							int altura2 = Integer.parseInt(strAlturas[1]);
							if (altura2 > 0){
								/*
								 *calculo los datos usando la vista de la altura 2 
								 */
								String[] strPerfilTemporario2 = FuncionesViewDatosAltura2.getDatosDireccionVientoRangos(inicioRango, finRango);
											
								int cantDatosAlt2 = Integer.parseInt(strPerfilTemporario2[0]);
								double porcentajeAlt2 = 0;
							
								if(cantDatosAlt2 != 0){
									cantidadX100 = cantDatosAlt2*100;
									porcentajeAlt2 = cantidadX100/cantDatos;
								}
							
								obj_DireccionVientos.setCantDatosAlt2(cantDatosAlt2);
								obj_DireccionVientos.setPorcentajeAlt2(porcentajeAlt2);		
							}
						}	
					
						//chequeo que la tabla DatosArchivo tenga datos en la tercera altura y recupero la segunda altura
						if (strAlturas[2] != null) {				
					
							int altura3 = Integer.parseInt(strAlturas[2]);
							if (altura3 > 0){
							/*
							 *calculo los datos usando la vista de la altura 3 
							 */
								String[] strPerfilTemporario3 = FuncionesViewDatosAltura3.getDatosDireccionVientoRangos(inicioRango, finRango);
					
								int cantDatosAlt3 = Integer.parseInt(strPerfilTemporario3[0]);
								double porcentajeAlt3 = 0;
						
								if(cantDatosAlt3 != 0){
									cantidadX100 = cantDatosAlt3*100;
									porcentajeAlt3 = cantidadX100 / cantDatos;
								}
						
								obj_DireccionVientos.setCantDatosAlt3(cantDatosAlt3);
								obj_DireccionVientos.setPorcentajeAlt3(porcentajeAlt3);	
							}
						}							
					 // Iniciar la sesion con Hibernate
				     SessionFactory sess = HibernateUtil.getSessionFactory();
				     session = sess.getCurrentSession();
				     
				     // Comenzar la transaccion
				     tx = session.beginTransaction();
				     //session.connection().setAutoCommit(false);
				     
					// Guardo en la BD el objeto
					session.save(obj_DireccionVientos);
					
					// cometer la transaccion o sino no se escribe nada en la BD
					tx.commit();
				}
			  }
			}else terminoOk = "No hay información en la altura uno para calcular los datos para la dirección del viento";
			
		} catch (HibernateException e) {
				
				System.out.println(e.getMessage()); 
				System.out.println(e.getCause()); 
				log.warn("Ocurrio un error al insertar el objeto");
				
				terminoOk = "No se pudieron ingresar correctamente los datos para la dirección del viento";
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
	 return terminoOk;	
	}

public static List getDatosTablaParaGraficoRosa(String nombreTabla, int division) throws IOException{
		
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener el objeto de " + nombreTabla);
		log.info("Obtener el objeto de " + nombreTabla);
				

	    try {		
	    		//hago un query con todos los datos 
		       String consulta="from " + nombreTabla + " where division = "+ division + " order by finRango" ;
			        
		        //lo ejecuto y lo guardo en una lista de objetos.
		  //      listaDatosArchivo = session.createQuery(queryObjeto).list();
	    	
	    	//hago un query contando la cantidad de datos
	        StringBuilder queryObjeto= new StringBuilder();
	        queryObjeto.append(consulta);
	        
	        
	        //lo ejecuto y lo guardo en una lista de objetos.
	        List listaDatos = session.createQuery(queryObjeto.toString()).list();
		       
			tx.commit();

			// cerrar la sesion
			//session.close();
				return listaDatos;
	      } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar el objeto " + nombreTabla);
		   	
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
	   return null;
	  }
}
