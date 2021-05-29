package visual.client;


import visual.graficos.AreaDemo;
import visual.graficos.GraficoFrecuenciaDeVelocidades;
import visual.graficos.GraficoFuncionLogaritmica;
import visual.graficos.GraficoPerfilTemporarioHora;
import visual.graficos.GraficoPerfilTemporarioMes;
import visual.graficos.GraficoRosaDeViento;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class VisualGWT implements EntryPoint {
  
  private int ancho = 600;
  private int alto = 500;
  
  public void onModuleLoad() {
  
//    String parametro  = Window.Location.getParameter("grafico");
    String parametro = "Histograma";
//    System.out.println("salida de parametro " + parametro);
    Panel panel = RootPanel.get();
//    if(parametro.compareToIgnoreCase("areademo") == 0){
//      
//      AreaDemo grafTimeLine = new AreaDemo();
//      panel.add(grafTimeLine.getWidget());
//    }
//    else{
//      AreaTimeLine grafTimeLine = new AreaTimeLine();
//      panel.add(grafTimeLine.getWidget());
//      Mensaje.MensajeConfirmacion("cargando tablas");
//    };
//      
    
//  AreaDemo grafTimeLine = new AreaDemo();
//  panel.add(grafTimeLine.getWidget());
  
    if(parametro.compareToIgnoreCase("Todos") == 0){
    	GraficoPerfilTemporarioHora perfilHora = new GraficoPerfilTemporarioHora();
      	panel.add(perfilHora.getWidget());
      	GraficoPerfilTemporarioMes perfilMes = new GraficoPerfilTemporarioMes();
     	panel.add(perfilMes.getWidget());
     	GraficoFuncionLogaritmica funcLogar = new GraficoFuncionLogaritmica();
      	panel.add(funcLogar.getWidget());
      	GraficoRosaDeViento rosaViento = new GraficoRosaDeViento();    		
 		panel.add(rosaViento.getWidget());
 		GraficoFrecuenciaDeVelocidades frecVeloc = new GraficoFrecuenciaDeVelocidades();
     	panel.add(frecVeloc.getWidget());
      	
    }else{ 	  
    
    	if(parametro.compareToIgnoreCase("RosaViento") == 0){
    
    		GraficoRosaDeViento rosaViento = new GraficoRosaDeViento();    		
     		panel.add(rosaViento.getWidget());
     		
    	  }else{
    		   if(parametro.compareToIgnoreCase("FuncLogaritmica") == 0){
    			   GraficoFuncionLogaritmica funcLogar = new GraficoFuncionLogaritmica();    			  
	       	   		panel.add(funcLogar.getWidget());
    		   }else{ 
    				if(parametro.compareToIgnoreCase("FuncPotencia") == 0){ 		
    
    				}else {
    			           if(parametro.compareToIgnoreCase("PerfilHora") == 0){   
    			        	  	GraficoPerfilTemporarioHora perfilHora = new GraficoPerfilTemporarioHora();
    			       	   		panel.add(perfilHora.getWidget());
    			           }else{
    			       	    	if(parametro.compareToIgnoreCase("PerfilMes") == 0){
    			       	    		GraficoPerfilTemporarioMes perfilMes = new GraficoPerfilTemporarioMes();
    			       	      		panel.add(perfilMes.getWidget());
    			       	    	}else{
        			       	    	if(parametro.compareToIgnoreCase("Histograma") == 0){
        			       	    		GraficoFrecuenciaDeVelocidades frecVeloc = new GraficoFrecuenciaDeVelocidades();
        			       	      		panel.add(frecVeloc.getWidget());
        			       	    	}
    			       	        }
    			          }
    			   }
    		 }	           
         }
    }

//    	GraficoPerfilTemporarioHora perfilHora = new GraficoPerfilTemporarioHora();
//  	panel.add(perfilHora.getWidget());
//  	GraficoPerfilTemporarioMes perfilMes = new GraficoPerfilTemporarioMes();
// 	panel.add(perfilMes.getWidget());
// 	GraficoFuncionLogaritmica funcLogar = new GraficoFuncionLogaritmica();
//  	panel.add(funcLogar.getWidget());
    
  	
    
//      PieChartFromQuery pie = new PieChartFromQuery();
//      panel.add(pie.getWidget());
    // Create a callback to be called when the visualization API
    // has been loaded.
//    Runnable onLoadCallback = new Runnable() {
//      public void run() {
//        Panel panel = RootPanel.get();
// 
//        // Create a pie chart visualization.
//        PieChart pie = new PieChart(createTable(), createOptions());
//
////        pie.addSelectHandler(createSelectHandler(pie));
//        panel.add(pie);
//      }
//    };
//
//    // Load the visualization api, passing the onLoadCallback to be called
//    // when loading is done.
//    VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//  }
//  
//  private Options createOptions() {
//    Options options = Options.create();
//    options.setWidth(ancho);
//    options.setHeight(alto);
//    options.set3D(true);
//
////    options.setTitle("Gráfico de Torta");
//    return options;
//  }
//  
//  private SelectHandler createSelectHandler(final PieChart chart) {
//    return new SelectHandler() {
//      @Override
//      public void onSelect(SelectEvent event) {
//        String message = "";
//        
//        // May be multiple selections.
//        JsArray<Selection> selections = chart.getSelections();
//
//        for (int i = 0; i < selections.length(); i++) {
//          // add a new line for each selection
//          message += i == 0 ? "" : "\n";
//          
//          Selection selection = selections.get(i);
//
//          if (selection.isCell()) {
//            // isCell() returns true if a cell has been selected.
//            
//            // getRow() returns the row number of the selected cell.
//            int row = selection.getRow();
//            // getColumn() returns the column number of the selected cell.
//            int column = selection.getColumn();
//            message += "cell " + row + ":" + column + " selected";
//          } else if (selection.isRow()) {
//            // isRow() returns true if an entire row has been selected.
//            
//            // getRow() returns the row number of the selected row.
//            int row = selection.getRow();
//            message += "row " + row + " selected";
//          } else {
//            // unreachable
//            message += "Pie chart selections should be either row selections or cell selections.";
//            message += "  Other visualizations support column selections as well.";
//          }
//        }
//        
////        Window.alert(message);
//      }
//    };
//  }
//
//  private AbstractDataTable createTable() {
//    DataTable data = DataTable.create();
//    data.addColumn(ColumnType.STRING, "Tareas");
//    data.addColumn(ColumnType.NUMBER, "Horas por día");
//    data.addRows(3);
//    data.setValue(0, 0, "Trabajar");
//    data.setValue(0, 1, 9);
//    data.setValue(1, 0, "Dormir");
//    data.setValue(1, 1, 8);
//    data.setValue(2, 0, "Oseo");
//    data.setValue(2, 1, 7);
//    return data;
  }
}
