package visual.funcionescarga;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


import visual.hibernate.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;



public class FuncionesViewDatosAltura1 {

	public static String[] getDatosPerfilVertical() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener el objeto de Datos Altura 1");
		log.info("Obtener el objeto de Datos Altura 1");
		
		//List listaDatosArchivo = null;
		String[] strDevolucion = new String[3];
	    try {		
	    		//hago un query con todos los datos 
		        String queryObjeto="select count(view1.id.idDatosTodos), sum(view1.id.velPromAlt1), sum(view1.id.tempPromAlt1) from ViewDatosAltura1 as view1";
		        
		        //lo ejecuto y lo guardo en un iterador.
		        Iterator listaDatosArchivo = session.createQuery(queryObjeto).list().iterator();

		        Object[] tupla = (Object[]) listaDatosArchivo.next();
		
		        strDevolucion[0] = tupla[0].toString();
		        strDevolucion[1] = tupla[1].toString();
		        strDevolucion[2] = tupla[2].toString();
		        
//		        Component jfchooser = null;
//		        JOptionPane.showMessageDialog(jfchooser, "Datos de DatosArchivo 1: " + strDevolucion[0] + " STRING 2 " + strDevolucion[1] + "STRING 3 "+ strDevolucion[2]);
		        
		        //cometer la transaccion o sino no se escribe nada en la BD
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar los datos de la vista de Altura 1");

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

	public static String[] getDatosPerfilTemporarioMes(int mes) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener los datos para el mes en altura 1");
		log.info("Obtener los datos para el mes en altura 1");
		
		//List listaDatosArchivo = null;
		String[] strDevolucion = new String[3];
	    try {		
	    		//hago un query con todos los datos 
		        StringBuilder queryObjeto= new StringBuilder();
		        queryObjeto.append("select count(view1.id.idDatosTodos), sum(view1.id.velPromAlt1), sum(view1.id.desvioAlt1) from ViewDatosAltura1 as view1	where month(view1.id.fecha) = ");
		        queryObjeto.append(mes);
		        
		        //lo ejecuto y lo guardo en un iterador.
		        Iterator listaDatosArchivo = session.createQuery(queryObjeto.toString()).list().iterator();

		        Object[] tupla = (Object[]) listaDatosArchivo.next();
		
		        strDevolucion[0] = tupla[0].toString();
		        if (Integer.parseInt(strDevolucion[0]) != 0){
			        strDevolucion[1] = tupla[1].toString();
			        strDevolucion[2] = tupla[2].toString();
		        }

		        //cometer la transaccion o sino no se escribe nada en la BD
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar los datos de la vista de Altura 1");

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

	
	public static String[] getDatosPerfilTemporarioHora(int hora) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener los datos para la hora en altura 1");
		log.info("Obtener los datos para la hora en altura 1");
		
		//List listaDatosArchivo = null;
		String[] strDevolucion = new String[3];
	    try {		
	    		//hago un query con todos los datos 
		        StringBuilder queryObjeto= new StringBuilder();
		        queryObjeto.append("select count(view1.id.idDatosTodos), sum(view1.id.velPromAlt1), sum(view1.id.desvioAlt1) from ViewDatosAltura1 as view1	where hour(view1.id.hora) = ");
		        queryObjeto.append(hora);
		        
		        //lo ejecuto y lo guardo en un iterador.
		        Iterator listaDatosArchivo = session.createQuery(queryObjeto.toString()).list().iterator();

		        Object[] tupla = (Object[]) listaDatosArchivo.next();
		
		        strDevolucion[0] = tupla[0].toString();
		        if (Integer.parseInt(strDevolucion[0]) != 0){
			        strDevolucion[1] = tupla[1].toString();
			        strDevolucion[2] = tupla[2].toString();
		        }

		        //cometer la transaccion o sino no se escribe nada en la BD
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar los datos de la vista de Altura 1");

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

	public static String[] getDatosDireccionVientoRangos(BigDecimal inicioRango, BigDecimal finRango) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
				
		// Iniciar la sesion con Hibernate
        SessionFactory sess = HibernateUtil.getSessionFactory();
        session = sess.getCurrentSession();
   
        // Comenzar la transaccion
        tx = session.beginTransaction();
        
        Logger log = Logger.getLogger("Obtener los datos para la dirección en altura 1");
		log.info("Obtener los datos para la dirección en altura 1");
		
		//List listaDatosArchivo = null;
		String[] strDevolucion = new String[1];
	    try {		
	    		//hago un query con todos los datos 
		        StringBuilder queryObjeto= new StringBuilder();
		        
		    	if (inicioRango.compareTo(finRango) > 0){
		    		 	queryObjeto.append("select count(view1.id.idDatosTodos) from ViewDatosAltura1 as view1	where (view1.id.dirPromAlt1 between ");
				     	queryObjeto.append(inicioRango);
				     	queryObjeto.append(" and 360  or view1.id.dirPromAlt1 between  0 and ");
				     	queryObjeto.append(finRango);
				     	queryObjeto.append(") and ( view1.id.dirPromAlt1 <> ");
//				     	queryObjeto.append(inicioRango);
				     	queryObjeto.append(finRango);
				     	queryObjeto.append(")");
				     	queryObjeto.append(" and ( view1.id.velPromAlt1 > 0 )");
				     	
				     	
		    	}else{
		    			queryObjeto.append("select count(view1.id.idDatosTodos) from ViewDatosAltura1 as view1	where ( view1.id.dirPromAlt1 between ");
		    			queryObjeto.append(inicioRango);
		    			queryObjeto.append(" and ");
		    			queryObjeto.append(finRango);
		    			queryObjeto.append(" ) and ( view1.id.dirPromAlt1 <> ");
//				     	queryObjeto.append(inicioRango);
		    			queryObjeto.append(finRango);
				     	queryObjeto.append(")");
				     	queryObjeto.append(" and ( view1.id.velPromAlt1 > 0 )");
		    	}
		       
		        
		        //lo ejecuto y lo guardo en una lista, no puedo usar un iterador por que devuelve un solo elemento 
		        List listaDatosArchivo = session.createQuery(queryObjeto.toString()).list();

		
		        strDevolucion[0] = listaDatosArchivo.get(0).toString();

		        //cometer la transaccion o sino no se escribe nada en la BD
				tx.commit();

				// cerrar la sesion
				//session.close();
				
	   } catch (HibernateException e) {

		   	System.out.println(e.getMessage());
		   	log.warn("Ocurrio un error al buscar los datos de la vista de Altura 1");

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


public static String[] getDatosFrecuenciaVelocidad(int inicioVelocidad, int finVelocidad) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
			
	// Iniciar la sesion con Hibernate
    SessionFactory sess = HibernateUtil.getSessionFactory();
    session = sess.getCurrentSession();

    // Comenzar la transaccion
    tx = session.beginTransaction();
    
    Logger log = Logger.getLogger("Obtener los datos para la frecuencia de velocidad en la altura 1");
	log.info("Obtener los datos para la frecuencia de velocidad en la altura 1");
	
	//List listaDatosArchivo = null;
	String[] strDevolucion = new String[1];
    try {		
    		//hago un query con todos los datos 
	        StringBuilder queryObjeto= new StringBuilder();
	        
	        queryObjeto.append("select count(view1.id.idDatosTodos) from ViewDatosAltura1 as view1	where (view1.id.velPromAlt1 between ");
			queryObjeto.append(inicioVelocidad);
			queryObjeto.append(" and ");
			queryObjeto.append(finVelocidad);
			queryObjeto.append(") and ( view1.id.velPromAlt1 <> ");
			queryObjeto.append(finVelocidad);
			queryObjeto.append(")");
			     		
	        
	        //lo ejecuto y lo guardo en una lista, no puedo usar un iterador por que devuelve un solo elemento 
	        List listaDatosArchivo = session.createQuery(queryObjeto.toString()).list();

	
	        strDevolucion[0] = listaDatosArchivo.get(0).toString();

	        //cometer la transaccion o sino no se escribe nada en la BD
			tx.commit();

			// cerrar la sesion
			//session.close();
			
   } catch (HibernateException e) {

	   	System.out.println(e.getMessage());
	   	log.warn("Ocurrio un error al buscar los datos de la vista de Altura 1");

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
