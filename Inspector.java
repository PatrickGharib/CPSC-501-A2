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
           Class superClassObj = classObj.getSuperclass();
           Class[] interfaceObjs = classObj.getInterfaces();
           //Constructor[] constructorObjs = classObj.getConstructors();
           Field[] fieldObjs = classObj.getDeclaredFields();


           classInspector(classObj, object, fieldObjs);
           superClassInspector(superClassObj, object);
           for(Class c : interfaceObjs){
               interfaceInspector(c, object);
           }

          // constructorInspector(constructorObjs);

           if(recurseFlag){
               System.out.println("-------------RECURSE ON FIELD: START-------------");
               fieldInspector(fieldObjs, object, recurseFlag);
               System.out.println("-------------RECURSE ON FIELD: END-------------");
           }

       }catch(Exception exception){
           exception.printStackTrace();
           return;
       }
   }
   public void classInspector(Class classObj, Object object, Field[] declaredFields){
       Class superClassObj = classObj.getSuperclass();
       Class[] interfaceObjs = classObj.getInterfaces();
       Method[] methodObj = classObj.getDeclaredMethods();
       Constructor[] constructorObjs = classObj.getConstructors();

       System.out.println("Declaring class: " + object);
       System.out.println("Immediate SuperClass: " + superClassObj.getName());
       System.out.print("Interfaces: "); printClassObjects(interfaceObjs); System.out.println();

       methodInspector(methodObj);
       constructorInspector(constructorObjs);
       fieldValuesInspector(declaredFields,object);

       if (classObj.isArray()) {
           Object[] arrayContents;
           Class TypeOfArray = classObj.getComponentType();
           System.out.println("Component Type: " + TypeOfArray.getName());
           if (object instanceof Object[]) {
               arrayContents = (Object[]) object;
           } else {
               arrayContents = new Object[Array.getLength(object)];
               for (int i = 0; i < Array.getLength(object); i++) {
                   arrayContents[i] = Array.get(object, i);
               }

           }
           printArrayContents(arrayContents);
       }

   }
   public void fieldValuesInspector(Field[] declaredFields, Object object){
        for(Field field:declaredFields){
            try{
                field.setAccessible(true);
                String typeString = null;
                String valueOfFieldString = null;
                Class fieldType = field.getType();
                String modifiers = Modifier.toString(field.getModifiers());
                Object valueOfField = field.get(object);
                System.out.println("-----------------\nField: " + field.getName());
                System.out.println("Modifier: " + modifiers);
                if(fieldType.isArray()){
                    Class typeOfArray = fieldType.getComponentType();
                    typeString = typeOfArray.getName() + " [" + Array.getLength(valueOfField)+"]";

                }else{
                    typeString = fieldType.toString();
                    if(fieldType.isPrimitive()){
                        valueOfFieldString = valueOfField.toString();
                        }
                    else if(valueOfField!= null){valueOfFieldString = valueOfField.getClass().getName()+" "+valueOfField.hashCode();}
                }
                System.out.println("Type: " + typeString);

                if(fieldType.isArray()){
                    Object[] arrayContents;

                    if (valueOfField instanceof Object[]) {
                        arrayContents = (Object[]) valueOfField;
                    } else {
                        arrayContents = new Object[Array.getLength(valueOfField)];
                        for (int i = 0; i < Array.getLength(valueOfField); i++) {
                            arrayContents[i] = Array.get(valueOfField, i);
                        }

                        System.out.println("Array Contents: ");
                        printArrayContents(arrayContents);
                    }
                }else{System.out.println("  Value: " + valueOfFieldString);}
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
   }
   public void printArrayContents(Object[] arrayContents){
       for(int i =0; i < arrayContents.length; i++){
           Object element = arrayContents[i];

           String elementOutput = null;
           if(element != null)
                   //check if wrapper class instance
                   if(element instanceof Character ||
                           element instanceof Integer ||
                           element instanceof Float ||
                           element instanceof Long ||
                           element instanceof Short ||
                           element instanceof Double ||
                           element instanceof Byte ||
                           element instanceof Boolean)
                       elementOutput += String.valueOf(element);
                   else {
                       Class eClass = element.getClass();
                       elementOutput = eClass.getName() + " " + element.hashCode();
                   }

           System.out.println("Element " + i + ": " + elementOutput);

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
                   Object[] arrayContents;

                   if (fieldValue instanceof Object[]) {
                       arrayContents = (Object[]) fieldValue;
                   } else {
                       arrayContents = new Object[Array.getLength(fieldValue)];
                       for (int i = 0; i < Array.getLength(fieldValue); i++) {
                           arrayContents[i] = Array.get(fieldValue, i);
                       }

                   }
                   Class arrayComponentType = fieldType.getComponentType();
                   System.out.println("Field: " + field.getName() + "is an array");
                   if (!arrayComponentType.isPrimitive()) {
                       System.out.println("-------------\nComponent Type: "+arrayComponentType.getTypeName());
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
               System.out.println();


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
    private void interfaceInspector(Class interfaceClassToInspect, Object object){
        Class superClassOfInterface = null;

        System.out.printf("\n\n-----------------INTERFACE %s INSPECTION START-----------------\n\n", interfaceClassToInspect.getName());
        if (interfaceClassToInspect.getSuperclass() != null){
            superClassOfInterface = interfaceClassToInspect.getSuperclass();
            System.out.println("SuperClass: " + superClassOfInterface.getName());
        }
        Method[] methodsOfInterFace = interfaceClassToInspect.getDeclaredMethods();
        Field[] fieldsOfInterface = interfaceClassToInspect.getDeclaredFields();

        methodInspector(methodsOfInterFace);
        fieldValuesInspector(fieldsOfInterface, object);

        if(superClassOfInterface != null){
            System.out.println("\nSuperClass of " + interfaceClassToInspect.getName() +" is "  + superClassOfInterface.getName() );
            superClassInspector(superClassOfInterface,object);
        }else{
            System.out.println("\n no superClass to the Interface " + interfaceClassToInspect.getName());

        }
        System.out.printf("\n\n-----------------INTERFACE %s INSPECTION END-----------------\n\n", interfaceClassToInspect.getName());

    }
    private void superClassInspector(Class superClassToInspect, Object object){
        Class innerSuperClass = null;
        Class[] innerSuperInterface = null;

        System.out.printf("\n\n-----------------SUPERCLASS %s INSPECTION START-----------------\n\n", superClassToInspect.getName());
        if(superClassToInspect.getSuperclass() != null){
          innerSuperClass = superClassToInspect.getSuperclass();
          System.out.println("SuperClass: " + innerSuperClass.getName());
        }
        if(superClassToInspect.getInterfaces() != null){
            innerSuperInterface = superClassToInspect.getInterfaces();
            System.out.print("SuperInterfaces: ");
            printClassObjects(innerSuperInterface);
            System.out.println();
        }
        Constructor[] innerSuperConstructors = superClassToInspect.getConstructors();
        Method[] innerSuperMethods = superClassToInspect.getDeclaredMethods();
        Field[] innerSuperFields = superClassToInspect.getDeclaredFields();

        constructorInspector(innerSuperConstructors);
        methodInspector(innerSuperMethods);
        fieldValuesInspector(innerSuperFields, object);

        if(innerSuperClass != null){
            System.out.println("\nSuperClass of " + superClassToInspect.getName() +" is "  + innerSuperClass.getName() );
            superClassInspector(innerSuperClass,object);
        }else{
            System.out.println("\n no other SuperClass of " + superClassToInspect.getName());

        }
        if(innerSuperInterface.length>0) {
            for (int i = 0; i < innerSuperInterface.length; i++) {
                System.out.println("\nSuperInterface of " + superClassToInspect.getName() + " is " + innerSuperInterface[i].getName());
                interfaceInspector(innerSuperClass, object);
            }
        } else{
                System.out.println("\n no SuperInterface of " + superClassToInspect.getName());
            }System.out.printf("\n\n-----------------SUPERCLASS %s INSPECTION END-----------------\n\n", superClassToInspect.getName());



    }


}



