package Usuario;

import java.io.Serializable;

public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String nombreUsuario;
	
	public Usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String get() {
		return this.nombreUsuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Usuario usuario = (Usuario) obj;
		return nombreUsuario.equals(usuario.nombreUsuario);
	}

	@Override
	public int hashCode() {
		return nombreUsuario.hashCode();
	}

}
