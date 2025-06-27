package Concurrente.Semaforos;

import java.util.concurrent.Semaphore;

public class LectorEscritorSEMImpl implements LectorEscritorSEM{
    
	
	private Semaphore e;
	private Semaphore r,w;
	private int nr, nw;//Lectores y escritores activos
	private int dr, dw;//Lectores y escritores que fueron retrasados
	
	
	private int N;
	public LectorEscritorSEMImpl() {
		this.e =  new Semaphore(1);
		this.r =  new Semaphore(0);
		this.w = new Semaphore(0);
		this.nr = 0;
		this.nw = 0;
		this.dr = 0;
		this.dw = 0;
		
	}	

	@Override
	public void takeRead() {
		// TODO Auto-generated method stub
		try {
			e.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nr > 0 || nw > 0) {
			dw++;
			e.release();
			try {
				w.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		nw++;
		e.release();
		
	}

	@Override
	public void releaseRead() {
		// TODO Auto-generated method stub
		try {
			e.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		nw--;
		if(dr > 0) {
			dr--;
			r.release();
		}
		else if (dw > 0) {
			dw--;
			w.release();
		}
		else {
			e.release();
		}
	}

	@Override
	public void takeWrite() {
		// TODO Auto-generated method stub
		try {
			e.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nw>0) {
			dr++;
			e.release();
			try {
				r.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		nr++;
				
		if(dr > 0) {
			dr--;
			r.release();
		}
		else {
			e.release();
		}
	}

	@Override
	public void releaseWrite() {
		// TODO Auto-generated method stub
		try {
			e.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nr--;
		if(nr == 0 && dw>0) {
			dw--;
			w.release();
		}
		else {
			e.release();
		}
	}

}
