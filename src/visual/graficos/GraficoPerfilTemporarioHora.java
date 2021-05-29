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

import java.util.ArrayList;
import java.util.List;

import visual.client.AccesoDatosService;
import visual.client.AccesoDatosServiceAsync;
import visual.client.basededatos.DatosPerfilTemporarioHora;
import visual.shared.Mensaje;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;


/**
 * Demo for AreaChart visualization.
 */
public class GraficoPerfilTemporarioHora extends VerticalPanel {
  
  int width = 600;
  int height = 340;
  private DataTable data;
  String nombreTabla = "DatosPerfilTemporarioHora";
  
  public GraficoPerfilTemporarioHora() {
    super();
    setBorderWidth(3);
    setPixelSize(width, height*2);
   
    add(new Graphic());

  }
  
  public class Graphic extends HorizontalPanel {
    @SuppressWarnings("deprecation")
    public Graphic() {
      Runnable onLoadCallback = new Runnable() {
        public void run() {     
        	
        	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos para el gráfico
	 	   	final AccesoDatosServiceAsync accesServiceGraf = GWT.create(AccesoDatosService.class);
	 	   	
	 	   accesServiceGraf.getDatosTablaParaGrafico(nombreTabla,new AsyncCallback<List>() {

	 	 	      @Override
	 	 	      public void onFailure(Throwable caught) {
	 	 	        Mensaje.MensajeError("No se pudieron recuperar los datos para el Perfil Temporario Hora  " + caught.getMessage());
	 	 	      }
	 	 	      @Override
	 	 	      public void onSuccess(final List result) { 	 	
	 	 	    	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos de alturas
	 	 		   	final AccesoDatosServiceAsync accesServiceAlt = GWT.create(AccesoDatosService.class);
	 	 		   	
	 	 		  accesServiceAlt.getDatosAlturas(new AsyncCallback<String[]>() {

	 	 		 	      @Override
	 	 		 	      public void onFailure(Throwable caught) {
	 	 		 	        Mensaje.MensajeError("No se pudieron recuperar las alturas para el Perfil Temporario Hora  " + caught.getMessage());
	 	 		 	      }
	 	 		 	      @Override
	 	 		 	      public void onSuccess(String[] alturas) { 	 	
	 	 		 	    	  setDataTable(result, alturas);
	 	 		 	    	  setDataTableBarChart(result, alturas);
	 	 		 	    	  VerticalPanel vertPanel = new VerticalPanel();
	 	 		 	    	  vertPanel.add(new ComboChart(getDataTable(), createOptionsComboChart()));	 	 		 	    	  
	 	 		 	    	  vertPanel.add(new ColumnChart(getDataTable(), createOptionsComboChart()));
	 	 		 	    	  //vertPanel.add(new BarChart(createTablePie(), createOptionsBarChart()));
	 	 		 	    	  add(vertPanel);
	 	 		 	    	  //add(new Table(createTablePie(),createOptionsTableChart()));	
	 	 		 	    	  VerticalPanel vertPanel2 = new VerticalPanel();
	 	 		 	    	  vertPanel2.add(new BarChart(getDataTable(), createOptionsBarChart()));
	 	 		 	    	  vertPanel2.add(new Table(getDataTable(),createOptionsTableChart()));
	 	 		 	    	  add(vertPanel2);
	 	 		 	    	  
	 	 		 	    	 
	 	 		 	      }
	 	 		   		});	 	 		   	
	 	 	      	} 
	 	 	      });
	 	   	
        }
      };     

//    creo esta llamada para poder generar la tabla
      Runnable onLoadCallbackTable = new Runnable() {
          public void run() {    
  	 	   	
          }
        };     
      
      VisualizationUtils.loadVisualizationApi(onLoadCallback, ComboChart.PACKAGE,Table.PACKAGE);
//      VisualizationUtils.loadVisualizationApi(onLoadCallbackTable, Table.PACKAGE);

    }

