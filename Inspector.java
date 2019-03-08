import java.lang.reflect.*;
import java.util.*;


public class Inspector{
    private HashSet<Integer> uniqueObjectInspectionHash;
    public Inspector(){
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
           fieldInspector(fieldObjs, object, recurseFlag);

       }catch(Exception exception){
           exception.printStackTrace();
       }
   }
   public void fieldInspector(Field[] fieldObjs, Object object, boolean recurseFlag){
       System.out.println("\n-------------" +
               "Inspecting Fields-------------\n");
       for (Field field : fieldObjs) {
           try {
               Class fieldType = field.getType();
               if (fieldType.isArray()) {
                   Object fieldValue = field.get(object);
                   int lengthOfArray = Array.getLength(fieldValue);
                   Object[] arrayContents;

                   if (object instanceof Object[]) {
                       arrayContents = (Object[]) object;
                   } else {
                       arrayContents = new Object[Array.getLength(object)];
                       for (int i = 0; i < Array.getLength(object); i++) {
                           arrayContents[i] = Array.get(object, i);
                       }

                   }
                   Class arrayComponentType = fieldType.getComponentType();
                   System.out.println("Field: " + field.getName() + "is an array");
                   if (!arrayComponentType.isPrimitive()) {
                       System.out.print("Component Type: "+arrayComponentType.getTypeName());
                       if (arrayContents.length > 0) {
                           for (Object arrayObject : arrayContents) {
                               if (arrayObject != null) {
                                   if (duplicateInspectionCheck(arrayObject)) {
                                       System.out.println("No need to check duplicate objects.");
                                   }else{inspect(arrayObject, recurseFlag);}
                               }else{System.out.println(" null object found.");}
                           }
                       }else{System.out.println(" Empty Array.");}
                   }else{System.out.println(" Is Primitive");}
               }else if(!fieldType.isPrimitive()){
                   System.out.println("Field: " + field + " is Primitive");
                   //String modifiers = Modifier.toString(field.getModifiers());
                   //System.out.println("Modifiers: " + modifiers);
               }



           }catch (Exception exception){
               exception.printStackTrace();
           }


       }
   }
    public boolean duplicateInspectionCheck(Object object){
        HashSet<Integer> uniqueObjectInspectionHash = this.getUniqueObjectInspectionHash();
        if (uniqueObjectInspectionHash.contains(object.hashCode())){return true;}
        else{return false;}
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
                   if (i != (classObjects.length - 1)) {
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



