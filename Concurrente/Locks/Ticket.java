package Concurrente.Locks;
import java.util.concurrent.atomic.AtomicInteger;


public class Ticket extends Lock{
	
	private volatile AtomicInteger number;
	private VolatileObject<Integer> next;
	private int N = 100;
	
	public Ticket() {
		this.number = new AtomicInteger(1);
		this.next = new VolatileObject<Integer>();
		this.next.setObj(1);
	}
	
	@SuppressWarnings("unchecked")
	public Ticket(int N) {
		this.N = N;
		this.number = new AtomicInteger(1);
		this.next = new VolatileObject<Integer>();
		this.next.setObj(1);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void takeLock() {
		int myTurn = number.getAndAdd(1);

		if (myTurn == N) {
			number.getAndAdd(-N);
		}

		if (myTurn > N) {
			myTurn = myTurn - N;
		}
		
		while (myTurn!=this.next.getObj()) {
			Thread.yield(); // Evita consumo excesivo de CPU
		}
	}

	public void releaseLock() {
		int nextTurn = (this.next.getObj() % N) + 1;
		this.next.setObj(nextTurn);
	}

	@Override
	public void takeLock(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void releaseLock(int id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
