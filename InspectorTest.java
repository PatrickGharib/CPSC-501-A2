import org.junit.Test;
import static org.junit.Assert.*;

public class InspectorTest {

    @Test
    public void getDeclaringClassTest() {
        Inspector inspector = new Inspector();
        String testString = "hello";
        Class c = "  ".getClass();
        assertEquals(c, inspector.getDeclaringClass(testString));
    }
    @Test
    public void testInspectorObject(){
        assertNotNull(new Inspector());

        Inspector inspector = new Inspector();
        Class inspectorClass = inspector.getClass();
        assertNotNull(inspectorClass);
    }
}