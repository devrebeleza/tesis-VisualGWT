package visual.servXML;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.mapping.List;



import visual.client.basededatos.*;
import visual.hibernate.HibernateUtil;


public class CopyOfTablaEnXML {

  public Document docXML;
  public CopyOfTablaEnXML(){
    
    
//    
////    Document docXML = DocumentHelper.createDocument();
//    Document docXML = new Document();
//    
////    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//    Session session = factory.openSession();
//    Session dom4jSession = session.getSession(EntityMode.DOM4J);
//    Transaction tx = session.beginTransaction();
//
//    List results = dom4jSession.createQuery("from"+ nombreTabla).list(); 
//    
//    //c left join fetch c.accounts where c.lastName like :lastName").list();
//    
//    for ( int i=0; i<results.size(); i++ ) {
//        //add the customer data to the XML document
//        DatosArchivo customer = (DatosArchivo) results.get(i);
//        docXML.add(customer);
//    }
//
//    tx.commit();
//    session.close();
   
  }
  
  public Document getTablaEnXML(String nombreTabla) throws IOException{
    
    System.out.println("obteniendo tabla "+ nombreTabla + " en XML");
    Session session = null;
    
    try{
      String consultaHQL = "FROM " + nombreTabla;
      
      session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      List<DatosArchivo> resultado = new ArrayList<DatosArchivo>(session.createQuery(consultaHQL).list());
      
      

//    genero el documento xml con los datos desde la tabla que necesito
      Document docXML = (Document) DocumentHelper.createDocument();
//    Este elemento junta los datos de la tabla en xml y los guarda en el documento  
      Element elementoTabla = (Element) ((Document) docXML).addElement(nombreTabla);
      
      //agrego cada uno de los elementos recuperados al elementoTabla y se agrega al documento docXML   
      for ( int i=0; i<resultado.size(); i++ ) {      
        DatosArchivo elemento = (DatosArchivo) resultado.get(i);
        elementoTabla.add((Attribute) elemento);
      }

    
//    System.out.println(docXML.asXML());
//    escribir el archivo xml
//    XMLWriter salidaXML = new XMLWriter(new FileWriter(new File("docXML/"+nombreTabla+".xml")));
//    
////    no genera nada si docXML es chico o se corta abruptamente si docXML es muy grande
//    salidaXML.write(docXML.asXML());
//    salidaXML.write("una salida después > < > , > <");
//    
//    
//    System.out.println("salida XML en string " + salidaXML.toString());
    System.out.println(((Node) docXML).asXML());
    session.getTransaction().commit();
    session.close();
    
    return  docXML;
    
    }catch (HibernateException e) {
      System.out.println(e.getMessage());
     
     // cuando ocurre un error hace rollback
      if (session.getTransaction() != null)
        try {
          session.getTransaction().rollback();
        } catch (HibernateException e1) {
          
          System.out.println("El rollback no fue exitoso");
        }

       if (session != null)
        try {
          session.close();
        } catch (HibernateException e2) {
          System.out.println("El cierre de sesion no fue exitoso");      
        }      
    } 
    return  null;
  }
	
  
 public void setTablaEnXML(String nombreTabla) throws IOException{
    
    Logger log = Logger.getLogger("obteniendo tabla "+ nombreTabla + "en XML");
    log.info("obteniendo tabla "+ nombreTabla + "en XML");
    System.out.println("obteniendo tabla "+ nombreTabla + " en XML");
    
    Session session = null;
    Transaction tx = null;
//  creo la configuración dependiendo de la tabla a mapear
//    Configuration config = new Configuration();
    AnnotationConfiguration config = new AnnotationConfiguration();  // se cambio desde el tutorial por que no andaba con configuration
    
    if (nombreTabla.equals("DatosArchivo")){
      // verificar que toma bien la clase, si no, agregar en este paquete todos los *.hbm.xml y llamar a addFile("*.hbm.xml")
      // anda perfecto con .class y sin .class
          System.out.println("clase datos archivo");
          config.addClass(DatosArchivo.class);
    }else if (nombreTabla.equals("DatosPerfilTemporarioHora")){
          System.out.println("clase datos perfil temporario hora");
          config.addClass(DatosPerfilTemporarioHora.class);
    }else if (nombreTabla.equals("DatosPerfilTemporarioMes")){
          System.out.println("clase datos perfil temporario Mes");
          config.addClass(DatosPerfilTemporarioMes.class);
    }else if (nombreTabla.equals("DatosPerfilVertical")){
          System.out.println("clase datos perfil vertical");
          config.addClass(DatosPerfilVertical.class);
    }else if (nombreTabla.equals("DatosTodos")){
          System.out.println("clase datos todos");
          config.addClass(DatosTodos.class);
    }else if (nombreTabla.equals("DireccionVientoRangos")){
          System.out.println("clase direccion viento rangos");
          config.addClass(DireccionVientoRangos.class);
    };
    
    try{
//  creo el sessionfactory desde la configuración y abro la sesión  (tuve que agregar el configure())
    SessionFactory sessionFactory= config.configure().buildSessionFactory();    
    session = sessionFactory.openSession();
//  inicio una nueva sesión en modo DOM4J (permite interactuar con documentos W3C - framework XML flexible para java)
    org.hibernate.Session dom4jSession = session.getSession(EntityMode.DOM4J);

    // Comenzar la transaccion
     tx = session.beginTransaction();
     
//  genero el documento xml con los datos desde la tabla que necesito
    docXML = DocumentHelper.createDocument();
//  Este elemento junta los datos de la tabla en xml y los guarda en el documento  
    Element elementoTabla = docXML.addElement(nombreTabla);
    
//    preparo la consulta
    String consultaHQL = "FROM " + nombreTabla;
    List results = dom4jSession.createQuery(consultaHQL).list(); 
    
    //agrego cada uno de los elementos recuperados al elementoTabla y se agrega al documento docXML   
    for ( int i=0; i<results.size(); i++ ) {      
       Element elemento = (Element) results.get(i);
       elementoTabla.add(elemento);
    }

    
//    System.out.println(docXML.asXML());
//    escribir el archivo xml
//    XMLWriter salidaXML = new XMLWriter(new FileWriter(new File("docXML/"+nombreTabla+".xml")));
//    
////    no genera nada si docXML es chico o se corta abruptamente si docXML es muy grande
//    salidaXML.write(docXML.asXML());
//    salidaXML.write("una salida después > < > , > <");
//    
//    
//    System.out.println("salida XML en string " + salidaXML.toString());
//    System.out.println(docXML.asXML());
    tx.commit();
    session.close();

    
    }catch (HibernateException e) {
      System.out.println(e.getMessage());
      log.warn("Ocurrio un error al intentar obtener datos de la tabla " + nombreTabla);
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
  }
}
