package project3;

import java.util.Iterator;
import java.lang.Iterable;
import java.lang.IllegalArgumentException; 
import java.util.NoSuchElementException;

/**
 * MDeque class holds all the required public methods from the class MDeque Javadoc
 * page and additional methods for the functionalities of the Decode class.
 * The type of elements held in this class is generic.
 * This class extends the Object class and implements the Iterator interface.
 * 
 * This class implements a variation of a Doubly LinkedList design. 
 * 
 * @author Nick Yi
 *
 */
public class MDeque<E> extends Object implements Iterable<E> {
	
	// define internal Node class 
	private class Node {
		E data;
		Node next;
		Node previous;
		
		public Node(E data) {
			this.data = data;
			this.next = null;
			this.previous = null;
		} 
		
		public String convertString() {
			return String.valueOf(this.data);
		}
	}
	
	Node head = null;
	Node tail = null;
	Node middle = null;
	int size = 0;

	/**
	 * retrieves the back element of mdeque.
	 * @return the data in the last/back Node.
	 */
	public E peekBack() {
		if (tail == null) {
			return null; 
		}
		return tail.data; 
	}
	
	/**
	 * retrieves the front element of mdeque.
	 * @return the data in the first/front Node.
	 */
	public E peekFront() {
		if (head == null) {
			return null; 
		}
		return head.data;
	}
	
	/**
	 * retrieves the middle element of mdeque.
	 * @return the data in the middle Node.
	 */
	public E peekMiddle() {
		if (middle == null) {
			return null;
		}
		return middle.data; 
	}
	
	/**
	 * retrieves and removes the back element of mdeque.
	 * @return the data in the last/back Node.
	 */
	public E popBack() {
		if (tail == null) {
			return null; 
		}
		
		// store data from the last/back Node in a temporary variable
		E ret = tail.data;
		
		// the case where the initial size of the mdeque is 1
		if(this.size() == 1) {
			tail = null;
			head = null;
			middle = null;
			
			// update size of mdeque
			size--;
			
			return ret;
		}
		tail = tail.previous;
		tail.next = null;
		
		// update the middle pointer when the size of mdeque is even
		if (this.size()%2 == 0) {
			middle = middle.previous;
		}
		
		// update size of mdeque
		size--;
		
		return ret;
	}
	
	/**
	 * retrieves and removes the first element of mdeque.
	 * @return the data in the first/front Node.
	 */
	public E popFront() {
		if (head == null) {
			return null; 
		}
		
		// store data from the first/front Node in a temporary variable
		E ret = head.data;
		
		// the case where the initial size of the mdeque is 1
		if (this.size() == 1) {
			head = null;
			tail = null;
			middle = null;
			
			// update size of mdeque
			size--;
			
			return ret;
		}
		head = head.next;
		head.previous = null;
		
		// update the middle pointer when the size of mdeque is odd
		if (this.size()%2 == 1) {
			middle = middle.next;
		}
		
		// update size of mdeque
		size--;
		
		return ret; 
	}
	
	/**
	 * retrieves and removes the middle element of mdeque.
	 * @return the data in the middle Node.
	 */
	public E popMiddle() {
		if (middle == null) {
			return null; 
		}

		// store data from the middle Node in a temporary variable
		E ret = middle.data;
		
		// the case where the initial size of the mdeque is 1
		if (this.size() == 1) {
			middle = null;
			head = null;
			tail = null;
		} 
		
		// the case where the initial size of the mdeque is 2
		else if (this.size() == 2) {
			middle = middle.previous;
			tail = tail.previous;
			middle.next = null; 
		} 
		
		// update the middle pointer when the size of mdeque is odd
		else if (this.size()%2 == 1) {
			middle.next.previous = middle.previous; 
			middle.previous.next = middle.next;
			middle = middle.next;
		} 
		
		// update the middle pointer when the size of mdeque is even
		else {
			middle.next.previous = middle.previous; 
			middle.previous.next = middle.next;
			middle = middle.previous;
		} 
		
		// update size of mdeque
		size--;
		
		return ret;
	}

