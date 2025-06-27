package Concurrente;

import java.io.Serializable;

import Concurrente.Semaforos.Almacenar;
import Concurrente.Semaforos.Producto;

public class PuertoConcurrente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Almacenar prodConsumidor;
	public PuertoConcurrente() {
		prodConsumidor = new Almacenar();
	}
	
	public int extraerPuerto() {
		int puerto = (int) prodConsumidor.extraer().getObj();
		return puerto;
	}
	
	public void aniadirPuerto(int puerto) {
		Producto<Integer> p = new Producto<Integer>(puerto);
		prodConsumidor.almacenar(p);
	}
}
