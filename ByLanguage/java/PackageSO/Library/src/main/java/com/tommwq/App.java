package com.tommwq;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.attribute.PosixFilePermission.*;

class OperatingSystem {
    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return getOperatingSystem().toLowerCase().contains("windows");
    }

    public static boolean isLinux() {
        return getOperatingSystem().toLowerCase().contains("linux");
    }

    public static boolean isMac() {
        return getOperatingSystem().toLowerCase().contains("mac");
    }

    public static String getArch() {
        return System.getProperty("os.arch");
    }

    public static boolean is32bit() {
        return getArch().contains("86");
    }

    public static boolean is64bit() {
        return getArch().contains("64");
    }

    public static String getShortName() {
        if (isWindows()) {
            return "windows";
        } else if (isLinux()) {
            return "linux";
        } else if (isMac()) {
            return "mac";
        }
        return "";
    }
}

public class App {

    public static <T> T[] append(T[] array, T element) {
        List<T> list = new ArrayList<>(Arrays.asList(array));
        list.add(element);
        return list.toArray(array);
    }

    public static String getDynamicLibraryPath() {
        return String.format("%s%d",
                OperatingSystem.getShortName(),
                OperatingSystem.is32bit() ? 32 : 64);
    }

    public static String getDynamicLibraryFileName(String dynamicLibraryFile) {
        return getDynamicLibraryPath() + "/" + dynamicLibraryFile;
    }

    private static App instance = new App();
    private boolean initialized;

    public static App get() {
        return instance;
    }

    public void init() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        if (initialized) {
            return;
        }

        _init();

        initialized = true;
    }

    private void _init() throws IOException, NoSuchFieldException, IllegalAccessException {
        File tempDir = Files.createTempDirectory("tmp").toFile();
        tempDir.createNewFile();
        tempDir.deleteOnExit();
        File nativeDll = new File(tempDir, "native.dll");

        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(getDynamicLibraryFileName("native.dll"));
        Files.copy(inputStream, nativeDll.toPath());

        Field field = ClassLoader.class.getDeclaredField("usr_paths");
        boolean originalAccessible = field.isAccessible();
        field.setAccessible(true);
        String[] usrPaths = (String[]) field.get(null);
        String[] newUsrPaths = append(usrPaths, tempDir.getAbsolutePath());
        field.set(null, newUsrPaths);
        field.setAccessible(originalAccessible);

        System.loadLibrary("native");
    }

    public native void hello();
}
