package Mensaje;

import java.io.Serializable;

public abstract class Mensaje implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tipoM;
	public abstract TipoMensaje getTipo();
	public abstract Object get();
}
