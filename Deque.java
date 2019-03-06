/**
 * Deque Interface. Don't bother with documentation.
 * Refer to the Java API if you need to better understand how
 * Deque should work.
 * @author Stephen J. Sarma-Weierman
 */

public interface Deque<T> {
    //throws exceptions
    public void addFirst(T obj);
    public T removeFirst();
    public T getFirst();
    public void addLast(T obj);
    public T removeLast();
    public T getLast();
    
    //returns special value
    public boolean offerFirst(T obj);
    public T pollFirst();
    public T peekFirst();
    public boolean offerLast(T obj);
    public T pollLast();
    public T peekLast();
    
    //generic collection methods
    public int size();
    public boolean contains(T obj);
    public boolean remove(T obj);
    public boolean isEmpty();
    public void clear();
}
