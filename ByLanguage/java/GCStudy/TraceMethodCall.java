public class TraceMethodCall {
    public static void main(String... args) {
        Runtime runtime = Runtime.getRuntime();
        runtime.traceMethodCalls(true);

        foo();
    }

    public static void foo() {
        System.out.println("ok");
    }
}
