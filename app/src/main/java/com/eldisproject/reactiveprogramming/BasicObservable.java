package com.eldisproject.reactiveprogramming;

import io.reactivex.Observable;

public class BasicObservable {
    private Observable<String> observable = Observable.fromArray("Eldis", "Chika", "Jinan", "Cinhap", "Oniel", "Fidly", "Lala");

    public Observable<String> getObservable() {
        return observable;
    }
}
