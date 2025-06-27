package Concurrente.Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LectorEscritorMONImpl implements LectorEscritorMON{
    
	private final Lock lock;
	private int nr, nw;//Lectores y escritores activos
	private Condition colaLectores;
	private Condition colaEscritores;
	
	public LectorEscritorMONImpl() {
		this.lock = new ReentrantLock();
		this.colaLectores = lock.newCondition();
		this.colaEscritores = lock.newCondition();
		this.nr = 0;
		this.nw = 0;
		
	}	

	@Override
	public  void takeRead() {
		lock.lock();
		while(nw>0) {
			try {
				colaLectores.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nr++;
		lock.unlock();
	}

	@Override
	public  void releaseRead() {
		lock.lock();
		nr--;
		if(nr == 0) colaEscritores.signal();
		lock.unlock();
	}

	@Override
	public void takeWrite() {
		lock.lock();
		while(nw>0 || nr>0) {
			try {
				colaEscritores.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nw++;
		lock.unlock();
	}

	@Override
	public void releaseWrite() {
		lock.lock();
		nw--;
		colaEscritores.signal();
		colaLectores.signalAll();
		lock.unlock();
	}

}
