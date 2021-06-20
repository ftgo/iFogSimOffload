package org.fog.offload;

import java.util.Collection;

public interface Subject<TEvent extends Event> {

    boolean addListener(Listener<TEvent> listener);

    boolean removeListener(Listener<TEvent> listener);

    void notifyEvent(TEvent event);

    default void notifyEvent(TEvent event, Collection<Listener<TEvent>> listeners) {
        for (Listener<TEvent> listener : listeners) {
            listener.updateEvent(event);
        }
    }
}