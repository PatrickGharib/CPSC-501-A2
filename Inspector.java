import java.lang.reflect.*;
import java.util.*;


public class Inspector{
    private HashSet<Integer> uniqueObjectInspectionHash;
    public Inspector(){
        this.uniqueObjectInspectionHash = new HashSet<Integer>();
    }
    public void inspect(Object object, boolean recurseFlag) {
       HashSet<Integer> ObjectHash = this.getUniqueObjectInspectionHash();
       uniqueObjectInspectionHash.add(object.hashCode());

       try {
           Class classObject = object.getClass();
           System.out.println(object);
           Class superClassObject = classObject.getSuperclass();
           System.out.println(superClassObject);
           Class[] interfaceObjects = classObject.getInterfaces();
           printClassObjects(interfaceObjects);
           Method methodObjects[] = classObject.getDeclaredMethods();
           methodInspector(methodObjects);


           Constructor constructorObjects[] = classObject.getConstructors();
           constructorInspector(constructorObjects);

           Field fieldObjects[] = classObject.getDeclaredFields();
           for (Field field : fieldObjects) {
               Class fieldType = field.getType();
               int modifiers = field.getModifiers();
               String toStringModifiers = Modifier.toString(modifiers);

           }
       }catch(Exception exception){
           exception.printStackTrace();
       }
   }
   public HashSet<Integer> getUniqueObjectInspectionHash(){return this.uniqueObjectInspectionHash;}

       private void methodInspector(Method[] methodObjects){
        System.out.println("\n-------------" +
                "Inspecting Methods-------------\n");
           for (Method method : methodObjects){
               Class[] methodExceptionTypes = method.getExceptionTypes();
               Class[] methodParameterTypes = method.getParameterTypes();
               Class methodReturnType = method.getReturnType();
               int modifiers = method.getModifiers();
               String toStringModifiers = Modifier.toString(modifiers);

               System.out.println("-----------------\nMethod: " + method.getName());
               System.out.println("Modifier: " + toStringModifiers);
               System.out.println("Return Type: " + methodReturnType.getName());
               System.out.print("Parameter Types(s): ");
               printClassObjects(methodParameterTypes);



               if (methodExceptionTypes.length > 0) {
                   System.out.print("Exceptions: ");
                   printClassObjects(methodExceptionTypes);
                   System.out.println();
               }else{System.out.println();}
               System.out.println();
           }
       }
       private void printClassObjects(Class[] classObjects){
           if (classObjects.length > 0) {
               for (int i = 0; i < classObjects.length; i++) {
                   System.out.print(classObjects[i].getName());
                   if (i != classObjects.length - 1) {
                       System.out.print(",");
                   }
               }
           }else{System.out.print("");}
       }

       private void constructorInspector(Constructor constructorObjects[]){
           System.out.println("\n-------------" +
                   "Inspecting Constructors-------------\n");
           for (Constructor constructor : constructorObjects) {
               Class constructorParameterTypes[] = constructor.getParameterTypes();
               int modifiers = constructor.getModifiers();
               String toStringModifiers = Modifier.toString(modifiers);

               System.out.println("-----------------\nconstructor: " + constructor.getName());
               System.out.println("modifiers: " + toStringModifiers);
               System.out.print("Parameter Type(s): ");
               displayClassTypeObjects(constructorParameterTypes);
               System.out.println("\n");
           }
       }
      /* public void fieldInspector(Field[] fieldObjects, Object object, Boolean recurseFlag){
           for (Field field : fieldObjects){
               try{
                   Class fieldType = field.getType();
                   if (fieldType.isArray()){
                       System.out.print("Field: " + field.getName() + "[] ");
                       Class arrayBaseType = fieldType.getComponentType();
                       Object valueOfField = field.get(object);
                       Object elementsOfArray[] = object.getObjectArray(fieldValue);
                       int arrayLength = Array.getLength(valueOfField);
                       if (!arrayBaseType.isPrimitive()){
                           System.out.print(arrayBaseType.getTypeName()+"\n");
                           if (array.length > 0){

                           }
                       }

                   }
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
       }*/

    //needs modification
    private void displayClassTypeObjects(Class[] classTypeObjects){
        if(classTypeObjects.length > 0){
            for(Class c : classTypeObjects){
                System.out.print(c.getName());

                //add a comma if not last element in array
                if(classTypeObjects[classTypeObjects.length -1] != c)
                    System.out.print(", ");
            }
        }
        else{System.out.print("");}
    }


}



