package visual.shared;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

public class Mensaje extends SC {

	Window ventana = new Window();
	
	public static void MensajeError(String mensaje){
		
		SC ventana = new SC();
		ventana.warn(mensaje);
		
	}
	
	public static void MensajeConfirmacion(String mensaje){
		
		SC ventana = new SC();
		ventana.say(mensaje);
		
	}
}
