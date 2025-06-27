package Mensaje;

import java.util.AbstractMap.SimpleEntry;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajeClientesConfPedirProd extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    Mueble m;
    Usuario emisor;

    public MensajeClientesConfPedirProd(Mueble find, Usuario u) {
    	this.m = find;
        this.emisor = u;
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CONF_PEDIR_PROD;
    }

    @Override
    public Object get() {
        SimpleEntry<Mueble, Usuario> entry = new SimpleEntry<>(m, emisor);
        return entry;
    }
    
}
