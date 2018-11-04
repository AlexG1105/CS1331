/**
 * @author dprewitt3, lprinn3
 * @version 1.0.1
 * @param <E> is the generic type to be later defined
 * defines the List interface to be used in HW5
*/
public interface List<E> {

    /*Initial capacity of the list to be instantiated*/
    int INITIAL_CAPACITY = 10;
    /**
     * Insert element to the back of the list. If the list is
     * is at capacity, double the capacity
     *
     * @param e the element to be added to the list
     * @throws IllegalArgumentException if invalid `E` is given
    */
    void add(E e);
    /**
     * Get an element from the list.
     * @param index given index
     * @return E is returning an element of type E for the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     *
    */
    E get(int index);
    /**
     * Replace all instances of e with replaceWith.
     *
     * @param e to be replaced in the list
     * @param replaceWith to replace e in the list
     * @throws IllegalArgumentException if one or both
     * invalid `E`'s are passed in the method
    */
    void replace(E e, E replaceWith);
    /**
     * Removes all instances of element e in the list and returns the count
     *
     * @param e the element to be removed from the list
     * @return int representing the count
    */
    int remove(E e);

    /**
     * Counts the number of times element e occurs in the list
     *
     * @param e the element to be counted in the list
     * @return int representation of the count
    */
    int contains(E e);
    /**
     * Returns if the list is empty or not
     * @return boolean (true or false) if list is empty or not
     *
    */
    boolean isEmpty();
    /**
     * Clears all elements in the list
     *
    */
    void clear();
    /**
     * Counts the number of elements in the list
     * @return int representing the number of elements in the list
    */
    int size();
   /**
    * Returns the E[] parameter containing as many non-null elements
    * in the list as it can fit
    * @param e the array to store all of the non null elements in
    * @return E[] an array that contains the non null elements
    *
   */
    E[] toArray(E[] e);
}
