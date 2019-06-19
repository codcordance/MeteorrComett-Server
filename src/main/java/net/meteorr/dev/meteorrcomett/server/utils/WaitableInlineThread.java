package net.meteorr.dev.meteorrcomett.server.utils;

import net.meteorr.dev.meteorrcomett.server.utils.annotations.MeteorrComettWaitableThread;

/**
 * @author RedLux
 */
@MeteorrComettWaitableThread(timeout = 10000L)
public class WaitableInlineThread extends Thread {
    public WaitableInlineThread(ThreadGroup threadGroup, String testRun) {
        super(threadGroup, testRun);
    }
}
