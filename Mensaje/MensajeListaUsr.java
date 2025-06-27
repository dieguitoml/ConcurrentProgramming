package Mensaje;

public class MensajeListaUsr extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Aqui Guardamos una copia de la lista de usuarios
	
	public MensajeListaUsr(/*Recibo la lista de usuarios*/) {
		
		
	}

	@Override
	public TipoMensaje getTipo() {
		// TODO Auto-generated method stub
		return TipoMensaje.MENSAJE_LISTA_USR;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

}
