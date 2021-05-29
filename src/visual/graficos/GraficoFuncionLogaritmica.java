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

import visual.client.AccesoDatosService;
import visual.client.AccesoDatosServiceAsync;
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

//import com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options;


/**
 * Demo for AreaChart visualization.
 */
public class GraficoFuncionLogaritmica extends VerticalPanel {
  
  int width = 600;
  int height = 340;
  private DataTable data;
 // String nombreTabla = "DatosPerfilTemporarioMes";
  
  public GraficoFuncionLogaritmica() {
    super();
    setBorderWidth(3);
    setPixelSize(width, height);
    add(new Graphic());

  }
  
  public class Graphic extends HorizontalPanel {
    @SuppressWarnings("deprecation")
    public Graphic() {
      Runnable onLoadCallback = new Runnable() {
        public void run() {     
        	
        	// Creo la interfaz del servicio RPC de acceso a datos para ser usado en la busqueda de los datos para el gráfico
	 	   	final AccesoDatosServiceAsync accesServiceGraf = GWT.create(AccesoDatosService.class);
	 	   	
	 	   	accesServiceGraf.getDatosFunciones(new AsyncCallback<Double[][]>() {

	 	 	      @Override
	 	 	      public void onFailure(Throwable caught) {
	 	 	        Mensaje.MensajeError("No se pudieron recuperar los datos para el Perfil Vertical - Función Logaritmica  " + caught.getMessage());
	 	 	      }
	 	 	      @Override
	 	 	      public void onSuccess(Double[][] result) { 	 	
	 	 	    	  setDataTable(result);	 	 	
		 	    	  add(new ComboChart(createTablePie(), createOptionsComboChart()));
		 	    	 add(new Table(createTablePie(),createOptionsTableChart()));
	 	 	   	 	 		   	
	 	 	      	} 
	 	 	      });
	 	   	
        }
      };
      
//      creo esta llamada para poder generar la tabla
      Runnable onLoadCallbackTable = new Runnable() {
          public void run() {    
  	 	   	
          }
        };     
      
      VisualizationUtils.loadVisualizationApi(onLoadCallback, ComboChart.PACKAGE);
      VisualizationUtils.loadVisualizationApi(onLoadCallbackTable, Table.PACKAGE);
    }

    @SuppressWarnings("deprecation")
    private Options createOptionsComboChart() {
      Options options = Options.create();
      options.setWidth(width);
      options.setHeight(height);
      options.setTitle("Perfil Vertical - Funciones");
      
      AxisOptions tituloV = AxisOptions.create();
      tituloV.setTitle("Velocidad del Viento(m/s)");
      options.setVAxisOptions(tituloV);      
      
      AxisOptions tituloH = AxisOptions.create();
      tituloH.setTitle("Metros sobre el Suelo (m)");
      options.setHAxisOptions(tituloH);
      
//      options.setBackgroundColor("gray");//  set3D(true);
      return options;
    }
    
    @SuppressWarnings("deprecation")
    private com.google.gwt.visualization.client.visualizations.Table.Options createOptionsTableChart() {
      com.google.gwt.visualization.client.visualizations.Table.Options options = com.google.gwt.visualization.client.visualizations.Table.Options.create();
      String wi = String.valueOf(width);
      String he = String.valueOf(height);
      options.setWidth(wi);
      options.setHeight(he); 
      
      return options;
    }
    
    private AbstractDataTable createTablePie() {
      
      return data;
    }
  }
  

  public final void setDataTable(Double[][] results){
	  data = DataTable.create();
	  
	  
	  data.addColumn(ColumnType.NUMBER, "altura");	  
	  data.addColumn(ColumnType.NUMBER, "Función Logarítmica");
	  data.addColumn(ColumnType.NUMBER, "Función de Potencia");
	  
	  for(int altura=0; altura< 101; altura++){
		  	  int fila = data.addRow();
		  	 
		  	//data.setValue(fila, 0, altura);
		  	  if(results[altura][0].compareTo(-1.0) ==  0){
//		  		  Double funcLoga = results[altura][0];
				  Double funcPot = results[altura][1];
				  /*data.setValue(fila, 0, altura);
//				  data.setValue(fila, 1, funcLoga);
				  data.setValue(fila, 2, funcPot);*/
				  data.setValue(fila, 2, altura);
//				  data.setValue(fila, 1, funcLoga);
				  data.setValue(fila, 0, funcPot);
		  	  }else{
				  Double funcLoga = results[altura][0];
				  Double funcPot = results[altura][1];
				  /*data.setValue(fila, 0, altura);
				  data.setValue(fila, 1, funcLoga);
				  data.setValue(fila, 2, funcPot);  
				  */
				  data.setValue(fila, 0, altura);
				  data.setValue(fila, 1, funcLoga);
				  data.setValue(fila, 2, funcPot);  
				  
				  
		  	  }
	  }
}
	  	 	    	

  
  public Widget getWidget() {
    return this;
  }
}
