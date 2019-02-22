public interface List<T> extends Iterable<T> {
    public boolean add(T o);
    public boolean add(int index, T o);
    public void clear();
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
