import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author jalennall
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head;
	private LinearNode<T> tail;
	int size;
	int modCount;

	public IUDoubleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (isEmpty()) {// empty list
			head = tail = newNode;
		} else {// general case
			newNode.setNext(head);
			head.setPrevious(newNode);
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (isEmpty()) {// empty list
			head = tail = newNode;
			//			tail.setPrevious(newNode);
			//			newNode.setNext(tail);
			//			head = newNode;
		} else {// general case
			tail.setNext(newNode);
			newNode.setPrevious(tail);
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
		LinearNode<T> current = head;
		while (current != null && !current.getElement().equals(target)) {
			current = current.getNext();
		}
		if (current == null) {// didn't find
			throw new NoSuchElementException();
		}
		// found target
		LinearNode<T> newNode = new LinearNode<T>(element);
		newNode.setNext(current.getNext());
		newNode.setPrevious(current);
		current.setNext(newNode);
		if (current == tail) {
			tail = newNode;
		} else {
			newNode.getNext().setPrevious(newNode);
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (isEmpty()) {// emptylist
			head = tail = newNode;
		} else if (index == 0) {// head
			newNode.setNext(head);
			head.setPrevious(newNode);
			head = newNode;
		} else if (index == size) // tail
		{
			newNode.setNext(null);
			newNode.setPrevious(tail);
			tail.setNext(newNode);
			tail = newNode;
		} else {// general
			LinearNode<T> current = head;
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			newNode.setPrevious(current);
			newNode.getNext().setPrevious(newNode);
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		T retVal = null;
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else if (head == tail && head != null) {// 1 element list
			retVal = head.getElement();
			head = tail = null;
		} else {
			retVal = head.getElement();
			LinearNode<T> helper = head;
			head = helper.getNext();
			head.setPrevious(null);
			helper.setNext(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {
		T retVal = null;
		if (size == 0) {// empty
			throw new NoSuchElementException();
		}
		if (head == tail) {// 1 element list
			retVal = head.getElement();
			head = tail = null;
		} else {// general case
			LinearNode<T> holder = tail;
			retVal = tail.getElement();
			tail = tail.getPrevious();
			tail.setNext(null);
			holder.setPrevious(null);
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
		while (current != null && !found) {// searches
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		if (!found) {// not in list
			throw new NoSuchElementException();
		}
		if (size() == 1) { // only node
			head = tail = null;
		} else if (current == head) { // first node
			head = current.getNext();
			head.setPrevious(null);
		} else if (current == tail) { // last node
			tail = current.getPrevious();
			tail.setNext(null);
		} else { // somewhere in the middle
			previous.setNext(current.getNext());
			if (size == 3) {
				current.setPrevious(current.getPrevious().getPrevious());
				tail.setPrevious(head);
				current.setNext(null);
			}
		}
		size--;
		modCount++;
		return current.getElement();
		// ListIterator<T> lit = listIterator();
		// T retVal = null;
		// boolean foundIt = false;
		// while(lit.hasNext() && !foundIt);{
		// retVal = lit.next();
		// if(retVal.equals(element){
		// foundIt = true;
		// }
		// if(!foundIt) {
		// throw new NoSuchElementException();
		// }
		// lit.remove();
		// return retVal;
		// }
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		if (size == 1) {// single element list
			retVal = head.getElement();
			head = tail = null;
		} else if (index == 0) {// head
			retVal = head.getElement();
			head = head.getNext();
		} else {
			LinearNode<T> current = head;
			int currentIndex = 0;
			while (currentIndex < index - 1) {
				current = current.getNext();
				currentIndex++;
			}
			retVal = current.getNext().getElement();
			current.setNext(current.getNext().getNext());
			if (current.getNext() == null) {// tail
				tail = current;
			}
		}
		size--;
		modCount++;
		return retVal;
		// ListIterator<T> lit = listIterator(index);
		// T retVal=lit.next();
		// lit.remove();
		// return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		for (int i = 0; i < index; i++) {
			newNode = newNode.getNext();
		}
		newNode.setElement(element);
		modCount++;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		for (int i = 0; i < index; i++) {
			newNode = newNode.getNext();
		}
		return newNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		if (isEmpty()) {
			return -1;
		}
		LinearNode<T> newNode = new LinearNode<T>();
		newNode = head;
		int index = 0;
		boolean found = false;
		while (!found && index < size) {
			if (newNode.getElement() == element) {
				found = true;
			} else {
				index++;
				newNode = newNode.getNext();
			}
		}
		if (!found) {
			return -1;
		}
		return index;
	}

	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		if (isEmpty()) {
			return false;
		}
		LinearNode<T> checker = head;
		while (checker != null && checker.getElement() != target) {
			checker = checker.getNext();
		}
		if (checker == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	public String toString() {
		String String = "[";
		for (int i = 0; i < size; i++) {
			String += "[" + i + "]";
		}
		String += "]";
		return String;
	}
public int CalcSize(LinearNode<T> n) {
	LinearNode<T> current = head;
	while(current != null && !current.getElement().equals(n.getElement())) {
		current = current.getNext();
	}
	if (current == null) {
		throw new NoSuchElementException();
	}
	else {
		if(current == tail) {
			return 1;
		}
		else {
		return	CalcSize(current.getNext())+1;
	
		}
	
}
	
}
	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLLIterator(startingIndex);
	}

	private class DLLIterator implements ListIterator<T> {
		private LinearNode<T> nextNode;
		private LinearNode<T> lastReturnNode;
		private int nextIndex;
		private int iterModCount;

		/** Creates a new iterator for the list */
		public DLLIterator() {
			this(0);
		}

		public DLLIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			nextIndex = 0;
			iterModCount = modCount;
			lastReturnNode = null;
			for (; nextIndex < startingIndex; nextIndex++) {
				nextNode = nextNode.getNext();

			}
		}

		@Override
		/*
		 Inserts the specified element into the list (optional operation).The element is inserted immediately before the element that
		 would be returned by next(), if any, and after the element
		 that would be returned by previous(), if any. (If the
		// list contains no elements, the new element becomes the sole element
		 on the list.)// The new element is inserted before the implicit
		 cursor: a subsequent call to next would be unaffected, and a
		 subsequent call to previous would return the new element.(This call increases by one the value that would be returned by a
		 call to nextIndex or previousIndex.)
		 */
		public void add(T element) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			LinearNode<T> newNode = new LinearNode<T>(element);
			if (isEmpty()) {//empty list
				head = tail = newNode;
				size++;
				modCount++;
			}
			else if (nextNode == head && lastReturnNode == null){//head
				addToFront(element);
				nextNode = head.getNext();
				lastReturnNode = head;
			}
			else if(nextNode == null && lastReturnNode != null) {//tail
				addToRear(element);
				lastReturnNode = tail;
				nextNode = null;
			}
			else if(nextNode == lastReturnNode) {//previous call
				if(nextNode == head) {
					addToFront(element);
					nextNode = head.getNext();
					lastReturnNode = head;
				}
				else if(nextNode == null) {
					addToRear(element);
					lastReturnNode = tail;
					nextNode = null;
				}
				else
				{
					newNode.setNext(nextNode);
					newNode.setPrevious(lastReturnNode);
					lastReturnNode.setNext(newNode);
					nextNode.setPrevious(newNode);
					lastReturnNode = newNode;	
					size++;
					modCount++;
				}
			}
			else { 
				if (lastReturnNode == null)
				{
					newNode.setNext(nextNode);
					newNode.setPrevious(lastReturnNode);
					nextNode.setPrevious(newNode);
					lastReturnNode = newNode;
					size++;
					modCount++;
				}
				else
				{
					newNode.setNext(nextNode);
					newNode.setPrevious(lastReturnNode);
					lastReturnNode.setNext(newNode);
					nextNode.setPrevious(newNode);
					lastReturnNode = newNode;	
					size++;
					modCount++;
				}

			}
			iterModCount++;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		@Override
		public boolean hasPrevious() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != head);
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturnNode = nextNode;
			T elem = nextNode.getElement();
			nextNode = nextNode.getNext();
			nextIndex++;
			return elem;

		}

		@Override
		public int nextIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			if (nextNode == null) {// iterator at end of list
				nextNode = tail;
				lastReturnNode = nextNode;
				nextIndex--;
				return nextNode.getElement();
			} else {// else
				nextNode = nextNode.getPrevious();
				lastReturnNode = nextNode;
				nextIndex--;
				return nextNode.getNext().getElement();
			}
		}

		@Override
		public int previousIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}

			if (lastReturnNode == null) {
				throw new IllegalStateException();
			}			
			if (lastReturnNode == head) {
				head = lastReturnNode.getNext();
				if (head != null) {
					head.setPrevious(null);
				}
			} else {
				lastReturnNode.getPrevious().setNext(lastReturnNode.getNext());
			}
			if (lastReturnNode == tail) {
				tail = lastReturnNode.getPrevious();
				if (tail != null) {
					tail.setNext(null);
				}
			} else {
				lastReturnNode.getNext().setPrevious(lastReturnNode.getPrevious());
			}
			size--;
			modCount++;
			iterModCount++;
			lastReturnNode = null;

		}

		@Override
		public void set(T element) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturnNode == null) {
				throw new IllegalStateException();
			}
			lastReturnNode.setElement(element);
			iterModCount++;
			modCount++;
		}
	}
}