package Concurrente.Locks;

public class Bakery extends Lock{
	
	private  VolatileObject<Integer>[] turn;
	private int numProcesos = 1;
	@SuppressWarnings("unchecked")
	public Bakery(int n){
		this.numProcesos = n;
		this.turn = new VolatileObject[numProcesos];	
		for(int i = 0; i < numProcesos; ++i) {
			turn[i] = new VolatileObject<Integer>();
			turn[i].setObj(0);
		}	
	}
	

	private int max() {
		int max = turn[0].getObj();
		for (int i = 0; i < this.numProcesos; i++) {
			if (turn[i].getObj() > max)
				max = turn[i].getObj();
		}
		return max;
	}
	
	private boolean operator(int a, int b, int c, int d){
		return (a>c) || (a==c && b > d);
	}

	@Override
	public void takeLock(int id) {
		this.turn[id - 1].setObj(1);
		this.turn[id - 1].setObj(max() + 1);
		for (int j = 1; j <= this.numProcesos; j++) {
			if (j != id) {
				while (this.turn[j - 1].getObj() != 0 &&
						operator(turn[id - 1].getObj(), id, turn[j - 1].getObj(), j)); 
			}
		}
	}

	@Override
	public void releaseLock(int id) {
		this.turn[id - 1].setObj(0);
	}
}
