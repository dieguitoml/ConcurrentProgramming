package Mensaje;

public class MensajeConfCerrarConexion extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_CERRAR_CONEXION;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

}
