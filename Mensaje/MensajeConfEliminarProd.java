package Mensaje;

import Mueble.Mueble;

public class MensajeConfEliminarProd extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Mueble m;
	
	public MensajeConfEliminarProd(Mueble m) {
		this.m = m;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_ELIMINAR_PROD;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return m;
	}

}