	/**
	 * inserts the specified item at the back of mdeque.
	 * @param item is the generic data that is to be inserted.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void pushBack(E item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("Invalid. Item cannot be null.");
		}
		
		// new Node to which to insert in mdeque
		Node newNode = new Node(item);
		
		// the case where the mdeque is null
		if (head == null) {
			head = newNode;
			tail = newNode;
			middle = newNode;
		}
		
		// the case where the mdeque is not null
		else {
			tail.next = newNode;
			newNode.previous = tail;
			tail = newNode;
			tail.next = null;
			
			// update the middle pointer when the size of mdeque is odd
			if (this.size()%2 == 1) {
				middle = middle.next; 
			}
		}
		
		// update size of mdeque
		size++;
	}
	
	/**
	 * inserts the specified item at the front of mdeque.
	 * @param item is the generic data that is to be inserted.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void pushFront(E item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("Invalid. Item cannot be null.");
		}

		// new Node to which to insert in mdeque
		Node newNode = new Node(item);
		
		// the case where the mdeque is null
		if (head == null) {
			head = newNode;
			tail = newNode;
			middle = newNode;
		}
		
		// the case where the mdeque is not null
		else {
			newNode.next = head;
			newNode.previous = null;
			head.previous = newNode;
			head = newNode;	
			
			// update the middle pointer when the size of mdeque is even
			if (this.size()%2 == 0) {
				middle = middle.previous; 
			}
		}
		
		// update size of mdeque
		size++;
	}
	
	/**
	 * inserts the specified item in the middle of mdeque.
	 * @param item is the generic data that is to be inserted.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void pushMiddle(E item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("Invalid. Item cannot be null.");
		}
		
		// new Node to which to insert in mdeque
		Node newNode = new Node(item);
		
		// the case where the mdeque is empty
		if (this.size() == 0) {
			head = newNode;
			tail = newNode;
			middle = newNode;
		}
		
		// the case where the size of mdeque is 1
		else if (this.size() == 1) {
			tail.next = newNode; 
			newNode.previous = tail;
			tail = newNode; 
			middle = newNode; 
		}
		
		// the case where the size of mdeque is greater than 1
		else {
			
			// inserting the new specified item in an even mdeque
			if ( this.size()%2 == 0) {
				middle.previous.next = newNode; 
				newNode.previous = middle.previous;
				middle.previous = newNode; 
				newNode.next = middle;
				middle = newNode; 
			}
			
			// inserting the new specified item in an odd mdeque
			else {
				newNode.next = middle.next;
				middle.next = newNode;
				newNode.previous = middle;
				newNode.next.previous = newNode;
				middle = newNode; 
			}
		}
		
		// update size of mdeque
		size++;
	}
	
	/**
	 * generates a string representation of mdeque.
	 * this method wraps a recursive method.
	 * @return the string representation of the entire specified mdeque.
	 */
	public String toString() {
		return ( "[" + this.stringHelper(this.tail) + "]" );
	}
	
	/**
	 * this method is the wrapper recursive method for toString().
	 * the method is implemented recursively and is an additional 
	 * private method of the MDeque class.
	 * @param is the last/back element of the mdeque.
	 * @return the recursive call(s) until the base case is reached.
	 */
	private String stringHelper(Node lastElement) {
		
		// the case where the mdeque is null
		if (lastElement == null) {
			return "";
		} 
		
		// the base case to be reached
		else if (lastElement.previous == null ) {
			return lastElement.convertString();
		} 
		
		// calls stringHelper method and converts current lastElement into string
		else {
			return ( stringHelper(lastElement.previous) + ", " + lastElement.convertString() );
		}
	}
	
	/**
	 * @return an iterator over the elements in mdeque in proper sequence.
	 */
	public Iterator<E> iterator() {
		return new MDequeBothIterator(head, true);
	}
	
	/**
	 * @return an iterator over the elements in mdeque in reverse sequential order.
	 */
	public Iterator<E> reverseIterator() {
		return new MDequeBothIterator(tail, false);
	}

	/**
	 * define the iterator class for both iterator() and reverseIterator() methods.
	 */
	private class MDequeBothIterator implements Iterator<E> {
		Node current;
		boolean towardsRight;
		
		/**
		 * @param the initial node for the iterator to start on. 
		 * @param indicates whether or not the iteration will occur towards the 
		 * right of the mdeque; distinguishes regular iteration and reverse iteration.
		 */
        public MDequeBothIterator(Node current, boolean towardsRight) {
            this.current = current;
            this.towardsRight = towardsRight;
        }
        
		/**
		 * iteration on mdeque; allows for both regular or reverse iteration.
		 * @throws NoSuchElementException if the iterator has gone through the whole mdeque sequence.
		 */
        @Override
        public E next() throws NoSuchElementException {
            if (this.hasNext()) {
                E temp = current.data;
                if (towardsRight) {
                	current = current.next;
                } else {
                	current = current.previous;
                }
                return temp;
            } else {
                throw new NoSuchElementException();
            }
        }
        
		/**
		 * @return whether the iterator has or has not iterated through the whole mdeque sequence.
		 */
        @Override
        public boolean hasNext() {
        	return current != null;
        }
	}
	
	/**
	 * An additional method in the MDeque class.
	 * is utilized in the Decode class for throwing NoSuchElementException
	 * for instructions 'F' and 'B'.
	 * @return the current size of mdeque.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * An additional method in the MDeque class.
	 * is utilized in the Decode class to execute the reversing of sequence instruction.
	 */
	public void reverse() {
		Node tempNode = null;
		Node current = head;
		
		// iterate through the whole mdeque sequence and switch the previous and next nodes
		while (current != null) {
			tempNode = current.next;
			current.next = current.previous;
			current.previous = tempNode;
			current = current.previous;
		}
		
		// switch the pointer of head, tail, and middle to match the reversed mdeque sequence
		tempNode = head;
		head = tail;
		tail = tempNode;
	}
}

