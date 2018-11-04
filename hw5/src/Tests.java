import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.lang.StackWalker.StackFrame;
import java.lang.reflect.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.function.Consumer;
import java.lang.StackWalker.*;

/**
 * Test cases for cs1331 2018's hw5 assignment
 * @author Joseph Azevedo
 */
@SuppressWarnings("unchecked")
public class Tests extends TestsBase {
    private int INITIAL_CAPACITY = 0;

    public static void main(String[] args) {
        Tests t = new Tests();
        t.bootstrap();
    }

    @Override
    protected void runTests() {
        testGeneral();
        testAdd();
        testGet();
        testReplace();
        testRemove();
        testContains();
        testIsEmptySize();
        testClear();
        testToArray();
    }

    public void testGeneral() {
        // General object regs
        assertFieldSignature(List.class, "", int.class, "INITIAL_CAPACITY");
        assertFieldSignature(MyList.class, "private", int.class, "size");
        assertFieldSignature(MyList.class, "private", ((new Object[0]).getClass()), "elements");
        // No args
        MyList<Object> a = new MyList<Object>();
        INITIAL_CAPACITY = (int) getPrivateFieldValue(a, "INITIAL_CAPACITY");
        Object[] elementsA = getElements(a);
        assert elementsA != null : "Inner array null";
        assert elementsA.length == INITIAL_CAPACITY : String.format("No args constructor doesn't have size %d", INITIAL_CAPACITY);
        assert a.size() == 0 : "MyList not empty by default";
        assert !Arrays.stream(elementsA).filter(o -> (o != null)).findFirst().isPresent() : "Inner elements array not full of null objects";
        // Single args
        for(int i = 0; i < 10; ++i) {
            MyList<Object> b = new MyList<Object>(i);
            Object[] elementsB = getElements(b);
            assert elementsB != null : "Inner array null";
            assert elementsB.length == i : String.format("Single args constructor doesn't have specified size %d", i);
            assert a.size() == 0 : "MyList not empty by default";
            assert !Arrays.stream(elementsB).filter(o -> (o != null)).findFirst().isPresent() : "Inner elements array not full of null objects";
        }
        assertExceptionThrown(() -> {new MyList<Object>(-1);}, NegativeArraySizeException.class, "Invalid MyList constructor size");

        // Milestone
        System.out.println("Passed all constructor tests!");
    }

    public <T> T[] getElements(List<T> instance) {
        return (T[])getPrivateFieldValue(instance, "elements");
    }
    
    public void testAdd() {
        // test that add increases size
        MyList<Object> a = new MyList<>();
        for(int i = 0; i < 20; ++i) {
            int oldSize = a.size();
            a.add(String.valueOf(i));
            assert oldSize + 1 == a.size() : "Size not incremented after add";
        }
        // Test that null throws IAE
        assertExceptionThrown(() -> a.add(null), IllegalArgumentException.class, "null argument to MyList.add(...)");
        // Test growing behavior
        for(int i = 0; i < 300; ++i) {
            if(getElements(a).length == a.size()) {
                int oldLength = getElements(a).length;
                a.add(String.valueOf(4000 - i));
                assert getElements(a).length == 2 * oldLength : "Backing array not doubled";
                assert a.size() == oldLength + 1 : "Size after growing & add() not adjusted";
            } else a.add(String.valueOf(4000 - i));
        }
        String[] s = new String[5];
        MyList<Object> b = new MyList<>();
        for(int i = 0; i < s.length; ++i) {
            s[i] = "a" + String.valueOf(i);
            b.add("a" + String.valueOf(i));
        }
        for(int i = 0; i < s.length; ++i) {
            assert s[i].equals(getElements(b)[i]) : "Add doesn't add elements to the end of the backing array";
        }

        // Milestone
        System.out.println("Passed all add tests!");
    }

    public void testGet() {
        // test that get throws ioobe for invalid index (negative, above bounds)
        MyList<Object> a = new MyList<>();
        for(int i = -2; i < 3; ++i) {
            final int j = i;
            assertExceptionThrown(() -> a.get(j), IndexOutOfBoundsException.class, String.format("%d get() index on empty list", i));
        }
        MyList<Object> b = new MyList<>();
        for(int i = 0; i < 30; ++i) {
            Object val = "a" + String.valueOf(i);
            b.add(val);
            assert val.equals(b.get(i)) : "Get most recent item added doesn't work";
            assert val.equals(b.get(b.size() - 1)) : "get size() - 1 doesn't return most recent item";
        }

        // Milestone
        System.out.println("Passed all get tests!");
    }

