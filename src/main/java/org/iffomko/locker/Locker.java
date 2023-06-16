package org.iffomko.locker;

/**
 * Семафор, который имеет два состояния.
 * У него есть два метода: один блокирует, а второй разблокирует замок.
 */
public class Locker {
    private final Object synchronizedObject;
    private volatile boolean isLocked;

    public Locker() {
        synchronizedObject = new Object();
        isLocked = false;
    }

    /**
     * Блокирует замок для потока
     */
    public void obtainLocker() {
        while (true) {
            synchronized (synchronizedObject) {
                if (!isLocked) {
                    isLocked = true;
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
     * Освобождает замок для потока
     */
    public void releaseLocker() {
        synchronized (synchronizedObject) {
            isLocked = false;
            synchronizedObject.notify();
        }
    }
}
