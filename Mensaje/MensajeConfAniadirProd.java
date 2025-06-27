package Mensaje;

import Mueble.Mueble;

public class MensajeConfAniadirProd extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Mueble m;
	public  MensajeConfAniadirProd(Mueble m) {
		this.m = m;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_ANIADIR_PROD;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return this.m;
	}
}
