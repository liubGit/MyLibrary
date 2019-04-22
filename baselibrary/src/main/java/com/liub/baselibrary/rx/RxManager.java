package com.liub.baselibrary.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Create by liub on 2019/4/4
 * Describe:observer 与subscribers
 */
public class RxManager {
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public void addObserver(DisposableObserver observer) {
        if (observer != null) {
            compositeDisposable.add(observer);
        }
    }

    public void clear() {
        compositeDisposable.dispose();
    }

}
