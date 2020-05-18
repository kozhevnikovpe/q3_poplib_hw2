package com.pavelekozhevnikov.lesson2;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private PublishSubject<Object> eventBus = PublishSubject.create();

    public static EventBus getInstance(){
        return instance;
    }

    private EventBus(){
        eventBus = PublishSubject.create();
        //собственное событие
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(eventBus);
    }

    public void send(Object o) {
        eventBus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return eventBus;
    }
}
