import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;


public class InspectorTest {


    @Test
    public void testInspectorObject() {
        assertNotNull(new Inspector());

        Inspector inspector = new Inspector();
        Class inspectorClass = inspector.getClass();
        assertNotNull(inspectorClass);
    }

    @Test
    public void inspectTest() {
        Inspector inspector = new Inspector();

        try {
            Object object = new ClassA();
            assertFalse(inspector.duplicateInspectionCheck(object));
            inspector.inspect(object, false);

            HashSet<Integer> objectHash = inspector.getUniqueObjectInspectionHash();
            assertEquals(1, objectHash.size());
            assertTrue(inspector.duplicateInspectionCheck(object));

            Object obj2 = new ClassA(12);
            assertFalse(inspector.duplicateInspectionCheck(obj2));
            inspector.inspect(obj2, false);
            assertEquals(2, objectHash.size());
            assertTrue(inspector.duplicateInspectionCheck(obj2));

            Object obj3 = new ClassB();
            assertFalse(inspector.duplicateInspectionCheck(obj3));
            inspector.inspect(obj3, false);
            assertEquals(3, objectHash.size());
            assertTrue(inspector.duplicateInspectionCheck(obj3));
            //classB should recurse twice, but HashSet should already have classB hashcode
            inspector.inspect(obj3, true);
            assertEquals(5, objectHash.size());

            Object obj4 = new ClassD();
            assertFalse(inspector.duplicateInspectionCheck(obj4));
            inspector.inspect(obj4, false);
            assertEquals(6, objectHash.size());
            assertTrue(inspector.duplicateInspectionCheck(obj4));

            Object obj5 = new ClassB[10];
            assertFalse(inspector.duplicateInspectionCheck(obj5));
            inspector.inspect(obj5, false);
            assertTrue(inspector.duplicateInspectionCheck(obj5));

            String stringTesting = "THIS IS NONSENSE!";
            assertFalse(inspector.duplicateInspectionCheck(stringTesting));
            inspector.inspect(stringTesting, true);
            assertTrue(inspector.duplicateInspectionCheck(stringTesting));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}