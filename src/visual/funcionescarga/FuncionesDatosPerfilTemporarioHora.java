package visual.funcionescarga;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;


import visual.client.basededatos.DatosArchivo;
import visual.client.basededatos.DatosPerfilTemporarioHora;
import visual.client.basededatos.DatosPerfilTemporarioMes;
import visual.client.basededatos.DatosPerfilVertical;
import visual.client.basededatos.DatosTodos;
import visual.client.basededatos.DireccionVientoRangos;


public class FuncionesDatosPerfilTemporarioHora {

	
	public static String limpiarTablaDatosPerfilTemporarioHora(){
		
		Session session = null;
		Transaction tx = null;
		Logger log = Logger.getLogger("Eliminando datos de la tabla DatosPerfilTemporarioHora");
		log.info("Eliminando datos de la tabla DatosTodos");
		String terminoOk;
		try{ 
			session = HibernateUtil.getSessionFactory().getCurrentSession();			
			tx = session.beginTransaction();
			
			// necesito sql nativo por que hql no contiene truncate y debo reiniciar el campo clave que es auto_increment
		    Query query = session.createSQLQuery("TRUNCATE TABLE DatosPerfilTemporarioHora");
		    query.executeUpdate();

		    tx.commit();
		    terminoOk = "OK";
			// cerrar la sesion
			//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al intentar limpiar la tabla DatosPerfilTemporarioHora");
		   	terminoOk = "No se pudo vaciar la tabla de datos para el perfil temporario por hora";
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
	
//	select count(view3.idDatosTodos), sum(view3.velPromAlt2),  sum(view3.id.desvioAlt2)
//	from ViewDatosAltura2 as view3
//	where hour(view3.hora) = 5
	public static String addDatosPerfilTemporarioHora(){

		Session session = null;
		Transaction tx = null;
		
		Logger log = Logger.getLogger("Guardar los datos en la tabla DatosPerfilTemporarioMes");
		log.info("Guardar los datos en la tabla DatosPerfilTemporarioMes");
		
		String[] strAlturas = null;
		strAlturas = FuncionesDatosArchivo.getAlturasDatosArchivo(); 
		
		String terminoOk;
		
		try {
			
			
			//chequeo que la tabla DatosArchivo tenga datos y recupero la primera altura
			if (strAlturas[0] != null) {
				terminoOk = "OK";
				for(int hora = 0; hora <= 23 ; hora++){
				
					boolean insertar = false;
					// Creo el objeto para la tabla 
					DatosPerfilTemporarioHora obj_PerfilTempHora = new DatosPerfilTemporarioHora();
				
				
					//int altura1 = Integer.parseInt(strAlturas[0]);
					/*
					 *calculo los datos usando la vista de la altura 1 
					 */
					String[] strPerfilTemporario1 = FuncionesViewDatosAltura1.getDatosPerfilTemporarioHora(hora);
				
					int cantAltura1 = Integer.parseInt(strPerfilTemporario1[0]);
					if(cantAltura1 != 0){

						Double totalVelocidadPromedioAltura1 = Double.parseDouble(strPerfilTemporario1[1]);
						Double totalDesvioAltura1 = Double.parseDouble(strPerfilTemporario1[2]);						
						double promedioVelocidadAltura1 = totalVelocidadPromedioAltura1/ cantAltura1;
						double promedioDesvioAltura1 = totalDesvioAltura1/ cantAltura1;						
					
						obj_PerfilTempHora.setHora(hora);
						obj_PerfilTempHora.setCantidadAlt1(cantAltura1);
						obj_PerfilTempHora.setTotalVelocidadPromedioAlt1(totalVelocidadPromedioAltura1);
						obj_PerfilTempHora.setTotalDesvioAlt1(totalDesvioAltura1);
						obj_PerfilTempHora.setPromedioVelocidadAlt1(promedioVelocidadAltura1);
						obj_PerfilTempHora.setPromedioDesvioAlt1(promedioDesvioAltura1);
						
						insertar = true;
					}	
					//chequeo que la tabla DatosArchivo tenga datos y recupero la segunda altura
					if (strAlturas[1] != null) {				
						int altura2 = Integer.parseInt(strAlturas[1]);
						if (altura2 > 0){
						/*
						 *calculo los datos usando la vista de la altura 2 
						 */
							String[] strPerfilTemporario2 = FuncionesViewDatosAltura2.getDatosPerfilTemporarioHora(hora);
											
							int cantAltura2 = Integer.parseInt(strPerfilTemporario2[0]);
							if (cantAltura2 != 0){
							
								Double totalVelocidadPromedioAltura2 = Double.parseDouble(strPerfilTemporario2[1]);
								Double totalDesvioAltura2 = Double.parseDouble(strPerfilTemporario2[2]);
								double promedioVelocidadAltura2 = totalVelocidadPromedioAltura2 / cantAltura2;
								double promedioDesvioAltura2 = totalDesvioAltura2 / cantAltura2;
						
								obj_PerfilTempHora.setCantidadAlt2(cantAltura2);
								obj_PerfilTempHora.setTotalVelocidadPromedioAlt2(totalVelocidadPromedioAltura2);
								obj_PerfilTempHora.setTotalDesvioAlt2(totalDesvioAltura2);
								obj_PerfilTempHora.setPromedioVelocidadAlt2(promedioVelocidadAltura2);
								obj_PerfilTempHora.setPromedioDesvioAlt2(promedioDesvioAltura2);						
							}
						}
					}
					//chequeo que la tabla DatosArchivo tenga datos y recupero la tercer altura
					if (strAlturas[2] != null) {				
						int altura3 = Integer.parseInt(strAlturas[2]);
						if (altura3 > 0){
						/*
						 *calculo los datos usando la vista de la altura 3 
						 */
							String[] strPerfilTemporario3 = FuncionesViewDatosAltura3.getDatosPerfilTemporarioHora(hora);
					
							int cantAltura3 = Integer.parseInt(strPerfilTemporario3[0]);
							if (cantAltura3 != 0){
							
								Double totalVelocidadPromedioAltura3 = Double.parseDouble(strPerfilTemporario3[1]);
								Double totalDesvioAltura3 = Double.parseDouble(strPerfilTemporario3[2]);
								double promedioVelocidadAltura3 = totalVelocidadPromedioAltura3 / cantAltura3;
								double promedioDesvioAltura3 = ( totalDesvioAltura3 / cantAltura3);
						
								obj_PerfilTempHora.setCantidadAlt3(cantAltura3);
								obj_PerfilTempHora.setTotalVelocidadPromedioAlt3(totalVelocidadPromedioAltura3);
								obj_PerfilTempHora.setTotalDesvioAlt3(totalDesvioAltura3);
								obj_PerfilTempHora.setPromedioVelocidadAlt3(promedioVelocidadAltura3);
								obj_PerfilTempHora.setPromedioDesvioAlt3(promedioDesvioAltura3);
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
						session.save(obj_PerfilTempHora);				

						// cometer la transaccion o sino no se escribe nada en la BD
						tx.commit();
					}
				}
			}else  terminoOk = "No hay información en la altura uno para calcular los datos para el perfil temporario por hora";
			
		} catch (HibernateException e) {
				
				System.out.println(e.getMessage()); 
				System.out.println(e.getCause()); 
				log.warn("Ocurrio un error al insertar el objeto");
				
				terminoOk = "No se pudieron ingresar correctamente los datos para el perfil temporario por hora";
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
		
	
}
