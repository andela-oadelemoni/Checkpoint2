package checkpoint.andela.parser;

import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ReactantBuffer
{
	// PROPERTIES
	public static final int RESOURCE_LIMIT = 1;
	private BlockingQueue<TreeMap<String, String>> queue = new ArrayBlockingQueue<>(RESOURCE_LIMIT);
	
	public boolean isFull() {
		return queue.size() >= RESOURCE_LIMIT;
	}
	
	public boolean isEmpty() {
		return queue.size() == 0;
	}
	
	public synchronized void putReactant(TreeMap<String, String> reactant) {
		while (isFull()) {
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				// Ignore
			}
		}
		queue.add(reactant);
		notifyAll();
	}
	
	public synchronized TreeMap<String, String> getReactant() {
		while (isEmpty()) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				// Ignore
			}
		}
		TreeMap<String, String> reactantData = queue.poll();
		notifyAll();
		return reactantData;
	}
	 
}
