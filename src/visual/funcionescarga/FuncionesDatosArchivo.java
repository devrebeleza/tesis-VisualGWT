package visual.funcionescarga;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import visual.client.basededatos.DatosArchivo;

public class FuncionesDatosArchivo {


	public static String limpiarTablaDatosArchivo(){
		
		Session session = null;
		Transaction tx = null;
		Logger log = Logger.getLogger("Eliminando datos de la tabla DatosPerfilArchivo");
		log.info("Eliminando datos de la tabla DatosTodos");
		
		String terminoOk;
		try{ 
			session = HibernateUtil.getSessionFactory().getCurrentSession();			
			tx = session.beginTransaction();
			
			// necesito sql nativo por que hql no contiene truncate y debo reiniciar el campo clave que es auto_increment
		    Query query = session.createSQLQuery("TRUNCATE TABLE DatosArchivo");
		    query.executeUpdate();

		    tx.commit();
		    terminoOk = "OK";
			// cerrar la sesion
			//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al intentar limpiar la tabla DatosArchivo");
		   	terminoOk = "No se pudo limpiar la tabla Datos Archivo";
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
	
	public static String addDatosArchivo(String lugarAdquisicion, int deadband1, int deadband2, int deadband3,
			 String sobreescritura, int altura1, int altura2, int altura3, int intervalo, int cantidadPaquetes){
		
	
		Session session = null;
		Transaction tx = null;
		//frmAltaAplicacion.Cerrarse();
		Logger log = Logger.getLogger("Cargar tabla DatosArchivo");
		log.info("Cargar tabla DatosArchivo");
		String terminoOk;
			try {
								
				//iniciar la sesion con Hibernate
	            SessionFactory sess = HibernateUtil.getSessionFactory();
	            session = sess.getCurrentSession();
				//comenzar la transaccion
				tx = session.beginTransaction();

				// Creo el objeto para la tabla 
				DatosArchivo obj_datosArchivo = new DatosArchivo();
				
				obj_datosArchivo.setLugarAdquisicion(lugarAdquisicion);
				obj_datosArchivo.setDeadband1(deadband1);
				
				if (deadband2 > 0)	obj_datosArchivo.setDeadband2(deadband2);
				
				if (deadband3 > 0) obj_datosArchivo.setDeadband3(deadband3);
				
				obj_datosArchivo.setSobreescritura(sobreescritura);
				
				obj_datosArchivo.setAltura1(altura1);
				
				if (altura2 > 0) obj_datosArchivo.setAltura2(altura2);
				
				if (altura3 > 0) obj_datosArchivo.setAltura3(altura3);
				
				obj_datosArchivo.setIntervalo(intervalo);
				
				if (cantidadPaquetes > 0) obj_datosArchivo.setCantidadPaquetes(cantidadPaquetes);
								
					// Guardo en la BD el objeto
					session.save(obj_datosArchivo);				

					// cometer la transaccion o sino no se escribe nada en la BD
					tx.commit();
				
					terminoOk = "OK";

				// cerrar la sesion
				//session.close();
				
			} catch (HibernateException e) {
				
				System.out.println(e.getMessage());
				log.warn("Ocurrio un error al insertar el objeto Datos Archivo");
				
				terminoOk = "No se pudieron guardar correctamente los datos del archivo";
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
		// TODO Auto-generated method stub
		return terminoOk;
}
	
//	public static void tomarValoresDatosArchivo(String [] str){
//		
//		str[0]= JOptionPane.showInputDialog("Ingrese un valor para lugar de Adquisicion ", "");
//		str[1]= JOptionPane.showInputDialog("Ingrese un valor para deadband1 ", "");
//		str[2]= JOptionPane.showInputDialog("Ingrese un valor para deadband2 ", "-1");
//		str[3]= JOptionPane.showInputDialog("Ingrese un valor para deadband3 ", "-1");
//		str[4]= JOptionPane.showInputDialog("Permite sobreescritura ?", "");
//		str[5]= JOptionPane.showInputDialog("Ingrese un valor para la altura 1", "");
//		str[6]= JOptionPane.showInputDialog("Ingrese un valor para la altura 2", "-1");
//		str[7]= JOptionPane.showInputDialog("Ingrese un valor para la altura 3", "-1");
//		str[8]= JOptionPane.showInputDialog("Ingrese el intervalo de toma de datos en minutos ", "");
//		str[9]= JOptionPane.showInputDialog("Ingrese la cantida de paquetes ", "-1");		
//	}
	
	public static String[] getAlturasDatosArchivo() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener el objeto de Datos Archivo");
		log.info("Obtener el objeto de Datos Archivo");
		
		String[] strDevolucion = new String[3];
		//List listaDatosArchivo = null;
		DatosArchivo obj_datosArchivo = null;
	    try {		
	    		//hago un query con todos los datos 
		       // String queryObjeto="from DatosArchivo";
			        
		        //lo ejecuto y lo guardo en una lista de objetos.
		  //      listaDatosArchivo = session.createQuery(queryObjeto).list();
		        
		        obj_datosArchivo = (DatosArchivo) session.load(DatosArchivo.class, 1);
		        
//		        Component jfchooser = null;
//				JOptionPane.showMessageDialog(jfchooser, "Datos de DatosArchivo 1: " );
//				
		        if (obj_datosArchivo != null){
		        	strDevolucion[0] = String.valueOf(obj_datosArchivo.getAltura1());
		        	if (obj_datosArchivo.getAltura2() != null){
		        		strDevolucion[1] = String.valueOf(obj_datosArchivo.getAltura2());		
		        	}
		        	if (obj_datosArchivo.getAltura3() != null){
		        		strDevolucion[2] = String.valueOf(obj_datosArchivo.getAltura3());	
		        	}
		        }
//				JOptionPane.showMessageDialog(jfchooser, "Datos de DatosArchivo 2: " + altura);
				
		        //cometer la transaccion o sino no se escribe nada en la BD
		       
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar el objeto DatosArchivo");
		   	obj_datosArchivo = null;
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
	
	return strDevolucion;
	}
	
}
