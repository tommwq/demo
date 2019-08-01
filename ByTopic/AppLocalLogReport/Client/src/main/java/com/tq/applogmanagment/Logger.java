package com.tq.applogmanagement;

import java.util.stream.Stream;
import java.util.stream.Collectors;


class Logger {

    // TODO mutex
    private static long sequence = 1;

    public static enum Level {
        Debug;
    }
    
    public long enter(Object... parameters) {

        String fileName = "unknown";
        int lineNumber = -1;
        String moduleName = "unknown";
        String methodName = "unknown";
        String className = "unknown";
        Object result = null;
        
        Throwable throwable = new Throwable();
        StackTraceElement[] stacks = throwable.getStackTrace();
        
        if (stacks.length > 1) {
            fileName = stacks[1].getFileName();
            lineNumber = stacks[1].getLineNumber();
            methodName = stacks[1].getMethodName();
            className = stacks[1].getClassName();
        }

        long token = sequence++;
        record(token,
               Level.Debug.ordinal(),
               System.currentTimeMillis(),
               moduleName,
               fileName,
               lineNumber,
               className,
               methodName,
               stringify(parameters),
               stringify(result));

        return token;
    }

    public void leave(long token, Object aResult) {
        String fileName = "unknown";
        int lineNumber = -1;
        String className = "unknown";
        String methodName = "unknown";
        String moduleName = "unknown";
        Object result = aResult;
        
        Throwable throwable = new Throwable();
        StackTraceElement[] stacks = throwable.getStackTrace();
        
        if (stacks.length > 1) {
            fileName = stacks[1].getFileName();
            lineNumber = stacks[1].getLineNumber();
            methodName = stacks[1].getMethodName();
            className = stacks[1].getClassName();
        }

        record(token,
               Level.Debug.ordinal(),
               System.currentTimeMillis(),
               moduleName,
               fileName,
               lineNumber,
               className,
               methodName,
               "",
               stringify(result));

    }

    private static String stringify(Object nullable) {
        if (nullable == null) {
            return "";
        }

        if (nullable.getClass().isArray()) {
            Object[] array = (Object[]) nullable;
            return stringify(array);
        }

        return nullable.toString();
    }

    private static String stringify(Object[] nullable) {
        if (nullable == null) {
            return "";
        }
        
        return String.join(",", Stream.of(nullable)
                           .map(Logger::stringify)
                           .collect(Collectors.toList()));
    }

    private void record(long sequence,
                        int level,
                        long localTime,
                        String packageName,
                        String fileName,
                        int lineNumber,
                        String className,
                        String methodName,
                        String parameterValues,
                        String result) {
        
        
        String message = String.join("\n", Stream.of("sequence",
                                                     sequence,
                                                     "level",
                                                     level,
                                                     "time",
                                                     localTime,
                                                     "package",
                                                     packageName,
                                                     "file",
                                                     fileName,
                                                     "line",
                                                     lineNumber,
                                                     "class",
                                                     className,
                                                     "method",
                                                     methodName,
                                                     "input",
                                                     parameterValues,
                                                     "output",
                                                     result)
                                     .map(Logger::stringify)
                                     .collect(Collectors.toList()));
        System.out.println(message);
    }
}
