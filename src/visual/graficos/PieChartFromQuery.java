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

import java.util.Date;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.Query;
import com.google.gwt.visualization.client.Query.Callback;
import com.google.gwt.visualization.client.QueryResponse;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;

//import com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.ComboChart;


/**
 * Demo for AreaChart visualization.
 */
//public class AreaDemo implements LeftTabPanel.WidgetProvider {
public class PieChartFromQuery extends VerticalPanel {
  
  int width = 600;
  int height = 340;
  
  public PieChartFromQuery() {
    super();
    setBorderWidth(3);
//    setBorder("1");
    setPixelSize(width, height);
//    add(new Label("Some Widget"));
    add(new Graphic());
//    setWidth("75");
//    setHeight("75");
    
//    draw();
  }
  
  static DataTable getCompanyPerformance() {
    DataTable data = getCompanyPerformanceWithNulls();
    data.setValue(2, 1, 660);
    data.setValue(2, 2, 1120);
    return data;
  }

  static DataTable getCompanyPerformanceWithNulls() {
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.STRING, "Year");
    data.addColumn(ColumnType.NUMBER, "Sales");
    data.addColumn(ColumnType.NUMBER, "Expenses");
    data.addRows(4);
    data.setValue(0, 0, "2004");
    data.setValue(0, 1, 1000);
    data.setValue(0, 2, 400);
    data.setValue(1, 0, "2005");
    data.setValue(1, 1, 1170);
    data.setValue(1, 2, 460);
    data.setValue(2, 0, "2006");
    data.setValueNull(2, 1);
    data.setValueNull(2, 2);
    data.setValue(3, 0, "2007");
    data.setValue(3, 1, 1030);
    data.setValue(3, 2, 540);
    return data;
  }
  
  public class Graphic extends HorizontalPanel {
    @SuppressWarnings("deprecation")
    public Graphic() {
      Runnable onLoadCallback = new Runnable() {
        public void run() {     
        	
        	String dataUrl = "http://spreadsheets.google.com/tq?"
        	      + "key=pWiorx-0l9mwIuwX5CbEALA&range=A1:B12&gid=0&headers=-1";

        	    // Create a query to go to the above URL.
        	    Query query = Query.create(dataUrl);

        	    System.out.println("consulta: " + query.toString());
        	    // Send the query.
        	    query.send(new Callback() {
        	      public void onResponse(QueryResponse response) {
        	        if (response.isError()) {
        	          Window.alert(response.getMessage());
        	        } else {
        	        	
        	          // Get the data from the QueryResponse.
        	          DataTable data = response.getDataTable();
        	         
        	          int row = data.addRow();
        	          data.setValue(row,0, "Biofuels");
        	          data.setValue(row,1, 0.5);
        	          int numFilas = data.getNumberOfRows();
        	          int numColumnas = data.getNumberOfColumns();
        	          int i;
        	          int j;
        	          for (i=0; i<numFilas; i++ )
        	        	  for(j=0; j<numColumnas;j++ ){
        	        		 System.out.println("dato: "+i+","+j+" - "+ data.getFormattedValue(i, j));
        	        	  }
        	          
        	          // Create the Options object.
        	          com.google.gwt.visualization.client.visualizations.corechart.Options options = createOptions();

        	          // Create a PieChart and add it to a panel.
        	        
        	          LineChart line = new LineChart(data, options);
//        	          PieChart pie = new PieChart(data, options);
//        	          pie.addSelectHandler(createSelectHandler(pie));
        	          add(line);
        	          
        	        }
        	      }
        	    });
        }
      };
//      VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
      VisualizationUtils.loadVisualizationApi(onLoadCallback, ComboChart.PACKAGE);
    }
    
    private com.google.gwt.visualization.client.visualizations.corechart.Options createOptions() {
    	com.google.gwt.visualization.client.visualizations.corechart.Options options = com.google.gwt.visualization.client.visualizations.corechart.Options.create();
        options.setWidth(800);
        options.setHeight(440);
//        options.set3D(true);
        options.setTitle("Mis Actividades Diarias");
        return options;
      }
    
//    private AbstractDataTable createTable() {
//        DataTable data = DataTable.create();
//        data.addColumn(ColumnType.STRING, "Task");
//        data.addColumn(ColumnType.NUMBER, "Hours per Day");
//        data.addRows(2);
//        data.setValue(0, 0, "Work");
//        data.setValue(0, 1, 14);
//        data.setValue(1, 0, "Sleep");
//        data.setValue(1, 1, 10);
//        return data;
//      }
    
//    @SuppressWarnings("deprecation")
//    private Options createOptionsPie() {
//      Options options = Options.create();
//      options.setWidth(width);
//      options.setHeight(height);
//      options.setTitle("GWT Chart test.");
////      options.set3D(true);
//      return options;
//    }
//    @SuppressWarnings("deprecation")
//    private com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options createOptionsComboChart() {
//      com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options options = com.google.gwt.visualization.client.visualizations.corechart.ComboChart.Options.create();
//      options.setWidth(width);
//      options.setHeight(height);
//      options.setTitle("GWT Chart test.");
////      options.set3D(true);
//      return options;
//    }
//    private AbstractDataTable createTablePie() {
//      DataTable data = DataTable.create();
////      data.addColumn(ColumnType.STRING, "Data set");
////      data.addColumn(ColumnType.NUMBER, "Value");
////      data.addRows(2);
////      data.setValue(0, 0, "Data set 1");
////      data.setValue(0, 1, 100);
////      data.setValue(1, 0, "Data set 2");
////      data.setValue(1, 1, 10);
//      data.addColumn(ColumnType.STRING, "Year");
//      data.addColumn(ColumnType.NUMBER, "Sales");
//      data.addColumn(ColumnType.NUMBER, "Expenses");
//      data.addRows(4);
//      
//      data.setValue(0, 0, "2004");
//      data.setValue(0, 1, 1000);
//      data.setValue(0, 2, 400);
//      data.setValue(1, 0, "2005");
//      data.setValue(1, 1, 1170);
//      data.setValue(1, 2, 460);
//      data.setValue(2, 0, "2006");
//      data.setValueNull(2, 1);
//      data.setValueNull(2, 2);
//      data.setValue(3, 0, "2007");
//      data.setValue(3, 1, 1030);
//      data.setValue(3, 2, 540);
//      return data;
//    }
  }
  
