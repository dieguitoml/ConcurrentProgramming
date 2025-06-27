package Mensaje;

import java.util.HashMap;
import java.util.Map;

import Concurrente.SocketConcurrencia;
import Mueble.Mueble;
import Usuario.Usuario;

public class MensajePreparadoCS extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String ip;
	int puerto;
	Usuario cliente;
	private Mueble muebleSolicitado;
	
	public MensajePreparadoCS(String ip, int puerto, Usuario Cliente, Mueble m) {
		this.ip = ip;
		this.puerto = puerto;
		this.cliente = Cliente;
		this.muebleSolicitado = m;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_PREPARADO_CS;
	}

	@Override
	public Object get() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("ip", ip);
		mapa.put("puerto", puerto);
		mapa.put("Cliente", cliente);
		mapa.put("muebleSolicitado", muebleSolicitado);
		return mapa;
	}

}
