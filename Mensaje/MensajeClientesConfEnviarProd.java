package Mensaje;

import java.util.AbstractMap.SimpleEntry;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajeClientesConfEnviarProd extends Mensaje {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    Mueble m;
    Usuario u;

    
    public MensajeClientesConfEnviarProd() {
    }
    
    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CONF_ENVIAR_PROD;
    }

    @Override
    public Object get() {
    	return null;
    }
    
}
