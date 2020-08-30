import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author JalenNall
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode= new LinearNode<T>(element);

		if (isEmpty() ) {
			head = tail = newNode;
		}
		else {
			newNode.setNext(head);
			head = newNode;
		}

		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode= new LinearNode<T>(element);
		if(isEmpty()) {
			head =  tail = newNode;
		}
		else {
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		LinearNode<T> targetNode= head;
		while(targetNode!=null && targetNode.getElement()!=target) {
			targetNode = targetNode.getNext();
		}
		if(targetNode == null) {
			throw new NoSuchElementException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);
		newNode.setNext(targetNode.getNext());
		targetNode.setNext(newNode);
		if(targetNode == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if(index<0 || index>size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode =new LinearNode<T>(element);
		if (isEmpty()) {
			head = tail = newNode;
		}
		else if(index ==0) {
			newNode.setNext(head);
			head = newNode;
			if(size ==0) {
				tail = newNode.getNext();
			}
		}
		else {
			LinearNode<T> current = head;
			for(int i = 0;i<index-1;i++) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			if(newNode.getNext()==null){
				tail = newNode;
			}

		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		T retVal = null;

		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		else if(head == tail) {
			retVal = head.getElement();
			head = tail = null;

		}
		else {
			retVal = head.getElement();
			head = head.getNext();	
		}
		size--;
		modCount++;
		return retVal;

	}


	@Override
	public T removeLast() {
		T retVal = null;
		if(size ==0) {//empty
			throw new NoSuchElementException();
		}
		if(head.getNext()==null) {// 1 element list
			retVal = head.getElement();
			head = tail = null;
		}
		else {//general case
			LinearNode<T> current = head;
			while(current.getNext()!= tail) {
				current = current.getNext();
			}
			retVal = tail.getElement();
			tail = current;
			current.setNext(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found) {
			throw new NoSuchElementException();
		}

		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}

		size--;
		modCount++;

		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if(index<0 || index>= size) {
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		if (size == 1) {
			retVal = head.getElement();
			head = tail = null;
		}
		else if(index ==0){
			retVal = head.getElement();
			head = head.getNext();
		}
		else {
			LinearNode <T> current = head;
			int currentIndex = 0;
			while(currentIndex < index-1) {
				current = current.getNext();
				currentIndex++;
			}
			retVal = current.getNext().getElement();
			current.setNext(current.getNext().getNext());
			if(current.getNext() == null) {
				tail = current;
			}			
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		for (int i=0;i<index;i++) {
			newNode = newNode.getNext();

		}
		newNode.setElement(element);
		modCount++;
	}

	@Override
	public T get(int index) {
		if (index<0 || index >=size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		for (int i=0;i<index;i++) {
			newNode = newNode.getNext();

		}
		return newNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty()) {
			return -1;
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		int index = 0;
		boolean found = false;
		while(!found&&index < size) {
			if(newNode.getElement() == element) {
				found = true;
			}
			else {
				index++;
				newNode = newNode.getNext();
			}
		}
		if(!found) {
			return -1;
		}
		return index;
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}

		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}

		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		if(isEmpty()) {
			return false;
		}
		LinearNode<T> checker = head;
		while(checker!= null && checker.getElement()!=target) {
			checker = checker.getNext();}
		if(checker == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO 
		return size ==0;
	}

	@Override
	public int size() {

		return size;
	}
	@Override
	public String toString() {
		String String = "[";
		for (int i=0;i<size;i++) {
			String += "["+ i +"]";
		}
		String += "]";
		return String;
	}
	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode;
		private int iterModCount;
		private boolean canRemove;


		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove =false;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount!=modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			canRemove = true;
			return retVal;
		}

		@Override
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!canRemove) {
				throw new IllegalStateException();
			}
			canRemove = false;
			if(head.getNext() == nextNode) {//removing the head
				head = nextNode;
				if(head == null) {
					tail = null;
				}
			}
			else {
				//general case -- in the middle somewhere
				LinearNode<T> current = head;
				while(current.getNext().getNext() != nextNode) {
					current = current.getNext();
				}
				current.setNext(nextNode);
				if(nextNode == null) {//removing the tail
					tail = current;
				}
			}
			modCount++;
			iterModCount++;
			size--;
		}
	}
}
