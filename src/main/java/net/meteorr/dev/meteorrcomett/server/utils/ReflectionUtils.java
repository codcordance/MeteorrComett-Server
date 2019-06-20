package net.meteorr.dev.meteorrcomett.server.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author RedLux
 */
public class ReflectionUtils {
    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        File runner = new File(ReflectionUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        String pathrunner = runner.getPath();
        if (pathrunner.endsWith(".jar")) return getJarClasses(runner, packageName).toArray(new Class[0]);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (Object directory : dirs) classes.addAll(getClasses((File) directory, packageName));
        return classes.toArray(new Class[0]);
    }

    private static Set<Class<?>> getJarClasses(File jarFile, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if(name.startsWith(packageName) && name.endsWith(".class"))
                    classes.add(Class.forName(name.substring(0, name.length() - 6)));
            }
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static List<Class<?>> getClasses(File directory, String packageName) throws ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) return classes;
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(getClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
        }
        return classes;
    }
}
