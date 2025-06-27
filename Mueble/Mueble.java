package Mueble;

import java.io.Serializable;

public class Mueble implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tipoMuble;
	int id_mueble;
	String descripcion;
	
	public Mueble (String tipoMueble, int id_mueble, String descripcion) {
		this.tipoMuble = tipoMueble;
		this.id_mueble = id_mueble;
		this.descripcion = descripcion;
	}

	public Mueble(Mueble m) {
		// TODO Auto-generated constructor stub
		this.tipoMuble = m.tipoMuble;
	    this.id_mueble = m.id_mueble;
	    this.descripcion = m.descripcion;
	}

	public int getId() {
		return this.id_mueble;
	}
	
	public String getTipo() {
		return this.tipoMuble;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}	
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Mueble mueble = (Mueble) o;
	    return id_mueble == mueble.id_mueble &&
	           tipoMuble.equals(mueble.tipoMuble);
	}

	@Override
	public int hashCode() {
    	return java.util.Objects.hash(id_mueble, tipoMuble);
	}

}
