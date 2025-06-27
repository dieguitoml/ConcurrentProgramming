package Mensaje;

import Mueble.Mueble;
import Usuario.Usuario;
import java.util.AbstractMap.SimpleEntry;




public class MensajeAniadirProd extends Mensaje{

	private static final long serialVersionUID = 1L;
    
	Usuario u;
	Mueble m;
	
	//Aqui tenemos un objeto de tipo Producto
	public MensajeAniadirProd(Usuario u, Mueble m) {
		this.u = u;
		this.m = m;
	}
	@Override
	public TipoMensaje getTipo() {
		return TipoMensaje.MENSAJE_ANIADIR_PROD;
	}
	@Override
	public Object get() {
		SimpleEntry<Usuario, Mueble> entry = new SimpleEntry<>(u, m);
		return entry;
	}
}

