package checkpoint.andela.parser;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ReactantBuffer
{

	// PROPERTIES
	public static final int RESOURCE_LIMIT = 1;
	private BlockingQueue<Reactant> queue = new ArrayBlockingQueue<>(RESOURCE_LIMIT);
	private boolean done = false;
	// LIST TO HOLD LOG FILES.
	
	public boolean isFull() {
		return queue.size() >= RESOURCE_LIMIT;
	}
	
	public boolean isEmpty() {
		return queue.size() == 0;
	}
	
	public synchronized void putReactant(Reactant reactant) {
		while (isFull()) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				// Ignore
			}
		}
		queue.add(reactant);
		notifyAll();
	}
	
	public synchronized Reactant getReactant() {
		while (isEmpty()) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				// Ignore
			}
		}
		Reactant reactantData = queue.poll();
		notifyAll();
		return reactantData;
	}
	
	public synchronized void setDone(boolean done) {
		this.done = done;
		notifyAll();
	}
	
	public synchronized boolean isDone() {
		return done;
	}
	
}
