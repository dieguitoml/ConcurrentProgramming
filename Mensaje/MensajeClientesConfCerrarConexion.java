package Mensaje;

public class MensajeClientesConfCerrarConexion extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MensajeClientesConfCerrarConexion() {
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CONF_CERRAR_CONEXION;
    }

    @Override
    public Object get() {
        return null;
    }
    
}
