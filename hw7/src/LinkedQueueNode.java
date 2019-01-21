/**
 * LinkedQueue node. This is provided and you do not need to submit it.
 * DO NOT CHANGE THIS FILE
 * @author 1331 TAs
 * @version 1.0
 * @param <E> The type of object stored in the LinkedQueueNode
 */
public class LinkedQueueNode<E> {

    private E data;

    private LinkedQueueNode<E> next;

    /**
     * Create a LinkedQueueNode.
     * @param data The data of this node.
     */
    public LinkedQueueNode(E data) {
        this(data, null);
    }

    /**
     * Create a LinkedQueueNode.
     * @param data The data of this node.
     * @param next The next node in the queue.
     */
    public LinkedQueueNode(E data, LinkedQueueNode<E> next) {
        this.data = data;
        this.next = next;
    }

    /**
     * Get the next node in the queue.
     * @return the next node in the queue.
     */
    public LinkedQueueNode<E> getNext() {
        return next;
    }

    /**
     * Set the next node in the queue.
     * @param node The new next node.
     */
    public void setNext(LinkedQueueNode<E> node) {
        this.next = node;
    }

    /**
     * Get the data of this node.
     * @return The data of this node.
     */
    public E getData() {
        return data;
    }

}
