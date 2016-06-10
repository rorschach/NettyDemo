package me.rorschach.nettydemo;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by lei on 16-6-10.
 */
public class RxBus {

    public static final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public static RxBus getDefault() {
        return BusHolder.INSTANCE;
    }

    private static class BusHolder {
        public static final RxBus INSTANCE = new RxBus();
    }

    public <T extends Event> Observable<T> toObservable(Class<T> t) {
        return bus.ofType(t);
    }

    public void post(Object o) {
        if (bus.hasObservers()) {
            bus.onNext(o);
        }
    }

}