    public void testContains() {
        // contains correctly returns number of elements in list
        MyList<Object> a = new MyList<>();
        final int aNum = 4;
        final int numCount = 20;
        final int numRepeat = 3;
        for(int i = 0; i < aNum; ++i) { a.add("a"); }
        for(int i = 0; i < numRepeat; ++i) for(int j = 0; j < numCount; ++j) { a.add(String.valueOf(j)); }
        assert a.contains("a") == aNum : "contains returns unexpected count";
        assert a.contains("b") == 0 : "contains returns nonzero for nonpresent element";
        for(int i = 0; i < numCount; ++i) {
            String val = String.valueOf(i);
            int num1 = a.contains(val);
            int num2 = a.remove(val);
            assert num1 == num2 : "elements contained not equal to elements removed";
            assert a.contains(val) == 0 : "elements still contains removed value";
            assert num1 == numRepeat : "contains returns unexpected count";
        }
        assertExceptionThrown(() -> a.contains(null), IllegalArgumentException.class, "null contains(...) argument");

        // Milestone
        System.out.println("Passed all contains tests!");
    }

    public void testReplace() {
        MyList<Object> a = new MyList<>();
        final int aNum = 4;
        final int numCount = 20;
        final int numRepeat = 3;
        for(int i = 0; i < aNum; ++i) { a.add("a"); }
        for(int i = 0; i < numRepeat; ++i) for(int j = 0; j < numCount; ++j) { a.add(String.valueOf(j)); }
        int numFound = a.contains("a");
        a.replace("a", "b");
        a.replace("$", "/");
        assert a.contains("b") == numFound : "Contains after replacement not returning proper amount";
        assert a.contains("a") == 0 : "MyList still contains replaced element";
        assertExceptionThrown(() -> {a.replace(null, "");}, IllegalArgumentException.class, "replace first argument null");
        assertExceptionThrown(() -> {a.replace("", null);}, IllegalArgumentException.class, "replace second argument null");
        assertExceptionThrown(() -> {a.replace(null, null);}, IllegalArgumentException.class, "replace both arguments null");

        // Milestone
        System.out.println("Passed all replace tests!");
    }

    public void testRemove() {
        MyList<Object> a = new MyList<>();
        final int aNum = 4;
        final int numCount = 20;
        final int numRepeat = 3;
        for(int i = 0; i < aNum; ++i) { a.add("a"); }
        for(int i = 0; i < numRepeat; ++i) for(int j = 0; j < numCount; ++j) { a.add(String.valueOf(j)); }
        int oldSize = a.size();
        int removed = a.remove("a");
        assert removed == aNum : "Removed doesn't return an expected value";
        assert a.size() == oldSize - removed : "size() is not lower by exactly the return value of remove()";
        assert a.get(0) != null : "remove() doesn't correctly shift elements to the left";
        assert a.remove("$") == 0 : "remove doesn't return 0 for an element not found in list";
        int oldElementsSize = getElements(a).length;
        while(!a.isEmpty()) a.remove(a.get(0));
        assert oldElementsSize == getElements(a).length : "Remove changes elements size";
        assertExceptionThrown(() -> a.remove(null), IllegalArgumentException.class, "remove given null as argument");

        // Milestone
        System.out.println("Passed all remove tests!");
    }

    public void testIsEmptySize() {
        MyList<Object> a = new MyList<>();
        a.add(" ");
        a.add("a");
        assert a.size() == 2;
        assert !a.isEmpty() : "isEmpty returns unexpected value";
        a.remove(" ");
        assert a.size() == 1;
        assert !a.isEmpty() : "isEmpty returns unexpected value";
        a.remove("a");
        assert a.size() == 0;
        assert a.isEmpty() : "isEmpty returns unexpected value";

        MyList<Object> b = new MyList<>();
        int num = 5;
        for(int i = 0; i < num; ++i) b.add(String.valueOf(i));
        assert b.size() == num;
        b.remove("0");
        assert b.size() == num - 1;
        assert b.size() != getElements(b).length : "size returns elements.length after a removal";

        // Milestone
        System.out.println("Passed all isEmpty and size tests!");
    }

