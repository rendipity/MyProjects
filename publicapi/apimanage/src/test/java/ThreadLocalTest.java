public class ThreadLocalTest {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    ThreadLocal<String> withInitialValueThreadLocal = ThreadLocal.withInitial(()->"hello,ThreadLocal");

    public void method1(){
        threadLocal.set("aaa");
        threadLocal.get();
        threadLocal.remove();
    }
}
