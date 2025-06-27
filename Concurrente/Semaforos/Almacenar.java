package Concurrente.Semaforos;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class Almacenar implements Almacen , Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Semaphore empty;
	private Semaphore full;
	private Semaphore cs1,cs2;
	private Producto<Integer> producto[];
	private static final int N = 10;
	private int ini;//Primer elemento lleno
	private int fin;//Primer elemento vac�o
	@SuppressWarnings("unchecked")
	public Almacenar() {
		this.producto =  new Producto[N];
		for(int i=0;i<N;i++) {
			producto[i] = new Producto<Integer>(100-i);
		}
		this.empty =  new Semaphore(0);
		this.full =  new Semaphore(N);
		this.cs1 = new Semaphore(1);
		this.cs2 = new Semaphore(1);
		this.ini = 0;
		this.fin = 0;
		
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void almacenar(Producto producto) {
		// TODO Auto-generated method stub
		
		try {
			empty.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cs1.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.producto[fin]=producto;
		this.fin = (this.fin+1)%N;
		cs1.release();
		full.release();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Producto extraer() {
		// TODO Auto-generated method stub+
		Producto pr;
		try {
			full.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cs2.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pr = this.producto[ini];
		this.ini = (this.ini + 1)%N;
		cs2.release();
		empty.release();
		return pr;
	}

}
