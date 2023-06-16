package org.iffomko.locker;

/**
 * Семафор, который имеет два состояния.
 * У него есть два метода: один блокирует, а второй разблокирует замок.
 */
public class Locker {
    private final Object SYNCHRONIZED_OBJECT;
    private volatile boolean isLocked;

    public Locker() {
        SYNCHRONIZED_OBJECT = new Object();
        isLocked = false;
    }

    /**
     * Блокирует замок для потока
     */
    public void obtainLocker() {
        while (true) {
            synchronized (SYNCHRONIZED_OBJECT) {
                if (!isLocked) {
                    isLocked = true;
                    break;
                }

                try {
                    SYNCHRONIZED_OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Освобождает замок для потока
     */
    public void releaseLocker() {
        synchronized (SYNCHRONIZED_OBJECT) {
            isLocked = false;
            SYNCHRONIZED_OBJECT.notify();
        }
    }
}