    public void testClear() {
        MyList<Object> a = new MyList<>();
        int num = 5;
        for(int i = 0; i < num; ++i) a.add(String.valueOf(i));
        assert a.size() != 0;
        a.clear();
        assert a.size() == 0 : "Clear doesn't make size 0 after call";
        assert getElements(a).length != 0 : "Clear changes size of inner array";
        assert a.isEmpty() : "call chain clear -> isEmpty is false";

        // Milestone
        System.out.println("Passed all clear tests!");
    }

    public void testToArray() {
        MyList<Object> a = new MyList<>();
        int num = 5;
        int offset = 15;
        for(int i = 0; i < num; ++i) a.add(String.valueOf(i));
        Object[] arr = new Object[a.size()];
        a.toArray(arr);
        assert !(Arrays.stream(arr)
                .filter(o -> o == null)
                .findFirst().isPresent()) : "toArray does not return all non-null if e.length == size";
        for(int i = 1; i < 20; ++i) {
            Object[] objArr = new Object[num + i];
            a.toArray(objArr);
            java.util.List<Object> nullObjs = Arrays.stream(objArr)
                    .filter(o -> o == null)
                    .collect(Collectors.toList());
            assert nullObjs.size() == i : "toArray does not fill null if e.length > size";
        }

        // Test non-null paddings get set to null @764
        Object[] arr2 = new Object[a.size() + offset];
        for(int i = a.size(); i < a.size() + offset; ++i) {
            arr2[i] = "not null";
        }
        arr2 = a.toArray(arr2);
        for(int i = a.size(); i < a.size() + offset; ++i) {
            assert arr2[i] == null : "non-null elements in padding positions don't get set to null in toArray";
        }

        // Ensure reference non-equivalence for inner array and result of toArray @806
        assert getElements(a) != a.toArray(new Object[getElements(a).length]) : "toArray must not return the reference to its inner array";

        // Ensure reference equivalence for parameter and result of toArray
        Object[] arr3 = new Object[3];
        assert arr3 == a.toArray(arr3) : "result of toArray must be the same array object inputted as a parameter";

        // Ensure null argument throws IllegalArgumentException (@hw5 clarifications #10)
        assertExceptionThrown(() -> a.toArray(null), IllegalArgumentException.class, "null toArrray parameter");

        // Milestone
        System.out.println("Passed all toArray tests!");
    }

    @Override
    protected String funStringName() { return "Squirtle"; }

    @Override
    protected String className() { return "Tests"; }

    @Override
    protected String funString() {
        return "               _,........__\n            ,-\'            \"`-.\n          ,\'                   `-.\n        ,\'                        \\\n      ,\'                           .\n      .\'\\               ,\"\".       `\n     ._.\'|             / |  `       \\\n     |   |            `-.\'  ||       `.\n     |   |            \'-._,\'||       | \\\n     .`.,\'             `..,\'.\'       , |`-.\n     l                       .\'`.  _/  |   `.\n     `-.._\'-   ,          _ _\'   -\" \\  .     `\n`.\"\"\"\"\"\'-.`-...,---------\',\'         `. `....__.\n.\'        `\"-..___      __,\'\\          \\  \\     \\\n\\_ .          |   `\"\"\"\"\'    `.           . \\     \\\n  `.          |              `.          |  .     L\n    `.        |`--...________.\'.        j   |     |\n      `._    .\'      |          `.     .|   ,     |\n         `--,\\       .            `7\"\"\' |  ,      |\n            ` `      `            /     |  |      |    _,-\'\"\"\"`-.\n             \\ `.     .          /      |  \'      |  ,\'          `.\n              \\  v.__  .        \'       .   \\    /| /              \\\n               \\/    `\"\"\\\"\"\"\"\"\"\"`.       \\   \\  /.\'\'                |\n                `        .        `._ ___,j.  `/ .-       ,---.     |\n                ,`-.      \\         .\"     `.  |/        j     `    |\n               /    `.     \\       /         \\ /         |     /    j\n              |       `-.   7-.._ .          |\"          \'         /\n              |          `./_    `|          |            .     _,\'\n              `.           / `----|          |-............`---\'\n                \\          \\      |          |\n               ,\'           )     `.         |\n                7____,,..--\'      /          |\n                                  `---.__,--.\' Squirtle says Congratulations :)";
    }
}

/**
 * Base class for test cases
 * @author Joseph Azevedo
 */
abstract class TestsBase {
    private static final double EPSILON = 0.00001d;
    private static final String LINE_SEP = System.getProperty("line.separator");

