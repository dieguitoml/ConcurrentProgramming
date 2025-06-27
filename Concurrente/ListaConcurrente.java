package Concurrente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Concurrente.Semaforos.LectorEscritorSEM;
import Concurrente.Semaforos.LectorEscritorSEMImpl;

public class ListaConcurrente<T> {//Clase genérica que controla el acceso concurrente a una lista 

private List<T> lista;
  private LectorEscritorSEM lock;
  
  public ListaConcurrente() {
	  this.lista = new ArrayList<T>();
	  this.lock = new LectorEscritorSEMImpl();
  }
  
  public List<T> read() {
	    lock.takeRead();
	    List<T> copia = new ArrayList<>(lista);
	    lock.releaseRead();
	    return copia;
	}
  
  public void add(T t) {
	  lock.takeWrite();
	  lista.add(t);
	  lock.releaseWrite();
  }
  
  public void erase (T t) {
	  lock.takeWrite();
	  lista.remove(t);
	  lock.releaseWrite();
  }
}
