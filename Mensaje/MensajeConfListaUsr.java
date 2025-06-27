package Mensaje;

import java.util.List;

import Concurrente.ListaConcurrente;
import Usuario.Usuario;

public class MensajeConfListaUsr extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Usuario> l;
	
	public MensajeConfListaUsr(List<Usuario> list) {
		this.l=list;
	}
	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_CONF_LISTA_USR;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return this.l;
	}

}

