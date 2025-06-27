package Mensaje;

public class MensajeClientesCerrarConexion extends Mensaje {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MensajeClientesCerrarConexion() {
    }

    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_CLIENTES_CERRAR_CONEXION;
    }

    @Override
    public Object get() {
        return null;
    }
    
}
