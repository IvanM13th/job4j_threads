package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * Аннотация @ThreadSafe говорит о том,
 * что класс можно использовать в многопоточном режиме
 * @param <T>
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    /**
     * Аннотация @GuardedBy выставлдена над общим ресурсом,
     * в данном случае - коллекция list.
     * Параметр "this" указывает на параметр,
     * по которому будет происходить синхронизация
     */
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private synchronized List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}