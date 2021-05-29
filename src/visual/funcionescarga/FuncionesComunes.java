package visual.funcionescarga;

import java.io.IOException;
import java.math.BigDecimal;

import java.sql.Savepoint;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import visual.hibernate.HibernateUtil;

public class FuncionesComunes {
//	no funciona por el momento el save point
	Session session = null;
	private Savepoint savepoint;
	
	static BigDecimal convertirStrABigDecimal(String string) {
		// TODO Auto-generated method stub
		String str = string.trim();
		str = str.replaceAll(",", ".");
		return(new BigDecimal(str));
	}
	
	static Integer convertirStrAInteger(String string) {
		// TODO Auto-generated method stub
		String str = string.trim();
		str = str.replaceAll(",", ".");
		return(new Integer(str));
	}
	

public static List getDatosTablaParaGrafico(String nombreTabla) throws IOException{
		
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
		       String consulta="from " + nombreTabla;
			        
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


//	public Savepoint setSavepoint(final String savePoint)
//	{
//	   session.getSession(null).doWork(new Work()
//	   {
//	      public void execute(Connection connection) throws SQLException
//	      {
//	         savepoint = connection.setSavepoint(savePoint);
//	      }
//	   });
//	   return savepoint;
//	}

//	public void rollbackSavepoint(final Savepoint savepoint)
//	{
//	   getSession().doWork(new Work(){
//	      public void execute(Connection connection) throws SQLException
//	      {
//	         connection.rollback(savepoint);
//	      }
//	   });
//	}
}
