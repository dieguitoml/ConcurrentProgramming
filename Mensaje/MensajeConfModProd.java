package Mensaje;

import java.util.AbstractMap.SimpleEntry;

import Mueble.Mueble;

public class MensajeConfModProd extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Mueble antiguo,nuevo;

	public MensajeConfModProd(Mueble antiguo, Mueble nuevo) {
		// TODO Auto-generated constructor stub
		this.antiguo = antiguo;
		this.nuevo = nuevo;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_MOD_PROD;
	}

	@Override
	public Object get() {
		SimpleEntry<Mueble,Mueble> entr = new SimpleEntry<Mueble, Mueble>(antiguo,nuevo);
		return entr;
	}

}
