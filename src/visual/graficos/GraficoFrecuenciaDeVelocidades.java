/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package visual.graficos;

import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.WHITE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import visual.client.AccesoDatosService;
import visual.client.AccesoDatosServiceAsync;
import visual.client.basededatos.DatosFrecuenciaVelocidad;
import visual.client.basededatos.DatosPerfilTemporarioHora;
import visual.client.basededatos.DireccionVientoRangos;
import visual.shared.ImageAnchor;
import visual.shared.Mensaje;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.VisualizationUtils;

import com.google.gwt.visualization.client.visualizations.Table;
//import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.Series;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;


/**
 * Demo for AreaChart visualization.
 */
public class GraficoFrecuenciaDeVelocidades extends VerticalPanel {
  
  int width = 600;
  int height = 340;
  private DataTable data;
  private DataTable dataAlt1;
  private DataTable dataAlt2;
  private DataTable dataAlt3;
  
  String nombreTabla = "DatosFrecuenciaVelocidad";
  private TabSet toptabSet;  
  
 //private ListBox listbDivision;
  private ListBox listbAlturas;
  private TabPanel  tab;
  
  public GraficoFrecuenciaDeVelocidades() {
    super();
    setBorderWidth(3);
    setPixelSize(width, height);
    
   // Label etiquetaDivision = new Label("Rangos Rosa de Viento");
    
    /*listbDivision = new ListBox(false);
    listbDivision.setSelectedIndex(0);
    listbDivision.addItem("4");
    listbDivision.addItem("8");
    listbDivision.addItem("12");
    listbDivision.addItem("16");
    listbDivision.addItem("20");
    listbDivision.addItem("24");
    listbDivision.setTitle("Rangos de Rosa de Viento");
    listbDivision.setName("División de Rosa de Viento");*/
    
    
    Label etiquetaAlturas = new Label("Altura a graficar");
    
    listbAlturas = new ListBox(false);
    listbAlturas.setSelectedIndex(0);
    listbAlturas.addItem("1");
    listbAlturas.addItem("2");
    listbAlturas.addItem("3");
    listbAlturas.setTitle("Seleccionar Altura");
    
    
    Button botonGraficar = new Button("Graficar");
    botonGraficar.addClickHandler(new botonClickHandler());
    
    HorizontalPanel hPanel = new HorizontalPanel();
   
   
    hPanel.add(etiquetaAlturas);
    hPanel.add(listbAlturas);
	//hPanel.add(etiquetaDivision);
    //hPanel.add(listbDivision);
    
    hPanel.add(botonGraficar);
    hPanel.setHorizontalAlignment(ALIGN_RIGHT);
    
 // inicializo el plano contenedor de los gráficos
    toptabSet = new TabSet();  
    toptabSet.setStyleName("crm-TabSet");
    toptabSet.setTabBarPosition(Side.TOP);  
    toptabSet.setWidth(1210);  
    toptabSet.setHeight(570);    
    
    tab = new TabPanel();
    tab.setWidth("1210");
    tab.setHeight("570"); 

    
    add(hPanel);  
  }
  
  
  private class botonClickHandler implements ClickHandler {
	    @Override
	    public void onClick(ClickEvent event) {
			/*int seleccionDivision = listbDivision.getSelectedIndex();
			String division =  listbDivision.getItemText(seleccionDivision);
			
			int divInt = Integer.parseInt(division);*/
			
			int seleccionAltura = listbAlturas.getSelectedIndex();
			String altura =  listbAlturas.getItemText(seleccionAltura);
			
			int alturaInt = Integer.parseInt(altura);
			 			
			setGraficoTab(alturaInt);	
		
		}			  
	  }
  
  
  public class Graphic extends HorizontalPanel {
    @SuppressWarnings("deprecation")
    public Graphic(final int altura) {
    	
      Runnable onLoadCallback = new Runnable() {
        public void run() {     
        	
        	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos para el gráfico
	 	   	final AccesoDatosServiceAsync accesServiceGraf = GWT.create(AccesoDatosService.class);
	 	   	
	 	   accesServiceGraf.getDatosTablaParaGraficoHistograma(nombreTabla, new AsyncCallback<List>() {

	 	 	      @Override
	 	 	      public void onFailure(Throwable caught) {
	 	 	        Mensaje.MensajeError("No se pudieron recuperar los datos para la Frecuencia de Velocidades  " + caught.getMessage());
	 	 	      }
	 	 	      @Override
	 	 	      public void onSuccess(final List result) { 	 	
	 	 	    	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos de alturas
	 	 		   	final AccesoDatosServiceAsync accesServiceAlt = GWT.create(AccesoDatosService.class);
	 	 		   	
	 	 		   	accesServiceAlt.getDatosAlturas(new AsyncCallback<String[]>() {

	 	 		 	      @Override
	 	 		 	      public void onFailure(Throwable caught) {
	 	 		 	        Mensaje.MensajeError("No se pudieron recuperar las alturas para la Frecuencia de Velocidades  " + caught.getMessage());
	 	 		 	      }
	 	 		 	      @Override
	 	 		 	      public void onSuccess(String[] alturas) { 	
	 	 		 	    	  
	 	 		 	    	  setDataTableTodos(result, alturas);	 	 	
	 	 		 	    	 
	 	 		 	    	  Boolean datosAltura1 = alturas[0] != null;
	 	 		 	    	  Boolean datosAltura2 = alturas[1] != null;
	 	 		 		  	  Boolean datosAltura3 = alturas[2] != null;
	 	 		 	    	  
	 	 		 		  	  /*double[] datosAlt = new double[division+1];
	 	 		 		  	  RadarPlot plotAlt = null;
	 	 		 		  	  RadarChart chartRosaViento = null;*/
	 	 		 		  	  
	 	 		 		  	  //Frame frame = new Frame();	 	 		 		  	  
	 	 					  //frame.setPixelSize(595, 530);
	 	 		 		  	
	 	 		 		  	  if ((altura == 1) && (datosAltura1) ){
	 	 		 		  		  setDataTableAlt1(result, alturas);	
	 	 		 		  		  	 	 		 		  		  
		 	 					  add(new ComboChart(getDataTableAlt1(), createOptionsComboChartAlt1()));
		 	 					  //add(new ColumnChart(getDataTableTodos(), createOptionsComboChart()));		 	 					  
		 	 					  
	 	 		 		  	  }else{
	 	 		 		  		  	if ((altura == 2) && (datosAltura2)){
	 	 		 		  		  		setDataTableAlt2(result, alturas);
	 	 		 		  		  		add(new ComboChart(getDataTableAlt2(), createOptionsComboChartAlt2()));
	 	 		 		  		  	}else{	 	 		 		  	  
	 	 		 		  		  		if ((altura == 3) && (datosAltura3)){
	 	 		 		  		  			setDataTableAlt3(result, alturas);
	 	 		 		  		  			add(new ComboChart(getDataTableAlt3(), createOptionsComboChartAlt3()));	 	 		 			 	    	 
	 	 		 		  		  		}
	 	 		 		  		  	}
	 	 		 		  	  }	 	 		 	    	  	 	 		
	 	 					  add(new Table(getDataTableTodos(),createOptionsTableChart()));	 					         
	 	 		 	      }
	 	 		  });
	 	 	 }
	 	   });
        }};    
      
        VisualizationUtils.loadVisualizationApi(onLoadCallback, ComboChart.PACKAGE,Table.PACKAGE);
    
    }
   
    
    private Options createOptionsComboChart() {
    	
        Options options = Options.create();
        options.setWidth(width);
        options.setHeight(height);
        options.setTitle("Frecuencia de Velocidad");
        
        AxisOptions tituloV = AxisOptions.create();
        tituloV.setTitle("Frecuencia (%)");
        options.setVAxisOptions(tituloV);      
        
        AxisOptions tituloH = AxisOptions.create();
        tituloH.setTitle("Velocidad del viento (m/s)");
        options.setHAxisOptions(tituloH);
        
        options.setSeriesType("bars");
        Series ser = null;
        ser.setType("line");
        options.setSeries(1, ser);
//        options.setBackgroundColor("gray");//  set3D(true);        
        return options;
      }
    
private Options createOptionsComboChartAlt1() {
    	
        Options options = Options.create();
        options.setWidth(width);
        options.setHeight(height);
        options.setTitle("Frecuencia de Velocidad");
        
        AxisOptions tituloV = AxisOptions.create();
        tituloV.setTitle("Frecuencia (%)");
        options.setVAxisOptions(tituloV);      
        
        AxisOptions tituloH = AxisOptions.create();
        tituloH.setTitle("Velocidad del viento (m/s)");
        options.setHAxisOptions(tituloH);
        
        options.setSeriesType("bars");
        Series ser = null;
        ser.setType("line");
        options.setSeries(1, ser);
//        options.setBackgroundColor("gray");//  set3D(true);        
        return options;
      }

private Options createOptionsComboChartAlt2() {
	
    Options options = Options.create();
    options.setWidth(width);
    options.setHeight(height);
    options.setTitle("Frecuencia de Velocidad");
    
    AxisOptions tituloV = AxisOptions.create();
    tituloV.setTitle("Frecuencia (%)");
    options.setVAxisOptions(tituloV);      
    
    AxisOptions tituloH = AxisOptions.create();
    tituloH.setTitle("Velocidad del viento (m/s)");
    options.setHAxisOptions(tituloH);
    
    options.setSeriesType("bars");
    Series ser = null;
    ser.setType("line");
    options.setSeries(1, ser);
     options.setColors("purple");
//    options.setBackgroundColor("gray");//  set3D(true);        
    return options;
  }
    
private Options createOptionsComboChartAlt3() {
	
    Options options = Options.create();
    options.setWidth(width);
    options.setHeight(height);
    options.setTitle("Frecuencia de Velocidad");
    
    AxisOptions tituloV = AxisOptions.create();
    tituloV.setTitle("Frecuencia (%)");
    options.setVAxisOptions(tituloV);      
    
    AxisOptions tituloH = AxisOptions.create();
    tituloH.setTitle("Velocidad del viento (m/s)");
    options.setHAxisOptions(tituloH);
    
    options.setSeriesType("bars");
    Series ser = null;
    ser.setType("line");
    options.setSeries(1, ser);
    
    options.setColors("red");
//    options.setBackgroundColor("gray");//  set3D(true);        
    return options;
  }
    @SuppressWarnings("deprecation")
    private com.google.gwt.visualization.client.visualizations.Table.Options createOptionsTableChart() {
      com.google.gwt.visualization.client.visualizations.Table.Options options = com.google.gwt.visualization.client.visualizations.Table.Options.create();
      String wi = String.valueOf(width);
      String he = String.valueOf(height+190);
      options.setWidth(wi);
      options.setHeight(he);     
      return options;
    }
    
