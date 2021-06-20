package org.fog.offload;

public interface Listener<TEvent extends Event> {
    void updateEvent(TEvent event);
}