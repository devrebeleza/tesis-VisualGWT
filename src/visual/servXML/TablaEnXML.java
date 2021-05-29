package visual.servXML;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.mapping.List;


import org.hibernate.classic.Session;


import visual.client.basededatos.*;
import visual.hibernate.HibernateUtil;


public class TablaEnXML {

//  public Document docXML;
  public TablaEnXML(){
      
  }
  
  public Document getTablaEnXML(String nombreTabla) throws IOException{
    
    Logger log = Logger.getLogger("obteniendo tabla "+ nombreTabla + " en XML");
    log.info("obteniendo tabla "+ nombreTabla + " en XML");
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
    Document docXML = DocumentHelper.createDocument();
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
    File fl = new File("docXML/"+nombreTabla+".ds.xml");
    FileWriter fwriter = new FileWriter(fl);
    
    fwriter.write(docXML.asXML());
	fwriter.flush();
	fwriter.close();
//    
////    no genera nada si docXML es chico o se corta abruptamente si docXML es muy grande
//    salidaXML.write(docXML.asXML());
//    salidaXML.write("una salida después > < > , > <");
//    
//    
//    System.out.println("salida XML en string " + salidaXML.toString());
	System.out.println("salida XML en string " + fl.toString());
    System.out.println(docXML.asXML());
    tx.commit();
    session.close();
    
    return  docXML;
    
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
    return  null;
  }
	
  
 public void setTablaEnXML(String nombreTabla) throws IOException{
    
    Logger log = Logger.getLogger("obteniendo tabla "+ nombreTabla + " en XML");
    log.info("obteniendo tabla "+ nombreTabla + " en XML");
    System.out.println("obteniendo tabla "+ nombreTabla + " en XML");
    
    Session session = null;
    Transaction tx = null;
    File fl = new File("docXML/"+nombreTabla+".ds.xml");
    
    FileWriter fwriter = new FileWriter(fl);
    
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
    	System.out.println("dentro del try de tablaEnXML");
//  creo el sessionfactory desde la configuración y abro la sesión  (tuve que agregar el configure())   
//    	SessionFactory sessionFactory= config.buildSessionFactory(); 
//    SessionFactory sessionFactory= config.configure().buildSessionFactory(); -- esto funcionaba antes, ahora no funciona
    
//    session = sessionFactory.openSession();
    session = HibernateUtil.getSessionFactory().getCurrentSession();  // agregado para probar el acceso normal a hibernate
    
 // Comenzar la transaccion
    tx = session.beginTransaction();
//  inicio una nueva sesión en modo DOM4J (permite interactuar con documentos W3C - framework XML flexible para java)
    org.hibernate.Session dom4jSession = session.getSession(EntityMode.DOM4J);
    
     
     
//  genero el documento xml con los datos desde la tabla que necesito
    Document docXML = DocumentHelper.createDocument();
//  Este elemento junta los datos de la tabla en xml y los guarda en el documento  
    Element elementoTabla = docXML.addElement(nombreTabla);
    
//    preparo la consulta
    System.out.println("ejecuto la consulta From "+nombreTabla);
    String consultaHQL = "FROM " + nombreTabla;
    List results = dom4jSession.createQuery(consultaHQL).list(); 
    
    //agrego cada uno de los elementos recuperados al elementoTabla y se agrega al documento docXML   
    for ( int i=0; i<results.size(); i++ ) {      
       Element elemento = (Element) results.get(i);
       elementoTabla.add(elemento);
       System.out.println("elemento "+i+": "+elemento);
    }

    
//    System.out.println(docXML.asXML());
//    escribir el archivo xml
//    XMLWriter salidaXML = new XMLWriter(new FileWriter(new File("docXML/"+nombreTabla+".xml")));
//    File fl = new File("docXML/"+nombreTabla+".data.xml");
//    FileWriter fwriter = new FileWriter(fl);
    
//    tengo que modificar el archivo XML por que no puedo leer correctamente los tag, ya que me genera el mismo nombre
//    de tag superior e inferior EJ: <DatosArchivo><DatosArchivo> "datos del renglon" </DatosArchivo></DatosArchivo>
    String xmlModif = docXML.asXML();
    xmlModif = xmlModif.replaceFirst("<"+nombreTabla+">"+"<"+nombreTabla+">", "<List>"+"<"+nombreTabla+">");
    xmlModif = xmlModif.replaceFirst("</"+nombreTabla+">"+"</"+nombreTabla+">", "</"+nombreTabla+">"+"</List>");
    
//    agrego esto por que para las tablas ViewDatosAltura*, me agrega id como tag y no puedo leer el archivo luego
//    xmlModif = xmlModif.replaceFirst("<id>", "");
//    xmlModif = xmlModif.replaceFirst("</id>", "");
//    xmlModif = xmlModif.replaceFirst("1970-01-01", "");
//    System.out.println("documento XML : " + docXML.toString());
    System.out.println("-------------------------------------------------------------" );
    System.out.println("documento XML modificado: " + xmlModif);
//    fwriter.write(docXML.asXML());
    fwriter.write(xmlModif);
	fwriter.flush();
	fwriter.close();
	
	
//    
////    no genera nada si docXML es chico o se corta abruptamente si docXML es muy grande
//    salidaXML.write(docXML.asXML());
//    salidaXML.write("una salida después > < > , > <");
//    
//    
//    System.out.println("salida XML en string " + salidaXML.toString());
//	System.out.println("salida XML en string " + fl.toString());
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