    private AbstractDataTable getDataTableTodos() {       
    	
    	return data;
      
    }
    
    private AbstractDataTable getDataTableAlt1() {       
        return dataAlt1;
      }
    
    private AbstractDataTable getDataTableAlt2() {       
        return dataAlt2;
      }
    
    private AbstractDataTable getDataTableAlt3() {       
        return dataAlt3;
      }
  }
  

  public final void setDataTableTodos(List result, String[] alturas){
	  data = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  double weibull = 0.0;
	 	    	
	  int pos = 0;
	  DatosFrecuenciaVelocidad obj_DatosFrecuenciaVelocidad;
	 	  	  
	  data.addColumn(ColumnType.NUMBER, "Velocidad");
	  Boolean datosAltura1 = alturas[0] != null;
	  Boolean datosAltura2 = alturas[1] != null;
	  Boolean datosAltura3 = alturas[2] != null;
	  if(datosAltura1){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 1: " + alturas[0] + "m");
	  }
	  if(datosAltura2){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 2: " + alturas[1] + "m");
	  }
	  if(datosAltura3){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 3: " + alturas[2] + "m");	 	
	  }
	  
	  //data.addColumn(ColumnType.NUMBER, "Weibull: ");
	  NumberFormat nf = NumberFormat.getFormat("0.0000");                                       		            
	  while(pos < listaBackup.size()){
		  obj_DatosFrecuenciaVelocidad = (DatosFrecuenciaVelocidad) listaBackup.get(pos);
	 	  		  
	 	  int fila = data.addRow();
	 	  Double velocidad = obj_DatosFrecuenciaVelocidad.getVelocidad();	 	  	 	 
	 	  
	 	  data.setValue(fila, 0, velocidad);
	 	 //System.out.println("setDataTableTodos velocidad : " + velocidad);
	 	  if(datosAltura1){
		 	  		  
	 		  Double porcAlt1 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt1()));
	 		 //Double porcAlt1 = obj_DatosFrecuenciaVelocidad.getPorcentajeAlt1();
		 	  data.setValue(fila, 1, porcAlt1);
		 	  //weibull = porcAlt1;
		 	 //data.setValue(fila, 4, weibull);
		 	  
	 	  }
		  if(datosAltura2){
		  	  		  
		 	  Double porcAlt2 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt2()));
			  //Double porcAlt2 = obj_DatosFrecuenciaVelocidad.getPorcentajeAlt2();
		 	  data.setValue(fila, 2, porcAlt2);
		  }
		  if(datosAltura3){
		 	  		  
		 	  Double porcAlt3 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt3()));
			  //Double porcAlt3 = obj_DatosFrecuenciaVelocidad.getPorcentajeAlt3();
		 	  data.setValue(fila, 3, porcAlt3);
		  }
		  
	 	  		 
	 	  pos++;
	  }
	 	    	
  }
  
  public final void setDataTableAlt1(List result, String[] alturas){
	  dataAlt1 = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DatosFrecuenciaVelocidad obj_DatosFrecuenciaVelocidad;
	 	  	  
	  dataAlt1.addColumn(ColumnType.NUMBER, "Velocidad");
	  Boolean datosAltura1 = alturas[0] != null;
	  Boolean datosAltura2 = alturas[1] != null;
	  Boolean datosAltura3 = alturas[2] != null;
	  if(datosAltura1){
		  dataAlt1.addColumn(ColumnType.NUMBER, "Altura 1: " + alturas[0] + "m");
	  }
	 /* if(datosAltura2){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 2: " + alturas[1] + "m (%)");
	  }
	  if(datosAltura3){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 3: " + alturas[2] + "m (%)");	 	
	  }*/
	  
	  NumberFormat nf = NumberFormat.getFormat("0.0000");                                       		            
	  while(pos < listaBackup.size()){
		  obj_DatosFrecuenciaVelocidad = (DatosFrecuenciaVelocidad) listaBackup.get(pos);
	 	  		  
	 	  int fila = dataAlt1.addRow();
	 	  Double velocidad = obj_DatosFrecuenciaVelocidad.getVelocidad();
	 	 System.out.println("setDataTableAlt1 fila : " + fila);
	 	 
	 	 dataAlt1.setValue(fila, 0, velocidad);
	 	  if(datosAltura1){
		 	  		  
	 		  Double porcAlt1 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt1()));	 	 		  
	 		 dataAlt1.setValue(fila, 1, porcAlt1);
	 		System.out.println("setDataTableAlt1 porcAlt1 : " + porcAlt1);
		 	  
	 	  }
		 /* if(datosAltura2){
		  	  		  
		 	  Double porcAlt2 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt2()));
		 	  data.setValue(fila, 2, porcAlt2);
		  }
		  if(datosAltura3){
		 	  		  
		 	  Double porcAlt3 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt3()));
		 	  data.setValue(fila, 3, porcAlt3);
		  }*/
	 	  		 
	 	  pos++;
	  }
	 	    	
  }
  
  public final void setDataTableAlt2(List result, String[] alturas){
	  dataAlt2 = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DatosFrecuenciaVelocidad obj_DatosFrecuenciaVelocidad;
	 	  	  
	  dataAlt2.addColumn(ColumnType.NUMBER, "Velocidad");
	  Boolean datosAltura1 = alturas[0] != null;
	  Boolean datosAltura2 = alturas[1] != null;
	  Boolean datosAltura3 = alturas[2] != null;
	  /*if(datosAltura1){
		  dataAlt2.addColumn(ColumnType.NUMBER, "Altura 1: " + alturas[0] + "m (%)");
	  }*/
	  if(datosAltura2){
	 	  dataAlt2.addColumn(ColumnType.NUMBER, "Altura 2: " + alturas[1] + "m");
	  }
	  /*if(datosAltura3){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 3: " + alturas[2] + "m (%)");	 	
	  }*/
	  
	  NumberFormat nf = NumberFormat.getFormat("0.0000");                                       		            
	  while(pos < listaBackup.size()){
		  obj_DatosFrecuenciaVelocidad = (DatosFrecuenciaVelocidad) listaBackup.get(pos);
	 	  		  
	 	  int fila = dataAlt2.addRow();
	 	 Double  velocidad = obj_DatosFrecuenciaVelocidad.getVelocidad();
	 	 System.out.println("setDataTableAlt2 fila : " + fila);
	 	 
	 	 dataAlt2.setValue(fila, 0, velocidad);
	 	 
	 	  /*if(datosAltura1){
		 	  		  
	 		  Double porcAlt1 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt1()));	 	 		  
	 		 dataAlt1.setValue(fila, 1, porcAlt1);
		 	  
	 	  }*/
		  if(datosAltura2){
		  	  		  
		 	  Double porcAlt2 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt2()));
		 	  dataAlt2.setValue(fila, 1, porcAlt2);
		  }
		  /*if(datosAltura3){
		 	  		  
		 	  Double porcAlt3 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt3()));
		 	  data.setValue(fila, 3, porcAlt3);
		  }*/	 	  		 
	 	  pos++;
	  }
	 	    	
  }
  
  public final void setDataTableAlt3(List result, String[] alturas){
	  dataAlt3 = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DatosFrecuenciaVelocidad obj_DatosFrecuenciaVelocidad;
	 	  	  
	  dataAlt3.addColumn(ColumnType.NUMBER, "Velocidad");
	  Boolean datosAltura1 = alturas[0] != null;
	  Boolean datosAltura2 = alturas[1] != null;
	  Boolean datosAltura3 = alturas[2] != null;
	  /*if(datosAltura1){
		  dataAlt1.addColumn(ColumnType.NUMBER, "Altura 1: " + alturas[0] + "m (%)");
	  }
	  if(datosAltura2){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 2: " + alturas[1] + "m (%)");
	  }*/
	  if(datosAltura3){
		  dataAlt3.addColumn(ColumnType.NUMBER, "Altura 3: " + alturas[2] + "m");	 	
	  }
	  
	  NumberFormat nf = NumberFormat.getFormat("0.0000");                                       		            
	  while(pos < listaBackup.size()){
		  obj_DatosFrecuenciaVelocidad = (DatosFrecuenciaVelocidad) listaBackup.get(pos);
	 	  		  
	 	  int fila = dataAlt3.addRow();
	 	 Double  velocidad = obj_DatosFrecuenciaVelocidad.getVelocidad();
	 	  
	 	  dataAlt3.setValue(fila, 0, velocidad);
	 	  /*if(datosAltura1){
		 	  		  
	 		  Double porcAlt1 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt1()));	 	 		  
	 		 dataAlt1.setValue(fila, 1, porcAlt1);
		 	  
	 	  }
		 /* if(datosAltura2){
		  	  		  
		 	  Double porcAlt2 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt2()));
		 	  data.setValue(fila, 2, porcAlt2);
		  }*/
		  if(datosAltura3){
		 	  		  
		 	  Double porcAlt3 = Double.parseDouble(nf.format(obj_DatosFrecuenciaVelocidad.getPorcentajeAlt3()));
		 	  dataAlt3.setValue(fila, 1, porcAlt3);
		  }
	 	  		 
	 	  pos++;
	  }
	 	    	
  }
	  
  
  public Widget getWidget() {
    return this;
  }
  
  private void setGraficoTab(int alturaInt) {
	    
	  int cont = 0;
	    boolean creoTab = true;
	    Tab tabRecorre;
	    
	    
//	    System.out.println("cantidad de widget " + tab.getWidgetCount());
//	    System.out.println("cantidad de widget " + toptabSet.getNumTabs());
	     
	    while ((cont < toptabSet.getNumTabs()) && (creoTab)){
	      tabRecorre = toptabSet.getTab(cont); 

	      String tituloCompara =  "ALtura " + alturaInt;
	      boolean comparo = tabRecorre.getTitle().equalsIgnoreCase(tituloCompara);
//	      String say = "titulo tab seleccionado tabrecorre: " + comparo + " title " + tabRecorre.getTitle() + " name " + record.getName();
//	      SC.say(say);
	      if (comparo){
	        
	        creoTab = false;
	        toptabSet.selectTab(tabRecorre);
	        tab.selectTab(cont);
	      }     
	      cont++;
	    }
	    
	  
	    if (creoTab) {
	      Tab tTab1 = new Tab(" ");
	      
	      HLayout layoutTab = new HLayout();
//	      layoutTab.setWidth100();
//	      layoutTab.setHeight100();
	      layoutTab.setWidth(595);
	      layoutTab.setHeight(530);

	      HorizontalPanel grafico = new Graphic(alturaInt);
	      
	      layoutTab.addMember(grafico.asWidget());
	      layoutTab.draw();
	    
//	      Tab tTab1 = new Tab(record.getName());
	      String titulo =  "ALtura " + alturaInt;
	      tTab1.setTitle(titulo);
	      //SC.say("titulo tab creado: " + tTab1.getTitle());
	     
	      tTab1.setPane(layoutTab);
	      tTab1.setCanClose(true);
//	      }
	      toptabSet.addTab(tTab1);
	      toptabSet.selectTab(tTab1);
	      
	      FlowPanel flow;
	      flow = new FlowPanel();
	      flow.add(grafico);	     
	      
	      TabBar tabBar = new TabBar() ;
	      tabBar.setTitle(titulo);	   	      
//	      tab.add(tabBar.asWidget());
	      tab.add(flow, getTabTitulo(flow, titulo));	      	      
	      tab.setAnimationEnabled(true);
	      
	      
//	      surLayout.setMembers(toptabSet); 
	     
//	      toptabSet.addChild(new Graphic(divInt,alturasInt));
	      tab.selectTab(tab.getWidgetCount()-1);
	      
	      add(tab);
	    }

	  }
  
  private Widget getTabTitulo(final Widget widget, final String titulo){
	  
	  final HorizontalPanel hPanel = new HorizontalPanel();
	  hPanel.setStyleName("labelStyle");
	  
	  
	  final Label label = new Label(titulo);	  
//	  label.setWidth("12px");
//	  label.setHeight("2px");
	  
	  DOM.setStyleAttribute(label.getElement(), "whiteSpace", "nowrap");
	  
	  ImageAnchor cerrarBtn = new ImageAnchor();
//	  cerrarBtn.setResource(images.cross());
//	  cerrarBtn.setResourceImage(images);
	  com.google.gwt.user.client.ui.Button botonCerrar = new com.google.gwt.user.client.ui.Button("x");
//	  botonCerrar.getElement().setId("botonCerrar");
	  botonCerrar.setStyleName("botonCerrar");
	  botonCerrar.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
		
		@Override
		public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
			// TODO Auto-generated method stub
			 int indiceWidget = tab.getWidgetIndex(widget);
			  if(indiceWidget > 0 ){
				  tab.selectTab(indiceWidget - 1);
				  System.out.println("indice widget: " + indiceWidget);
			  }
			  tab.remove(indiceWidget);
			  toptabSet.removeTab(indiceWidget);
			  if (tab.getWidgetCount() == 0){
				  
			  }
		}
	});
	  
	  cerrarBtn.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler(){
		  public void onClick(com.google.gwt.event.dom.client.ClickEvent event){
			  int indiceWidget = tab.getWidgetIndex(widget);
			  if(indiceWidget > 0 ){
				  tab.selectTab(indiceWidget - 1);
			  }
			  tab.remove(indiceWidget);
		  }
	  });
	  
	  hPanel.add(label);
	  hPanel.add(new HTML("&nbsp&nbsp&nbsp"));
//	  hPanel.add(cerrarBtn);
	  hPanel.add(botonCerrar);
//	  hPanel.setStyleName("gwt.TabLayoutPanelTab");
	  
	  return hPanel;
  }
  
}
