package Concurrente.Locks;

public class RompeEmpate extends Lock {
	private  VolatileObject<Integer> []in;
	private  VolatileObject<Integer> []last;
	private int numProcesos;
	
	
	@SuppressWarnings("unchecked")
	public RompeEmpate(int n) {
		this.numProcesos = n;
		this.in = new VolatileObject[n];
		this.last = new VolatileObject[n];
		for(int i=0;i<n;i++) {
			this.in[i] =new VolatileObject<Integer>();
			this.last[i] = new VolatileObject<>();
			this.in[i].setObj(0);
			this.last[i].setObj(0);
		}
	}
	
	@Override
	public void takeLock(int id) {
		for(int j=1;j<=this.numProcesos;j++) {
			in[id-1].setObj(j);
			last[j-1].setObj(id);
			for(int k=1;k<=this.numProcesos;k++) {
				if(k!=id) {
					while(in[k-1].getObj()>=in[id-1].getObj() && last[j-1].getObj()==id);
				}
			}
		}
	}
	@Override
	public void releaseLock(int id) {
		in[id-1].setObj(0);
	}
}
