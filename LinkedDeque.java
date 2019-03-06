import java.util.NoSuchElementException;

/**
 * Linked implementation of Java's "Deque"
 * 
 * @author Brennon Miller
 * @version 1.0.0
 */

public class LinkedDeque<T> implements Deque<T> {
    private Node<T> front;  // First item in the deque
    private Node<T> back;   // Last    "   "  "    "
    int size = 0;           // Number of items in the deque.

    public LinkedDeque() {}

    // Throws an exception:
    public void addFirst(T obj) {
        Node newVal = new Node(obj);    // Create new Node for obj
        if (isEmpty()) {
            this.back = newVal;         // Set as back if deque is already empty
        } else {
            link(this.front, newVal);   // Otherwise, link to front...
        }
        this.front = newVal;            // ...and set as new front
        size++;
    }
    
    public T removeFirst() {
        T val = this.getFirst();            // Get value to return later.
        this.front = this.front.getNext();  // Set front to next Node.
        size--;                             // Decrement size.
        return val;                         // Return saved value.
    }

    public T getFirst() {
        if (this.isEmpty()) {                   // This is empty!
            throw new NoSuchElementException(); // YEET!
        }
        return this.front.element;
    }

    public void addLast(T obj) {        // See addFirst for details; same order
        Node newVal = new Node(obj);    // of events, other end.
        if (isEmpty()) {
            this.front = newVal;
        } else {
            link(newVal, this.back);
        }
        this.back = newVal;
        size++;
    }

    public T removeLast() {
        T val = this.getLast();
        this.front = this.back.getPrev();
        size--;
        return val;
    }

    public T getLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.back.element;
    }
    
    // Each of the following calls its exception-throwing equivalent, but will
    // catch the exception and replace it with a special value if needed.

    public boolean offerFirst(T obj) {
        addFirst(obj);
        return true;
    }

    public T pollFirst() {
        try {
            return removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public T peekFirst() {
        try {
            T obj = this.getFirst();
            return obj;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean offerLast(T obj) {
        addLast(obj);
        return true;
    }

    public T pollLast() {
        try {
            return removeLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public T peekLast() {
        try {
            T obj = this.getLast();
            return obj;
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    
    // Gives other information:
    public int size() { return this.size; }

    public boolean contains(T obj) {
        return (findNodeContaining(obj, this.front) == null);
    }

    public boolean remove(T obj) {
        Node wanted = findNodeContaining(obj, this.front);
        if (wanted == null) {
            return false;
        }
        if (wanted == this.front) {
            removeFirst();
        } else if (wanted == this.back) {
            removeLast();
        } else {
            link(wanted.getPrev(), wanted.getNext());
        }
        size--;
        return true;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public void clear() {
        this.front = null;
        this.back = null;
        size = 0;
    }


// ===== Private/helper code ===================================================

    private class Node<T> {
        private T element;
        private Node next;
        private Node prev;

        protected Node(T obj) {
            this.element = obj;
            this.prev = null;
            this.next = null;
        }

        /**
         * @return T return the element
         */
        protected T getElement() {
            return element;
        }

        /**
         * @return Node return the next
         */
        protected Node getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        protected void setNext(Node next) {
            this.next = next;
        }

        /**
         * @return Node return the prev
         */
        protected Node getPrev() {
            return prev;
        }

        /**
         * @param prev the prev to set
         */
        protected void setPrev(Node prev) {
            this.prev = prev;
        }

    }

    private Node findNodeContaining(T obj, Node n) {
        if (n == null) {
            return null;
        }
        if (n.getElement() == obj) {
            return n;
        }
        if (n.getElement().equals(obj) && obj != null) {
            return n;
        }
        return findNodeContaining(obj, n.getNext());
    }

    private void link(Node prev, Node next) {
        prev.setNext(next);
        next.setPrev(prev);
    }

}