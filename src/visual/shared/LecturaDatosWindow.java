package visual.shared;


//
//import inicio.client.AccesoDatosService;
//import inicio.client.AccesoDatosServiceAsync;
//import inicio.funcionescarga.FuncionesComunes;
//import inicio.shared.Mensaje;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import visual.client.AccesoDatosService;
import visual.client.AccesoDatosServiceAsync;
import com.smartgwt.client.widgets.Window; 
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;


public class LecturaDatosWindow extends FormPanel {

	
	public String archivoString = null;
	
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
	
	// Se crea el formulario 
    final FormPanel form = new FormPanel();
    
    final Window win = new Window();
    
    String lugarAdquisicion;
 	 int deadband1 = 0;
 	 int deadband2 = 0;
 	 int deadband3 = 0;
 	String sobreescritura;
 	 int altura1 = 0;
 	 int altura2 = 0; 
 	 int altura3 = 0; 
 	 int intervalo = 0; 
 	 int cantidadPaquetes = 0;
 	 int maxDivisonDireccionViento = 0;
 	
	public LecturaDatosWindow(){		
		
		
	 	
	// y se pone a apuntar al servicio
    form.setAction(UPLOAD_ACTION_URL);

    //Se fija al formulario para que utilice el metodo POST y
    //la codificación multipart MIME
    form.setEncoding(FormPanel.ENCODING_MULTIPART);
    form.setMethod(FormPanel.METHOD_POST);
    form.addStyleName("table-center");
    form.addStyleName("demo-FormPanel");
    form.setSize("365px", "374px");

    // Se crea un  panel y se pone en el formulario.
    VerticalPanel panel = new VerticalPanel();
    form.setWidget(panel);
    panel.setSize("356px", "373px");
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    panel.add(horizontalPanel);
    
    HorizontalPanel horPanLugarAdq = new HorizontalPanel();
    panel.add(horPanLugarAdq);
    horPanLugarAdq.setWidth("348px");
    
    final Label lblLugarAdq = new Label("    Lugar Adquisición");
    
    horPanLugarAdq.add(lblLugarAdq);
    lblLugarAdq.setWidth("151px");
    
    final TextBox textBoxLugarAdq = new TextBox();
    textBoxLugarAdq.setAlignment(TextAlignment.CENTER);
    textBoxLugarAdq.setStyleName("gwt-TextBox-Ok");
    textBoxLugarAdq.setName("Lugar Adquisición");
    horPanLugarAdq.add(textBoxLugarAdq);
    textBoxLugarAdq.setWidth("131px");
    textBoxLugarAdq.setText("AACCCCCC");
    
    HorizontalPanel horPanDeadBand1 = new HorizontalPanel();
    panel.add(horPanDeadBand1);
    horPanDeadBand1.setWidth("348px");
    
    final Label lblDeadBand1 = new Label("    Dead Band 1");
    horPanDeadBand1.add(lblDeadBand1);
    lblDeadBand1.setWidth("151px");
    
    final IntegerBox intBoxDeadBand1 = new IntegerBox();
    intBoxDeadBand1.setText("90");
    intBoxDeadBand1.setStyleName("gwt-TextBox-Ok");
    intBoxDeadBand1.setAlignment(TextAlignment.CENTER);
    horPanDeadBand1.add(intBoxDeadBand1);
    intBoxDeadBand1.setWidth("131px");
    
    HorizontalPanel horPanDeadBand2 = new HorizontalPanel();
    panel.add(horPanDeadBand2);
    horPanDeadBand2.setWidth("348px");
    
    final Label lblDeadBand2 = new Label("    Dead Band 2");
    horPanDeadBand2.add(lblDeadBand2);
    lblDeadBand2.setWidth("151px");
    
    final IntegerBox intBoxDeadBand2 = new IntegerBox();
    intBoxDeadBand2.setText("90");
    intBoxDeadBand2.setStyleName("gwt-TextBox-Ok");
    intBoxDeadBand2.setAlignment(TextAlignment.CENTER);
    horPanDeadBand2.add(intBoxDeadBand2);
    intBoxDeadBand2.setWidth("131px");
    
    HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
    panel.add(horizontalPanel_1);
    horizontalPanel_1.setWidth("348px");
    
    final Label lblDeadBand3 = new Label("    Dead Band 3");
    horizontalPanel_1.add(lblDeadBand3);
    lblDeadBand3.setWidth("151px");
    
    final IntegerBox intBoxDeadBand3 = new IntegerBox();
    intBoxDeadBand3.setAlignment(TextAlignment.CENTER);
    intBoxDeadBand3.setStyleName("gwt-TextBox-Ok");
    horizontalPanel_1.add(intBoxDeadBand3);
    intBoxDeadBand3.setWidth("131px");
    
    HorizontalPanel horPanSobreescritura = new HorizontalPanel();
    panel.add(horPanSobreescritura);
    horPanSobreescritura.setWidth("348px");
    
    final Label lblSobreescritura = new Label("    Sobreescritura");
    horPanSobreescritura.add(lblSobreescritura);
    lblSobreescritura.setWidth("151px");
    
    final TextBox textBoxSobrees = new TextBox();
    textBoxSobrees.setAlignment(TextAlignment.CENTER);
    textBoxSobrees.setStyleName("gwt-TextBox-Ok");
    textBoxSobrees.setText("NO");
    horPanSobreescritura.add(textBoxSobrees);
    textBoxSobrees.setWidth("131px");
    
    HorizontalPanel horPanAlturaUno = new HorizontalPanel();
    panel.add(horPanAlturaUno);
    horPanAlturaUno.setWidth("348px");
    
    final Label lblAlturaUno = new Label("    Altura Uno");
    horPanAlturaUno.add(lblAlturaUno);
    lblAlturaUno.setWidth("151px");
    
    final IntegerBox intBoxAlt1 = new IntegerBox();
    intBoxAlt1.setText("10");
    intBoxAlt1.setStyleName("gwt-TextBox-Ok");
    intBoxAlt1.setAlignment(TextAlignment.CENTER);
    horPanAlturaUno.add(intBoxAlt1);
    intBoxAlt1.setWidth("131px");
    
    HorizontalPanel horPanAlturaDos = new HorizontalPanel();
    panel.add(horPanAlturaDos);
    horPanAlturaDos.setWidth("348px");
    
    final Label lblAlturaDos = new Label("    Altura Dos");
    horPanAlturaDos.add(lblAlturaDos);
    lblAlturaDos.setWidth("151px");
    
    final IntegerBox intBoxAlt2 = new IntegerBox();
    intBoxAlt2.setText("11");
    intBoxAlt2.setStyleName("gwt-TextBox-Ok");
    intBoxAlt2.setAlignment(TextAlignment.CENTER);
    horPanAlturaDos.add(intBoxAlt2);
    intBoxAlt2.setWidth("131px");
    
    HorizontalPanel horPanAlturaTres = new HorizontalPanel();
    panel.add(horPanAlturaTres);
    horPanAlturaTres.setWidth("348px");
    
    final Label lblAlturaTres = new Label("    Altura Tres");
    horPanAlturaTres.add(lblAlturaTres);
    lblAlturaTres.setWidth("151px");
    
    final IntegerBox intBoxAlt3 = new IntegerBox();
    intBoxAlt3.setStyleName("gwt-TextBox-Ok");
    intBoxAlt3.setAlignment(TextAlignment.CENTER);
    horPanAlturaTres.add(intBoxAlt3);
    intBoxAlt3.setWidth("131px");
    
    HorizontalPanel horPanIntervalo = new HorizontalPanel();
    panel.add(horPanIntervalo);
    horPanIntervalo.setWidth("348px");
    
    final Label lblIntervaloMin = new Label("    Intervalo Min");
    horPanIntervalo.add(lblIntervaloMin);
    lblIntervaloMin.setSize("151px", "");
    
    final IntegerBox intBoxInterv = new IntegerBox();
    intBoxInterv.setText("10");
    intBoxInterv.setStyleName("gwt-TextBox-Ok");
    intBoxInterv.setAlignment(TextAlignment.CENTER);
    horPanIntervalo.add(intBoxInterv);
    intBoxInterv.setWidth("131px");
    
    HorizontalPanel horPanCantPaquetes = new HorizontalPanel();
    panel.add(horPanCantPaquetes);
    horPanCantPaquetes.setWidth("348px");
    
    final Label lblCantPaq = new Label("    Cantidad de Paquetes");
    horPanCantPaquetes.add(lblCantPaq);
    lblCantPaq.setWidth("151px");
    
    final IntegerBox intBoxCantPaq = new IntegerBox();
    intBoxCantPaq.setText("3276");
    intBoxCantPaq.setStyleName("gwt-TextBox-Ok");
    intBoxCantPaq.setAlignment(TextAlignment.CENTER);
    horPanCantPaquetes.add(intBoxCantPaq);
    intBoxCantPaq.setWidth("131px");
    
    HorizontalPanel horPanDireccionViento = new HorizontalPanel();
    panel.add(horPanDireccionViento);        
    horPanDireccionViento.setWidth("348px");
    
    final Label lblMaxDireccionViento = new Label("    División Dirección de Viento");
    horPanDireccionViento.add(lblMaxDireccionViento);
    lblMaxDireccionViento.setWidth("151px");
    
    final ListBox comboBoxMaxDirViento = new ListBox(false);
    comboBoxMaxDirViento.setDirectionEstimator(true);
    comboBoxMaxDirViento.setStyleName("gwt-TextBox-Ok");
    comboBoxMaxDirViento.addItem("4");
    comboBoxMaxDirViento.addItem("16");
    comboBoxMaxDirViento.addItem("20");
    comboBoxMaxDirViento.addItem("24");
    comboBoxMaxDirViento.addItem("28");
    comboBoxMaxDirViento.addItem("32");
    comboBoxMaxDirViento.setSelectedIndex(0);
    horPanDireccionViento.add(comboBoxMaxDirViento);
    comboBoxMaxDirViento.setWidth("135px");
    
//    final IntegerBox intBoxDeadBand1 = new IntegerBox();
//    intBoxDeadBand1.setText("90");
//    intBoxDeadBand1.setStyleName("gwt-TextBox-Ok");
//    intBoxDeadBand1.setAlignment(TextAlignment.CENTER);
//    horPanDireccionViento.add(intBoxDeadBand1);
//    intBoxDeadBand1.setWidth("131px");
    HorizontalPanel horPanEnvioUno = new HorizontalPanel();
    panel.add(horPanEnvioUno);
    horPanEnvioUno.setWidth("348px");
    horPanCantPaquetes.setWidth("348px");
    
            // Se crea un TextBox, se le da un nombre.
//            final TextBox tb = new TextBox();
//            tb.setName("textBoxFormElement");
//            panel.add(tb);
//            tb.setWidth("211px");
    
        
        // Se crea el widget FileUpload.
        final FileUpload upload = new FileUpload();
        horPanEnvioUno.add(upload);
        upload.setName("uploadFormElement");
        panel.setCellHorizontalAlignment(upload, HasHorizontalAlignment.ALIGN_CENTER);
        panel.setCellVerticalAlignment(upload, HasVerticalAlignment.ALIGN_BOTTOM);
        upload.setSize("296px", "35px");
            
                // Se crea el botón y su evento
                Button button = new Button("Submit", new ClickHandler() {
                    public void onClick(ClickEvent event) {
                    	// controlo que el archivo enviado sea de tipo txt
                    	String filename =  upload.getFilename();
                    	System.out.println("upload " +  upload.toString());
                    	
                    	int index = filename.lastIndexOf(".");
                    	String ext = filename.substring(index + 1);
                    	if (ext.equalsIgnoreCase("txt")){
                    		            		
//                    		com.google.gwt.user.client.Window.alert("OK: El archivo es correcto con la extensión :" + filename) ;
                    		//Se hace el submit del formulario para 
                            //enviar los datos al servidor
                            form.submit();
                    	}
                    		else                    			
                    			Mensaje.MensajeError("ERROR: La extensión del archivo no es correcta \" " + ext + " \"");
//                    			com.google.gwt.user.client.Window.alert("ERROR: El archivo no es correcto con la extensión" + ext);          	                   
                    }
                });
                horPanEnvioUno.add(button);
                button.setText("Enviar");
                horPanEnvioUno.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_RIGHT);
            panel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_RIGHT);


    // Se adiciona el control de eventos al formulario para
    // hacer las operaciones necesarias antes de enviar el
    // formulario
    form.addSubmitHandler(new FormPanel.SubmitHandler() {
        public void onSubmit(SubmitEvent event) {
            // Este evento se activa justo antes de que el
            // formulario sea activado. 
            // Podemos aprovechar esta oportunidad para 
            // realizar alguna validación
//            if (tb.getText().length() == 0) {
//            	com.google.gwt.user.client.Window.alert("La caja de texto no puede estar vacia");
//                event.cancel();
//            }
        	lblLugarAdq.setStyleName("gwt-Label");
        	lblDeadBand1.setStyleName("gwt-Label");
        	lblAlturaUno.setStyleName("gwt-Label");
        	lblDeadBand2.setStyleName("gwt-Label");
        	lblAlturaDos.setStyleName("gwt-Label");
        	lblDeadBand3.setStyleName("gwt-Label");
        	lblAlturaTres.setStyleName("gwt-Label");
        	lblSobreescritura.setStyleName("gwt-Label");
        	lblIntervaloMin.setStyleName("gwt-Label");
        	lblCantPaq.setStyleName("gwt-Label");
        	
        	int cont = 0;
        	if(textBoxLugarAdq.getText().length() == 0){
        		lblLugarAdq.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else{
        		lugarAdquisicion = textBoxLugarAdq.getText();
        	}
        		
        	if(intBoxDeadBand1.getText().length() == 0){
        		lblDeadBand1.setStyleName("gwt-Label-Error");
        		cont ++;
        	} else deadband1 = intBoxDeadBand1.getValue();
        		
        	if(intBoxAlt1.getText().length() == 0){
        		lblAlturaUno.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else altura1 = intBoxAlt1.getValue();
        	
        	if(((intBoxDeadBand2.getText().length() == 0) && (intBoxAlt2.getText().length() != 0)) || 
        	  ((intBoxDeadBand2.getText().length() != 0) && (intBoxAlt2.getText().length() == 0))){
        		lblDeadBand2.setStyleName("gwt-Label-Error");
        		lblAlturaDos.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else{ 
        		if ((intBoxDeadBand2.getText().length() != 0) && (intBoxAlt2.getText().length() != 0)){
        		deadband2 = intBoxDeadBand2.getValue();
        		 altura2 = intBoxAlt2.getValue();
        		}
        	}
        	
        	if(((intBoxDeadBand3.getText().length() == 0) && (intBoxAlt3.getText().length() != 0)) || 
        	  ((intBoxDeadBand3.getText().length() != 0) && (intBoxAlt3.getText().length() == 0))){
        		lblDeadBand3.setStyleName("gwt-Label-Error");
        		lblAlturaTres.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else{ 
        		if ((intBoxDeadBand3.getText().length() != 0) && (intBoxAlt3.getText().length() != 0)){
        		   deadband3 = intBoxDeadBand3.getValue();
        		   altura3 = intBoxAlt3.getValue();
    			}
        	}

        	if(textBoxSobrees.getText().length() == 0){
        		lblSobreescritura.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else sobreescritura = textBoxSobrees.getText();
        	
        	if(intBoxInterv.getText().length() == 0){
        		lblIntervaloMin.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else intervalo = intBoxInterv.getValue();
        	
        	if(intBoxCantPaq.getText().length() == 0){
        		lblCantPaq.setStyleName("gwt-Label-Error");
        		cont ++;
        	}else cantidadPaquetes = intBoxCantPaq.getValue();
        	        	
        	 int indice = comboBoxMaxDirViento.getSelectedIndex();        
        	 
        	 String indiceSeleccionado = comboBoxMaxDirViento.getItemText(indice).trim();
        	 indiceSeleccionado = indiceSeleccionado.replaceAll(",", ".");
        	 maxDivisonDireccionViento = new Integer(indiceSeleccionado);
     		
        	if (cont != 0) {
        		Mensaje.MensajeError("   Por favor complete el formulario");
//        		com.google.gwt.user.client.Window.alert("   Por favor complete el formulario");
        		event.cancel();
        	}
        }
    });

        //Aqui se administra la respuesta que se retorna a la
        //solicitud actual , en este caso para saber si 
        //se subio el archivo o no.

    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
        public void onSubmitComplete(SubmitCompleteEvent event) {
            // Cuando el envío del formulario se completa 
            // con éxito, Este evento es lanzado. 
            // Suponiendo que el servicio ha devuelto una
            // respuesta html o texto, podemos lanzar esa
            // respuesta como un evento en una                      
        	// ventana.
        	win.destroy();
//        	com.google.gwt.user.client.Window.alert(event.getResults());
        	
//        	System.out.println("  ---  " + event.getResults());
        	archivoString = event.getResults();
        	
        	// Creo el la interfaz del servicio RPC de acceso a datos para ser usado en la creación del archivo
        	final AccesoDatosServiceAsync accesService = GWT.create(AccesoDatosService.class);
        	
        	accesService.procesarArchivo(archivoString, new AsyncCallback<Void>() {

      	      @Override
      	      public void onFailure(Throwable caught) {
      	        
      	    	  Mensaje.MensajeError("No se pudo crear el archivo para procesar los datos" + caught.getMessage());
//      	        com.google.gwt.user.client.Window.alert("No se pudo crear el archivo para procesar los datos" + caught.getMessage());
      	      }

      	      @Override
      	      public void onSuccess(Void result) {
      	    	  Mensaje.MensajeConfirmacion("El archivo ha sido guardado con exito");
//      	    	 com.google.gwt.user.client.Window.alert("El archivo ha sido guardado con exito");      	
      	    	
      	    // Creo la interfaz del servicio RPC para guardar los datos del formulario
      	    	final AccesoDatosServiceAsync accesServiceDatosArchivo = GWT.create(AccesoDatosService.class);
      	   
				accesServiceDatosArchivo.setDatosArchivo(lugarAdquisicion, deadband1, deadband2, deadband3, 
      	    			sobreescritura, altura1, altura2, altura3, intervalo, cantidadPaquetes, new AsyncCallback<Void>() {

      	           	      @Override
      	           	      public void onFailure(Throwable caught) {
      	           	        Mensaje.MensajeError("No se pudieron guardar los datos del formulario" + caught.getMessage());
//      	           	        com.google.gwt.user.client.Window.alert("No se pudieron guardar los datos del formulario" + caught.getMessage());
      	           	      }

      	           	      @Override
      	           	      public void onSuccess(Void result) {
      	           	    	  Mensaje.MensajeConfirmacion("Los datos del Formulario se han guardado con exito");
//      	           	    	 com.google.gwt.user.client.Window.alert("Los datos del Formulario se han guardado con exito");  
      	           	 
      	           	    	  // Creo la interfaz del servicio RPC de acceso a datos para ser usado en la inserción en la base
      	                 	final AccesoDatosServiceAsync accesServiceIns = GWT.create(AccesoDatosService.class);
      	                 	
      	                 	accesServiceIns.setDatosTodos(maxDivisonDireccionViento, new AsyncCallback<String>() {

      	               	      @Override
      	               	      public void onFailure(Throwable caught) {
      	               	        Mensaje.MensajeError("No se pudieron guardar los datos en la Base de Datos" + caught.getMessage());
//      	               	        com.google.gwt.user.client.Window.alert("No se pudieron guardar los datos en la Base de Datos" + caught.getMessage());
      	               	      }

      	               	      @Override
      	               	      public void onSuccess(String result) {
      	               	    	  if (!(result.equalsIgnoreCase("OK")))      	               	    	
      	               	    			  Mensaje.MensajeError(result);      	               	    			  
      	               	    	  else{
      	               	    		  Mensaje.MensajeConfirmacion("Los datos se han guardado con exito");      	               	    		        	               
//      	               	    	 com.google.gwt.user.client.Window.alert("Los datos se han guardado con exito");
      	               	    	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la inserción en la base
      	               	 	   	final AccesoDatosServiceAsync accesServiceXML = GWT.create(AccesoDatosService.class);
      	               	 	   	
      	               	 	   	accesServiceXML.setTodasTablaEnXML( new AsyncCallback<Void>() {

      	               	 	 	      @Override
      	               	 	 	      public void onFailure(Throwable caught) {
      	               	 	 	        Mensaje.MensajeError("No se pudieron generar los archivo .ds.xml para las tablas  " + caught.getMessage());
//      	               	 	 	        com.google.gwt.user.client.Window.alert("No se pudieron guardar los datos en la Base de Datos" + caught.getMessage());
      	               	 	 	      }

      	               	 	 	      @Override
      	               	 	 	      public void onSuccess(Void result) { 	    	  
      	               	 	 	    		  Mensaje.MensajeConfirmacion("Los archivo .ds.xml se generaron con éxito");      	               	    		        	               
//      	               	 	 	    	 com.google.gwt.user.client.Window.alert("Los datos se han guardado con exito");      	
      	               	 	 	      	} 
      	               	 	 	      });
      	               	      		}
      	               	      	} 
      	               	      });
      	           	      	} 
      	           	      });
				
      	    
      	    	 
      	      	} 
      	      });
        //  genero el documento xml con los datos desde la tabla que necesito
//            Document docXML = DocumentHelper.createDocument();
        //  Este elemento junta los datos de la tabla en xml y los guarda en el documento  
           
//            docXML.addComment(event.getResults());
        	
        	
//        	System.out.println("valor en String: " + event.getResults());//+ docXML.getStringValue()
        	
//        	com.google.gwt.user.client.Window.alert("El archivo fué leido con exito");
            
        }
    });

    win.setWidth(382);  
    win.setHeight(410);  
    win.setTitle("Seleccione el Archivo con los datos eólicos");  
    win.setShowMinimizeButton(false);  
    win.setIsModal(true);  
    win.setShowModalMask(true);  
    win.centerInPage();
    win.addItem(form);
    win.show();
  }
	 
}