    @SuppressWarnings("deprecation")
    private Options createOptionsComboChart() {
      Options options = Options.create();
      options.setWidth(width);
      options.setHeight(height);
      options.setTitle("Perfil Temporario Hora");
      
      AxisOptions tituloV = AxisOptions.create();
      tituloV.setTitle("Velocidad del viento (m/s)");
      options.setVAxisOptions(tituloV);      
      
      AxisOptions tituloH = AxisOptions.create();
      tituloH.setTitle("Hora");
      options.setHAxisOptions(tituloH);
      
//      options.setBackgroundColor("gray");//  set3D(true);
      return options;
    }
    
    @SuppressWarnings("deprecation")
    private Options createOptionsBarChart() {
      Options options = Options.create();
      options.setWidth(width);
      options.setHeight(height);
      options.setTitle("Perfil Temporario Hora");
      
      AxisOptions tituloV = AxisOptions.create();
      tituloV.setTitle("Hora");      
      options.setVAxisOptions(tituloV);      
      
      AxisOptions tituloH = AxisOptions.create();
      tituloH.setTitle("Velocidad del viento (m/s)");
      options.setHAxisOptions(tituloH);
      
     // options.setPointSize(250);
      options.setLineWidth(10);
//      options.setBackgroundColor("gray");//  set3D(true);
      return options;
    }
    
    @SuppressWarnings("deprecation")
    private com.google.gwt.visualization.client.visualizations.Table.Options createOptionsTableChart() {
      com.google.gwt.visualization.client.visualizations.Table.Options options = com.google.gwt.visualization.client.visualizations.Table.Options.create();
      String wi = String.valueOf(width);
      String he = String.valueOf(height*2);
      options.setWidth(wi);
      options.setHeight(he);     
      return options;
    }
    
    private AbstractDataTable getDataTable() {
      
      return data;
    }
  }
  

  public final void setDataTable(List result, String[] alturas){
	  data = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DatosPerfilTemporarioHora obj_perfilHora;
	 	  	  
	  data.addColumn(ColumnType.NUMBER, "Hora");	  
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
	  while(pos < listaBackup.size()){
	 	  obj_perfilHora = (DatosPerfilTemporarioHora) listaBackup.get(pos);
	 	  		  
	 	  int fila = data.addRow();
	 	  int hora = obj_perfilHora.getHora();
	 	  		  	 	  		  
	 	  data.setValue(fila, 0, hora);
	 	  if(datosAltura1){
		 	  		  
	 		  Double promAlt1 = obj_perfilHora.getPromedioVelocidadAlt1();
		 	  data.setValue(fila, 1, promAlt1);
	 	  }
		  if(datosAltura2){
		  	  		  
		 	  Double promAlt2 = obj_perfilHora.getPromedioVelocidadAlt2();
		 	  data.setValue(fila, 2, promAlt2);
		  }
		  if(datosAltura3){
		 	  		  
		 	  Double promAlt3 = obj_perfilHora.getPromedioVelocidadAlt3();
		 	  data.setValue(fila, 3, promAlt3);
		  }
	 	  		 
	 	  pos++;
	  }
	 	    	
  }

  public final void setDataTableBarChart(List result, String[] alturas){
	  data = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DatosPerfilTemporarioHora obj_perfilHora;
	 	  	  
	  data.addColumn(ColumnType.NUMBER, "Hora");
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
	  while(pos < listaBackup.size()){
	 	  obj_perfilHora = (DatosPerfilTemporarioHora) listaBackup.get(pos);
	 	  		  
	 	  int fila = data.addRow();
	 	  int hora = obj_perfilHora.getHora();
	 	  		  	 	  		  
	 	  data.setValue(fila, 0, hora);
	 	  if(datosAltura1){
		 	  		  
	 		  Double promAlt1 = obj_perfilHora.getPromedioVelocidadAlt1();
		 	  data.setValue(fila, 1, promAlt1);
	 	  }
		  if(datosAltura2){
		  	  		  
		 	  Double promAlt2 = obj_perfilHora.getPromedioVelocidadAlt2();
		 	  data.setValue(fila, 2, promAlt2);
		  }
		  if(datosAltura3){
		 	  		  
		 	  Double promAlt3 = obj_perfilHora.getPromedioVelocidadAlt3();
		 	  data.setValue(fila, 3, promAlt3);
		  }
	 	  		 
	 	  pos++;
	  }
	 	    	
  }
  
  public Widget getWidget() {
    return this;
  }
}
