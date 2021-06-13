package org.fog.offload;

import com.sun.istack.internal.NotNull;
import org.fog.entities.Tuple;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.Float.compare;
import static org.fog.examples.DataPlacement.*;
import static org.fog.offload.Bits.Tag.COMPRESSION;
import static org.fog.offload.Bits.Tag.CRITICAL;

public class StorageState implements Subject<StorageEvent> {
    private final transient Object lock = new Object();

    private final Set<Listener<StorageEvent>> listeners = new LinkedHashSet<>();

    private final DeviceState deviceState;
    private final long total;
    private final float minThreshold;
    private final float maxThreshold;
    private final float lower;
    private final float upper;

    private long current;

    private boolean offloading;

    public StorageState(DeviceState deviceState, long total) {
        this(deviceState, total, 20.0f, 99.9f);
    }

    public StorageState(DeviceState deviceState, long total, float minThreshold, float maxThreshold) {
        if (compare(minThreshold, 0.1f) < 0 || compare(minThreshold, 99.9f) > 0)
            throw new IllegalArgumentException("Minimum threshold percentage should be between 0.1 and 99.9.");

        if (compare(maxThreshold, 0.1f) < 0 || compare(maxThreshold, 99.9f) > 0)
            throw new IllegalArgumentException("Maximum threshold percentage should be between 0.1 and 99.9.");

        if (minThreshold >= maxThreshold)
            throw new IllegalArgumentException("Minimum threshold should be less than maximum threshold.");


        this.deviceState = deviceState;
        this.total = total;

        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;

        this.lower = this.total * (this.minThreshold) / 100;
        this.upper = this.total * this.maxThreshold / 100;
    }

    @Override
    public boolean add(Listener<StorageEvent> listener) {
        return this.listeners.add(listener);
    }

    @Override
    public boolean remove(Listener<StorageEvent> listener) {
        return this.listeners.add(listener);
    }

    @Override
    public void trigger(StorageEvent event) {
        trigger(event, this.listeners);
    }

    public boolean save(Tuple tuple) {
        return save(tuple, new Bits());
    }

    public boolean save(Tuple tuple, @NotNull Bits bits) {
        synchronized (this.lock) {
            if (this.offloading && !bits.get(CRITICAL))
                return false;

            long size = getSize(tuple);

            if (bits.get(COMPRESSION)) {
                float compression = getCompression();
                size *= (100 - compression) / 100;
            }

            long expected = this.current + size;

            if (compare(expected, this.total) > 0) {
                trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.FAILED));
                return false;
            }

            if (compare(expected, this.upper) > 0) {
                trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.HIT));
            }

            this.current += size;

            trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.OK));
        }
        return true;
    }

    public boolean delete(Tuple tuple) {
        return delete(tuple, new Bits());
    }

    public boolean delete(Tuple tuple, Bits bits) {
        synchronized (this.lock) {
            if (bits.get(CRITICAL))
                return false;

            long size = getSize(tuple);

            if (bits.get(COMPRESSION)) {
                float compression = getCompression();
                size *= (100 - compression) / 100;
            }

            long expected = this.current - size;

            if (compare(expected, 0) < 0) {
                trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.FAILED));
                return false;
            }


            if (compare(expected, this.lower) < 0) {
                trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.HIT));
            }

            this.current -= size;

            trigger(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.OK));
        }
        return true;
    }

    private float getCompression() {
        DeviceType type = DeviceType.of(this.deviceState.getDevice().getName());
        switch (type) {
            case DC:
                return DC_Storage_Compression;
            case RPOP:
                return RPOP_Storage_Compression;
            case LPOP:
                return LPOP_Storage_Compression;
            case HGW:
                return HGW_Storage_Compression;
            default:
                return 0;
        }
    }

    private long getSize(Tuple tuple) {
        long size = tuple.getCloudletFileSize();


        return size;
    }

    public long getCurrent() {
        return this.current;
    }

    public long getTotal() {
        return this.total;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public Set<Listener<StorageEvent>> getListeners() {
        return listeners;
    }

    public float getMinThreshold() {
        return minThreshold;
    }

    public float getMaxThreshold() {
        return maxThreshold;
    }

    public float getLower() {
        return lower;
    }

    public float getUpper() {
        return upper;
    }

    public void setOffloading(boolean offloading) {
        this.offloading = offloading;
    }

    public boolean isOffloading() {
        return offloading;
    }

    @Override
    public String toString() {
        return "StorageState{" +
                "total=" + total +
                ", current=" + current +
                ", lower=" + lower +
                ", upper=" + upper +
                ", minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                ", offloading=" + offloading +
                '}';
    }
}