package Mensaje;

public class MensajeError extends Mensaje{

    private static final long serialVersionUID = 1L;
    private String error;
    
    public MensajeError(String error) {
        this.error = error;
    }
    
    @Override
    public TipoMensaje getTipo() {
        return TipoMensaje.MENSAJE_ERROR;
    }

    @Override
    public Object get() {
        return error;
    }
    
}
