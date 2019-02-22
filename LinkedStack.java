/**
 * An implementation of the LinkedStack class.
 * @author Stephen J. Sarma-Weierman
 * @author Brennon Miller
 * @version 1.0
 */

import java.util.EmptyStackException;

public class LinkedStack<T> implements Stack<T> {

    private Node first;
    private int size = 0;

    public LinkedStack() {}

    /**
     * Places a new item on top of the stack.
     *
     * @param obj the item to add.
     */
    public void push(T obj) {
        Node current = new Node(obj);
        if (!empty()) {                     // If another first exists, move it.
            current.setNextNode(this.first);
        }
        this.first = current;
        size++;                 // Do NOT forget this!
    }

    /**
     * Returns the first element of the stack, without removing it.
     *
     * @return the top item in the stack.
     */
    public T peek() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return first.getElement();
    }

    /**
     * Returns the first element of the stack, removing it in the process.
     *
     * @return the top item in the stack.
     */
    public T pop() {
        if (empty()) {
            throw new EmptyStackException();
        }
        Node current = first;
        this.first = this.first.getNextNode();
        size--;
        return current.getElement();
    }

    /**
     * Checks if the stack is empty or not.
     */
    public boolean empty() {
        return size == 0;
    }

    // Private class
    // No documentation required
    private class Node {
        private T element;
        private Node next;

        public Node(T obj) {
            this.element = obj;
            this.next = null;
        }

        public void setElement(T obj) {
            this.element = obj;
        }

        public void setNextNode(Node n) {
            this.next = n;
        }

        public T getElement() {
            return element;
        }

        public Node getNextNode() {
            return next;
        }
    }
}
