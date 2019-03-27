import java.util.Iterator;
import java.util.NoSuchElementException;

public class LList<T> implements List<T> {

    protected int size;
    protected Node first;
    protected Node last;

    @Override
    public boolean add(T o) {
        Node n = new Node(o);
        if (size == 0) {
            first = n;
        } else {
            link(last, n);
        }
        last = n;
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T o) {
        if (index >= size) {
            return add(o);
        }
        Node n = new Node(o);
        if (index == 0) {
            link(n, first);
            first = n;
            size++;
            return true;
        } else {
            Node next = getNodeAt(index);
            insertBefore(n, next);
            size++;
            return true;
        }
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public boolean contains(T o) {
        return (indexOf(o) >= 0);
    }

    @Override
    public T get(int index) {
        return getNodeAt(index).getElement();
    }

    @Override
    public T set(int index, T o) {
        Node currentNode = getNodeAt(index);
        T returnable = currentNode.getElement();
        currentNode.setElement(o);
        return returnable;
    }

    @Override
    public boolean remove(T o) {
        verifyIntegrity();
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            this.clear();
        }
        Node toRemove = getNodeAt(indexOf(o));
        link(toRemove.getPrevious(), toRemove.getNext());
        size--;
        return true;
    }

    @Override
    public T remove(int index) {
        verifyIntegrity();
        Node toRemove = getNodeAt(index);
        T element = toRemove.getElement();
        link(toRemove.getPrevious(), toRemove.getNext());
        size--;
        return element;
    }

    @Override
    public int indexOf(T o) {
        if (size == 0) {
            return -1;  // No nodes to find.
        }
        Node current = this.first;
        int index = 0;
        while (index < size) {
            if (current.element.equals(o)) {    // If there's a match...
                return index;
            }
            current = current.getNext();    // Go to next node and repeat.
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T o) {
        if (size == 0) {
            return -1;  // No nodes to find.
        }
        Node current = this.last;
        int index = size - 1;
        while (index >= 0) {
            if (current.element.equals(o)) {    // If there's a match...
                return index;
            }
            current = current.getPrevious();    // Go to previous node & repeat.
            index--;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node current = first;
        for (int i = 0; i < size; i++) {
            array[i] = current.getElement();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        verifyIntegrity();
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                T obj = getNodeAt(cursor).getElement();
                cursor++;
                return obj;
            }
        };
    }
    /**
     * Use this method to link two nodes.
     *
     * @param previous
     * @param next
     */
    protected void link(Node previous, Node next) {
        verifyIntegrity();
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
    }

    /**
     * Use this method to insert a new node into a list after a node.
     *
     * @param previous
     * @param newNode
     */
    protected void insertAfter(Node previous, Node newNode) {
        verifyIntegrity();
        Node next = previous.getNext();
        link(previous, newNode);
        link(newNode, next);
    }

    /**
     * Use this method to insert a new node into a list before a node.
     *
     * @param newNode
     * @param next
     */
    protected void insertBefore(Node newNode, Node next) {
        verifyIntegrity();
        Node previous = next.getPrevious();
        link(previous, newNode);
        link(newNode, next);
    }

    protected Node getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    private void verifyIntegrity() {
        Node c = first;
        Object[] nodes;
        nodes = new Object[size];
        int i = 0;
        while (c != null) {
            for (int j = 0; j < i; j++) {
                if (nodes[j] == c) {
                    throw new IllegalStateException("Circular reference");
                }
            }
            if (i == size) {
                throw new IllegalStateException("More nodes than objects in chain");
            } else if (i == size - 1 && c != last) {
                throw new IllegalStateException("last node isn't last");
            }
            nodes[i] = c;
            c = c.getNext();
            i++;
        }

        c = last;
        i = size - 1;
        while (c != null) {
            if (nodes[i] != c) {
                throw new IllegalStateException("Previous reference broken");
            }
            c = c.getPrevious();
            i--;
        }

        if (i != -1) {
            throw new IllegalStateException("Previous reference broken");
        }
    }

    protected class Node {

        private T element;
        private Node previous;
        private Node next;

        public Node(T element) {
            this.element = element;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
