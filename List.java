public interface List<T> extends Iterable<T> {

    /**
     Add an element to the end of the list.
     @param o the object to add.
     @return true if object added successfully
     */
    public boolean add(T o);

    /**
     Add an element to the list at a particular index.
     @param index the point in the list at which the object will be placed.
     @param o the object to add
     @return true if successful
     */
    public boolean add(int index, T o);

    /**
     Removes all elements from the list.
     */
    public void clear();

    /**
     Checks whether a copy of an object is in the list.
     @param o the object to search for
     @return true if the object is in the list
     */
    public boolean contains(T o);

    public T get(int index);

    public T set(int index, T o);

    public boolean remove(T o);

    public T remove(int index);

    public int indexOf(T o);

    public int lastIndexOf(T o);

    public boolean isEmpty();

    public int size();

    public Object[] toArray();
}
