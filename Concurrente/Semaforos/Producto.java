package Concurrente.Semaforos;

import java.io.Serializable;

public class Producto <T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T obj;
	
	public Producto (T obj) {
		this.obj = obj;
	}
	
	public void resetProduct(T objeto) {
		this.obj=objeto;
	}
	public T getObj() {
		return this.obj;
	}
	
	public void setObj(T obj) {
		this.obj = obj;
	}

}
