package org.iffomko.lockerForSeveralThreads;

/**
 * Семафор, который может пускать не только один поток, но и больше
 */
public class ModifiedLocker {
    private volatile int totalThreads;
    private final Object synchronizedObject;
    private final int maxThreads;

    /**
     * Максимальное количество потоков, которые могут зайти в критическую секцию
     * @param maxThreads количество потоков
     */
    public ModifiedLocker(int maxThreads) {
        this.maxThreads = maxThreads;
        this.totalThreads = 0;
        this.synchronizedObject = new Object();
    }

    /**
     * Занимаем место в критической секции
     */
    public void obtainLocker() {
        while(true) {
            synchronized(synchronizedObject) {
                if (totalThreads < maxThreads) {
                    totalThreads++;
                    break;
                }

                try {
                    synchronizedObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.interrupted();
                }
            }
        }
    }

    /**
     * Освобождаем место в критической секции
     */
    public void releaseLocker() {
        synchronized(synchronizedObject) {
            totalThreads--;
            synchronizedObject.notify();
        }
    }
}