    private final ByteArrayOutputStream captureSysOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream captureSysErr = new ByteArrayOutputStream();
    private final ByteArrayOutputStream cacheSysOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream cacheSysErr = new ByteArrayOutputStream();
    private final PrintStream printToSysOut = new PrintStream(cacheSysOut);
    private final PrintStream printToSysErr = new PrintStream(cacheSysErr);
    private PrintStream originalSysOut;
    private PrintStream originalSysErr;

    protected abstract void runTests();
    protected abstract String className();
    protected abstract String funString();
    protected abstract String funStringName();

    public void bootstrap() {
        boolean caught = false;
        try {
            assert 0 == 1;
        } catch (AssertionError err) {
            caught = true;
        }
        if (!caught) {
            System.out.println("This program requires assertions to be enabled in order to function properly.");
            System.out.println("To do so, add the '-ea' flag to your JVM call like this:");
            System.out.printf("java -ea %s%n", className());
            return;
        }

        // Initialize the capture for sys out/err
        originalSysOut = System.out;
        originalSysErr = System.err;
        System.setOut(new PrintStream(captureSysOut));
        System.setErr(new PrintStream(captureSysErr));

        try {
            this.runTests();

            // Print the result text to the console
            printResultText(null);

        } catch (Throwable ex) {
            printResultText(ex);
        }
    }


    public static boolean epsilonEquals(double a, double b) {
        return Math.abs(b - a) < EPSILON;
    }

    private void printResultText(Throwable ex) {
        System.setOut(new PrintStream(originalSysOut));
        System.setErr(new PrintStream(originalSysErr));

        System.out.println("-- Retrieving output from sysout --");
        for (String line : (new String(cacheSysOut.toByteArray()).split(LINE_SEP))) {
            System.out.println(" > " + line);
        }
        for (String line : (new String(captureSysOut.toByteArray()).split(LINE_SEP))) {
            System.out.println("buffer> " + line);
        }
        System.out.println();
        System.out.println("-- Retrieving output from syserr --");
        for (String line : (new String(cacheSysErr.toByteArray()).split(LINE_SEP))) {
            System.out.println(" > " + line);
        }
        for (String line : (new String(captureSysErr.toByteArray()).split(LINE_SEP))) {
            System.out.println("buffer> " + line);
        }
        System.out.println();

        if (ex == null) {
            System.out.printf("Passed all tests! Have a %s! :)%n%n", funStringName());
            System.out.println(funString());
            System.out.println();
        } else if (ex instanceof AssertionError) {
            System.out.println("-- Failed test --");
            ex.printStackTrace();
        } else {
            System.out.println(" -- Tests failed: Exception occurred ");
            ex.printStackTrace();
        }
    }

    /**
     * Asserts that *something* was outputted to System.out OR System.err;
     * Copies the content of each to a seperate stream for later printing and
     * clears the output & buffer of each
     * @param assertion The message to output on a failed assertion
     */
    public void assertOutput(String assertion) {
        String out = captureSysOut.toString().trim();
        String err = captureSysErr.toString().trim();
        assert !out.isEmpty() || !err.isEmpty() : assertion;
        printToSysOut.print(new String(captureSysOut.toByteArray()));
        printToSysErr.print(new String(captureSysErr.toByteArray()));
        captureSysOut.reset();
        captureSysErr.reset();
    }

    public void assertMethodExists(Class c, String methodName) {
        assert hasMethod(c, methodName) : String.format("Class '%s' does not include required method '%s()'", c.getTypeName(), methodName);
    }

    public void assertMethodDoesNotExist(Class c, String methodName) {
        assert !(hasMethod(c, methodName)) : String.format("Class '%s' includes disallowed method '%s()'", c.getTypeName(), methodName);
    }

    public void assertFieldExists(Class c, String fieldName) {
        assert hasField(c, fieldName) : String.format("Class '%s' does not include required field '%s'", c.getTypeName(), fieldName);
    }

    public void assertFieldDoesNotExist(Class c, String fieldName) {
        assert !(hasField(c, fieldName)) : String.format("Class '%s' includes disallowed field '%s'", c.getTypeName(), fieldName);
    }

