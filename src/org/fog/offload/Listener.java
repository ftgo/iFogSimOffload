package org.fog.offload;

public interface Listener<TEvent extends Event> {
    void update(TEvent event);
}