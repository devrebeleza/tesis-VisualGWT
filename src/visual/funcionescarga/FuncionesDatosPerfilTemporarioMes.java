package visual.funcionescarga;

import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import visual.client.basededatos.DatosPerfilTemporarioMes;

public class FuncionesDatosPerfilTemporarioMes {

	

	public static String limpiarTablaDatosPerfilTemporarioMes(){
		
		Session session = null;
		Transaction tx = null;
		Logger log = Logger.getLogger("Eliminando datos de la tabla DatosPerfilTemporarioMes");
		log.info("Eliminando datos de la tabla DatosTodos");
		String terminoOk;
		try{ 
			session = HibernateUtil.getSessionFactory().getCurrentSession();			
			tx = session.beginTransaction();
			
			// necesito sql nativo por que hql no contiene truncate y debo reiniciar el campo clave que es auto_increment
		    Query query = session.createSQLQuery("TRUNCATE TABLE DatosPerfilTemporarioMes");
		    query.executeUpdate();

		    tx.commit();
		    terminoOk = "OK";
			// cerrar la sesion
			//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al intentar limpiar la tabla DatosPerfilTemporarioMes");
		   	terminoOk = "No se pudo vaciar la tabla de datos para el perfil temporario por mes";
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
	
	public static String addDatosPerfilTemporarioMes(){

		Session session = null;
		Transaction tx = null;
		
		Logger log = Logger.getLogger("Guardar los datos en la tabla DatosPerfilTemporarioMes");
		log.info("Guardar los datos en la tabla DatosPerfilTemporarioMes");
		
		String[] strAlturas = null;
		strAlturas = FuncionesDatosArchivo.getAlturasDatosArchivo(); 
		
		String terminoOk;
		
		//chequeo que la tabla DatosArchivo tenga datos de por lo menos una altura y recupero la primera altura
		if (strAlturas[0] != null) {
		  try {
			  	terminoOk = "OK";
				for(int mes = 1; mes <= 12 ; mes++){
				
					boolean insertar = false;
					// Creo el objeto para la tabla 
					DatosPerfilTemporarioMes obj_PerfilTempMes = new DatosPerfilTemporarioMes();
					/*
					 *calculo los datos usando la vista de la altura 1 
					 */
					String[] strPerfilTemporario1 = FuncionesViewDatosAltura1.getDatosPerfilTemporarioMes(mes);
				
					int cantAltura1 = Integer.parseInt(strPerfilTemporario1[0]);
					if(cantAltura1 != 0){

						Double totalVelocidadPromedioAltura1 = Double.parseDouble(strPerfilTemporario1[1]);
						Double totalDesvioAltura1 = Double.parseDouble(strPerfilTemporario1[2]);
						double promedioVelocidadAltura1 = ( totalVelocidadPromedioAltura1/ cantAltura1);
						double promedioDesvioAltura1 = ( totalDesvioAltura1/ cantAltura1);
					
						String[] mesString = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
						
						obj_PerfilTempMes.setMes(mesString[mes-1]);
						obj_PerfilTempMes.setCantidadAlt1(cantAltura1);
						obj_PerfilTempMes.setTotalVelocidadPromedioAlt1(totalVelocidadPromedioAltura1);
						obj_PerfilTempMes.setTotalDesvioAlt1(totalDesvioAltura1);
						obj_PerfilTempMes.setPromedioVelocidadAlt1(promedioVelocidadAltura1);
						obj_PerfilTempMes.setPromedioDesvioAlt1(promedioDesvioAltura1);
						
						insertar = true;
					}
					//chequeo que la tabla DatosArchivo tenga datos y recupero la segunda altura
					if (strAlturas[1] != null) {				
						int altura2 = Integer.parseInt(strAlturas[1]);
						if (altura2 > 0){
							/*
							 *calculo los datos usando la vista de la altura 2 
							 */
							String[] strPerfilTemporario2 = FuncionesViewDatosAltura2.getDatosPerfilTemporarioMes(mes);
											
							int cantAltura2 = Integer.parseInt(strPerfilTemporario2[0]);
							if (cantAltura2 != 0){
							
								Double totalVelocidadPromedioAltura2 = Double.parseDouble(strPerfilTemporario2[1]);
								Double totalDesvioAltura2 = Double.parseDouble(strPerfilTemporario2[2]);
								double promedioVelocidadAltura2 = ( totalVelocidadPromedioAltura2 / cantAltura2);
								double promedioDesvioAltura2 = ( totalDesvioAltura2 / cantAltura2);
						
								obj_PerfilTempMes.setCantidadAlt2(cantAltura2);
								obj_PerfilTempMes.setTotalVelocidadPromedioAlt2(totalVelocidadPromedioAltura2);
								obj_PerfilTempMes.setTotalDesvioAlt2(totalDesvioAltura2);
								obj_PerfilTempMes.setPromedioVelocidadAlt2(promedioVelocidadAltura2);
								obj_PerfilTempMes.setPromedioDesvioAlt2(promedioDesvioAltura2);
							}
						}
					}	
					if (strAlturas[2] != null) {				
						int altura3 = Integer.parseInt(strAlturas[2]);
						if (altura3 > 0){
							/*
							 *calculo los datos usando la vista de la altura 3 
							 */
							String[] strPerfilTemporario3 = FuncionesViewDatosAltura3.getDatosPerfilTemporarioMes(mes);
					
							int cantAltura3 = Integer.parseInt(strPerfilTemporario3[0]);
							if (cantAltura3 != 0){
							
								Double totalVelocidadPromedioAltura3 = Double.parseDouble(strPerfilTemporario3[1]);
								Double totalDesvioAltura3 = Double.parseDouble(strPerfilTemporario3[2]);
								double promedioVelocidadAltura3 = ( totalVelocidadPromedioAltura3 / cantAltura3);
								double promedioDesvioAltura3 = ( totalDesvioAltura3 / cantAltura3);
						
								obj_PerfilTempMes.setCantidadAlt3(cantAltura3);
								obj_PerfilTempMes.setTotalVelocidadPromedioAlt3(totalVelocidadPromedioAltura3);
								obj_PerfilTempMes.setTotalDesvioAlt3(totalDesvioAltura3);
								obj_PerfilTempMes.setPromedioVelocidadAlt3(promedioVelocidadAltura3);
								obj_PerfilTempMes.setPromedioDesvioAlt3(promedioDesvioAltura3);
							}	
						}
					}
				
					if (insertar){
						//iniciar la sesion con Hibernate
						SessionFactory sess = HibernateUtil.getSessionFactory();
						session = sess.getCurrentSession();
						//comenzar la transaccion
						tx = session.beginTransaction();
					
						// Guardo en la BD el objeto
						//	System.out.println(obj_PerfilTempMes.getMes());
						session.save(obj_PerfilTempMes);				
					}
			}
			
		  } catch (HibernateException e) {
				
				System.out.println(e.getMessage());
				log.warn("Ocurrio un error al insertar el objeto");
				
				terminoOk = "No se pudo guardar la información para el perfil temporario de los Meses";
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
			terminoOk = "No hay información en la altura uno para calcular los datos de la dirección del viento";
		}
		
	 return terminoOk;	
	}
	
}