    public void assertPure(Object obj, String methodName, String[] watchedFields) {
        try {
            Object[] values = new Object[watchedFields.length];
            for (int i = 0; i < values.length; ++i) {
                if(hasField(obj.getClass(), watchedFields[i])) values[i] = getPrivateFieldValue(obj, watchedFields[i]);
            }
            if (hasMethod(obj.getClass(), methodName)) {
                Method mthd = getFirstMethod(obj.getClass(), methodName);
                mthd.setAccessible(true);
                mthd.invoke(obj);
            }
            for (int i = 0; i < values.length; ++i) {
                if(hasField(obj.getClass(), watchedFields[i])) { 
                    Object newVal = getPrivateFieldValue(obj, watchedFields[i]); 
                    assert newVal.equals(values[i]) : String.format("Method '%s.%s()' is not pure: value of '%s' changed from '%s' to '%s'", 
                            obj.getClass().getName(), methodName, watchedFields[i], values[i], newVal);
                }
            }
        } catch (Exception ex) {
            assert false : String.format("Method '%s.%s()' is not pure: %s", obj.getClass().getName(), methodName, ex.toString());
        }
    }

    public void assertGetter(Object obj, String getterName, String fieldName) {
        try {
            if(!hasField(obj.getClass(), fieldName) || !hasMethod(obj.getClass(), getterName)) return;
            Object trueVal = getPrivateFieldValue(obj, fieldName);
            Method getter = getFirstMethod(obj.getClass(), getterName);
            getter.setAccessible(true);
            Object result = getter.invoke(obj);
            assert (trueVal == null && result == null) || trueVal.equals(result) : String.format("Getter '%s.%s()' returns different value from backing field", obj.getClass().getName(), getterName);
        } catch (Exception ex) {
            assert false : String.format("Getter '%s.%s()' does not behave as expected: %s", obj.getClass().getName(), getterName, ex.toString());
        }
    }

    public void assertModifiersOn(Class c, String memberName, String modifiers) {
        try {
            String[] modifierStrings = modifiers.split(" ");
            int searchMods = 0x0;
            for (int i = 0; i < modifierStrings.length; i++) {
                searchMods |= modifierFromString(modifierStrings[i]);
            }
            
            if (hasMethod(c, memberName)) {
                assert (getFirstMethod(c, memberName).getModifiers() & searchMods) == searchMods
                        : String.format("'%s.%s()' does not have modifiers '%s'", c.getName(), memberName, modifiers);
            } else if (hasField(c, memberName)) {
                assert (getField(c, memberName).getModifiers() & searchMods) == searchMods
                        : String.format("'%s.%s' does not have modifiers '%s'", c.getName(), memberName, modifiers);
            } else {
                assert false : String.format("Cannot find '%s.%s'", c.getName(), memberName);
            }
        } catch (Exception ex) {
            assert false : ex.toString();
        }
    }

    public void assertModifiersOnMethod(Class c, String methodName, String modifiers) {
        try {
            String[] modifierStrings = modifiers.split(" ");
            int searchMods = 0x0;
            for (int i = 0; i < modifierStrings.length; i++) {
                searchMods |= modifierFromString(modifierStrings[i]);
            }
            
            if (hasMethod(c, methodName)) {
                assert (getFirstMethod(c, methodName).getModifiers() & searchMods) == searchMods
                        : String.format("'%s.%s()' does not have modifiers '%s'", c.getName(), methodName, modifiers);
            } else {
                assert false : String.format("Cannot find '%s.%s'", c.getName(), methodName);
            }
        } catch (Exception ex) {
            assert false : ex.toString();
        }
    }

    public void assertModifiersOnField(Class c, String fieldName, String modifiers) {
        try {
            String[] modifierStrings = modifiers.split(" ");
            int searchMods = 0x0;
            for (int i = 0; i < modifierStrings.length; i++) {
                searchMods |= modifierFromString(modifierStrings[i]);
            }
            
            if (hasField(c, fieldName)) {
                assert (getField(c, fieldName).getModifiers() & searchMods) == searchMods
                        : String.format("'%s.%s' does not have modifiers '%s'", c.getName(), fieldName, modifiers);
            } else {
                assert false : String.format("Cannot find '%s.%s'", c.getName(), fieldName);
            }
        } catch (Exception ex) {
            assert false : ex.toString();
        }
    }

