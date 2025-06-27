package Mensaje;

import java.util.AbstractMap.SimpleEntry;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajeEliminarProd extends Mensaje{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Mueble m;
	
	//Algo de tipo Producto
	
	public MensajeEliminarProd (Usuario u, Mueble m) {
		this.m = m;
		this.usuario = u;
	}

	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.MENSAJE_ELIMINAR_PROD;
	}

	@Override
	public Object get() {
		SimpleEntry<Usuario, Mueble> entry = new SimpleEntry<>(usuario, m);
		return entry;
	}
	
	
}
