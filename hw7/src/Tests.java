/**
 * Tests for hw7
 * @author Tillson Galloway
 * @version 1.0.3
 */
public class Tests {

    // if the output of the tests have weird symbols instead of colors, set this to false (generally a problem on Windows)
    private final static boolean USE_COLORS = true;

    public static void main(String[] args) {
        try {
            test();
            Color.prettyPrint(Color.CYAN + "Tests passed!");
        } catch (AssertionFailure failure) {
            Color.prettyPrint(Color.RED + "Tests failed: " + Color.CYAN + failure.getMessage());
            Color.prettyPrint(Color.RED + "Stack trace:");
            for (StackTraceElement element : failure.getStackTrace()) {
                System.out.println(Color.strip(element.toString()));
            }
        }
    }

    private static void test() throws AssertionFailure {

        // LinkedQueue<String> Tests
        LinkedQueue<String> queue = new LinkedQueue<>();
        String[] testQueueValues = { "Isaac Brock", "Thom Yorke", "Trey Anastasio", "Sonar Jockey" };
        queue.enqueue("Isaac Brock");
        queue.enqueue("Thom Yorke");
        queue.enqueue("Trey Anastasio");
        queue.enqueue("Sonar Jockey");

        assertEquals("size() returns an incorrect value", queue.size(), testQueueValues.length);

        for (int i = 0; i < testQueueValues.length; i++) {
            assertEquals("get() returns an incorrect value for a given index", queue.get(i), testQueueValues[i]);
        }
        assertDoesThrowException("IndexOutOfBoundsException not thrown when get() is called with an illegal index", IndexOutOfBoundsException.class, () -> queue.get(4));

        for (int i = 0; i < testQueueValues.length; i++) {
            assertEquals("dequeue() returns an incorrect value", queue.dequeue(), testQueueValues[i]);
            assertEquals("size() returns an incorrect value", queue.size(), testQueueValues.length - i - 1);
        }
        assertNull("dequeue() does not return null when empty", queue.dequeue());

        // LinkedQueue<Integer> Tests
        LinkedQueue<Integer> intQueue = new LinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            intQueue.enqueue(i);
        }
        int index = 0;
        for (Object element : intQueue) {
            assertEquals("The iterator's next() method returns an incorrect value", element, index);
            index++;
        }

        intQueue.doAdd(7, 100);
        assertEquals("get() returns an incorrect value for an element added at an arbitrary index using doAdd()", intQueue.get(7), 100);
        intQueue.doAdd(11, 200);
        assertEquals("get() returns an incorrect value for an element added at an arbitrary index using doAdd()", intQueue.get(11), 200);

        assertDoesThrowException("IndexOutOfBoundsException not thrown when doAdd() is called with an illegal index", IndexOutOfBoundsException.class, () -> intQueue.doAdd(13, 99));
        assertDoesThrowException("UnsupportedOperationException not thrown when doSet() is called", UnsupportedOperationException.class, () -> intQueue.doSet(5, 99));

        assertBoolean("isEmpty() returns an incorrect value", !intQueue.isEmpty());
        intQueue.clear();
        assertDoesThrowException("get() returns a value after the queue is cleared", IndexOutOfBoundsException.class, () -> queue.get(0));
        assertEquals("size() does not return 0 after the queue is cleared", intQueue.size(), 0);
        assertBoolean("isEmpty() returns an incorrect value", intQueue.isEmpty());

        for (int i = 0; i < 10; i++) {
            intQueue.enqueue(i);
        }
        intQueue.doRemove(5);
        assertEquals("doRemove() does not update the queue's size", intQueue.size(), 9);
        assertEquals("get() returns an incorrect value after doRemove() is called on an arbitrary index", intQueue.get(5), 6);
        assertDoesThrowException("IndexOutOfBoundsException not thrown when doRemove() is called with an illegal index", IndexOutOfBoundsException.class, () -> intQueue.doRemove(9));
        intQueue.doRemove(1);
        assertEquals("doRemove() does not update the queue's size", intQueue.size(), 8);
        intQueue.doRemove(7);
        assertEquals("doRemove() does not update the queue's size", intQueue.size(), 7);
        int[] expectedValues = { 0, 2, 3, 4, 6, 7, 8 };
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals("get() returns an incorrect value for some index after a number of doRemove() operations.", intQueue.get(i), expectedValues[i]);
        }
        intQueue.doRemove(0);
        assertEquals("doRemove() does not update the queue's size", intQueue.size(), 6);
        assertEquals("get() returns an incorrect value for some index after a number of doRemove() operations.", intQueue.get(0), 2);
        intQueue.clear();
        intQueue.doAdd(0, 5);
        assertEquals("get() does not return value added to queue with doAdd()", intQueue.get(0), 5);
    }

    // MARK: Utility methods

    private static void assertEquals(String check, Object obj1, Object obj2) throws AssertionFailure {
        if (obj2 == null) {
            assertNull(check, obj1);
            return;
        }
        if (obj1 == null || !obj1.equals(obj2)) {
            throw new AssertionFailure(check);
        }
    }

    private static void assertBoolean(String check, boolean b) throws AssertionFailure {
        if (!b) {
            throw new AssertionFailure(check);
        }
    }

    private static void assertNull(String check, Object obj) throws AssertionFailure {
        if (obj != null) {
            throw new AssertionFailure(check);
        }
    }

    private static void assertDoesThrowException(String check, Class exceptionType, Runnable runnable) throws AssertionFailure {
        try {
            runnable.run();
            throw new AssertionFailure(check);
        } catch (Exception ex) {
            if (!exceptionType.isInstance(ex)) {
                AssertionFailure failure = new AssertionFailure(String.format("Unexpected exception thrown for test '%s': %s%n", check, ex.toString()));
                failure.setStackTrace(ex.getStackTrace());
                throw failure;
            }
        }
    }

    private static class AssertionFailure extends Throwable {
        private AssertionFailure(String message) {
            super(message);
        }
    }

    private enum Color {
        RED("\u001B[31m"),
        CLEAR("\u001B[0m"),
        CYAN("\u001B[36m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String toString() {
            if (USE_COLORS) {
                return code;
            }
            return "";
        }

        public static void prettyPrint(String message) {
            System.out.println(message + Color.CLEAR);
        }

        public static String strip(String message) {
            return message.replaceAll("\u001B\\[[;\\d]*m", "");
        }
    }
}