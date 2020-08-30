import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList. An Iterator with working
 * remove() method is implemented, but ListIterator is unsupported.
 * 
 * @author 
 *
 * @param <T> type to store
 */


public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;

	private T[] array;
	private int rear;
	private int modCount;

	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty list with the given initial capacity
	 * 
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[]) (new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}

	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	//
	public void addToFront(T element) {

		if (rear == array.length) {
			expandCapacity();
		}
		for (int index = rear - 1; index >= 0; index--) {
			array[index + 1] = array[index];
		}
		rear++;
		modCount++;
		array[0] = element;
	}

	//
	@Override
	public void addToRear(T element) {
		if (rear == array.length) {
			expandCapacity();
		}
		
		array[rear] = element;
		rear++;
		modCount++;
	}

	//
	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		if (rear == array.length) {
			expandCapacity();
		}
		int index = indexOf(target);
		if (index < 0 || index >= rear) {
			throw new NoSuchElementException();
		}
		for (int i = rear - 1; i > index; i--) {
			array[i + 1] = array[i];
		}
		rear++;
		array[index+1] = element;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}
		if (rear == array.length) {
			expandCapacity();
		}
		for (int i = rear - 1; i > index; i--) {
			array[i + 1] = array[i];
		}
		rear++;
		array[index] = element;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (rear == 0) {
			throw new NoSuchElementException();
		}
		
		T retval = array[0];
		for (int i = 0; i < rear-1; i++) {
			array[i] = array[i+1];
		}

		rear--;
		array[rear] = null;
		modCount++;
		return retval;
	}

	@Override
	public T removeLast() {
		if (rear == 0) {
			throw new NoSuchElementException();
		}
		
		rear--;		
		T retVal = array[rear];
		array[rear] = null;
		
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);

		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}

		T retVal = array[index];

		// shift elements
		for (int i = index; i < rear; i++) {
			array[i] = array[i + 1];
		}

		rear--;
		array[rear] = null;
		modCount++;

		return retVal;
	}

	@Override
	public T remove(int index) {
		if ((index < 0 || index >= rear)) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = array[index];
		for (int i = index; i < rear; i++) {
			array[i] = array[i + 1];
		}
		rear--;
		array[rear] = null;
		modCount++;
		return retVal;
		//[1,1,2,2]
		//
	}

	@Override
	public void set(int index, T element) {
		if ((index < 0 || index >= rear)) {
			throw new IndexOutOfBoundsException();
		}
		array[index] = element;
		modCount++;
	}

	@Override
	public T get(int index) {
		if ((index < 0 || index >= rear)) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;

		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}

		return index;
	}

	@Override
	public T first() {
		if (array[0] == array[rear]) {
			throw new NoSuchElementException();
		}
		return array[0];
	}

	@Override
	public T last() {
		if (array[0] == array[rear]) {
			throw new NoSuchElementException();
		}

		return array[rear - 1];
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		// TODO
		return (rear == 0);
	}

	@Override
	public int size() {
		return rear;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");

		for (T element : this) {
			str.append(element.toString() + ", ");
		}

		if (size() > 0) {
			str.delete(str.length() - 2, str.length());
		}

		str.append("]");

		return str.toString();

	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		private boolean canRemove;

		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextIndex < rear);
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			nextIndex++;
			canRemove = true;
			return array[nextIndex - 1];
		}

		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!canRemove) {
				throw new IllegalStateException();
			}
			for (int i = nextIndex - 1; i < rear - 1; i++) {
				array[i] = array[i + 1];
			}
			rear--;
			array[rear] = null;
			nextIndex--;
			modCount++;
			iterModCount++;
			canRemove = false;

		}
	}
}
