import java.lang.reflect.*;
import java.util.*;


public class Inspector{
    private HashSet<Integer> uniqueObjectInspectionHash;
    private Inspector(){
        this.uniqueObjectInspectionHash = new HashSet<Integer>();
    }
    public void inspect(Object object, boolean recurseFlag) {
       HashSet<Integer> uniqueObjectInspectionHash = this.getUniqueObjectInspectionHash();
       uniqueObjectInspectionHash.add(object.hashCode());

       try {
           Class classObj = object.getClass();
           System.out.println("Declaring class: " + object);

           Class superClassObj = classObj.getSuperclass();
           System.out.println("Immediate SuperClass: " + superClassObj.getName());

           Class[] interfaceObjs = classObj.getInterfaces();
           System.out.print("Interfaces: "); printClassObjects(interfaceObjs); System.out.println();


           Method[] methodObj = classObj.getDeclaredMethods();
           methodInspector(methodObj);


           Constructor[] constructorObjs = classObj.getConstructors();
           constructorInspector(constructorObjs);

           Field[] fieldObjs = classObj.getDeclaredFields();

       }catch(Exception exception){
           exception.printStackTrace();
       }
   }
   public void fieldInspector(Field[] fieldObjs, Objects object, boolean recurseFlag){
       for (Field field : fieldObjs) {
           try{
               Class fieldType = field.getType();
               if (fieldType.isArray()){
                   System.out.println("Field: " + field.getName() + "is an array");


                   }
               String modifiers = Modifier.toString(field.getModifiers());
           }catch (Exception exception){
               exception.printStackTrace();
           }


       }
   }
   private HashSet<Integer> getUniqueObjectInspectionHash(){return this.uniqueObjectInspectionHash;}

       private void methodInspector(Method[] methodObjs){
        System.out.println("\n-------------" +
                "Inspecting Methods-------------\n");
           for (Method method : methodObjs){
               Class[] allMethodExceptionTypes = method.getExceptionTypes();
               Class[] allMethodParameterTypes = method.getParameterTypes();
               Class methodReturnType = method.getReturnType();
               String modifiers = Modifier.toString(method.getModifiers());


               System.out.println("-----------------\nMethod: " + method.getName());
               System.out.println("Modifier: " + modifiers);
               System.out.println("Return Type: " + methodReturnType.getName());
               System.out.print("Parameter Types(s): ");
               printClassObjects(allMethodParameterTypes);



               if (allMethodExceptionTypes.length > 0) {
                   System.out.print("Exceptions: ");
                   printClassObjects(allMethodExceptionTypes);
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

       private void constructorInspector(Constructor[] constructorObjs){
           System.out.println("\n-------------" +
                   "Inspecting Constructors-------------\n");
           for (Constructor constructor : constructorObjs) {
               Class[] allConstructorParameterTypes = constructor.getParameterTypes();
               String modifiers = Modifier.toString(constructor.getModifiers());

               System.out.println("-----------------\nconstructor: " + constructor.getName());
               System.out.println("modifiers: " + modifiers);
               System.out.print("Parameter Type(s): ");
               printClassObjects(allConstructorParameterTypes);
               System.out.println("\n");
           }
       }



}



