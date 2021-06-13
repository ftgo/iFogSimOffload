package org.fog.offload;

import org.fog.entities.Tuple;

import static java.lang.String.format;

public class StorageEvent extends Event {

    public enum Type {SAVE, DELETE}

    public enum Status {OK, FAILED, HIT}

    private final StorageState storageState;

    private final Tuple tuple;

    private final Bits bits;

    private final Type type;

    private final Status status;

    public StorageEvent(StorageState storageState, Tuple tuple, Type type, Status status) {
        this(storageState, tuple, null, type, status);
    }

    public StorageEvent(StorageState storageState, Tuple tuple, Bits bits, Type type, Status status) {
        this.storageState = storageState;
        this.tuple = tuple;
        this.bits = bits;
        this.type = type;
        this.status = status;
    }

    public StorageState getStorageState() {
        return storageState;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public Bits getBits() {
        return bits;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return format("StorageEvent{status=%s, bits=%s, type=%s, tuple=%s}",
                status, bits, type, Tuples.toString(tuple));
    }
}
