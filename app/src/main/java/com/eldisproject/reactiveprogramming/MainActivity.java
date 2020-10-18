package com.eldisproject.reactiveprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BasicObservable basicObservable = new BasicObservable();
    private Disposable disposable; //dispose subsciption of Observer to Observable and avoid memory leak

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Observable
        Observable<String> nameObservable = basicObservable.getObservable();

        //Observer
        Observer<String> nameObserver = getNameObserver();

        //Subsrciption
        nameObservable.subscribeOn(Schedulers.io()) //tell observable to run on bg thread
                .observeOn(AndroidSchedulers.mainThread()) //tell observer to receive data on UI thread
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toUpperCase().startsWith("L");
                    }
                })
                .subscribe(nameObserver);
    }

    private Observer<String> getNameObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + d);
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();

        if (disposable.isDisposed()) {
            Log.d(TAG, "onDestroy: disposable make unsub on Observer to observable" );
        }
    }
}