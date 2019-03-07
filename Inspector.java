import java.lang.reflect.*;
public class Inspector{
   public void inspect(Object object, Boolean recursive){
       Class classObject = object.getClass();
       Class superClassObject = classObject.getSuperclass();
       Class[] interfaceObjects = classObject.getInterfaces();

       Method methodObjects[] = classObject.getDeclaredMethods();
       for (Method method : methodObjects){
           Class methodExceptionTypes[] = method.getExceptionTypes();
           Class methodParameterTypes[] = method.getParameterTypes();
           Class methodReturnType = method.getReturnType();
           int modifiers = method.getModifiers();
           String toStringModifiers = Modifier.toString(modifiers);
       }

       Constructor constructorObjects[] = classObject.getConstructors();
       for (Constructor constructor : constructorObjects){
           Class constructorParameterTypes[] = constructor.getParameterTypes();
           int modifiers = constructor.getModifiers();
           String toStringModifiers = Modifier.toString(modifiers);
       }

       Field fieldObjects[] = classObject.getDeclaredFields();
       for (Field field : fieldObjects){
           Class fieldType = field.getType();
           int modifiers = field.getModifiers();

       }





   }
    //public static Class getDeclaringClass(Object objectToInspect){
      //  Class classObject = objectToInspect.getClass();
        //return classObject;
        //System.out.println("Declaring Class: " + classObject);

   // };
    //public static void getImmediateSuperClass(){};
    // public static void getImplementedInterfaces(){};
    
    // public static void getDeclaredMethods(){};
    // public static void getMethodThrownExceptions(){};
    // public static void getMethodParameterTypes(){};
    // public static void getMethodReturnTypes(){};
    // public static void getMethodmodifiers(){};
    
    // public static void getDeclaredConstructors(){};
    // public static void getConstructorParamaterTypes(){};
    // public static void getConstructorModifiers(){};

    // public static void getDeclaredFields(){};
    // public static void getFieldType(){};
    // public static void getFieldModifier(){};
   /* public static void main (String [] args){
        Object object = new Object();
        getDeclaringClass(object);

    }*/
}