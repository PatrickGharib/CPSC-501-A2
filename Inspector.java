import java.lang.reflect.*;
public class Inspector{
   public void inspect(Object object, Boolean recursive) {
       Class classObject = object.getClass();
       Class superClassObject = classObject.getSuperclass();
       Class[] interfaceObjects = classObject.getInterfaces();

       Method methodObjects[] = classObject.getDeclaredMethods();
       methodInspector(methodObjects);


       Constructor constructorObjects[] = classObject.getConstructors();
       for (Constructor constructor : constructorObjects) {
           Class constructorParameterTypes[] = constructor.getParameterTypes();
           int modifiers = constructor.getModifiers();
           String toStringModifiers = Modifier.toString(modifiers);
       }

       Field fieldObjects[] = classObject.getDeclaredFields();
       for (Field field : fieldObjects) {
           Class fieldType = field.getType();
           int modifiers = field.getModifiers();
           String toStringModifiers = Modifier.toString(modifiers);

       }
   }
   /**
    * inspect methods
    * includes inspection of:
    * exception types,parameter and return types,modifiers
    * */
       private void methodInspector(Method[] methodObjects){
           for (Method method : methodObjects){
               Class methodExceptionTypes[] = method.getExceptionTypes();
               Class methodParameterTypes[] = method.getParameterTypes();
               Class methodReturnType = method.getReturnType();
               int modifiers = method.getModifiers();
               String toStringModifiers = Modifier.toString(modifiers);

               System.out.println("method: " + toStringModifiers +
                                   methodReturnType.getName() +
                                   method.getName() +
                                   "(" );
                                   displayClassTypeObjects(methodParameterTypes);
               System.out.println(")");

               if (methodExceptionTypes.length > 0) {
                   System.out.print(" THROWS ");
                   displayClassTypeObjects(methodExceptionTypes);
                   System.out.println();
               }
           }
       }
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
