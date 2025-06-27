package Mensaje;

import Usuario.Usuario;

public class MensajeConexion extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Usuario user;
	
	public MensajeConexion (Usuario user){
		this.user = user;
	}
	
	public Usuario get() {
		return this.user;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONEXION;
	}
	
}
