package Concurrente.Locks;

public class VolatileObject <T> {
	private volatile T obj;
	
	public T getObj() {
		return this.obj;
	}
	
	public void setObj(T obj) {
		this.obj = obj;
	}	
}
