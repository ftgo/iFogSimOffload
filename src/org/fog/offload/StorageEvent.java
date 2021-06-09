package org.fog.offload;

import org.fog.entities.Tuple;

public class StorageEvent extends Event {

    public enum Type {SAVE, DELETE}

    public enum Status {OK, FAILED, HIT}

    private final StorageState storageState;

    private final Tuple tuple;

    private final Type type;

    private final Status status;

    public StorageEvent(StorageState storageState, Tuple tuple, Type type, Status status) {
        this.storageState = storageState;
        this.tuple = tuple;
        this.type = type;
        this.status = status;
    }

    public StorageState getStorageState() {
        return storageState;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }
}
