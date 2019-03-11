package com.monk.rxjava2.imetate2;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author monk
 * @date 2019-03-01
 */
public enum DisposableHelper implements Disposable {
    /*** */
    DISPOSABLE;

    public static boolean isDisposed(Disposable disposable) {
        return disposable == DISPOSABLE;
    }

    public static boolean dispose(AtomicReference<Disposable> disposableAtomicReference) {
        Disposable current = disposableAtomicReference.get();
        Disposable d = DISPOSABLE;
        if (current != d) {
            current = disposableAtomicReference.getAndSet(d);
            if (current != d) {
                if (current != null) {
                    current.dispose();
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isDisposed() {
        return false;
    }

    @Override
    public void dispose() {

    }
}
