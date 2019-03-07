import java.lang.reflect.*;
public class Inspector{
   // public void inspect(Object object, Boolean recursive){

   // }
    public static Class getDeclaringClass(Object objectToInspect){
        Class classObject = objectToInspect.getClass();
        return classObject;
        //System.out.println("Declaring Class: " + classObject);

    };
    // public static void getImmediateSuperClass(){};
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