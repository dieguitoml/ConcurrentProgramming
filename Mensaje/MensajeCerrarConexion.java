package Mensaje;

import Usuario.Usuario;

public class MensajeCerrarConexion extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Usuario u;
	
	public MensajeCerrarConexion (Usuario u) {
		this.u = u;
	}
	
	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CERRAR_CONEXION;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return this.u;
	}
   
	
}
