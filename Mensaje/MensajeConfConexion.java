package Mensaje;

import Concurrente.PuertoConcurrente;

public class MensajeConfConexion extends Mensaje {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private PuertoConcurrente pc;
	public MensajeConfConexion(PuertoConcurrente pc) {
		this.pc = pc;
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_CONEXION;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return this.pc;
	}

}
