package visual.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	 
	private static final SessionFactory sessionFactory = buildSessionFactory();
 
	private static SessionFactory buildSessionFactory() {
		try {
			// load from different directory "hibernate.cfg.xml"
//			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			return sessionFactory;
 
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.out.println("Fallo de creación de SessionFactory Inicial " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
 
}

