package Mensaje;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import Mueble.Mueble;
import Usuario.Usuario;

public class MensajePreparadoSC extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	private int puerto;
	private Mueble muebleSolicitado;
	private Usuario cliente;

	public MensajePreparadoSC (String ip, int puerto, Mueble m, Usuario c) {
		this.ip = ip;
		this.puerto = puerto;
		this.muebleSolicitado = m;
		this.cliente = c;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_PREPARADO_SC;
	}

	@Override
	public Object get() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("ip", ip);
		mapa.put("puerto", puerto);
		mapa.put("muebleSolicitado", muebleSolicitado);
		mapa.put("Cliente", cliente);
		return mapa;
	}

}
