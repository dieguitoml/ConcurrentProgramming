package Mensaje;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajeModProd extends Mensaje{

	private static final long serialVersionUID = 1L;

	Usuario usuario;
	Mueble viejo, nuevo;
	public MensajeModProd(Usuario u, Mueble viejo, Mueble nuevo) {
		this.usuario = u;
		this.viejo = viejo;
		this.nuevo = nuevo;
	}
	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_MOD_PROD;
	}
	@Override
	public Object get() {
		List<Mueble> muebles = new ArrayList<Mueble>();
		muebles.add(viejo);
		muebles.add(nuevo);
		SimpleEntry<Usuario, List<Mueble>> entry = new SimpleEntry<>(usuario, muebles);
		return entry;
	}
	
}
