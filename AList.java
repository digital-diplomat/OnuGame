import java.util.Iterator;
import java.util.Arrays;

public class AList<T> implements List<T> {
    private Object [] array;
    private int size = 0;
    private final static int DEFAULT_CAPACITY=25;
    private final static int MAX_CAPACITY=10000;

    public AList(){
        this(DEFAULT_CAPACITY);
    }

    public AList(int capacity) {
        array = new Object[capacity];
        size = 0;
    }

    @Override
    public boolean add(T o) {
        if (size < array.length || resizeArray(2 * size)) {
            array[size] = o;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean add(int index, T o) {
        if (size < array.length || resizeArray(2 * size)) {
            for (int i = size; i >= index; i--) {
                array[i] = array[i - 1];
            }
            array[index] = o;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean contains(T o) {
        return indexOf(o) != -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= 0 && index < size)
            return (T)array[index];
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T o) {
        T retVal = (T)array[index];    // Save original value to return.
        array[index] = o;
        return retVal;
    }

    @Override
    public boolean remove(T o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T obj = (T)array[index];
            for (int i = index; i < size-1; i++)
                array[i] = array[i+1];
            size--;
            return obj;
        } else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int indexOf(T o) {
        T obj;
        for (int i = 0; i < size; i++) {
            obj = (T)array[i];
            if (obj.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int lastIndexOf(T o) {
        T obj;
        for (int i = size - 1; i >= 0; i--) {
            obj = (T)array[i];
            if (obj.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                T obj = (T)array[cursor];
                cursor++;
                return obj;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    private boolean resizeArray(int newCapacity) {
        if (newCapacity > MAX_CAPACITY)
                return false;
        array = Arrays.copyOf(array, newCapacity);
        return true;
    }

}
