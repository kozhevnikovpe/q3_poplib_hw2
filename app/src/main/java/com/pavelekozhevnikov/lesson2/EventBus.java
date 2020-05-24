package com.pavelekozhevnikov.lesson2;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
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
        Observable<Long> observable = Observable.interval(3, TimeUnit.SECONDS);
        observable.subscribe(eventBus);
    }

    public void send(Object o) {
        eventBus.onNext(o);
    }

    public PublishSubject<Object> toPublishSubject() {
        return eventBus;
    }

    public Observer<Object> toObserver() {
        return eventBus;
    }
}
