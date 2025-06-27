package Mensaje;

public class MensajeClientesConfConexion extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MensajeClientesConfConexion() {
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CONF_CONEXION;
    }

    @Override
    public Object get() {
        return null;
    }
    
}
