package Concurrente;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Concurrente.Monitores.LectorEscritorMON;
import Concurrente.Monitores.LectorEscritorMONImpl;
import Mueble.Mueble;
import Usuario.Usuario;

public class MapaConcurrente <T,Z>{
	//Mapa de clave valor de tipo generico.
	//Se gestiona concurrentemente usando RW
	//Una tabla con RW de monitor, otra de semaforo
	
	private Map<T,Z> mapa;
	private LectorEscritorMON lock;
	
	public MapaConcurrente() {//Modelo lectores-escritores con monitores
		this.mapa = new HashMap<T,Z>();
		this.lock = new LectorEscritorMONImpl();
	}
	
	public Map<T,Z> read(){
		Map<T,Z> mapL;
		lock.takeRead();
		mapL = mapa;
		lock.releaseRead();
		return mapL;
	}
	
	public Z put(T t, Z z) {
		lock.takeWrite();
		Z aux = mapa.put(t, z);
		lock.releaseWrite();
		return aux;
	}
	
	public Set<T> keySet(){
		Set<T> key;
		lock.takeRead();
		key = mapa.keySet();
		lock.releaseRead();
		return key;
	}
	
	public void remove (T t) {
		lock.takeWrite();
		mapa.remove(t);
		lock.releaseWrite();
	}
	
	public Z get (T t) {
		Z z = null;
		lock.takeRead();
		z = mapa.get(t);
		lock.releaseRead();
		return z;
	}

	public void removeByValue(Z value) {
	    lock.takeWrite(); // asegurar exclusiï¿½n mutua
	    try {
	        Iterator<Map.Entry<T, Z>> it = mapa.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<T, Z> entry = it.next();
	            if (entry.getValue().equals(value)) {
	                it.remove(); // eliminaciï¿½n segura durante iteraciï¿½n
	            }
	        }
	    } finally {
	        lock.releaseWrite();
	    }
	}
	@SuppressWarnings("unchecked")
	public T findMuebleByTipoYId(T t) {
	    T resultado = null;
	    lock.takeRead();
	    for (T m : mapa.keySet()) {
	    	if (m instanceof Mueble && m.equals(t)) {
                resultado = m; // ✅ constructor copia
                break;
            }
	    }
	    lock.releaseRead();
	    return resultado;
	}
	
}
