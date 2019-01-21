import javafx.collections.ModifiableObservableListBase;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @author aguo36
 * @version 1.0.1
 * @param <E> generic type
 */
public class LinkedQueue<E> extends ModifiableObservableListBase<E>
        implements Iterable<E>, SimpleQueue<E> {
    private int numElements;
    private LinkedQueueNode first;
    private LinkedQueueNode last;

    /**
     * Constructor
     */
    public LinkedQueue() {
        numElements = 0;
        first = null;
        last = null;
    }

    @Override
    public void enqueue(E element) {
        super.add(element);
        /* This is so sad alexa play despacito
        LinkedQueueNode<E> node = new LinkedQueueNode<E>(element);

        if (isEmpty()) {
            first = node;
        } else {
            last.setNext(node);
        }

        last = node;
        numElements++;
        */
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            /*
            E firstData = (E) first.getData();

            first = first.getNext();
            numElements--;

            if (isEmpty()) {
                last = null;
            }

            return firstData;
            */
            return super.remove(0);
        }
    }

    @Override
    public E get(int index) {
        LinkedQueueNode temp = first;
        if (temp == null) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            if (temp.getNext() == null) {
                throw new IndexOutOfBoundsException();
            } else {
                temp = temp.getNext();
            }
        }
        return (E) temp.getData();
    }

    @Override
    public void doAdd(int index, E element) {
        LinkedQueueNode<E> addNode = new LinkedQueueNode<E>(element);
        LinkedQueueNode temp = first;
        if (index == 0) {
            addNode.setNext(temp);
            first = addNode;
            numElements++;
        } else {
            for (int i = 0; i < index - 1; i++) {
                if (temp.getNext() == null) {
                    throw new IndexOutOfBoundsException();
                } else {
                    temp = temp.getNext();
                }
            }
            addNode.setNext(temp.getNext());
            temp.setNext(addNode);
            numElements++;
        }
    }

    @Override
    public E doRemove(int index) {
        E removed;
        LinkedQueueNode temp = first;
        if (index == 0) {
            removed = (E) first.getData();
            first = temp.getNext();
            numElements--;
            return removed;
        } else {
            for (int i = 0; i < index - 1; i++) {
                if (temp.getNext() == null) {
                    throw new IndexOutOfBoundsException();
                } else {
                    temp = temp.getNext();
                }
            }
            if (temp.getNext() == null) {
                throw new IndexOutOfBoundsException();
            } else {
                removed = (E) temp
                        .getNext().getData();
                temp.setNext(temp.getNext().getNext());
            }
            numElements--;
            return removed;
        }
    }

    @Override
    public E doSet(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<E> {
        private LinkedQueueNode current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E currentData = (E) current.getData();
            current = current.getNext();
            return currentData;
        }
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }
}
