package Concurrente.Semaforos;

public interface LectorEscritorSEM {

	public void takeRead();
	public void releaseRead();
	public void takeWrite();
	public void releaseWrite();
}
