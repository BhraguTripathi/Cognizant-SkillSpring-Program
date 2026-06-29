package Week_1.DesignPattern;

public class SingletonPatternExample {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        logger1.log("Application started");

        Logger logger2 = Logger.getInstance();
        logger2.log("User logged in");

        System.out.println("Are logger1 and logger2 the same instance? "
                + (logger1 == logger2));
        System.out.println("Total logs recorded (shared state): "
                + logger1.getLogCount());

        someOtherMethod();
    }

    static void someOtherMethod() {
        Logger logger3 = Logger.getInstance();
        logger3.log("Called from another method");
        System.out.println("Total logs now: " + logger3.getLogCount());
    }
}

class Logger {
    private static Logger instance;
    private int logCount = 0;

    private Logger() {
        System.out.println("Logger instance created!");
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        logCount++;
        System.out.println("[LOG #" + logCount + "] " + message);
    }

    public int getLogCount() {
        return logCount;
    }
}