package Mensaje;

import Mueble.Mueble;

public class MensajeClientesPedirProd extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Mueble m;

    public MensajeClientesPedirProd(Mueble m) {
       this.m = m;
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_PEDIR_PROD;
    }

    @Override
    public Object get() {
        return m;
    }
    
}
