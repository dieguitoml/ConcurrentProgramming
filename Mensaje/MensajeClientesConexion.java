package Mensaje;

public class MensajeClientesConexion extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MensajeClientesConexion() {
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CONEXION;
    }

    @Override
    public Object get() {
        return null;
    }
    
}
