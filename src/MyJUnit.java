import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class MyJUnit {

    static TreeSet<Method> t = new TreeSet<>(new Comparator<Method>() {
        @Override
        public int compare(Method o1, Method o2) {
            if (o1 == o2) return 0;

            if (((o1.getAnnotation(BeforeSuite.class) != null) & (o2.getAnnotation(BeforeSuite.class) != null)) ||
                    ((o1.getAnnotation(AfterSuite.class) != null) & (o2.getAnnotation(AfterSuite.class) != null)))
                throw new RuntimeException("BeforeSuite/AfterSuite не в единственном экземляре");

            if (o1.getAnnotation(BeforeSuite.class) != null) return -1; // BeforeSuite всегда первый
            if (o1.getAnnotation(AfterSuite.class) != null) return 1; // AfterSuite всегда последний
            if (o2.getAnnotation(BeforeSuite.class) != null) return 1; // BeforeSuite всегда первый
            if (o2.getAnnotation(AfterSuite.class) != null) return -1; // AfterSuite всегда последний
            if (o2.getAnnotation(Test.class).value() - o1.getAnnotation(Test.class).value() == 0)
                return 1; // разрешаем во множество записывать одинаковые веса
            return o2.getAnnotation(Test.class).value() - o1.getAnnotation(Test.class).value();
        }
    });

    public static void start(Class inClass){

        Method[] methods = inClass.getDeclaredMethods();
        t.addAll(Arrays.asList(methods));

        try {
            for (Method method : t) {
                method.invoke(inClass.newInstance(), null);
            }

        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException" + e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException" + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void start(String className){

        Class inClass = null;
        try {
            inClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        start(inClass);
    }
}
