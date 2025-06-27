package Mensaje;

import java.util.AbstractMap.SimpleEntry;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajePedir extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Mueble mueble;
	private Usuario cliente;

	public MensajePedir(Mueble m, Usuario u) {
		this.mueble = m;
		this.cliente = u;
	}

	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.MENSAJE_PEDIR;
	}

	@Override
	public Object get() {
		SimpleEntry<Mueble, Usuario> entry = new SimpleEntry<>(mueble, cliente );
		return entry;
	}

}
