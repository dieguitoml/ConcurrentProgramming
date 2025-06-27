package Concurrente.Monitores;
public interface LectorEscritorMON {

	public void takeRead();
	public void releaseRead();
	public void takeWrite();
	public void releaseWrite();
}
