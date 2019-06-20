package net.meteorr.dev.meteorrcomett.server.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author RedLux
 */
public class ThreadsUtils {
    private static ThreadGroup rootThreadGroup = null;

    public static ThreadGroup getRootThreadGroup() {
        if (rootThreadGroup != null)
            return rootThreadGroup;
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentThreadGroup;
        while ((parentThreadGroup = threadGroup.getParent()) != null)
            threadGroup = parentThreadGroup;
        rootThreadGroup = threadGroup;
        return threadGroup;
    }

    public static List<Thread> getGroupThreads(final ThreadGroup group) {
        if (group == null)
            throw new NullPointerException("Null thread group");
        int nAlloc = group.activeCount();
        int n = 0;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[nAlloc];
            n = group.enumerate(threads);
        } while (n == nAlloc);
        return Arrays.asList(Arrays.copyOf(threads, n));
    }
}
