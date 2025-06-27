package Mensaje;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajeEmitir extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario cliente;
	private Mueble muebleSolicitado;

	
	public MensajeEmitir (Usuario u, Mueble m) {
		this.cliente = u;
		this.muebleSolicitado = m;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_EMITIR;
	}

	@Override
	public Object get() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("cliente", cliente);
		mapa.put("muebleSolicitado", muebleSolicitado);
		return mapa;
	}

}