    public void assertTypeOf(Class c, String memberName, Class type) {
        try {
            if (hasMethod(c, memberName)) {
                assert (getFirstMethod(c, memberName).getReturnType().equals(type))
                        : String.format("'%s.%s()' does not return type '%s'", c.getName(), memberName, type.getName());
            } else if (hasField(c, memberName)) {
                assert (getField(c, memberName).getType().equals(type))
                        : String.format("'%s.%s' is not of type '%s'", c.getName(), memberName, type.getName());
            } else {
                assert false : String.format("Cannot find '%s.%s'", c.getName(), memberName);
            }
        } catch (Exception ex) {
            assert false : ex.toString();
        }
    }
    
    public void assertParamsOn(Class c, String methodName, Class[] params) {
        try {
            if (hasMethod(c, methodName)) {
                Method mthd = getFirstMethod(c, methodName);
                mthd.setAccessible(true);
                assert mthd.getParameterTypes().length == params.length;
                for (int i = 0; i < params.length; ++i) assert mthd.getParameterTypes()[i].equals(params[i]);
            } else {
                assert false : String.format("Method '%s.%s()' not found");
            }
        } catch (Exception ex) {
            assert false : ex.toString();
        }
    }

    public void assertFieldSignature(Class c, String modifiers, Class type, String fieldName) {
        assertFieldExists(c, fieldName);
        assertModifiersOnField(c, fieldName, modifiers);
        assertTypeOf(c, fieldName, type);
    }

    public void assertMethodSignature(Class c, String modifiers, Class returnType, String methodName) {
        assertMethodSignature(c, modifiers, returnType, methodName, new Class[]{});
    }

    public void assertMethodSignature(Class c, String modifiers, Class returnType, String methodName, Class ... params) {
        assertMethodExists(c, methodName);
        assertModifiersOnMethod(c, methodName, modifiers);
        assertTypeOf(c, methodName, returnType);
        assertParamsOn(c, methodName, params);
    }

    public void assertGetterBehavior(Object obj, String getterName, String fieldName) {
        assertPure(obj, getterName, new String[]{fieldName});
        assertGetter(obj, getterName, fieldName);
    }

    public <T extends Exception> void assertExceptionThrown(Runnable op, Class<T> c, String opName) {
        boolean caught = false;
        try {
            op.run();
        } catch (Exception ex) {
            if(ex.getClass().equals(c)) caught = true;
        } finally {
            assert caught : String.format("Required operation '%s' did not throw exception %s", opName, c.getName());
        }
    }

    public Object getPrivateFieldValue(Object objFrom, String fieldName) {
        try {
            Field f = getField(objFrom.getClass(), fieldName);
            f.setAccessible(true);
            return f.get(objFrom);
        } catch (IllegalAccessException | NullPointerException ex) {
            return null;
        }
    }
    
    public void setPrivateFieldValue(Object objFrom, String fieldName, Object newValue) {
        try {
            Field f = getField(objFrom.getClass(), fieldName);
            f.setAccessible(true);
            f.set(objFrom, newValue);
        } catch (IllegalAccessException ex) {
            return;
        }
    }

    private static int modifierFromString(String s) {
        int m = 0x0;
        if ("public".equals(s))         m |= Modifier.PUBLIC;
        else if ("protected".equals(s)) m |= Modifier.PROTECTED;
        else if ("private".equals(s))   m |= Modifier.PRIVATE;
        else if ("static".equals(s))    m |= Modifier.STATIC;
        else if ("final".equals(s))     m |= Modifier.FINAL;
        else if ("transient".equals(s)) m |= Modifier.TRANSIENT;
        else if ("volatile".equals(s))  m |= Modifier.VOLATILE;
        return m;
    }

    protected Method getFirstMethod(Class c, String methodName) {
        return Arrays.asList(c.getDeclaredMethods()).stream().filter(m -> m.getName().equals(methodName)).findFirst().orElse(null);
    }

    protected boolean hasMethod(Class c, String methodName) {
        return Arrays.asList(c.getDeclaredMethods()).stream().filter(m -> m.getName().equals(methodName)).findFirst().isPresent();
    }

    public static Field getField(Class c, String fieldName) {
        return getAllFields(c).stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
    }

    protected boolean hasField(Class c, String fieldName) {
        return getAllFields(c).stream().filter(m -> m.getName().equals(fieldName)).findFirst().isPresent();
    }

    public static java.util.List<Field> getAllFields(Class<?> type) {
        java.util.List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
            fields.addAll(Arrays.stream(c.getInterfaces())
                    .map(ic -> Arrays.asList(ic.getDeclaredFields()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
        }
        return fields;
    }
}