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
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.RadarChart;
import com.googlecode.charts4j.RadarPlot;
import com.googlecode.charts4j.RadialAxisLabels;
import com.googlecode.charts4j.Shape;

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
public class GraficoRosaDeViento extends VerticalPanel {
  
  int width = 600;
  int height = 340;
  private DataTable data;
  String nombreTabla = "DireccionVientoRangos";
  private TabSet toptabSet;  
  
  private ListBox listbDivision;
  private ListBox listbAlturas;
  private TabPanel  tab;
  
  public GraficoRosaDeViento() {
    super();
    setBorderWidth(3);
    setPixelSize(width, height);
    
    Label etiquetaDivision = new Label("Rangos Rosa de Viento");
    
    listbDivision = new ListBox(false);
    listbDivision.setSelectedIndex(0);
    listbDivision.addItem("4");
    listbDivision.addItem("8");
    listbDivision.addItem("12");
    listbDivision.addItem("16");
    listbDivision.addItem("20");
    listbDivision.addItem("24");
    listbDivision.setTitle("Rangos de Rosa de Viento");
    listbDivision.setName("División de Rosa de Viento");
    
    
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
	hPanel.add(etiquetaDivision);
    hPanel.add(listbDivision);
    
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
			int seleccionDivision = listbDivision.getSelectedIndex();
			String division =  listbDivision.getItemText(seleccionDivision);
			
			int divInt = Integer.parseInt(division);
			
			int seleccionAltura = listbAlturas.getSelectedIndex();
			String altura =  listbAlturas.getItemText(seleccionAltura);
			
			int alturaInt = Integer.parseInt(altura);
			 			
			setGraficoTab(alturaInt, divInt);	
		
		}			  
	  }
  
  
  public class Graphic extends HorizontalPanel {
    @SuppressWarnings("deprecation")
    public Graphic(final int division, final int altura) {
    	
      Runnable onLoadCallback = new Runnable() {
        public void run() {     
        	
        	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos para el gráfico
	 	   	final AccesoDatosServiceAsync accesServiceGraf = GWT.create(AccesoDatosService.class);
	 	   	
	 	   accesServiceGraf.getDatosTablaParaGraficoRosa(nombreTabla, division, new AsyncCallback<List>() {

	 	 	      @Override
	 	 	      public void onFailure(Throwable caught) {
	 	 	        Mensaje.MensajeError("No se pudieron recuperar los datos para la Rosa de los Vientos  " + caught.getMessage());
	 	 	      }
	 	 	      @Override
	 	 	      public void onSuccess(final List result) { 	 	
	 	 	    	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos de alturas
	 	 		   	final AccesoDatosServiceAsync accesServiceAlt = GWT.create(AccesoDatosService.class);
	 	 		   	
	 	 		  accesServiceAlt.getDatosAlturas(new AsyncCallback<String[]>() {

	 	 		 	      @Override
	 	 		 	      public void onFailure(Throwable caught) {
	 	 		 	        Mensaje.MensajeError("No se pudieron recuperar las alturas para la Rosa de los Vientos  " + caught.getMessage());
	 	 		 	      }
	 	 		 	      @Override
	 	 		 	      public void onSuccess(String[] alturas) { 	 	
	 	 		 	    	  setDataTable(result, alturas, division);	 	 	
	 	 		 	    	 
	 	 		 	    	  Boolean datosAltura1 = alturas[0] != null;
	 	 		 	    	  Boolean datosAltura2 = alturas[1] != null;
	 	 		 		  	  Boolean datosAltura3 = alturas[2] != null;
	 	 		 	    	  
	 	 		 		  	  double[] datosAlt = new double[division+1];
	 	 		 		  	  RadarPlot plotAlt = null;
	 	 		 		  	  RadarChart chartRosaViento = null;
	 	 		 		  	  
	 	 		 		  	  Frame frame = new Frame();	 	 		 		  	  
	 	 					  frame.setPixelSize(595, 530);
	 	 		 		  	
	 	 		 		  	  if ((altura == 1) && (datosAltura1) ){
	 	 		 		  		 datosAlt =  getDatosSegunAltura(division, result, altura);  
	 	 		 		  		 plotAlt = getRadarPlotAltura(datosAlt,alturas[0]);

		 	 		 		  	  RadialAxisLabels radialAxisDivision = getRadialAxisLabelsSegunDivision(division);
		 	 					  AxisLabels contrenticAxisLables = getContrenticAxisSegunDatos(datosAlt);
		 	 					  
		 	 					  chartRosaViento = getRadarChart(plotAlt, radialAxisDivision, contrenticAxisLables);
		 	 					  
		 	 					  frame.setUrl(chartRosaViento.toURLString());
		 	 					  System.out.println("dirección de chart altura 1: " + chartRosaViento.toURLString());
	 	 		 		  	  }else{
	 	 		 		  		  	if ((altura == 2) && (datosAltura2)){
	 	 		 		  		  		datosAlt =  getDatosSegunAltura(division, result, altura);
	 	 		 		  		  		plotAlt = getRadarPlotAltura(datosAlt,alturas[1]);

	 	 	 	 		 		  	  RadialAxisLabels radialAxisDivision = getRadialAxisLabelsSegunDivision(division);
	 	 	 	 					  AxisLabels contrenticAxisLables = getContrenticAxisSegunDatos(datosAlt);
	 	 	 	 					  
	 	 	 	 					  chartRosaViento = getRadarChart(plotAlt, radialAxisDivision, contrenticAxisLables);
	 	 	 	 					  
	 	 	 	 					  frame.setUrl(chartRosaViento.toURLString());
	 	 	 	 					  System.out.println("dirección de chart altura 2: " + chartRosaViento.toURLString());
	 	 		 		  		  	}else{	 	 		 		  	  
	 	 		 		  		  		if ((altura == 3) && (datosAltura3)){
	 	 		 		  		  			datosAlt =  getDatosSegunAltura(division, result, altura);
	 	 		 		  		  			plotAlt = getRadarPlotAltura(datosAlt, alturas[2]);

	 	 		 	 		 		  	  RadialAxisLabels radialAxisDivision = getRadialAxisLabelsSegunDivision(division);
	 	 		 	 					  AxisLabels contrenticAxisLables = getContrenticAxisSegunDatos(datosAlt);
	 	 		 	 					  
	 	 		 	 					  chartRosaViento = getRadarChart(plotAlt, radialAxisDivision, contrenticAxisLables);
	 	 		 	 					  
	 	 		 	 					  frame.setUrl(chartRosaViento.toURLString());
	 	 		 	 					  System.out.println("dirección de chart altura 3: " + chartRosaViento.toURLString());
	 	 		 		  		  		}
	 	 		 		  		  	}
	 	 		 		  	  }
	 	 		 	    	  	 	 		 		  	  	 	 					  	 	 					  	 	 					  
	 	 					  add(frame);
	 	 					  add(new Table(getDataTable(),createOptionsTableChart()));	 					         
	 	 		 	      }
	 	 		  });
	 	 	 }
	 	   });
        }};    
      
      VisualizationUtils.loadVisualizationApi(onLoadCallback,Table.PACKAGE);
    
    }
    
    @SuppressWarnings("deprecation")
    private Options createOptionsTableChart() {
      Options options = Options.create();
      String wi = String.valueOf(width);
      String he = String.valueOf(height+190);
      options.setWidth(wi);
      options.setHeight(he);     
      return options;
    }
    
    private AbstractDataTable getDataTable() {       
      return data;
    }
  }
  

  public final void setDataTable(List result, String[] alturas, int division){
	  data = DataTable.create();
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DireccionVientoRangos obj_DireccionVientoRangos;
	 	  	  
	  data.addColumn(ColumnType.STRING, "Orientación");
	  Boolean datosAltura1 = alturas[0] != null;
	  Boolean datosAltura2 = alturas[1] != null;
	  Boolean datosAltura3 = alturas[2] != null;
	  if(datosAltura1){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 1: " + alturas[0] + "m (%)");
	  }
	  if(datosAltura2){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 2: " + alturas[1] + "m (%)");
	  }
	  if(datosAltura3){
	 	  data.addColumn(ColumnType.NUMBER, "Altura 3: " + alturas[2] + "m (%)");	 	
	  }
	  
	  NumberFormat nf = NumberFormat.getFormat("0.0000");                                       		            
	  while(pos < listaBackup.size()){
		  obj_DireccionVientoRangos = (DireccionVientoRangos) listaBackup.get(pos);
	 	  		  
	 	  int fila = data.addRow();
	 	  String grados = getOrientacionGrados(division, pos);
	 	  		  	 	  		  
	 	  data.setValue(fila, 0, grados);
	 	  if(datosAltura1){
		 	  		  
	 		  Double porcAlt1 = Double.parseDouble(nf.format(obj_DireccionVientoRangos.getPorcentajeAlt1()));	 	 		  
		 	  data.setValue(fila, 1, porcAlt1);
		 	  
	 	  }
		  if(datosAltura2){
		  	  		  
		 	  Double porcAlt2 = Double.parseDouble(nf.format(obj_DireccionVientoRangos.getPorcentajeAlt2()));
		 	  data.setValue(fila, 2, porcAlt2);
		  }
		  if(datosAltura3){
		 	  		  
		 	  Double porcAlt3 = Double.parseDouble(nf.format(obj_DireccionVientoRangos.getPorcentajeAlt3()));
		 	  data.setValue(fila, 3, porcAlt3);
		  }
	 	  		 
	 	  pos++;
	  }
	 	    	
  }

  // según la división son los cuadrantes
  private String getOrientacionGrados(int division, int indicador){
	  String retorno = null;
	 

	  if (division == 4){
	    switch (indicador){
	  	case 0: retorno = "0°";
	  	break;
	  	case 1: retorno = "90°";
	  	break;
	  	case 2: retorno = "180°";
	  	break;
	  	case 3: retorno = "270°";
	   };
	  }
	  else{if (division == 8){
		  	  switch (indicador){
		  	  case 0: retorno = "0°";
		  	  break;
		  	  case 1: retorno = "45°";
		  	  break;
		  	  case 2: retorno = "90°";
		  	  break;
		  	  case 3: retorno = "135°";
		  	  break;
		  	  case 4: retorno = "180°";
		  	  break;
		  	  case 5: retorno = "225°";
		  	  break;
		  	  case 6: retorno = "270°";
		  	  break;
		  	  case 7: retorno = "315°";
		  	  };
	  		}
	  		else{if (division == 12){
	  			 switch (indicador ){
	  		  	case 0: retorno = "0°";
	  		  	break;
	  		  	case 1: retorno = "30°";
	  		  	break;
	  		  	case 2: retorno = "60°";
	  		  	break;
	  		  	case 3: retorno = "90°";
	  		  	break;
	  		  	case 4: retorno = "120°";
	  		  	break;
	  		  	case 5: retorno = "150°";
	  		  	break;
	  		  	case 6: retorno = "180°";
	  		  	break;
	  		  	case 7: retorno = "210°";
	  		  	break;
	  		  	case 8: retorno = "240°";
	  		  	break;
	  		  	case 9: retorno = "270°";
	  		  	break;
	  		  	case 10: retorno = "300°";
	  		  	break;
	  		  	case 11: retorno = "330°";
	  		  	break;	  	
	  		  };	  			
	  		}
	  		else{if (division == 16){
	  			switch (indicador){
	  		  	case 0: retorno = "0°";
	  		  	break;
	  		  	case 1: retorno = "22,5°";
	  		  	break;
	  		  	case 2: retorno = "45°";
	  		  	break;
	  		  	case 3: retorno = "67,5°";
	  		  	break;
	  		  	case 4: retorno = "90°";
	  		  	break;
	  		  	case 5: retorno = "112,5°";
	  		  	break;
	  		  	case 6: retorno = "135°";
	  		  	break;
	  		  	case 7: retorno = "157,5°";
	  		  	break;
	  		  	case 8: retorno = "180°";
	  		  	break;
	  		  	case 9: retorno = "202,5°";
	  		  	break;
	  		  	case 10: retorno = "225°";
	  		  	break;
	  		  	case 11: retorno = "247,5°";
	  		  	break;
	  		  	case 12: retorno = "270°";
	  		  	break;
	  		  	case 13: retorno = "292,5°";
	  		  	break;
	  		  	case 14: retorno = "315°";
	  		  	break;
	  		  	case 15: retorno = "337,5°";
	  		  };	  			
	  		}
	  		else{if (division == 20){
	  			 switch (indicador){	  	
	  		  	case 0: retorno = "0°";
	  		  	break;
	  		  	case 1: retorno = "18°";
	  		  	break;
	  		  	case 2: retorno = "36°";
	  		  	break;
	  		  	case 3: retorno = "54°";
	  		  	break;
	  		  	case 4: retorno = "72°";
	  		  	break;
	  		  	case 5: retorno = "90°";
	  		  	break;
	  		  	case 6: retorno = "108°";
	  		  	break;
	  		  	case 7: retorno = "126°";
	  		  	break;
	  		  	case 8: retorno = "144°";
	  		  	break;
	  		  	case 9: retorno = "162°";
	  		  	break;
	  		  	case 10: retorno = "180°";
	  		  	break;
	  		  	case 11: retorno = "198°";
	  		  	break;
	  		  	case 12: retorno = "216°";
	  		  	break;
	  		  	case 13: retorno = "234°";
	  		  	break;
	  		  	case 14: retorno = "252°";
	  		  	break;
	  		  	case 15: retorno = "270°";
	  		  	break;
	  		  	case 16: retorno = "288°";
	  		  	break;
	  		  	case 17: retorno = "306°";
	  		  	break;
	  		  	case 18: retorno = "324°";
	  		  	break;
	  		  	case 19: retorno = "342°";
	  		  };
	  		} else { if (division == 24){
	  			 switch (indicador){	  	
		  		  	case 0: retorno = "0°";
		  		  	break;
		  		  	case 1: retorno = "15°";
		  		  	break;
		  		  	case 2: retorno = "30°";
		  		  	break;
		  		  	case 3: retorno = "45°";
		  		  	break;
		  		  	case 4: retorno = "60°";
		  		  	break;
		  		  	case 5: retorno = "75°";
		  		  	break;
		  		  	case 6: retorno = "90°";
		  		  	break;
		  		  	case 7: retorno = "105°";
		  		  	break;
		  		  	case 8: retorno = "120°";
		  		  	break;
		  		  	case 9: retorno = "135°";
		  		  	break;
		  		  	case 10: retorno = "150°";
		  		  	break;
		  		  	case 11: retorno = "165°";
		  		  	break;
		  		  	case 12: retorno = "180°";
		  		  	break;
		  		  	case 13: retorno = "195°";
		  		  	break;
		  		  	case 14: retorno = "210°";
		  		  	break;
		  		  	case 15: retorno = "225°";
		  		  	break;
		  		  	case 16: retorno = "240°";
		  		  	break;
		  		  	case 17: retorno = "255°";
		  		  	break;
		  		  	case 18: retorno = "270°";
		  		  	break;
		  		  	case 19: retorno = "285°";
		  		  	break;
		  		  	case 20: retorno = "300°";
		  		  	break;
		  		  	case 21: retorno = "315°";
		  		  	break;
		  		  	case 22: retorno = "330°";
		  		  	break;
		  		  	case 23: retorno = "345°";		  		  
		  		  };
	  			}else{if (division == 24){
		  			 switch (indicador){	  	
			  		  	case 0: retorno = "0°";
			  		  	break;
			  		  	case 1: retorno = "15°";
			  		  	break;
			  		  	case 2: retorno = "30°";
			  		  	break;
			  		  	case 3: retorno = "45°";
			  		  	break;
			  		  	case 4: retorno = "60°";
			  		  	break;
			  		  	case 5: retorno = "75°";
			  		  	break;
			  		  	case 6: retorno = "90°";
			  		  	break;
			  		  	case 7: retorno = "105°";
			  		  	break;
			  		  	case 8: retorno = "120°";
			  		  	break;
			  		  	case 9: retorno = "135°";
			  		  	break;
			  		  	case 10: retorno = "150°";
			  		  	break;
			  		  	case 11: retorno = "165°";
			  		  	break;
			  		  	case 12: retorno = "180°";
			  		  	break;
			  		  	case 13: retorno = "195°";
			  		  	break;
			  		  	case 14: retorno = "210°";
			  		  	break;
			  		  	case 15: retorno = "225°";
			  		  	break;
			  		  	case 16: retorno = "240°";
			  		  	break;
			  		  	case 17: retorno = "255°";
			  		  	break;
			  		  	case 18: retorno = "270°";
			  		  	break;
			  		  	case 19: retorno = "285°";
			  		  	break;
			  		  	case 20: retorno = "300°";
			  		  	break;
			  		  	case 21: retorno = "315°";
			  		  	break;
			  		  	case 22: retorno = "330°";
			  		  	break;
			  		  	case 23: retorno = "345°";		  		  
			  		  };
	  			}
	  		  }
	  		}
	  	  }
	  	}
	   }	
	  };		   	  	  	 
	  return retorno;
  }
  
  private double[] getDatosSegunAltura(int division, List result,int altura){
		
	  double[] retorno = new double[division+1];
	  
	  final List listaBackup = new ArrayList();
	  listaBackup.addAll(result);
	  
	 	    	
	  int pos = 0;
	  DireccionVientoRangos obj_DireccionVientoRangos;
	
	  
	  while(pos < listaBackup.size()){
		  obj_DireccionVientoRangos = (DireccionVientoRangos) listaBackup.get(pos);	 	  		  	 	 
	 	  if(altura == 1){		 	  		  
	 		  retorno[pos] = obj_DireccionVientoRangos.getPorcentajeAlt1();	 		 
	 	  }else{
	 		  	if(altura == 2){		  	  		  
	 		  		retorno[pos] = obj_DireccionVientoRangos.getPorcentajeAlt2();	 		  		
	 		  	}else{
	 		  		if(altura == 3){			 	  		  
	 				 	  retorno[pos] = obj_DireccionVientoRangos.getPorcentajeAlt3();	 				 	  
	 				  }
	 		  	}	 		  	
	 	  }	 	  
	 	  pos++;
	  }
//	  agrego esto para que haga un cierre en el gráfico	 
	  retorno[pos] = retorno[0];
	  
	  return retorno;
  }
	  
  private RadarPlot  getRadarPlotAltura(double[] datosAlt, String altura){

	  double max = getMaximo(datosAlt);
	  RadarPlot plotRetorno = Plots.newRadarPlot(DataUtil.scaleWithinRange(0, max,datosAlt));
	  Color plotColor = Color.newColor("CC3366");
	  plotRetorno.addShapeMarkers(Shape.CIRCLE, plotColor, 7);
//	  plotRetorno.addShapeMarkers(Shape.CIRCLE, plotColor, 7);
	  plotRetorno.setColor(plotColor);
	  plotRetorno.setLineStyle(LineStyle.newLineStyle(2, 3, 0));
	  Color colorRelleno = Color.newColor("FF0087", 20);		        
	  plotRetorno.setFillAreaColor(colorRelleno);		        
	  plotRetorno.setLegend("Altura " + altura + "m");
	  
	  return plotRetorno;
  }
  
  private double getMaximo(double[] datosAlt){
	  
	  double maXimo = datosAlt[0];
	  for (int pos=1; pos < datosAlt.length ; pos++){
		  double intermedio =  datosAlt[pos];		  
		  maXimo = Math.max(maXimo,intermedio);
	  }
	  return maXimo;
  }
	
  private RadialAxisLabels getRadialAxisLabelsSegunDivision(int division){
	  RadialAxisLabels radialAxisLabels = null;
	  if (division == 4){
		  radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "90°", "180°", "270°");		  		  
		  }
		  else{if (division == 8){
			  	   radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "45", "90°", "135", "180°", "225", "270°","315");
		  		}
		  		else{if (division == 12){		  			 
		  		       	 radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "30", "60", "90°", "120", "150", "180°", "210", "240", 
		  		       			 												  "270°","300","330");
		  		}
		  		else{if (division == 16){		  		
		  			  	radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "22.5", "45", "67.5", "90°", "112.5", "135", "157.5", "180°", 
		  			  															 "202.5","225","247.5", "270°", "292.5","315","337.5");
		  		}
		  		else{if (division == 20){		  			 
		  				radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "18", "36", "54", "72", "90°", "108", "126", "144", "162","180°",
		  																		 "198", "216", "234","252","270°", "288", "306","324","342");
		  		}
		  		else{if (division == 24){		  			 
	  				radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("0°", "15", "30", "45", "60", "75", "90°", "105", "120", "135","150",
	  																		"165", "180°","195", "210","225","240", "255", "270°","285","300","315" ,"330" ,"345" );
		  		}			  				  		
		  	  }
		  	}
		  }
		}
	  };	
	 
	  radialAxisLabels.setRadialAxisStyle(BLACK, 12);
	  return radialAxisLabels;		  
  }
  
  private AxisLabels getContrenticAxisSegunDatos(double[] datosAlt){
	  
	  List listaArray = new ArrayList<Double>(datosAlt.length);
	  listaArray.add(0);
	  for (int pos=0; pos < datosAlt.length ; pos++){
		  int dato =  (int) datosAlt[pos];
		  listaArray.add(dato);
	  }
	  
	  AxisLabels contrentricAxisLabels = AxisLabelsFactory.newNumericAxisLabels(listaArray);
	  contrentricAxisLabels.setAxisStyle(AxisStyle.newAxisStyle(BLACK, 12, AxisTextAlignment.RIGHT));
	  
	  return contrentricAxisLabels;
	  
  }
  
  private RadarChart getRadarChart(RadarPlot plotAlt, RadialAxisLabels radialAxisDivision, AxisLabels contrenticAxisLables){
	  RadarChart chartRosaViento = GCharts.newRadarChart(plotAlt);
	  chartRosaViento.setTitle("Rosa de Viento", BLACK, 20);
	  chartRosaViento.setSize(530, 530);
	  chartRosaViento.setGrid(5, 5, 2, 2);
		  
	  chartRosaViento.addRadialAxisLabels(radialAxisDivision);
	  chartRosaViento.addConcentricAxisLabels(contrenticAxisLables);
	  
	  return chartRosaViento;
  }
		  
		  
  
  public Widget getWidget() {
    return this;
  }
  
  private void setGraficoTab(int alturaInt, int divInt) {
	    
	  int cont = 0;
	    boolean creoTab = true;
	    Tab tabRecorre;
	    
	    
//	    System.out.println("cantidad de widget " + tab.getWidgetCount());
//	    System.out.println("cantidad de widget " + toptabSet.getNumTabs());
	     
	    while ((cont < toptabSet.getNumTabs()) && (creoTab)){
	      tabRecorre = toptabSet.getTab(cont); 

	      String tituloCompara =  "ALtura " + alturaInt + " división " + divInt;
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

	      HorizontalPanel grafico = new Graphic(divInt, alturaInt);
	      
	      layoutTab.addMember(grafico.asWidget());
	      layoutTab.draw();
	    
//	      Tab tTab1 = new Tab(record.getName());
	      String titulo =  "ALtura " + alturaInt + " división " + divInt;
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
