
public class TestClass {

    @BeforeSuite
    public static void beforePrint(){
        System.out.println("Перед всеми");
    }

    @AfterSuite
    public static void afterPrint(){
        System.out.println("После всех");
    }

    @Test(value = 10)
    public void print1(){
        System.out.println("метод print1");
    }

    @Test(value = 9)
    public void print2(){
        System.out.println("метод print2");
    }

    @Test(value = 4)
    public void print3(){
        System.out.println("метод print3");
    }

    @Test(value = 7)
    public void print4(){
        System.out.println("метод print4");
    }

    @Test
    public void print5(){
        System.out.println("метод print5");
    }

}