//  private SelectHandler createSelectHandler(final PieChart chart) {
//	    return new SelectHandler() {
//	      @Override
//	      public void onSelect(SelectEvent event) {
//	        String message = "";
//	        
//	        // May be multiple selections.
//	        JsArray<Selection> selections = chart.getSelections();
//
//	        for (int i = 0; i < selections.length(); i++) {
//	          // add a new line for each selection
//	          message += i == 0 ? "" : "\n";
//	          
//	          Selection selection = selections.get(i);
//
//	          if (selection.isCell()) {
//	            // isCell() returns true if a cell has been selected.
//	            
//	            // getRow() returns the row number of the selected cell.
//	            int row = selection.getRow();
//	            // getColumn() returns the column number of the selected cell.
//	            int column = selection.getColumn();
//	            message += "cell " + row + ":" + column + " selected";
//	          } else if (selection.isRow()) {
//	            // isRow() returns true if an entire row has been selected.
//	            
//	            // getRow() returns the row number of the selected row.
//	            int row = selection.getRow();
//	            message += "row " + row + " selected";
//	          } else {
//	            // unreachable
//	            message += "Pie chart selections should be either row selections or cell selections.";
//	            message += "  Other visualizations support column selections as well.";
//	          }
//	        }
//	        
//	        Window.alert(message);
//	      }
//	    };
//	  }
  
  public Widget getWidget() {
//    VerticalPanel result = new VerticalPanel();
//     
//    Options options = Options.create();
//    options.setHeight(240);
//    options.setTitle("Company Performance");
//    options.setWidth(400);
//    AxisOptions vAxisOptions = AxisOptions.create();
//    vAxisOptions.setMinValue(0);
//    vAxisOptions.setMaxValue(2000);
    //options.setVAxisOptions(vAxisOptions);

//    DataTable data = getCompanyPerformance();
//    AreaChart viz = new AreaChart(data, options);
//
//    Label status = new Label();
//    Label onMouseOverAndOutStatus = new Label();
//    viz.addSelectHandler(new SelectionDemo(viz, status));
//    viz.addReadyHandler(new ReadyDemo(status));
//    viz.addOnMouseOverHandler(new OnMouseOverDemo(onMouseOverAndOutStatus));
//    viz.addOnMouseOutHandler(new OnMouseOutDemo(onMouseOverAndOutStatus));
//    result.add(status);
//    result.add(viz);
//    result.add(onMouseOverAndOutStatus);
//    
//    resultCan.addChild(viz);
    
//    return result;
    return this;
  }
}
