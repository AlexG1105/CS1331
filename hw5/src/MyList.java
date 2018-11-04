/**
 * @param <E> is the generic type to be later defined
 *            Inherits the elements from List interface
 * @author aguo36
 * @version 1.0
 */
public class MyList<E> implements List<E> {
    private E[] elements;
    private int size = 0;

    /**
     * Default constructor for MyList
     */
    public MyList() {
        this.elements = (E[]) new Object[INITIAL_CAPACITY];
        this.size = size;
    }

    /**
     * Overloaded constructor for MyList
     *
     * @param capacity initializes backing array
     */
    public MyList(int capacity) {
        this.elements = (E[]) new Object[capacity];
        this.size = size;
    }

    @Override
    public void add(E e) {
        if (e == null) {
            throw new IllegalArgumentException(
                    "You are trying to add a null object!");
        }
        if (size != elements.length) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == null) {
                    elements[i] = e;
                    size = size();
                    break;
                }
            }
        } else {
            E[] temp = (E[]) new Object[elements.length];
            for (int i = 0; i < elements.length; i++) {
                temp[i] = elements[i];
            }
            elements = (E[]) new Object[temp.length * 2];
            for (int i = 0; i < temp.length; i++) {
                elements[i] = temp[i];
            }
            elements[temp.length] = e;
            size = size();
        }
    }

    @Override
    public E get(int index) {
        if (!(index >= 0 && index < size())
                || elements[index] == null) {
            throw new IndexOutOfBoundsException(
                    "Invalid Index!");
        } else {
            return elements[index];
        }
    }

    @Override
    public void replace(E e, E replaceWith) {
        if (e == null || replaceWith == null) {
            throw new IllegalArgumentException(
                    "You have passed a null object!");
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null
                    && elements[i].equals(e)) {
                elements[i] = replaceWith;
            }
        }
    }

    @Override
    public int remove(E e) {
        if (e == null) {
            throw new IllegalArgumentException(
                    "You have passed a null object!");
        }
        int removedCount = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null
                    && elements[i].equals(e)) {
                elements[i] = null;

                removedCount++;
                for (int j = i; j < elements.length; j++) {
                    if ((j + 1) == elements.length) {
                        elements[j] = null;
                    } else {
                        elements[j] = elements[j + 1];
                    }
                }
                i--;
            }
            size = size();
        }
        return removedCount;
    }

    @Override
    public int contains(E e) {
        int numContain = 0;
        if (e == null) {
            throw new IllegalArgumentException(
                    "You have passed a null object!");
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null
                    && elements[i].equals(e)) {
                numContain++;
            }
        }
        return numContain;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = size();
    }

    @Override
    public int size() {
        int numElements = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                numElements++;
            }
        }
        return numElements;
    }

    @Override
    public E[] toArray(E[] e) {
        if (e == null) {
            throw new IllegalArgumentException(
                    "You have passed a null object!");
        }
        for (int i = 0; i < e.length; i++) {
            if (i < elements.length && elements[i] != null) {
                e[i] = elements[i];
            } else {
                e[i] = null;
            }
        }
        return e;
    }
}
