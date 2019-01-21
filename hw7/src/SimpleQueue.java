/**
 * SimpleQueue interface. This is provided and you do not need to submit it.
 * DO NOT CHANGE THIS FILE
 * @author 1331 TAs
 * @version 1.0
 * @param <E> The type of object stored in the queue
 */
public interface SimpleQueue<E> {

    /**
     * Add an element to the back of the queue.
     * @param element The element to add to the queue.
     */
    void enqueue(E element);

    /**
     * Remove an element from the front of the queue.
     * @return The element removed.
     */
    E dequeue();

    /**
     * Get the size of the queue.
     * @return The size of the queue.
     */
    int size();

    /**
     * Empty the queue.
     */
    void clear();

    /**
     * Check if the queue is empty.
     * @return If the queue is empty.
     */
    boolean isEmpty();

}
