package org.fog.offload;

import java.util.Collection;

public interface Subject<TEvent extends Event> {

    boolean add(Listener<TEvent> listener);

    boolean remove(Listener<TEvent> listener);

    void trigger(TEvent event);

    default void trigger(TEvent event, Collection<Listener<TEvent>> listeners) {
        for (Listener<TEvent> listener : listeners) {
            listener.update(event);
        }
    }
}